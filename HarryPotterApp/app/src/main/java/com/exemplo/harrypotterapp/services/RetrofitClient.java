package com.exemplo.harrypotterapp.services;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Configuração centralizada do Retrofit para a HP-API
 */
public class RetrofitClient {
    
    private static final String BASE_URL = "https://hp-api.onrender.com/api/";
    private static Retrofit retrofit = null;
    
    /**
     * Retorna uma instância configurada do Retrofit
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            // Configurar interceptor de logging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            // Configurar cliente HTTP
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            
            // Criar instância do Retrofit
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    
    /**
     * Retorna uma instância do serviço da HP-API
     */
    public static HarryPotterApiService getApiService() {
        return getClient().create(HarryPotterApiService.class);
    }
}
