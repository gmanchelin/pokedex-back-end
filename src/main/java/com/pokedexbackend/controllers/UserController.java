package com.pokedexbackend.controllers;

import com.pokedexbackend.configs.CustomUserDetailsService;
import com.pokedexbackend.configs.JwtService;
import com.pokedexbackend.dto.LoggingUserDto;
import com.pokedexbackend.dto.UserDto;
import com.pokedexbackend.exceptions.NotFoundException;
import com.pokedexbackend.models.User;
import com.pokedexbackend.repositories.UserRepository;
import com.pokedexbackend.services.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    private final UserRepository userRepository;

    private final TrainerService trainerService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    public UserController(CustomUserDetailsService customUserDetailsService, UserRepository userRepository, TrainerService trainerService, JwtService jwtService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.trainerService = trainerService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{username}")
    public UserDto getLoggedUser(@PathVariable String username) throws NotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new NotFoundException("User not found.");
        }

        return new UserDto(username, user.get().getEmail(), user.get().getTrainer().getImg(), null);
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
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loggingUserDto.getUsernameOrEmail());
            String jwt = jwtService.generateToken(userDetails);
            Map<String, String> tokenResponse = new HashMap<>();
            tokenResponse.put("token", jwt);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) throws NotFoundException {
        if (userRepository.existsByUsername(userDto.getUsername()) || userRepository.existsByEmail(userDto.getEmail())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        userRepository.save(new User(
                        userDto.getUsername(),
                        userDto.getEmail(),
                        passwordEncoder.encode(userDto.getPassword()),
                        trainerService.getDefaultTrainer(),
                    "ROLE_USER"));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}