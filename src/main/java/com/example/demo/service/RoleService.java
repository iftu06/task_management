package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * Created by Divineit-Iftekher on 8/12/2017.
 */
@Service
public class RoleService {

    RoleRepository roleRepository;

    public List<Role> roles(){
        return roleRepository.findAll();
    }

}
