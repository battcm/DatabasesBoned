package com.example.demo.utils;

import com.example.demo.DTO.OrderDTO;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Service("com.example.demo.utils.OrderService")
public class OrderService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public OrderService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(OrderDTO orderDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insertOrder @restId='?', @IngrediantID='?',@drinkId='?',@date='?',@quant='?',@Type='?'");
        preparedStatement.setInt(1,orderDTO.getResId());
        preparedStatement.setInt(2,orderDTO.getIngreId());
        preparedStatement.setInt(3,orderDTO.getDrinkId());
        preparedStatement.setDate(4,orderDTO.getDate());
        preparedStatement.setInt(5,orderDTO.getQuantity());
        preparedStatement.setString(6,orderDTO.getType());

        return preparedStatement.execute();
    }

    public boolean update(OrderDTO orderDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec updateOrder @restId='?', @IngrediantID='?',@drinkId='?',@date='?',@quant='?',@Type='?'");
        preparedStatement.setInt(1,orderDTO.getResId());
        preparedStatement.setInt(2,orderDTO.getIngreId());
        preparedStatement.setInt(3,orderDTO.getDrinkId());
        preparedStatement.setDate(4,orderDTO.getDate());
        preparedStatement.setInt(5,orderDTO.getQuantity());
        preparedStatement.setString(6,orderDTO.getType());

        return preparedStatement.execute();
    }

    public boolean delete(OrderDTO orderDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insertOrder @restId='?', @IngrediantID='?',@drinkId=?");
        preparedStatement.setInt(1,orderDTO.getResId());
        preparedStatement.setInt(2,orderDTO.getIngreId());
        preparedStatement.setInt(3,orderDTO.getDrinkId());

        return preparedStatement.execute();
    }
}
