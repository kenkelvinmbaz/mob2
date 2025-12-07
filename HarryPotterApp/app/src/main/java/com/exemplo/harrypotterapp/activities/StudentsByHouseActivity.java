package com.exemplo.harrypotterapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Activity para listar estudantes de uma casa específica
 */
public class StudentsByHouseActivity extends AppCompatActivity {

    private RadioGroup rgHouses;
    private RadioButton rbGryffindor;
    private RadioButton rbSlytherin;
    private RadioButton rbHufflepuff;
    private RadioButton rbRavenclaw;
    private Button btnSearchStudents;
    private TextView tvStudentsList;
    private ProgressBar progressBar;
    private HarryPotterApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_by_house);

        // Inicializar views
        initializeViews();

        // Inicializar serviço da API
        apiService = RetrofitClient.getApiService();

        // Configurar listener do botão
        setupSearchButton();
    }

    private void initializeViews() {
        rgHouses = findViewById(R.id.rgHouses);
        rbGryffindor = findViewById(R.id.rbGryffindor);
        rbSlytherin = findViewById(R.id.rbSlytherin);
        rbHufflepuff = findViewById(R.id.rbHufflepuff);
        rbRavenclaw = findViewById(R.id.rbRavenclaw);
        btnSearchStudents = findViewById(R.id.btnSearchStudents);
        tvStudentsList = findViewById(R.id.tvStudentsList);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupSearchButton() {
        btnSearchStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = rgHouses.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(StudentsByHouseActivity.this, 
                            R.string.error_select_house, 
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                String houseName = getHouseName(selectedId);
                searchStudentsByHouse(houseName);
            }
        });
    }

    private String getHouseName(int radioButtonId) {
        if (radioButtonId == R.id.rbGryffindor) {
            return "gryffindor";
        } else if (radioButtonId == R.id.rbSlytherin) {
            return "slytherin";
        } else if (radioButtonId == R.id.rbHufflepuff) {
            return "hufflepuff";
        } else if (radioButtonId == R.id.rbRavenclaw) {
            return "ravenclaw";
        }
        return "";
    }

    private void searchStudentsByHouse(String house) {
        // Mostrar progress bar
        progressBar.setVisibility(View.VISIBLE);
        tvStudentsList.setText(R.string.loading);

        // Fazer chamada à API
        Call<List<Character>> call = apiService.getStudentsByHouse(house);
        
        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Character> students = response.body();
                    displayStudentsList(students, house);
                } else {
                    tvStudentsList.setText(R.string.error_general);
                    Toast.makeText(StudentsByHouseActivity.this, 
                            R.string.error_general, 
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                tvStudentsList.setText(R.string.error_network);
                Toast.makeText(StudentsByHouseActivity.this, 
                        R.string.error_network + ": " + t.getMessage(), 
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void displayStudentsList(List<Character> students, String house) {
        if (students.isEmpty()) {
            tvStudentsList.setText("Nenhum estudante encontrado para " + house + ".");
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append("Casa: ").append(capitalizeFirstLetter(house)).append("\n");
        result.append("Total de estudantes: ").append(students.size()).append("\n\n");
        result.append("═══════════════════════════\n\n");

        int count = 1;
        for (Character student : students) {
            result.append(count++).append(". ");
            result.append(student.getName() != null ? student.getName() : "Nome desconhecido");
            
            if (student.getActor() != null && !student.getActor().isEmpty()) {
                result.append("\n   Ator: ").append(student.getActor());
            }
            
            result.append("\n\n");
        }

        tvStudentsList.setText(result.toString());
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
