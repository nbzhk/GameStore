package com.example.gamestore;

import com.example.gamestore.entities.gameEntities.AddGameDTO;
import com.example.gamestore.entities.gameEntities.DeleteGameDTO;
import com.example.gamestore.entities.gameEntities.Game;
import com.example.gamestore.entities.userEntities.LoginUserDTO;
import com.example.gamestore.entities.userEntities.RegisterUserDTO;
import com.example.gamestore.entities.userEntities.User;
import com.example.gamestore.exeptions.GameNotFoundException;
import com.example.gamestore.exeptions.UserValidationException;
import com.example.gamestore.exeptions.ValidationException;
import com.example.gamestore.services.GameService;
import com.example.gamestore.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;


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
                isRunning = false;

                validateLoggedUser();

                this.userService.logout();
                yield "Logged out";
            }
            case "AddGame" -> {
                validateLoggedUser();
                checkAdministratorRights();
                AddGameDTO addGame = new AddGameDTO(commandData);

                Game game = gameService.add(addGame);

                yield String.format("Added %s", game.getTitle());
            }
            case "EditGame" -> {
                validateLoggedUser();
                checkAdministratorRights();

                String[] editData = new String[commandData.length - 2];
                System.arraycopy(commandData, 2, editData, 0, editData.length);

                String title = this.gameService.edit(Integer.parseInt(commandData[1]), editData);

                yield String.format("Edited %s", title);
            }
            case "DeleteGame" -> {
                validateLoggedUser();
                checkAdministratorRights();

                DeleteGameDTO deleteGame = new DeleteGameDTO(commandData[1]);

                String title = this.gameService.delete(deleteGame);

                yield String.format("Deleted %s", title);
            }
            //TODO: finish purchase logic
            case "purchaseGame" -> {
                validateLoggedUser();
                Set<Game> games =  Arrays.stream(commandData).skip(1)
                      .map(this.gameService::findByTitle)
                      .collect(Collectors.toSet());
                this.userService.purchaseGames(games);
                yield "";
            }
            case "AllGames" -> {
                List<Game> allGames = this.gameService.allGames();

                StringBuilder sb = new StringBuilder();
                allGames.forEach(g -> sb.append(String.format("%s %.2f%n", g.getTitle(), g.getPrice())));

                yield sb.toString();
            }
            case "DetailGame" -> {
                Game game = this.gameService.findByTitle(commandData[1]);

                if (game == null) {
                    throw new GameNotFoundException("No such game.");
                }

                yield String.format("Title: %s%nPrice: %.2f%nDescription: %s%nRelease date: %s",
                        game.getTitle(), game.getPrice(), game.getDescription(), game.getReleaseDate());

            }
            default -> "invalid command";
        };
    }

    private void checkAdministratorRights() throws UserValidationException {
        if (!this.userService.getCurrentLoggedUser().isAdministrator()) {
            throw new UserValidationException("You don't have rights to add/change games");
        }
    }

    private void validateLoggedUser() throws UserValidationException {
        if (this.userService.getCurrentLoggedUser() == null) {
            throw new UserValidationException("There is no user currently logged in.");
        }
    }

    @Override
    public void run(String... args) throws ValidationException {
        while (isRunning) {
            Scanner scanner = new Scanner(System.in);

            String command = scanner.nextLine();

            try {
                System.out.println(execute(command));
            } catch (ValidationException | GameNotFoundException | UserValidationException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

