package com.example.gamestore.entities.gameEntities;

import java.math.BigDecimal;

public class AddGameDTO {

    private final String title;
    private final BigDecimal price;
    private final double size;
    private final String trailer;
    private final String thumbnailUrl;
    private final String description;

    //commandData[0] is the command name and is used in the ConsoleRunner
    public AddGameDTO(String[] commandData) {
        GameValidator gameValidator = new GameValidatorImpl();
        this.title = gameValidator.validateTitle(commandData[1]);
        this.price = gameValidator.validatePrice(commandData[2]);
        this.size = gameValidator.validateSize(commandData[3]);
        this.trailer = gameValidator.validateTrailer(commandData[4]);
        this.thumbnailUrl = gameValidator.validateThumbnail(commandData[5]);
        this.description = gameValidator.validateDescription(commandData[6]);
    }



    public String getTitle() {
        return title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public double getSize() {
        return size;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDescription() {
        return description;
    }
}

