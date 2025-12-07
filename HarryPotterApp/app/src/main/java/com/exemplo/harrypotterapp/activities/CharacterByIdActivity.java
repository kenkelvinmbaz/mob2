package com.exemplo.harrypotterapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.exemplo.harrypotterapp.R;
import com.exemplo.harrypotterapp.models.Character;
import com.exemplo.harrypotterapp.services.HarryPotterApiService;
import com.exemplo.harrypotterapp.services.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity para buscar um personagem específico por ID
 */
public class CharacterByIdActivity extends AppCompatActivity {

    private EditText etCharacterId;
    private Button btnSearch;
    private TextView tvResult;
    private ProgressBar progressBar;
    private HarryPotterApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_by_id);

        // Inicializar views
        initializeViews();

        // Inicializar serviço da API
        apiService = RetrofitClient.getApiService();

        // Configurar listener do botão
        setupSearchButton();
    }

    private void initializeViews() {
        etCharacterId = findViewById(R.id.etCharacterId);
        btnSearch = findViewById(R.id.btnSearch);
        tvResult = findViewById(R.id.tvResult);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupSearchButton() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String characterId = etCharacterId.getText().toString().trim();

                if (characterId.isEmpty()) {
                    Toast.makeText(CharacterByIdActivity.this, 
                            R.string.error_empty_id, 
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                searchCharacterById(characterId);
            }
        });
    }

    private void searchCharacterById(String id) {
        // Mostrar progress bar
        progressBar.setVisibility(View.VISIBLE);
        tvResult.setText(R.string.loading);

        // Fazer chamada à API
        Call<List<Character>> call = apiService.getCharacterById(id);
        
        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Character> characters = response.body();

                    if (!characters.isEmpty()) {
                        Character character = characters.get(0);
                        displayCharacter(character);
                    } else {
                        tvResult.setText(R.string.error_no_character);
                    }
                } else {
                    tvResult.setText(R.string.error_general);
                    Toast.makeText(CharacterByIdActivity.this, 
                            R.string.error_general, 
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvResult.setText(R.string.error_network);
                Toast.makeText(CharacterByIdActivity.this, 
                        R.string.error_network + ": " + t.getMessage(), 
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayCharacter(Character character) {
        StringBuilder result = new StringBuilder();
        result.append("Nome: ").append(character.getName() != null ? character.getName() : "N/A").append("\n\n");
        result.append("Casa: ").append(character.getHouse() != null && !character.getHouse().isEmpty() 
                ? character.getHouse() : "Sem casa").append("\n\n");
        result.append("Ator: ").append(character.getActor() != null ? character.getActor() : "N/A").append("\n\n");
        result.append("Patronus: ").append(character.getPatronus() != null && !character.getPatronus().isEmpty() 
                ? character.getPatronus() : "N/A");

        tvResult.setText(result.toString());
    }
}
