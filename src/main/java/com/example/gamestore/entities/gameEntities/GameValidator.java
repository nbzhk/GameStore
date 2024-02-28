package com.example.gamestore.entities.gameEntities;

import java.math.BigDecimal;

public interface GameValidator {

    String TITLE_REGEX = "^[A-Z].{2,99}";
    String TRAILER_REGEX = "^.{11}$";
    String THUMBNAIL_REGEX = "^http://|https://.*";
    String validateTitle(String title);
    BigDecimal validatePrice(String priceLine);
    double validateSize(String sizeLine);
    String validateTrailer(String trailer);
    String validateThumbnail(String thumbnail);
    String validateDescription(String description);
}
