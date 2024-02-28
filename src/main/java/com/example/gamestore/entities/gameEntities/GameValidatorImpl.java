package com.example.gamestore.entities.gameEntities;

import com.example.gamestore.exeptions.ValidationException;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameValidatorImpl implements GameValidator {

    public GameValidatorImpl() {
    }

    @Override
    public String validateTitle(String title) {
        Pattern pattern = Pattern.compile(TITLE_REGEX);
        Matcher matcher = pattern.matcher(title);

        if (!matcher.find()) {
            throw new ValidationException("Title must start with uppercase letter" +
                    " and must be between 3 and 100 symbols long.");
        }

        return title;
    }
    @Override
    public BigDecimal validatePrice(String priceLine) {
        BigDecimal price = new BigDecimal(priceLine);

        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Price must have a positive value.");
        }

        return price;
    }

    @Override
    public double validateSize(String sizeLine) {
        double size = Double.parseDouble(sizeLine);

        if (size <= 0) {
            throw new ValidationException("Size can not be a negative number.");
        }

        return size;
    }

    @Override
    public String validateTrailer(String trailerId) {
        Pattern pattern = Pattern.compile(TRAILER_REGEX);
        Matcher matcher = pattern.matcher(trailerId);

        if (!matcher.find()) {
            throw new ValidationException("Invalid video ID.");
        }

        return trailerId;
    }

    @Override
    public String validateThumbnail(String thumbnailUrl) {
        Pattern pattern = Pattern.compile(THUMBNAIL_REGEX);
        Matcher matcher = pattern.matcher(thumbnailUrl);

        if (!matcher.find()) {
            throw new ValidationException("Invalid thumbnail URL.");
        }

        return thumbnailUrl;
    }

    @Override
    public String validateDescription(String description) {
        if (description.length() < 20) {
            throw new ValidationException("Description too short.");
        }

        return description;
    }

}
