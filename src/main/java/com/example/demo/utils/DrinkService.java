package com.example.demo.utils;

import com.example.demo.DTO.DrinkDTO;
import com.example.demo.DTO.DrinkIdDTO;
import com.example.demo.DTO.ResDTO;
import com.example.demo.DTOHAV.DrinkDTOCOM;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;
import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service("com.example.demo.utils.DrinkService")
public class DrinkService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public DrinkService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(DrinkDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insertDrink @name=?, @brand=?, @price=?");
        preparedStatement.setString(1,resDTO.getName());
        preparedStatement.setString(2,resDTO.getBrand());
        preparedStatement.setFloat(3,resDTO.getPrice());
        return preparedStatement.execute();
    }
    public boolean update(DrinkDTOCOM drinkDTODTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec updateDrink @id=?, @name=?, @brand=?, @price=?");
        preparedStatement.setInt(1,Integer.valueOf(drinkDTODTO.getId()));
        preparedStatement.setString(2,drinkDTODTO.getName());
        preparedStatement.setString(3,drinkDTODTO.getBrand());
        preparedStatement.setFloat(4,drinkDTODTO.getPrice());
        return preparedStatement.execute();
    }

    public boolean delete(DrinkIdDTO drinkDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec deleteDrink @id=?");
        System.out.println(drinkDTO.getDrinkId());
        preparedStatement.setInt(1,drinkDTO.getDrinkId());
        return preparedStatement.execute();
    }

    public JSONArray getTable(String attribute) throws SQLException {
        Connection dbConnection = DriverManager.getConnection(connectionString);
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("SELECT * from Drink Order By "+attribute);

        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData md = resultSet.getMetaData();
        int numCols = md.getColumnCount();
        List<String> colNames = IntStream.range(0, numCols)
                .mapToObj(i -> {
                    try {
                        return md.getColumnName(i + 1);
                    } catch (SQLException e) {

                        e.printStackTrace();
                        return "?";
                    }
                })
                .collect(Collectors.toList());
        List json = DSL.using(dbConnection)
                .fetch(resultSet)
                .map(new RecordMapper() {
                    @Override
                    public JSONObject map(Record r) {
                        JSONObject obj = new JSONObject();
                        colNames.forEach(cn -> obj.put(cn, r.get(cn)));
                        return obj;
                    }
                });
        return new JSONArray(json);

    }

}
