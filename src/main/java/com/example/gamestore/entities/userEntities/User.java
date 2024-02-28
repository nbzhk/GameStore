package com.example.gamestore.entities.userEntities;

import com.example.gamestore.entities.gameEntities.Game;
import com.example.gamestore.entities.Order;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(name = "full_name",nullable = false)
    private String fullName;
    @ManyToMany
    private Set<Game> games;
    @Column(name = "is_administrator", nullable = false)
    private boolean isAdministrator;
    @OneToMany(targetEntity = Order.class, mappedBy = "buyer")
    private Set<Order> orders;

    public User() {
        games = new HashSet<>();
        orders = new HashSet<>();
    }

    public User(String email, String password, String fullName) {
        this();

        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public boolean isAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(boolean administrator) {
        isAdministrator = administrator;
    }
}
