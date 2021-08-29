package com.example.demo.Utillity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {


    public static List<String> getAuthorities(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());
    }

    public static String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  (String) authentication.getPrincipal();
    }

    public static boolean isAdmin(){
        List<String> authorities = getAuthorities();
        return authorities.contains("ADMIN");
    }

//    public static UserDetails getPrincipal(){
//        return (UserDetails) authentication.getPrincipal();
//    }

}
