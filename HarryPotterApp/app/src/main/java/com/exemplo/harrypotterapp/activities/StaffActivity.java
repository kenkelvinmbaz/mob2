package com.exemplo.harrypotterapp.activities;

import android.os.Bundle;
import android.view.View;
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
 * Activity para listar todos os professores de Hogwarts
 */
public class StaffActivity extends AppCompatActivity {

    private TextView tvStaffList;
    private ProgressBar progressBar;
    private HarryPotterApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        // Inicializar views
        initializeViews();

        // Inicializar serviço da API
        apiService = RetrofitClient.getApiService();

        // Carregar lista de professores
        loadStaff();
    }

    private void initializeViews() {
        tvStaffList = findViewById(R.id.tvStaffList);
        progressBar = findViewById(R.id.progressBar);
    }

    private void loadStaff() {
        // Mostrar progress bar
        progressBar.setVisibility(View.VISIBLE);
        tvStaffList.setText(R.string.loading);

        // Fazer chamada à API
        Call<List<Character>> call = apiService.getStaff();
        
        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Character> staffList = response.body();
                    displayStaffList(staffList);
                } else {
                    tvStaffList.setText(R.string.error_general);
                    Toast.makeText(StaffActivity.this, 
                            R.string.error_general, 
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvStaffList.setText(R.string.error_network);
                Toast.makeText(StaffActivity.this, 
                        R.string.error_network + ": " + t.getMessage(), 
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayStaffList(List<Character> staffList) {
        if (staffList.isEmpty()) {
            tvStaffList.setText("Nenhum professor encontrado.");
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append("Total de professores: ").append(staffList.size()).append("\n\n");
        result.append("═══════════════════════════\n\n");

        int count = 1;
        for (Character staff : staffList) {
            result.append(count++).append(". ");
            result.append(staff.getName() != null ? staff.getName() : "Nome desconhecido");
            
            if (staff.getActor() != null && !staff.getActor().isEmpty()) {
                result.append("\n   Ator: ").append(staff.getActor());
            }
            
            result.append("\n\n");
        }

        tvStaffList.setText(result.toString());
    }
}
