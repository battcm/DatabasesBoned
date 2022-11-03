package com.example.demo.utils;

import com.example.demo.DTO.IngredDTO;
import com.example.demo.DTO.IngredIdDTO;
import com.example.demo.DTOHAV.IngredDTOCOM;
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

@Service("com.example.demo.utils.IngredService")
public class IngredService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();
    public IngredService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(IngredDTO resDTO) throws SQLException {
        PreparedStatement preparedStatemen = DriverManager.getConnection(connectionString).prepareStatement("SELECT * FROM Ingredient");
        System.out.println(preparedStatemen);
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insert_into_ingre @IngreName=?, @IngreType=?");
        preparedStatement.setString(1,resDTO.getName());
        preparedStatement.setString(2,resDTO.getType());
        return preparedStatement.execute();
    }

    public boolean update(IngredDTOCOM ingredDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec update_ingre @IngreID=?, @IngreName=?, @IngreType=?");
        preparedStatement.setInt(1,ingredDTO.getId());
        preparedStatement.setString(2,ingredDTO.getName());
        preparedStatement.setString(3,ingredDTO.getType());
        return preparedStatement.execute();
    }
    public boolean delete(IngredIdDTO ingredDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec delete_ingre @IngreId=? @IngreName=?, @IngreType=?");
        preparedStatement.setInt(1,ingredDTO.getIngreId());
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

    public JSONArray getTable(String attribute) throws SQLException {
        Connection dbConnection = DriverManager.getConnection(connectionString);
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("SELECT * from Ingredients Order By "+attribute);

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
