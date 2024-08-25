package com.travel.userservice.controller;

import com.travel.userservice.authentification.JwtTokenUtil;
import com.travel.userservice.dto.AuthRequest;
import com.travel.userservice.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @PostMapping()
    public String createAuthToken(@RequestBody AuthRequest authRequest) throws Exception{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        final UserDetails userDetails = customUserDetailService.loadUserByUsername(authRequest.getUsername());
        return jwtTokenUtil.generateToken(userDetails.getUsername());
    }
}
