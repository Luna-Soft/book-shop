package com.zutode.bookshopclone.auth.application.controller;

import com.zutode.bookshopclone.auth.application.dto.AuthRequestDto;
import com.zutode.bookshopclone.auth.application.dto.BearerTokenDto;
import com.zutode.bookshopclone.auth.application.dto.SignUpRequestDto;
import com.zutode.bookshopclone.auth.application.dto.UserDetailedDto;
import com.zutode.bookshopclone.auth.domain.model.AuthRequest;
import com.zutode.bookshopclone.auth.domain.model.BearerToken;
import com.zutode.bookshopclone.auth.domain.model.SignUpRequest;
import com.zutode.bookshopclone.auth.domain.model.entity.UserAccount;
import com.zutode.bookshopclone.auth.domain.service.UserService;
import com.zutode.bookshopclone.auth.domain.util.JwtUtil;
import com.zutode.bookshopclone.shop.application.exception.NoAuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;


    @GetMapping("/")
    public String welcome(Authentication authentication) {
        UserAccount principal = (UserAccount) authentication.getPrincipal();
        return "Welcome " + principal.getUsername();
    }

    @PostMapping("signUp")
    public BearerTokenDto createNewUser(@RequestBody SignUpRequestDto userDto){
        SignUpRequest user = modelMapper.map(userDto, SignUpRequest.class);
        UserDetails userDetails = userService.addNewUser(user);
        return modelMapper.map(jwtUtil.generateToken(userDetails.getUsername()), BearerTokenDto.class);
    }

    @PostMapping("/authenticate")
    @SneakyThrows
    public BearerTokenDto generateToken(@RequestBody AuthRequestDto authRequest) {
        AuthRequest auth = modelMapper.map(authRequest, AuthRequest.class);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword())
            );
            BearerToken source = jwtUtil.generateToken(auth.getUsername());
            return modelMapper.map(source, BearerTokenDto.class);
        } catch (Exception e) {
            throw new NoAuthenticationException("Invalidate credentials");
        }
    }


}
