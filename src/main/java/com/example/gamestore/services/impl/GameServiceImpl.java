package com.example.gamestore.services.impl;

import com.example.gamestore.entities.gameEntities.*;
import com.example.gamestore.exeptions.GameNotFoundException;
import com.example.gamestore.repositories.GameRepository;
import com.example.gamestore.services.GameService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final GameValidator gameValidator;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.gameValidator = new GameValidatorImpl();
    }

    @Override
    public Game add(AddGameDTO gameToAdd) {
        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(new PropertyMap<AddGameDTO, Game>() {
            @Override
            protected void configure() {
                map().setImageThumbnail(source.getThumbnailUrl());
            }
        });

        Game game = mapper.map(gameToAdd, Game.class);

        return this.gameRepository.save(game);
    }

    @Override
    public String delete(DeleteGameDTO gameToDelete) throws GameNotFoundException {

        Game game = this.gameRepository.findById(gameToDelete.getId());

        if (game == null){
            throw new GameNotFoundException("Game does not exist.");
        }
        String title = game.getTitle();
        this.gameRepository.delete(game);

        return title;
    }

    @Override
    public String edit(int id, String[] values) throws GameNotFoundException {
        Game game = this.gameRepository.findById(id);

        if (game == null) {
            throw new GameNotFoundException("Game not found.");
        }

        for (String value : values) {
            String[] valueData = value.split("=");
            String valueName = valueData[0];
            switch (valueName) {
                case "title" -> updateTitle(valueData[1], game.getId());
                case "price" -> updatePrice(valueData[1], game.getId());
                case "size" -> updateSize(valueData[1], game.getId());
                case "trailer" -> updateTrailer(valueData[1], game.getId());
                case "thumbnail" -> updateThumbnail(valueData[1], game.getId());
                case "description" -> updateDescription(valueData[1], game.getId());
            }
        }

        return game.getTitle();
    }

    private void updateTitle(String newTitle, int id) {
        this.gameValidator.validateTitle(newTitle);
        this.gameRepository.updateTitle(newTitle, id);
    }

    private void updatePrice(String newValue, int id) {
        this.gameValidator.validatePrice(newValue);
        BigDecimal newPrice = new BigDecimal(newValue);
        this.gameRepository.updatePrice(newPrice, id);
    }

    private void updateSize(String size, int id) {
        this.gameValidator.validateSize(size);
        double newSize = Double.parseDouble(size);
        this.gameRepository.updateSize(newSize, id);
    }

    private void updateTrailer(String newValue, int id) {
        this.gameValidator.validateTrailer(newValue);
        this.gameRepository.updateTrailer(newValue, id);
    }

    private void updateThumbnail(String newValue, int id) {
        this.gameValidator.validateThumbnail(newValue);
        this.gameRepository.updateThumbnail(newValue, id);
    }

    private void updateDescription(String newDescription, int id) {
        this.gameValidator.validateDescription(newDescription);
        this.gameRepository.updateDescription(newDescription, id);
    }
}
