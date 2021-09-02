package com.task.management.service;

import com.task.management.model.User;
import com.task.management.repository.RoleRepository;
import com.task.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 * Created by Divineit-Iftekher on 2/16/2018.
 */
@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
//
//    @Autowired
//    UserService(UserRepository userRepository, RoleRepository roleRepository) {
//        super(userRepository, "User");
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//    }
//
//
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
//
//    public User findByEmail(String email) {
//        return userRepository.findByUserName(email);
//    }
//
//    @Transactional
//    public void saveUser(User user) {
//        Role role = roleRepository.findByName("ROLE_USER");
//        user.getRoles().add(role);
//        user.setActive(1);
//        this.userRepository.save(user);
//    }
//
//    public List<User> list() {
//        if(userRepository == null){
//            System.out.println("why i am null");
//        }
//        List<User> users = userRepository.findAll();
//        return users;
//    }
//
//    public Optional<User> findUserById(Integer id) {
//        Optional<User> user = userRepository.findById(id);
//        return user;
//    }
//
//    public void getUser(){
//       CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//       CriteriaQuery <User>criteriaQuery = criteriaBuilder.createQuery(User.class);
//       Root<User> root = criteriaQuery.from(User.class);
//    }


}
