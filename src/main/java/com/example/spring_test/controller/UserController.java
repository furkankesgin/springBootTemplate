package com.example.spring_test.controller;

import com.example.spring_test.aspects.annotation.SecuredMethod;
import com.example.spring_test.bussiness.abstracts.IUserService;
import com.example.spring_test.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("user")
public class UserController {
    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @SecuredMethod(role = "can,a,GetAll")
    @GetMapping("/getAll/{page}")
    public ResponseEntity<Page<User>> getAll(@PathVariable Integer page, String search){
        try{
        return new ResponseEntity<>(userService.getAll(PageRequest.of(Integer.parseInt(page.toString()) - 1, 10)),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getById/{resellerId}")
    public Optional<User> getById(@PathVariable Integer resellerId) {
        return userService.getById(resellerId);
    }

    @PostMapping("/add")
    public User add(@RequestBody User user){
        return userService.add(user);
    }


    @PutMapping("/update")
    public User update(@RequestBody User user){
        return userService.update(user);
    }


}
