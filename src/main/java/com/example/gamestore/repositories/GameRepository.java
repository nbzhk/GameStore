package com.example.gamestore.repositories;

import com.example.gamestore.entities.gameEntities.Game;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Game findById(int id);

    Game findByTitle(String title);
    @Modifying
    @Transactional
    @Query("UPDATE games g SET g.title = :newValue WHERE g.id = :id")
    void updateTitle(String newValue, int id);
    @Modifying
    @Transactional
    @Query("UPDATE games g SET g.price = :newValue WHERE g.id = :id")
    void updatePrice(BigDecimal newValue, int id);
    @Modifying
    @Transactional
    @Query("UPDATE games g SET g.size = :newValue WHERE g.id = :id")
    void updateSize(double newValue, int id);

    @Modifying
    @Transactional
    @Query("UPDATE games g SET g.trailer = :newValue WHERE g.id = :id")
    void updateTrailer(String newValue, int id);

    @Modifying
    @Transactional
    @Query("UPDATE games g SET g.imageThumbnail = :newValue WHERE g.id = :id")
    void updateThumbnail(String newValue, int id);

    @Modifying
    @Transactional
    @Query("UPDATE games g SET g.description = :newValue WHERE g.id = :id")
    void updateDescription(String newValue, int id);
}
