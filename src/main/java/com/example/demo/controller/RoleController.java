package com.example.demo.controller;

import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class RoleController {


  @Autowired
  RoleService roleService;


//  @CrossOrigin
//  @RequestMapping(value = "/roles", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//  public Object save(@RequestBody String payload, HttpServletResponse resp, BindingResult res) throws Exception {
//    return this.save(payload);
//  }
//
//
//  @CrossOrigin
//  @RequestMapping(value = "/roles", method = RequestMethod.GET)
//  public Object list(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//    return roleService.roles();
//  }


}
