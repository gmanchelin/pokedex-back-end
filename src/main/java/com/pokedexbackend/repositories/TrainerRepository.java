package com.pokedexbackend.repositories;

import com.pokedexbackend.models.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    Optional<Trainer> findById(Integer id);

    Optional<Trainer> findByName(String name);

    List<Trainer> findAll();
}
