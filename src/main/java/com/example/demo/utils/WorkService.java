package com.example.demo.utils;

import com.example.demo.DTO.WorkDTO;
import com.example.demo.DTO.WorkIdDTO;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

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
        preparedStatement.setInt(2,restId.getResId());
        return preparedStatement.execute();
    }
}

