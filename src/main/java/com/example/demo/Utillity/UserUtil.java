package com.example.demo.Utillity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class UserUtil {

    private static final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    public static List<String> getAuthorities(){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return user.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .collect(Collectors.toList());
    }

    public static String getUserName(){
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return user.getUsername();
    }

    public static boolean isAdmin(){
        List<String> authorities = getAuthorities();
        return authorities.contains("ADMIN");
    }

}
