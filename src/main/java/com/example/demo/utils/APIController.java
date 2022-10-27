package com.example.demo.utils;

import com.example.demo.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

@RestController("com.example.demo.utils.APIController")
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/v1/")
public class APIController {
    @Autowired
    @Qualifier("com.example.demo.utils.LoginService")
    private LoginService loginService;
    @Autowired
    @Qualifier("com.example.demo.utils.ResService")
    private ResService resService;
    @Autowired
    @Qualifier("com.example.demo.utils.DrinkService")
    private DrinkService drinkService;
    @Autowired
    @Qualifier("com.example.demo.utils.ScheduleService")
    private ScheduleService scheduleService;
    @Autowired
    @Qualifier("com.example.demo.utils.IngredService")
    private IngredService ingredService;
    @Autowired
    @Qualifier("com.example.demo.utils.FoodService")
    private FoodService foodService;
    @Autowired
    @Qualifier("com.example.demo.utils.OrderService")
    private OrderService orderService;
    @PostMapping("/test/user")
    public ResponseEntity<Boolean> testUser(@RequestBody LoginDTO queryRequest){
    if(loginService.testUser(queryRequest)){
        return new ResponseEntity<>(true,HttpStatus.OK);
    }else{
        return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
    }

}
@PostMapping("/insert/user")
    public ResponseEntity<Boolean> insertUser(@RequestBody LoginDTO queryRequest) {
    return new ResponseEntity<>(loginService.insertUser(queryRequest),HttpStatus.OK);
}
@PostMapping("/count")
    public ResponseEntity<Integer> countCol(@RequestBody String table){
    int val=0;
    if(table.equalsIgnoreCase("Restaurant")){
        val=2;
    }else if(table.equalsIgnoreCase("Drink")){
        val=3;
    }else if(table.equalsIgnoreCase("Schedule")){
        val=4;
    }else if(table.equalsIgnoreCase("Ingredients")){
        val=2;
    }else if(table.equalsIgnoreCase("FoodItems")){
        val=2;
    }else if(table.equalsIgnoreCase("Orders")){
        val=7;
    }else if(table.equalsIgnoreCase("CanStoreWith")){
        val=2;
    }else if(table.equalsIgnoreCase("IsIn")){
        val=2;
    }else if(table.equalsIgnoreCase("Sells")){
        val=3;
    }else if(table.equalsIgnoreCase("WorksFor")){
        val=4;
    }else{
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(val,HttpStatus.OK);

}
    @PostMapping("/insert/res")
    public ResponseEntity<Boolean> insertRes(@RequestBody ResDTO resDTO){
        try {
            resService.insert(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/update/res")
    public ResponseEntity<Boolean> updateRes(@RequestBody ResDTO resDTO){
        try {
            resService.update(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/res")
    public ResponseEntity<Boolean> deleteRes(@RequestBody ResDTO resDTO){
        try {
            resService.delete(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PostMapping("/insert/drink")
    public ResponseEntity<Boolean> insertDrink(@RequestBody DrinkDTO drinkDTO){
        try {
            drinkService.insert(drinkDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/update/drink")
    public ResponseEntity<Boolean> updateDrink(@RequestBody DrinkDTO drinkDTO){
        try {
            drinkService.update(drinkDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/drink")
    public ResponseEntity<Boolean> deleteDrink(@RequestBody DrinkDTO drinkDTO){
        try {
            drinkService.delete(drinkDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PostMapping("/insert/schedule")
    public ResponseEntity<Boolean> insertSche(@RequestBody ScheduleDTO scheduleDTO){
        try {
            scheduleService.insert(scheduleDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/update/schedule")
    public ResponseEntity<Boolean> updateSche(@RequestBody ScheduleDTO scheduleDTO){
        try {
            scheduleService.update(scheduleDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/schedule")
    public ResponseEntity<Boolean> deleteSche(@RequestBody ScheduleDTO scheduleDTO){
        try {
            scheduleService.delete(scheduleDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PostMapping("/insert/ingred")
    public ResponseEntity<Boolean> insertIngred(@RequestBody IngredDTO ingredDTO){
        try {
            ingredService.insert(ingredDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/update/ingred")
    public ResponseEntity<Boolean> updateIngred(@RequestBody IngredDTO ingredDTO){
        try {
            ingredService.update(ingredDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/ingred")
    public ResponseEntity<Boolean> deleteIngred(@RequestBody IngredDTO ingredDTO){
        try {
            ingredService.delete(ingredDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PostMapping("/insert/food")
    public ResponseEntity<Boolean> insertFood(@RequestBody FoodDTO foodDTO){
        try {
            foodService.insert(foodDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/update/food")
    public ResponseEntity<Boolean> updateFood(@RequestBody FoodDTO foodDTO){
        try {
            foodService.update(foodDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/food")
    public ResponseEntity<Boolean> deleteFood(@RequestBody FoodDTO foodDTO){
        try {
            foodService.delete(foodDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    @PostMapping("/insert/order")
    public ResponseEntity<Boolean> insertOrder(@RequestBody OrderDTO orderDTO){
        try {
            orderService.insert(orderDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/update/order")
    public ResponseEntity<Boolean> updateOrder(@RequestBody OrderDTO orderDTO){
        try {
            orderService.update(orderDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/order")
    public ResponseEntity<Boolean> deleteOrder(@RequestBody OrderDTO orderDTO){
        try {
            orderService.delete(orderDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
}

