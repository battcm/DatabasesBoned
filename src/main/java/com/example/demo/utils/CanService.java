package com.example.demo.utils;

import com.example.demo.DTO.CanDTO;
import com.example.demo.DTO.CanIdDTO;
import com.example.demo.DTOHAV.CanStoreWithDTOCOM;
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

@Service("com.example.demo.utils.CanService")
public class CanService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public CanService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(CanDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insert_into_canStoreWith @IngredientAID=?, @IngredientBID=?");
        preparedStatement.setInt(1,resDTO.getIngredA());
        preparedStatement.setInt(2,resDTO.getIngredB());
        return preparedStatement.execute();
    }
    public boolean delete(CanIdDTO canStoreWithDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec delete_canStoreWith @IngredientAID=?, @IngredientBID=?");
        preparedStatement.setInt(1,canStoreWithDTO.getIngreAId());
        preparedStatement.setInt(2,canStoreWithDTO.getIngreBId());
        return preparedStatement.execute();
    }

    public JSONArray selectAll() throws SQLException {
        Connection dbConnection = DriverManager.getConnection(connectionString);
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("SELECT * from Restaurant");

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
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("SELECT * from CanStoreWith Order By "+attribute);

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
