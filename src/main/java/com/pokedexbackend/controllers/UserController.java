package com.pokedexbackend.controllers;

import com.pokedexbackend.configs.CustomUserDetailsService;
import com.pokedexbackend.configs.JwtService;
import com.pokedexbackend.dto.LoggingUserDto;
import com.pokedexbackend.dto.UserDto;
import com.pokedexbackend.models.Trainer;
import com.pokedexbackend.models.User;
import com.pokedexbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final Trainer DEFAULT_TRAINER = new Trainer(0, "MissingNo", "https://upload.wikimedia.org/wikipedia/commons/3/3b/MissingNo.svg");
    public UserController(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoggingUserDto loggingUserDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loggingUserDto.getUsernameOrEmail(),
                            loggingUserDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(loggingUserDto.getUsernameOrEmail());
            String jwt = jwtService.generateToken(userDetails);
            Map<String, String> tokenResponse = new HashMap<>();
            tokenResponse.put("token", jwt);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername()) || userRepository.existsByEmail(userDto.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto.getUsername(), userDto.getEmail(), encodedPassword);
        user.setTrainer(DEFAULT_TRAINER);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}