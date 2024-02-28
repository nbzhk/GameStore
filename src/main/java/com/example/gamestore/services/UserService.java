package com.example.gamestore.services;

import com.example.gamestore.entities.userEntities.LoginUserDTO;
import com.example.gamestore.entities.userEntities.RegisterUserDTO;
import com.example.gamestore.entities.userEntities.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User register(RegisterUserDTO command);

    User login(LoginUserDTO command);
}
