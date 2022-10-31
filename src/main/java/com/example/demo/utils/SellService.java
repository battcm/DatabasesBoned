package com.example.demo.utils;

import com.example.demo.DTO.IngredIdDTO;
import com.example.demo.DTO.ResDTO;
import com.example.demo.DTO.SellDTO;
import com.example.demo.DTO.SellIdDTO;
import com.example.demo.DTOHAV.SellsDTOCOM;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Service("com.example.demo.utils.SellService")
public class SellService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public SellService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(SellDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec InserteSells @RestId=?, @FoodId=?, @Mealtype=?");
        preparedStatement.setInt(1,resDTO.getRestID());
        preparedStatement.setInt(2,resDTO.getFoodId());
        preparedStatement.setString(3,""+resDTO.getType());
        return preparedStatement.execute();
    }
    public boolean update(SellsDTOCOM sellsDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec UpdateSells @RestID='?', @FoodID='?', @Mealtype='?'");
        preparedStatement.setInt(1,sellsDTO.getResId());
        preparedStatement.setInt(2,sellsDTO.getFoodId());
        preparedStatement.setString(2,sellsDTO.getMealType());
        return preparedStatement.execute();
    }

    public boolean delete(SellsKeyDTO sellsDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec DeleteSells @RestID='?', @FoodID='?'");
        preparedStatement.setInt(1,sellsDTO.getResId());
        preparedStatement.setInt(2,sellsDTO.getFoodid());
        return preparedStatement.execute();
    }
}

