package com.pokedexbackend.services;

import com.pokedexbackend.exceptions.AlreadyExistsException;
import com.pokedexbackend.exceptions.NotFoundException;
import com.pokedexbackend.models.Trainer;
import com.pokedexbackend.repositories.TrainerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TrainerService {

    private final TrainerRepository trainerRepository;


    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public Trainer addTrainer(Trainer trainer) throws AlreadyExistsException {
        Optional<Trainer> checkTrainer = trainerRepository.findByName(trainer.getName());
        if (checkTrainer.isPresent()) {
            throw new AlreadyExistsException("Username already taken.");
        }
        trainerRepository.save(trainer);
        return trainer;
    }

    public List<Trainer> getTrainers() {
        return trainerRepository.findAll();
    }

    public Trainer getDefaultTrainer() throws NotFoundException {
        return trainerRepository.findById(0).orElseThrow(() -> new NotFoundException("Default Trainer not found."));
    }
}
