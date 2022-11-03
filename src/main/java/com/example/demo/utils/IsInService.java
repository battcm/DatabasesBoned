package com.example.demo.utils;

import com.example.demo.DTO.IsInDTO;
import com.example.demo.DTO.IsInIdDTO;
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

@Service("com.example.demo.utils.IsInService")
public class IsInService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public IsInService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(IsInDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec InserteIsIn @FoodId=?, @IngredientID=?");
        preparedStatement.setInt(1,resDTO.getFoodid());
        preparedStatement.setInt(2,resDTO.getIngredid());
        return preparedStatement.execute();
    }
    public boolean delete(IsInIdDTO isInDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec DeleteIsIn @FoodID=?, @IngredientID=?");
        preparedStatement.setInt(1,isInDTO.getFoodid());
        preparedStatement.setInt(2,isInDTO.getIngreid());
        return preparedStatement.execute();
    }

    public JSONArray getTable(String attribute) throws SQLException {
        Connection dbConnection = DriverManager.getConnection(connectionString);
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("SELECT * from IsIn Order By "+attribute);

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
