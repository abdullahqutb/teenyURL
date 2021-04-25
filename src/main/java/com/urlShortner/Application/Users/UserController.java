package com.urlShortner.Application.Users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.urlShortner.Application.Users.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sun.security.util.Password;


import java.util.*;


@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

    final long value = 1000L;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/User/Signup")
    public @ResponseBody User addNewUser(@RequestBody User user) {
        System.out.println("NAME HERE");
        System.out.println(user.getEmail());
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setCreatedAt(System.currentTimeMillis() / value);
        try {
            System.out.println("saving");
            newUser = userRepository.save(newUser);
        } catch (DataIntegrityViolationException e) {
            System.out.println("NOT saving");
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Sign Up Failed!");
        }
        return newUser;
//        return "newUser";
    }

    @GetMapping("/User")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(required = false) String title) {
        try {
            List<User> user = new ArrayList<User>();

            userRepository.findAll().forEach(user::add);

            if (user.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/User/Login")
    public @ResponseBody User loginUser(@RequestBody User user){
        String email = user.getEmail();
        String password = user.getPassword();
        User myUser = userRepository.findByEmail(email);
//        ObjectMapper mapper = new ObjectMapper();
        if(myUser == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user exists with this email!");
        }
        else {
            if(password.equals("")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password not Entered!");
            }
            else if(!password.equals(myUser.getPassword())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong Password Entered!");
            }
            else{
                return myUser;
            }
        }
    }
}