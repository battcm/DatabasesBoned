package com.example.demo.utils;

import com.example.demo.DTO.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;

@Service("com.example.demo.utils.LoginService")
public class LoginService {
    private static final Random RANDOM = new SecureRandom();
    private static final Base64.Encoder enc = Base64.getEncoder();
    private static final Base64.Decoder dec = Base64.getDecoder();
    private Connection con;
    private String connectionString;
    Properties application = new Properties();


public LoginService(){
        try {
            application.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionString= String.format(application.getProperty("databaseConnection"),application.getProperty("serverName"),application.getProperty("spring.datasource.databaseName"),application.getProperty("username"),application.getProperty("password"));

}

    public boolean testUser(LoginDTO loginDTO){
            try {
                con = DriverManager.getConnection(connectionString);
               PreparedStatement preparedStatement= con.prepareStatement("SELECT Password FROM UserTable where UserName = ?");
                preparedStatement.setString(1,loginDTO.getUsername());
                ResultSet resultSet=preparedStatement.executeQuery();
                if(resultSet.next()){
                    System.out.println(resultSet.getString("PasswordHash"));
                    System.out.println(loginDTO.getPasswordHash());
                    if(resultSet.getString("PasswordHash").equals(loginDTO.getPasswordHash()))
                    return true;
                    else
                        return false;
                }
                con.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;

    }

    public boolean insertUser(LoginDTO loginDTO){
        try {
            con = DriverManager.getConnection(connectionString);
            PreparedStatement preparedStatement= con.prepareStatement("INSERT INTO UserTable (UserName,Password) Values(?,?,?)");
            preparedStatement.setString(1,loginDTO.getUsername());
            preparedStatement.setString(2,loginDTO.getPasswordHash());
            preparedStatement.setString(3,loginDTO.getPasswordSalt());
           boolean b= preparedStatement.execute();
            con.close();
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
    return false;
    }




    public boolean login(String username, String password) {
        try {
            con = DriverManager.getConnection(connectionString);
            ResultSet rs=this.con.createStatement().executeQuery("Select * From [UserTable] Where Username='"+username+"'");
            if(!rs.next())
            {
                JOptionPane.showMessageDialog(null, "Login Failed");
                return false;
            }

            if(rs.getString("PasswordHash").equals(this.hashPassword(rs.getString("PasswordSalt").getBytes(), password)))
                return true;
            else
            {
                JOptionPane.showMessageDialog(null, "Login Failed");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Login Failed");
            return false;
        }
    }

    public boolean register(String username, String password) {
        try {
            con = DriverManager.getConnection(connectionString);
            String salt=this.getNewSalt().toString();
            while(this.con.createStatement().executeQuery("Select * From [UserTable] Where PasswordSalt='"+salt+"'").next())
                salt=this.getNewSalt().toString();
            String query="EXEC dbo.Register"
                    + "\n@Username='"+username+"'"
                    + ",\n@PasswordSalt='"+salt+"'"
                    + ",\n@PasswordHash='"+this.hashPassword(salt.getBytes(), password)+"'";

            CallableStatement proc = this.con.prepareCall(query);
            proc.execute();
            if(proc.getWarnings()!=null)
            {
                JOptionPane.showMessageDialog(null, "Registration Failed");
                return false;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Registration Success");
                return true;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public byte[] getNewSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return salt;
    }

    public String getStringFromBytes(byte[] data) {
        return enc.encodeToString(data);
    }

    public String hashPassword(byte[] salt, String password) {

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f;
        byte[] hash = null;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException e) {
            JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            JOptionPane.showMessageDialog(null, "An error occurred during password hashing. See stack trace.");
            e.printStackTrace();
        }
        return getStringFromBytes(hash);
    }
}
