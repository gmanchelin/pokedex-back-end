package com.pokedexbackend.models;

import jakarta.persistence.*;


@Entity
@Table(name = "trainers")
public class Trainer {

    @Id
    @Column(name = "id_trainer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTrainer;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "img", nullable = false)
    private String img;

    public Trainer() {
    }

    public Trainer(String name, String img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getImg() { return img; }
}