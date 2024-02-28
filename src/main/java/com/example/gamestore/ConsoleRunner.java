package com.example.gamestore;

import com.example.gamestore.entities.gameEntities.AddGameDTO;
import com.example.gamestore.entities.gameEntities.DeleteGameDTO;
import com.example.gamestore.entities.gameEntities.Game;
import com.example.gamestore.entities.userEntities.LoginUserDTO;
import com.example.gamestore.entities.userEntities.RegisterUserDTO;
import com.example.gamestore.entities.userEntities.User;
import com.example.gamestore.exeptions.GameNotFoundException;
import com.example.gamestore.exeptions.ValidationException;
import com.example.gamestore.services.GameService;
import com.example.gamestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class ConsoleRunner implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;
    private static boolean isRunning = true;

    @Autowired
    public ConsoleRunner(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    private String execute(String command) throws GameNotFoundException {
        String[] commandData = command.split("\\|");

        String commandName = commandData[0];


        return switch (commandName) {
            case "RegisterUser" -> {
                RegisterUserDTO registerUser = new RegisterUserDTO(commandData);

                User user = userService.register(registerUser);
                yield String.format("%s was registered", user.getFullName());
            }
            case "LoginUser" -> {
                LoginUserDTO loginUser = new LoginUserDTO(commandData);

                User user = userService.login(loginUser);

                yield String.format("Successfully logged in %s", user.getFullName());
            }
            case "Logout" -> {
                //TODO: logout user
                //right now this only ends the program, but it should also logout the current user
                isRunning = false;
                yield "Logged out";
            }
            case "AddGame" -> {
                AddGameDTO addGame = new AddGameDTO(commandData);

                Game game = gameService.add(addGame);

                yield String.format("Added %s", game.getTitle());
            }
            case "EditGame" -> {
                //TODO: RETURN ONLY NEEDED ARRAY
                //the whole array is send to the gameService, but the first two arguments are unnecessary
                String title = this.gameService.edit(Integer.parseInt(commandData[1]), commandData);

                yield String.format("Edited %s", title);
            } case "DeleteGame" -> {
                DeleteGameDTO deleteGame = new DeleteGameDTO(commandData[1]);

                String title = this.gameService.delete(deleteGame);

                yield String.format("Deleted %s", title);
            }
            default -> "invalid command";
        };
    }

    @Override
    public void run(String... args) throws ValidationException {
        while (isRunning) {
            Scanner scanner = new Scanner(System.in);

            String command = scanner.nextLine();

            try {
                System.out.println(execute(command));
            } catch (ValidationException | GameNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

