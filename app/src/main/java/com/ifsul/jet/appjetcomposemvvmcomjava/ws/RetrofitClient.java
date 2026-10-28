package com.ifsul.jet.appjetcomposemvvmcomjava.ws;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private ApiCliente myApi;

    static HttpLoggingInterceptor logger;

    private RetrofitClient() {

        // configura logs
        logger = new HttpLoggingInterceptor();
        logger.setLevel(Level.BASIC);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logger)
                .build();

        // gera instânica retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiCliente.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // cria a API
        myApi = retrofit.create(ApiCliente.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }


    public ApiCliente getMyApi() {
        return myApi;
    }

}
