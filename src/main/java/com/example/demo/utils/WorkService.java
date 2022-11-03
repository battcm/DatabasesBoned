package com.example.demo.utils;

import com.example.demo.DTO.WorkDTO;
import com.example.demo.DTO.WorkIdDTO;
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

@Service("com.example.demo.utils.WorkService")
public class WorkService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public WorkService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(WorkDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insertWorkFor @username=?, @restid=?, @perm=?");
        preparedStatement.setString(1,resDTO.getUser());
        preparedStatement.setInt(2,resDTO.getRestId());
        preparedStatement.setString(3,""+resDTO.getPerm());
        return preparedStatement.execute();
    }

    public boolean update(WorkDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec updateWorksFor @username=?, @restid=?, @perm=?");
        preparedStatement.setString(1,resDTO.getUser());
        preparedStatement.setInt(2,resDTO.getRestId());
        preparedStatement.setString(3,""+resDTO.getPerm());
        return preparedStatement.execute();
    }

    public Boolean delete(WorkIdDTO restId) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec deleteWorkFor @user=?, @restId=?");
        preparedStatement.setString(1,restId.getUsername());
        preparedStatement.setInt(2,restId.getRestId());
        return preparedStatement.execute();
    }

    public JSONArray getTable(String attribute) throws SQLException {
        Connection dbConnection = DriverManager.getConnection(connectionString);
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("SELECT * from WorksFor Order By "+attribute);

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

