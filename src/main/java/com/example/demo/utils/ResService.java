package com.example.demo.utils;

import com.example.demo.DTO.ResDTO;
import com.example.demo.DTO.ResIdDTO;
import com.example.demo.DTOHAV.ResDTOCOM;
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

@Service("com.example.demo.utils.ResService")
public class ResService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public ResService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(ResDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insertRest @name=?, @addr=?");
        preparedStatement.setString(1,resDTO.getName());
        preparedStatement.setString(2,resDTO.getAddr());
        return preparedStatement.execute();
    }
    public boolean update(ResDTOCOM resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec updateRest @name=?, @addr=?, @id=?");
        preparedStatement.setString(1,resDTO.getName());
        preparedStatement.setString(2,resDTO.getAddr());
        preparedStatement.setInt(3,resDTO.getId());
        return preparedStatement.execute();
    }

    public boolean delete(ResIdDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec updateRest @id='?'");
        preparedStatement.setInt(1,resDTO.getRestId());
        return preparedStatement.execute();
    }
    public JSONArray selectAll() throws SQLException {
        Connection dbConnection=DriverManager.getConnection(connectionString);
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("SELECT * from Restaurant");

        ResultSet resultSet= preparedStatement.executeQuery();
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
    public JSONArray selectRestName() throws SQLException{
        Connection dbConnection=DriverManager.getConnection(connectionString);
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec selectRestName");
        ResultSet resultSet= preparedStatement.executeQuery();
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