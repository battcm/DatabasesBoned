package com.example.demo.utils;

import com.example.demo.DTO.FoodDTO;
import com.example.demo.DTO.FoodIdDTO;
import com.example.demo.DTOHAV.FoodDTOCOM;
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

@Service("com.example.demo.utils.FoodService")
public class FoodService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public FoodService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(FoodDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insert_into_foodItem @Name=?, @Calories=?");
        preparedStatement.setString(1,resDTO.getName());
        preparedStatement.setInt(2,resDTO.getCal());
        return preparedStatement.execute();
    }

    public boolean update(FoodDTOCOM foodDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec update_foodItem @FoodItemID='?', @Name='?', @Calories=?");
        preparedStatement.setInt(1,Integer.valueOf(foodDTO.getId()));
        preparedStatement.setString(2,foodDTO.getName());
        preparedStatement.setInt(3,foodDTO.getCal());
        return preparedStatement.execute();
    }

    public boolean delete(FoodIdDTO foodDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec delete_foodItem @FoodItemID=?");
        preparedStatement.setInt(1,foodDTO.getFoodId());
        return preparedStatement.execute();
    }

    public JSONArray selectIngred() throws SQLException {
        Connection dbConnection = DriverManager.getConnection(connectionString);
        PreparedStatement preparedStatement = dbConnection.prepareStatement("Exec selectIngred");
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