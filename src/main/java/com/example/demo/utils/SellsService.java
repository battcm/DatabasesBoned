package com.example.demo.utils;

import com.example.demo.DTO.SellsDTO;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Service("com.example.demo.utils.ResService")
public class SellsService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public SellsService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(SellsDTO sellsDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec InsertSells @RestID='?', @FoodID='?', @Mealtype='?'");
        preparedStatement.setInt(1,sellsDTO.getResId());
        preparedStatement.setInt(2,sellsDTO.getFoodId());
        preparedStatement.setString(2,sellsDTO.getMealType());
        return preparedStatement.execute();
    }

    public boolean update(SellsDTO sellsDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec UpdateSells @RestID='?', @FoodID='?', @Mealtype='?'");
        preparedStatement.setInt(1,sellsDTO.getResId());
        preparedStatement.setInt(2,sellsDTO.getFoodId());
        preparedStatement.setString(2,sellsDTO.getMealType());
        return preparedStatement.execute();
    }

    public boolean delete(SellsDTO sellsDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec DeleteSells @RestID='?', @FoodID='?'");
        preparedStatement.setInt(1,sellsDTO.getResId());
        preparedStatement.setInt(2,sellsDTO.getFoodId());
        return preparedStatement.execute();
    }
}
