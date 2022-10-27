package com.example.demo.utils;

import com.example.demo.DTO.IngredDTO;
import com.example.demo.DTO.ResDTO;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
@Service("com.example.demo.utils.IngredService")
public class IngredService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();
    public IngredService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(IngredDTO ingredDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insert_into_ingre @IngreName='?', @IngreType='?'");
        preparedStatement.setString(1,ingredDTO.getName());
        preparedStatement.setString(2,ingredDTO.getType());
        return preparedStatement.execute();
    }

    public boolean update(IngredDTO ingredDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec update_ingre @IngreId='?' @IngreName='?', @IngreType='?'");
        preparedStatement.setInt(1,ingredDTO.getId());
        preparedStatement.setString(2,ingredDTO.getName());
        preparedStatement.setString(3,ingredDTO.getType());
        return preparedStatement.execute();
    }
    public boolean delete(IngredDTO ingredDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec delete_ingre @IngreId='?' @IngreName='?', @IngreType='?'");
        preparedStatement.setInt(1,ingredDTO.getId());
        return preparedStatement.execute();
    }

}
