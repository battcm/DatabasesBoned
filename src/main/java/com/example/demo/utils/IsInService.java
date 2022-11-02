package com.example.demo.utils;

import com.example.demo.DTO.IsInDTO;
import com.example.demo.DTO.IsInIdDTO;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

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
}
