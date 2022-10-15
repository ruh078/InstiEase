package com.dbms.insti.service;

import com.dbms.insti.models.Users;
import com.dbms.insti.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserDao userdao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userdao) {
        this.userdao = userdao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userdao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username");
        }
        List<GrantedAuthority> grantList = new ArrayList<>();
        String role = Integer.toString(user.getRole());
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        grantList.add(authority);

        System.out.println(grantList);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail_id(), user.getPsw(), grantList
        );

    }
}