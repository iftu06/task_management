package com.example.demo.controller;

import com.example.demo.Utillity.ErrorMapper;
import com.example.demo.model.Project;
import com.example.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Divineit-Iftekher on 8/8/2017.
 */
@RestController
public class ProjectController {


    @Autowired
    ProjectService projectService;

    @CrossOrigin
    @PostMapping(value = "/project")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public Object save(@Valid @RequestBody Project project, BindingResult res) throws Exception {

        if (res.hasErrors()) {
            return ErrorMapper.mapError(res.getFieldErrors());
        }

        try {
            projectService.save(project);
            return ResponseEntity.ok("Project saved successfully");
        } catch (Exception exp) {
            exp.printStackTrace();
            return ResponseEntity.badRequest().body("Something wrong");
        }

    }

    @CrossOrigin
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_USER')")
    public Object list() throws Exception {
        return projectService.getProjects();
    }

    @CrossOrigin
    @GetMapping(value = "/projects/{id}")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public Object list(@PathVariable Integer id) throws Exception {
        Project project = projectService.getProject(id);
        if (project != null) {
            return ResponseEntity.ok(project);
        } else {
            return ResponseEntity.badRequest().body("Not Found");
        }
    }


    @CrossOrigin
    @DeleteMapping(value = "/project/delete/{id}")
    //@PreAuthorize("hasRole('ROLE_USER')")
    public Object delete(@PathVariable Integer id) throws Exception {
        try {
            projectService.remove(id);
            return ResponseEntity.ok().body("Project Deleted Successfully");
        } catch (Exception exp) {
            return ResponseEntity.badRequest().body("Something wrong.Please try again");
        }
    }


}
