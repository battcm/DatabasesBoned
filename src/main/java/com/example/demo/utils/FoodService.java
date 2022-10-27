package com.example.demo.utils;

import com.example.demo.DTO.FoodDTO;
import com.example.demo.DTO.ResDTO;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

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

    public boolean insert(FoodDTO foodDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insert_into_foodItem @Name='?', @Calories=?");
        preparedStatement.setString(1,foodDTO.getName());
        preparedStatement.setInt(2,foodDTO.getCal());
        return preparedStatement.execute();
    }

    public boolean update(FoodDTO foodDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec update_foodItem @FoodItemID='?', @Name='?', @Calories=?");
        preparedStatement.setInt(1,Integer.valueOf(foodDTO.getId()));
        preparedStatement.setString(2,foodDTO.getName());
        preparedStatement.setInt(3,foodDTO.getCal());
        return preparedStatement.execute();
    }

    public boolean delete(FoodDTO foodDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec delete_foodItem @FoodItemID='?'");
        preparedStatement.setInt(1,Integer.valueOf(foodDTO.getId()));
        return preparedStatement.execute();
    }
}