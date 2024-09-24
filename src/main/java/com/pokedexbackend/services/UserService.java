package com.pokedexbackend.services;

import com.pokedexbackend.dto.UserDto;
import com.pokedexbackend.exceptions.AlreadyExistsException;
import com.pokedexbackend.exceptions.NotFoundException;
import com.pokedexbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pokedexbackend.models.User;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public User findByUsername(String username) throws NotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new NotFoundException("Username not found.");
        }
        return userOpt.get();
    }

    public User addUser(UserDto userDto) throws AlreadyExistsException {
        Optional<User> userOpt = userRepository.findByUsername(userDto.getUsername());
        if (userOpt.isPresent()) {
            throw new AlreadyExistsException("Username already taken.");
        }
        User user = new User(userDto.getUsername(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return user;
    }
}
