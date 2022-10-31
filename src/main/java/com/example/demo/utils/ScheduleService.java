package com.example.demo.utils;

import com.example.demo.DTO.ScheduleDTO;
import com.example.demo.DTO.ScheduleIdDTO;
import com.example.demo.DTOHAV.ScheduleDTOCOM;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

@Service("com.example.demo.utils.ScheduleService")
public class ScheduleService {
    private Connection con;
    private String connectionString;
    Properties application = new Properties();
    public ScheduleService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));
    }

    public boolean insert(ScheduleDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insertSchedule @restId=?, @dayname='?',@Open=?,@Close=?");
        preparedStatement.setString(1,resDTO.getId());
        preparedStatement.setString(2,resDTO.getDay());
        preparedStatement.setTime(3,resDTO.getOpen());
        preparedStatement.setTime(4,resDTO.getClose());
        return preparedStatement.execute();
    }

    public boolean update(ScheduleDTOCOM scheduleDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec updateSchedule @restId='?', @dayname='?',@Open='?',@Close='?'");
        preparedStatement.setInt(1,scheduleDTO.getId());
        preparedStatement.setString(2,scheduleDTO.getDay());
        preparedStatement.setString(3,scheduleDTO.getOpen());
        preparedStatement.setString(4,scheduleDTO.getClose());
        return preparedStatement.execute();
    }

    public boolean delete(ScheduleIdDTO scheduleDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec deleteSchedule @restId='?', @dayname='?'");
        preparedStatement.setInt(1,scheduleDTO.getResId());
        preparedStatement.setString(2,scheduleDTO.getDay());
        return preparedStatement.execute();
    }
}
