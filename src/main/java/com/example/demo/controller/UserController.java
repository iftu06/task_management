package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {


    BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService userService;

    @Autowired
    UserController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

//
//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping(value = "/registration", consumes = "application/json", produces = "application/json")
//    @ResponseBody
//    public Object createNewUser(@Valid @RequestBody User user, BindingResult bindingResult, HttpServletRequest request) throws JsonProcessingException {
//        User userExists = userService.findByUserName(user.getUserName());
//        if (userExists != null) {
//            return ResponseContanier.builder().status("error")
//                    .response(Arrays.asList(Error.builder().field("userName").message("User already Exist").build()))
//                    .build();
//        }
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//
//        if (bindingResult.hasErrors()) {
//            List<Error> errors = bindingResult.getFieldErrors().stream().map(error ->
//                    Error.builder().field(error.getField()).message(error.getDefaultMessage()).build())
//                    .collect(Collectors.toList());
//
//            return ResponseContanier.builder().status("error").response(errors).build();
//
//        } else {
//            userService.saveUser(user);
//            return ResponseContanier.builder().status("success")
//                    .response("Successfully Inserted")
//                    .build();
//        }
//    }
//
//
//    @CrossOrigin
//    @GetMapping(value = "/users")
//    public Object list(@RequestParam(required = false) String seachParam, @RequestParam(name = "pageNum",defaultValue = "1",required = false) Integer pageNum, HttpServletRequest req) throws Exception {
//        return this.list(seachParam, pageNum);
//    }

//    @CrossOrigin
//    @GetMapping(value = "/users")
//    public Object list(@RequestParam String params, @RequestParam("pageNum") Integer pageNum, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        return userService.list();
//    }

//    @CrossOrigin
//    @GetMapping(value = "/users")
//    public Object list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        return userService.list();
//    }

//    @CrossOrigin
//    @GetMapping(value = "/users/{id}")
//    public Object getUserById(@PathVariable Integer id, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//        User user = userService.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"+id));
//        return user;
//    }

    @CrossOrigin
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(@RequestParam String params) throws Exception {
        System.out.println("login" + params);
        return null;
    }


}
