package com.example.demo.utils;

import com.example.demo.DTO.CanDTO;
import com.example.demo.DTO.CanIdDTO;
import com.example.demo.DTOHAV.CanStoreWithDTOCOM;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Service("com.example.demo.utils.CanService")
public class CanService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public CanService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(CanDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insert_into_canStoreWith @IngredientAID=?, @IngredientBID=?");
        preparedStatement.setInt(1,resDTO.getIngredA());
        preparedStatement.setInt(2,resDTO.getIngredB());
        return preparedStatement.execute();
    }
    public boolean delete(CanIdDTO canStoreWithDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec delete_canStoreWith @IngredientAID=?, @IngredientBID=?");
        preparedStatement.setInt(1,canStoreWithDTO.getIngreAId());
        preparedStatement.setInt(2,canStoreWithDTO.getIngreBId());
        return preparedStatement.execute();
    }
}
