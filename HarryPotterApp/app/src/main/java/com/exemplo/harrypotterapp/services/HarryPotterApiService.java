package com.exemplo.harrypotterapp.services;

import com.exemplo.harrypotterapp.models.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Interface Retrofit para consumir a HP-API
 * Base URL: https://hp-api.onrender.com/api
 */
public interface HarryPotterApiService {
    
    /**
     * Buscar um personagem específico por ID
     * Endpoint: /api/character/{id}
     */
    @GET("character/{id}")
    Call<List<Character>> getCharacterById(@Path("id") String id);
    
    /**
     * Listar todos os professores (staff) de Hogwarts
     * Endpoint: /api/characters/staff
     */
    @GET("characters/staff")
    Call<List<Character>> getStaff();
    
    /**
     * Listar estudantes de uma casa específica
     * Endpoint: /api/characters/house/{house}
     * Houses: gryffindor, slytherin, hufflepuff, ravenclaw
     */
    @GET("characters/house/{house}")
    Call<List<Character>> getStudentsByHouse(@Path("house") String house);
    
    /**
     * Listar todos os personagens
     * Endpoint: /api/characters
     */
    @GET("characters")
    Call<List<Character>> getAllCharacters();
}
