package com.example.diplomaanahit.controllers;


import com.example.diplomaanahit.dtos.AuthDTO;
import com.example.diplomaanahit.dtos.UserDTO;
import com.example.diplomaanahit.entities.Admin;
import com.example.diplomaanahit.entities.Lecturer;
import com.example.diplomaanahit.entities.Student;
import com.example.diplomaanahit.entities.UserEntity;
import com.example.diplomaanahit.mapper.UserMapper;
import com.example.diplomaanahit.repositories.LecturerRepository;
import com.example.diplomaanahit.security.AuthenticationTokenService;
import com.example.diplomaanahit.services.AdminDataService;
import com.example.diplomaanahit.services.LecturerDataService;
import com.example.diplomaanahit.services.StudentDataService;
import com.example.diplomaanahit.services.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/user/authentication")
public class UserLoginController {


    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentDataService studentService;

    @Autowired
    private LecturerDataService lecturerService;

    @Value("${security.token.secret}")
    private  String accessTokenSecret;

    @Autowired
    private AdminDataService adminService;


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

        AuthDTO auth = AuthenticationTokenService.login(userEntity, userMapper, accessTokenSecret);
        String token = auth.getAccessToken();
        return ResponseEntity.ok(Map.of(
                "accessToken", token,
                "user", auth.getUser(),
                "role", auth.getUser().getRegistrationType()
        ));
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
    public ResponseEntity<?> signUp(@RequestBody AuthDTO authDTO) throws Exception {
        UserEntity userEntity = new UserEntity();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userEntity.setPassword(encoder.encode(authDTO.getPassword()));
        userEntity.setEmail(authDTO.getEmail());
        userEntity.setLoginDate(LocalDate.now());
        UserEntity user = userDataService.findByEmail(authDTO.getEmail());
        Student student = studentService.findByEmail(authDTO.getEmail());
        Lecturer lecturer = lecturerService.findByEmail(authDTO.getEmail());
        Admin admin = adminService.findByEmail(authDTO.getEmail());
        if(user != null){
            throw new Exception("We have this user!!");
        }
        if(student != null){
            userEntity.setStudent(student);
        }
        if(lecturer != null){
            userEntity.setLecturer(lecturer);
        }
        if(admin != null){
            userEntity.setAdmin(admin);
        }
        userDataService.save(userEntity);
        AuthDTO auth = AuthenticationTokenService.login(userEntity, userMapper, accessTokenSecret);
        String token = auth.getAccessToken();
        UserDTO userDTO = userMapper.userDTOFromUserEntity(userEntity);
        return ResponseEntity.ok(Map.of(
                "accessToken", token,
                "user", userDTO,
                "role", auth.getUser().getRegistrationType()
        ));
    }
}


