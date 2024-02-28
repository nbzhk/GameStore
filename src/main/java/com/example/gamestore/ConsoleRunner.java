package com.example.gamestore;

import com.example.gamestore.entities.userEntities.RegisterUserDTO;
import com.example.gamestore.entities.userEntities.User;
import com.example.gamestore.exeptions.ValidationException;
import com.example.gamestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;



@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    @Autowired
    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }

    private String execute(String command) {
        String[] commandData = command.split("\\|");

        String commandName = commandData[0];


        String commandOutput =
                switch (commandName) {
                    case "RegisterUser" -> {
                        RegisterUserDTO registerUser = new RegisterUserDTO(commandData);

                        User user = userService.register(registerUser);
                        yield String.format("%s was registered", user.getFullName());
                    }

                    default -> "invalid command";
        };

        return commandOutput;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        String command = scanner.nextLine();

        try {
            System.out.println(execute(command));
        } catch (ValidationException e) {
            System.out.println(e.getMessage());
        }
    }
}

