package com.exemplo.harrypotterapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.exemplo.harrypotterapp.R;

/**
 * Activity principal (Dashboard) com botões de navegação
 */
public class DashboardActivity extends AppCompatActivity {

    private Button btnCharacterById;
    private Button btnListStaff;
    private Button btnStudentsByHouse;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inicializar views
        initializeViews();

        // Configurar listeners
        setupListeners();
    }

    private void initializeViews() {
        btnCharacterById = findViewById(R.id.btnCharacterById);
        btnListStaff = findViewById(R.id.btnListStaff);
        btnStudentsByHouse = findViewById(R.id.btnStudentsByHouse);
        btnExit = findViewById(R.id.btnExit);
    }

    private void setupListeners() {
        // Buscar personagem por ID
        btnCharacterById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CharacterByIdActivity.class);
                startActivity(intent);
            }
        });

        // Listar professores
        btnListStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, StaffActivity.class);
                startActivity(intent);
            }
        });

        // Listar estudantes por casa
        btnStudentsByHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, StudentsByHouseActivity.class);
                startActivity(intent);
            }
        });

        // Sair do aplicativo
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity(); // Fecha todas as activities e sai do app
            }
        });
    }
}
