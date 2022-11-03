package com.example.demo.utils;

import com.example.demo.DTO.OrderDTO;
import com.example.demo.DTOHAV.OrderDTOCOM;
import com.example.demo.DTOHAV.OrderKeyDTO;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;
import org.jooq.tools.json.JSONArray;
import org.jooq.tools.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public boolean insert(OrderDTO resDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insertOrder @restId=?, @FoodItemID=?,@drinkId=?,@date=?,@quant=?,@Type=?");
        preparedStatement.setInt(1,resDTO.getResId());
        preparedStatement.setInt(2,resDTO.getFoodId());
        preparedStatement.setInt(3,resDTO.getDrinkId());
        preparedStatement.setDate(4,resDTO.getDay());
        preparedStatement.setInt(5,resDTO.getQuan());
        preparedStatement.setString(6,resDTO.getStor());

        return preparedStatement.execute();

    }
    public boolean update(OrderDTOCOM orderDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec updateOrder @restId=?, @IngrediantID=?,@drinkId=?,@date=?,@quant=?,@Type=?");
        preparedStatement.setInt(1,orderDTO.getResId());
        preparedStatement.setInt(2,orderDTO.getIngreId());
        preparedStatement.setInt(3,orderDTO.getDrinkId());
        preparedStatement.setDate(4,orderDTO.getDate());
        preparedStatement.setInt(5,orderDTO.getQuantity());
        preparedStatement.setString(6,orderDTO.getType());

        return preparedStatement.execute();
    }

    public boolean delete(OrderDTOCOM orderDTO) throws SQLException {
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("Exec insertOrder @restId=?, @IngrediantID=?,@drinkId=?");
        preparedStatement.setInt(1,orderDTO.getResId());
        preparedStatement.setInt(2,orderDTO.getIngreId());
        preparedStatement.setInt(3,orderDTO.getDrinkId());

        return preparedStatement.execute();
    }

    public JSONArray getTable(String attribute) throws SQLException {
        Connection dbConnection = DriverManager.getConnection(connectionString);
        PreparedStatement preparedStatement = DriverManager.getConnection(connectionString).prepareStatement("SELECT * from Orders Order By "+attribute);

        ResultSet resultSet = preparedStatement.executeQuery();
        ResultSetMetaData md = resultSet.getMetaData();
        int numCols = md.getColumnCount();
        List<String> colNames = IntStream.range(0, numCols)
                .mapToObj(i -> {
                    try {
                        return md.getColumnName(i + 1);
                    } catch (SQLException e) {

                        e.printStackTrace();
                        return "?";
                    }
                })
                .collect(Collectors.toList());
        List json = DSL.using(dbConnection)
                .fetch(resultSet)
                .map(new RecordMapper() {
                    @Override
                    public JSONObject map(Record r) {
                        JSONObject obj = new JSONObject();
                        colNames.forEach(cn -> obj.put(cn, r.get(cn)));
                        return obj;
                    }
                });
        return new JSONArray(json);

    }
}
