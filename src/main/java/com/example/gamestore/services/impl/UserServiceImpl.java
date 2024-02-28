package com.example.gamestore.services.impl;

import com.example.gamestore.entities.userEntities.LoginUserDTO;
import com.example.gamestore.entities.userEntities.RegisterUserDTO;
import com.example.gamestore.entities.userEntities.User;
import com.example.gamestore.exeptions.ValidationException;
import com.example.gamestore.repositories.UserRepository;
import com.example.gamestore.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private User currentLoggedUser;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.currentLoggedUser = null;
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterUserDTO registerUser) {
        if (this.userRepository.existsByEmail(registerUser.getEmail())) {
            throw new ValidationException(String.format("There is already User with email: %s",
                    registerUser.getEmail()));
        }

        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(registerUser, User.class);

        if (this.userRepository.count() == 0) {
            user.setAdministrator(true);
        }


        return this.userRepository.save(user);
    }

    @Override
    public User login(LoginUserDTO loginUser) {
        if (this.userRepository.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword()) == null) {
            throw new ValidationException(("Wrong email or password."));
        }

        if (currentLoggedUser != null) {
            throw new ValidationException("User already logged in");
        }

        User user = this.userRepository.findByEmailAndPassword(loginUser.getEmail(), loginUser.getPassword());

        this.currentLoggedUser = user;
        return user;
    }
}

