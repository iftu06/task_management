package com.task.management.service;

import com.task.management.model.Role;
import com.task.management.repository.RoleRepository;
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
