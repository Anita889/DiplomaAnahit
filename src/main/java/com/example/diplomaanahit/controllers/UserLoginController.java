package com.example.diplomaanahit.controllers;


import com.example.diplomaanahit.dtos.AuthDTO;
import com.example.diplomaanahit.entities.RegistrationType;
import com.example.diplomaanahit.entities.UserEntity;
import com.example.diplomaanahit.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

        UserEntity userEntity = userDataService.findByEmailAndRegistrationType(email, RegistrationType.REGISTERED);
        if(userEntity == null){
            throw new Exception("Email or password was entered incorrect, please try again");
        }

        if(!passwordEncoder.matches(password, userEntity.getPassword())){
            throw new Exception("Email or password was entered incorrect, please try again");
        }
        

        return loginByUserEntity(userEntity);
    }

    private ResponseEntity<?> loginByUserEntity(UserEntity userEntity) {
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "password/change", method = RequestMethod.GET)
    public ResponseEntity passwordChange(@RequestParam String email) throws Exception {
        // URL Encoder replaces '+' with space
        email = email.replace(" ", "+");
        //
        UserEntity userEntity = userDataService.findByEmailAndRegistrationType(email, RegistrationType.REGISTERED);
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

    @RequestMapping(value = "password/change/{email}/{key}", method = RequestMethod.PUT)
    public ResponseEntity passwordChange(@PathVariable String email, @PathVariable String key, @RequestBody AuthDTO authDTO) throws Exception {
        UserEntity userEntity = userDataService.findByKey(key);
        if(userEntity == null || !userEntity.getEmail().equals(email) ){
                //|| userEntity.getTempKeyExpireDatetime() == null || userEntity.getTempKeyExpireDatetime().isBefore(LocalDateTime.now()) ) {
            throw new Exception("The activation key is not found or expired");
        }

        userEntity.setPassword(passwordEncoder.encode(authDTO.getPassword()));
      //  userEntity.setRegistrationType(RegistrationType.REGISTERED);
        userDataService.save(userEntity);
        return ResponseEntity.ok(true);
    }

    @RequestMapping(value = "password/check/{email}/{key}", method = RequestMethod.GET)
    public ResponseEntity checkKey(@PathVariable String email, @PathVariable String key) throws Exception {
        UserEntity userEntity = userDataService.findByKey(key);
        if(userEntity == null || !userEntity.getEmail().equals(email)
             //   || userEntity.getTempKeyExpireDatetime() == null || userEntity.getTempKeyExpireDatetime().isBefore(LocalDateTime.now())
        ) {
            throw new Exception("The activation key is not found or expired");
        }

        return ResponseEntity.ok(true);
    }

}


