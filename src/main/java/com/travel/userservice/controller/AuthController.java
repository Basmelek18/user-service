package com.travel.userservice.controller;

import com.travel.userservice.authentification.JwtTokenUtil;
import com.travel.userservice.dto.AuthRequest;
import com.travel.userservice.dto.JwtResponce;
import com.travel.userservice.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailService customUserDetailService;

    @PostMapping()
    public JwtResponce createAuthToken(@RequestBody AuthRequest authRequest) throws Exception{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword())
        );

        final UserDetails userDetails = customUserDetailService.loadUserByUsername(authRequest.getLogin());
        return new JwtResponce(jwtTokenUtil.generateToken(userDetails.getUsername()));
    }
}
