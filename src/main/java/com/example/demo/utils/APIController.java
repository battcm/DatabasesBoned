package com.example.demo.utils;

import com.example.demo.DTO.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController("com.example.demo.utils.APIController")
@CrossOrigin(origins = "http://localhost:5000")
@RequestMapping("/v1/")
public class APIController {
    @Autowired
    @Qualifier("com.example.demo.utils.LoginService")
    private LoginService loginService;
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
}
