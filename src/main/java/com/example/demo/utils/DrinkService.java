package com.example.demo.utils;

import com.example.demo.DTO.DrinkDTO;
import com.example.demo.DTO.DrinkIdDTO;
import com.example.demo.DTO.ResDTO;
import com.example.demo.DTOHAV.DrinkDTOCOM;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Service("com.example.demo.utils.DrinkService")
public class DrinkService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public DrinkService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(DrinkDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insertDrink @name=?, @brand=?, @price=?");
        preparedStatement.setString(1,resDTO.getName());
        preparedStatement.setString(2,resDTO.getBrand());
        preparedStatement.setFloat(3,resDTO.getPrice());
        return preparedStatement.execute();
    }
    public boolean update(DrinkDTOCOM drinkDTODTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec updateDrink @id='?', @name='?', @brand='?', @price='?'");
        preparedStatement.setInt(1,Integer.valueOf(drinkDTODTO.getId()));
        preparedStatement.setString(2,drinkDTODTO.getName());
        preparedStatement.setString(3,drinkDTODTO.getBrand());
        preparedStatement.setString(4,drinkDTODTO.getPrice());
        return preparedStatement.execute();
    }

    public boolean delete(DrinkIdDTO drinkDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec deleteDrink @id=?");
        System.out.println(drinkDTO.getDrinkId());
        preparedStatement.setInt(1,drinkDTO.getDrinkId());
        return preparedStatement.execute();
    }

}
