package com.example.gamestore.entities.gameEntities;

public class DeleteGameDTO {

    private final int id;

    public DeleteGameDTO(String id) {
        this.id = Integer.parseInt(id);
    }

    public int getId() {
        return id;
    }
}
