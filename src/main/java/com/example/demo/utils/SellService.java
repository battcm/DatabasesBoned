package com.example.demo.utils;

import com.example.demo.DTO.SellDTO;
import com.example.demo.DTO.SellIdDTO;
import com.example.demo.DTOHAV.SellsDTOCOM;
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

@Service("com.example.demo.utils.SellService")
public class SellService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public SellService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(SellDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec InserteSells @RestId=?, @FoodId=?, @Mealtype=?");
        preparedStatement.setInt(1,resDTO.getRestID());
        preparedStatement.setInt(2,resDTO.getFoodId());
        preparedStatement.setString(3,""+resDTO.getType());
        return preparedStatement.execute();
    }
    public boolean update(SellsDTOCOM sellsDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec UpdateSells @RestID=?, @FoodID=?, @Mealtype=?");
        preparedStatement.setInt(1,sellsDTO.getResId());
        preparedStatement.setInt(2,sellsDTO.getFoodId());
        preparedStatement.setString(3,sellsDTO.getMealType());
        return preparedStatement.execute();
    }

    public boolean delete(SellIdDTO sellsDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec DeleteSells @RestID='?', @FoodID='?'");
        preparedStatement.setInt(1,sellsDTO.getRestId());
        preparedStatement.setInt(2,sellsDTO.getFoodId());
        return preparedStatement.execute();
    }

    public JSONArray getTable(String attribute) throws SQLException {
        Connection dbConnection = DriverManager.getConnection(connectionString);
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("SELECT * from Sells Order By "+attribute);

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

