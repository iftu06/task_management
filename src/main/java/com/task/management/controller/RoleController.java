package com.task.management.controller;

import com.task.management.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
