package com.example.gamestore.services;

import com.example.gamestore.entities.gameEntities.AddGameDTO;
import com.example.gamestore.entities.gameEntities.DeleteGameDTO;
import com.example.gamestore.entities.gameEntities.Game;
import com.example.gamestore.exeptions.GameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {

    Game add(AddGameDTO game);
    String delete(DeleteGameDTO game) throws GameNotFoundException;

    String edit(int id, String[] values) throws GameNotFoundException;

    List<Game> allGames();

    Game findByTitle(String name);
}
