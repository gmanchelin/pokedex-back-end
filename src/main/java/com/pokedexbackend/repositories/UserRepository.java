package com.pokedexbackend.repositories;

import com.pokedexbackend.dto.UserDto;
import com.pokedexbackend.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    User save(UserDto userDto);
}