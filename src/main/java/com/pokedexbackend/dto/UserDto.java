package com.pokedexbackend.dto;

public class UserDto {

    private String username;
    private String email;
    private String img;
    private String password;

    public UserDto(String username, String email, String img, String password) {
        super();
        this.username = username;
        this.email = email;
        this.img = img;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}