package com.example.diplomaanahit.controllers;


import com.example.diplomaanahit.dtos.AuthDTO;
import com.example.diplomaanahit.entities.UserEntity;
import com.example.diplomaanahit.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.UUID;

@Controller
public class UserLoginController {


    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private UserDataService userDataService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody AuthDTO authDTO) throws Exception {
        String email = authDTO.getEmail();
        String password = authDTO.getPassword();

        UserEntity userEntity = userDataService.findByEmail(email);
        if(userEntity == null){
            throw new Exception("Please sign up!!!");
        }

        if(!passwordEncoder.matches(password, userEntity.getPassword())){
            throw new Exception("Email or password was entered incorrect, please try again");
        }
        

        return ResponseEntity.ok(userEntity);
    }

    @RequestMapping(value = "password/change", method = RequestMethod.GET)
    public ResponseEntity passwordChange(@RequestParam String email) throws Exception {
        UserEntity userEntity = userDataService.findByEmail(email);
        if(userEntity == null) {
            throw new Exception("User with that email address does not exist");
        }
        String key = UUID.randomUUID().toString();
        userEntity.setPassword(key);
        //userEntity.setTempKeyExpireDatetime(LocalDateTime.now().plusSeconds(tempKeyExpiration));

        userEntity = userDataService.save(userEntity);
        //emailService.sendUserPasswordChange(userEntity);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public ResponseEntity<?> signUp(@RequestBody AuthDTO authDTO){
        UserEntity userEntity = new UserEntity();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userEntity.setPassword(encoder.encode(authDTO.getPassword()));
        userEntity.setEmail(authDTO.getEmail());
        userEntity.setLoginDate(LocalDate.now());
        UserEntity user = userDataService.findByEmail(authDTO.getEmail());
        if(user == null
                ||  !passwordEncoder.matches(user.getPassword(), userEntity.getPassword())){
            userDataService.save(userEntity);
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.ok(false);
        }

}


