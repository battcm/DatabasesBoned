package com.example.demo.utils;

import com.example.demo.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController("com.example.demo.utils.APIController")
@CrossOrigin(origins = "http://localhost:5000")
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
    @Autowired
    @Qualifier("com.example.demo.utils.IsInService")
    private IsInService isInService;
    @Autowired
    @Qualifier("com.example.demo.utils.CanService")
    private CanService canService;
    @Autowired
    @Qualifier("com.example.demo.utils.SellService")
    private SellService sellService;
    @Autowired
    @Qualifier("com.example.demo.utils.WorkService")
    private WorkService workService;
    @PostMapping("/test/user")
    public ResponseEntity<Boolean> testUser(@RequestBody LoginDTO queryRequest){
    if(loginService.login(queryRequest.getUsername(), queryRequest.getPassword())){
        return new ResponseEntity<>(true,HttpStatus.OK);
    }else{
        return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
    }

}
@PostMapping("/insert/user")
    public ResponseEntity<Boolean> insertUser(@RequestBody LoginDTO queryRequest) {
    return new ResponseEntity<>(loginService.register(queryRequest.getUsername(), queryRequest.getPassword()),HttpStatus.OK);
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
    @PostMapping("/insert/drink")
    public ResponseEntity<Boolean> insertDrink(@RequestBody DrinkDTO resDTO){
        try {
            drinkService.insert(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/insert/schedule")
    public ResponseEntity<Boolean> insertSche(@RequestBody ScheduleDTO resDTO){
        try {
            scheduleService.insert(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/insert/ingred")
    public ResponseEntity<Boolean> insertIngred(@RequestBody IngredDTO resDTO){
        try {
            ingredService.insert(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/insert/food")
    public ResponseEntity<Boolean> insertFood(@RequestBody FoodDTO resDTO){
        try {
            foodService.insert(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/insert/order")
    public ResponseEntity<Boolean> insertOrder(@RequestBody OrderDTO resDTO){
        try {
            orderService.insert(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/insert/isa")
    public ResponseEntity<Boolean> insertIsa(@RequestBody IsInDTO resDTO){
        try {
            isInService.insert(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/insert/can")
    public ResponseEntity<Boolean> insertCan(@RequestBody CanDTO resDTO){
        try {
            canService.insert(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/insert/sell")
    public ResponseEntity<Boolean> insertSell(@RequestBody SellDTO resDTO){
        try {
            sellService.insert(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/insert/work")
    public ResponseEntity<Boolean> insertWork(@RequestBody WorkDTO resDTO){
        try {
            workService.insert(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/res")
    public ResponseEntity<Boolean> deleteRes(@RequestBody ResIdDTO resDTO){
        try {
            resService.delete(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/drink")
    public ResponseEntity<Boolean> deleteDrink(@RequestBody DrinkIdDTO resDTO){
        try {
            drinkService.delete(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/schedule")
    public ResponseEntity<Boolean> deleteSche(@RequestBody ScheduleIdDTO resDTO){
        try {
            scheduleService.delete(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/ingred")
    public ResponseEntity<Boolean> deleteIngred(@RequestBody IngredIdDTO resDTO){
        try {
            ingredService.delete(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/food")
    public ResponseEntity<Boolean> deleteFood(@RequestBody FoodIdDTO resDTO){
        try {
            foodService.delete(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/order")
    public ResponseEntity<Boolean> deleteOrder(@RequestBody OrderIdDTO resDTO){
        try {
            orderService.delete(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/isa")
    public ResponseEntity<Boolean> deleteIsa(@RequestBody IsInIdDTO resDTO){
        try {
            isInService.delete(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/can")
    public ResponseEntity<Boolean> deleteCan(@RequestBody CanIdDTO resDTO){
        try {
            canService.delete(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/sell")
    public ResponseEntity<Boolean> deleteSell(@RequestBody SellIdDTO resDTO){
        try {
            sellService.delete(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
    @PostMapping("/delete/work")
    public ResponseEntity<Boolean> deleteWork(@RequestBody WorkIdDTO resDTO){
        try {
            workService.delete(resDTO);
            return new ResponseEntity<>(true,HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(true,HttpStatus.FAILED_DEPENDENCY);
        }
    }
}

