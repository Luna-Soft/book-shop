package com.zutode.bookshopclone.auth.domain.service;

import com.zutode.bookshopclone.auth.domain.model.SignUpRequest;
import com.zutode.bookshopclone.auth.domain.model.entity.UserAccount;
import com.zutode.bookshopclone.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityNotFoundException;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find username: " + username));
        return new User(userAccount.getUsername(), userAccount.getPassword(), Collections.emptyList());
    }

    @Transactional
    public UserDetails addNewUser(SignUpRequest user){
        if(user.getPassword() != null && user.getPassword().equals(user.getConfirmPassword())){
            UserAccount userAccount = modelMapper.map(user, UserAccount.class);
            userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
            try{
                UserAccount save = userRepository.save(userAccount);
                return new User(save.getUsername(), save.getPassword(), Collections.emptyList());
            } catch (Exception e) {
                throw new RuntimeException("Something went wrong " + e.getMessage());
            }
        }
        throw new RuntimeException("Something went wrong");
    }

    @Transactional
    public UserAccount getUserDetails(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find user with name: " + username));
    }

    @Transactional
    public UserAccount findUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User by id: " + id + " doest not exist"));

    }



}
