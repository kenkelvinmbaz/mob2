package com.exemplo.harrypotterapp.models;

import com.google.gson.annotations.SerializedName;

public class Character {
    @SerializedName("id")
    private String id;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("house")
    private String house;
    
    @SerializedName("patronus")
    private String patronus;
    
    @SerializedName("actor")
    private String actor;
    
    @SerializedName("alive")
    private boolean alive;
    
    @SerializedName("image")
    private String image;

    // Construtores
    public Character() {}

    public Character(String id, String name, String house) {
        this.id = id;
        this.name = name;
        this.house = house;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getPatronus() {
        return patronus;
    }

    public void setPatronus(String patronus) {
        this.patronus = patronus;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", house='" + house + '\'' +
                ", patronus='" + patronus + '\'' +
                ", actor='" + actor + '\'' +
                ", alive=" + alive +
                '}';
    }
}
