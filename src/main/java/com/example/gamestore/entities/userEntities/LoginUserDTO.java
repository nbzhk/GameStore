package com.example.gamestore.entities.userEntities;

public class LoginUserDTO {
    private String email;
    private String password;

    // TODO: Validate Email and Password
    public LoginUserDTO(String[] commandData) {
        this.email = commandData[1];
        this.password = commandData[2];
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
