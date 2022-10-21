package com.example.demo.utils;

import com.example.demo.DTO.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

@Service("com.example.demo.utils.LoginService")
public class LoginService {
    @Autowired
    @Qualifier("com.example.demo.utils.UserTableRepository")
    private UserTableRepository userTableRepository;
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


public LoginService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString="jdbc:sqlserver://titan.csse.rose-hulman.edu;databaseName=MOHAN-BONE10;user=SodaBaseUserolingejj;password={Password123}";
                //String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));

}

    public boolean testUser(LoginDTO loginDTO){
            try {
                con = DriverManager.getConnection(connectionString);
               PreparedStatement preparedStatement= con.prepareStatement("SELECT Password FROM UserTable where UserName = ?");
                preparedStatement.setString(1,loginDTO.getUsername());
                ResultSet resultSet=preparedStatement.executeQuery();
                if(resultSet.next()){
                    System.out.println(resultSet.getString("Password"));
                    System.out.println(loginDTO.getPassword());
                    if(resultSet.getString("Password").equals(loginDTO.getPassword()))
                    return true;
                    else
                        return false;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;

    }

    public boolean insertUser(LoginDTO loginDTO){
        try {
            con = DriverManager.getConnection(connectionString);
            PreparedStatement preparedStatement= con.prepareStatement("INSERT INTO UserTable (UserName,Password) Values(?,?)");
            preparedStatement.setString(1,loginDTO.getUsername());
            preparedStatement.setString(2,loginDTO.getPassword());
           return preparedStatement.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    return false;
    }
}
