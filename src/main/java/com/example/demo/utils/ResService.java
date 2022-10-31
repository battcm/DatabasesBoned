package com.example.demo.utils;

import com.example.demo.DTO.ResDTO;
import com.example.demo.DTOHAV.ResDTOCOM;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Service("com.example.demo.utils.ResService")
public class ResService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


    public ResService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(ResDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insertRest @name=?, @addr=?");
        preparedStatement.setString(1,resDTO.getName());
        preparedStatement.setString(2,resDTO.getAddr());
        return preparedStatement.execute();
    }
    public boolean update(ResDTOCOM resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec updateRest @name=?, @addr=?, @id=?");
        preparedStatement.setString(1,resDTO.getName());
        preparedStatement.setString(2,resDTO.getAddr());
        preparedStatement.setInt(3,resDTO.getId());
        return preparedStatement.execute();
    }

    public boolean delete(ResDTOCOM resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec updateRest @id='?'");
        preparedStatement.setInt(1,resDTO.getId());
        return preparedStatement.execute();
    }
}
