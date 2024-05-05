package com.example.eback.serviceimpl;

import com.example.eback.entity.User;
import com.example.eback.entity.UserAuth;
import com.example.eback.repository.UserAuthRepository;
import com.example.eback.repository.UserRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserAuthRepository userAuthRepository;


//    @BeforeEach
//    void addUser4Use(){
//        System.out.println("执行前置数据插入");
//        UserAuth newUserAuth = new UserAuth();
//        newUserAuth.setId(777);
//        newUserAuth.setUserId(777);
//        newUserAuth.setPassword("test");
//
//
//        User newUser = new User();
//        newUser.setUsername("test777");
//        newUser.setUserId(777);
//        newUser.setEmail("test777@qq.com");
//        newUser.setUserAuth(newUserAuth);
//
//        userRepository.save(newUser);
//        userAuthRepository.save(newUserAuth);
//    }
//    @AfterEach
//    void deleteUser4Use(){
//        System.out.println("执行后置数据删除");
//        UserAuth newUserAuth = new UserAuth();
//        newUserAuth.setId(777);
//        newUserAuth.setUserId(777);
//        newUserAuth.setPassword("test");
//
//
//        User newUser = new User();
//        newUser.setUsername("test777");
//        newUser.setUserId(777);
//        newUser.setEmail("test777@qq.com");
//        newUser.setAge(24);
//        newUser.setIcon("");
//        newUser.setIntroduction("");
//        newUser.setUserAuth(newUserAuth);
//
//        userAuthRepository.delete(newUserAuth);
//        userRepository.delete(newUser);
//    }

    @Test
    void adduser() {
        Map<String, String> map = new HashMap<>();
        map.put("username", "test888");
        map.put("password", "test888");
        map.put("email", "test888@qq.com");
        userService.adduser(map);
    }

    @Test
    void check() {
        userService.check("test888", "test888");
    }

    @Test
    void getalluser() {
        userService.getalluser();
    }

    @Test
    void banuser() {
//        UserAuth newUserAuth = new UserAuth();
//        newUserAuth.setId(777);
//        newUserAuth.setUserId(777);
//        newUserAuth.setPassword("test777");

//        User newUser = new User();
//        newUser.setUsername("test888");
//        newUser.setEmail("test888@qq.com");
//        newUser.setAuthority(1);

        User user = userRepository.findByUsername("test888");
        userService.banuser(user);
//        userRepository.delete(newUser);
    }

    @Test
    void unbanuser() {
//        UserAuth newUserAuth = new UserAuth();
//        // unique
//        newUserAuth.setId(777);
//        newUserAuth.setUserId(777);
//        // unique
//
//        newUserAuth.setPassword("test777");
//
//
//        User newUser = new User();
//        newUser.setUsername("test777");
//        // unique
//        newUser.setUserId(777);
//
//        newUser.setEmail("test777@qq.com");
//        newUser.setAge(24);
//        newUser.setIcon("");
//        newUser.setIntroduction("");
//        newUser.setUserAuth(newUserAuth);
        User user = userRepository.findByUsername("test888");
        UserAuth userAuth = user.getUserAuth();
        userService.unbanuser(user);

        userAuthRepository.delete(userAuth);
        userRepository.delete(user);

    }
}