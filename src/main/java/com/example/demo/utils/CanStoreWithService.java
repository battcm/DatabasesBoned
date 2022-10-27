package com.example.demo.utils;

import com.example.demo.DTO.CanStoreWithDTO;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Service("com.example.demo.utils.ResService")
public class CanStoreWithService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public CanStoreWithService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(CanStoreWithDTO canStoreWithDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insert_into_canStoreWith @IngredientAID='?', @IngredientBID='?'");
        preparedStatement.setInt(1,canStoreWithDTO.getIngreAId());
        preparedStatement.setInt(2,canStoreWithDTO.getIngreBId());
        return preparedStatement.execute();
    }

    public boolean delete(CanStoreWithDTO canStoreWithDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec delete_canStoreWith @IngredientAID='?', @IngredientBID='?'");
        preparedStatement.setInt(1,canStoreWithDTO.getIngreAId());
        preparedStatement.setInt(2,canStoreWithDTO.getIngreBId());
        return preparedStatement.execute();
    }
}
