package com.ifsul.jet.appjetcomposemvvmcomjava.ws;

import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.Category;
import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.CategoryApiResponse;
import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.MealApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiCliente {
    public static final String API_KEY = "1";
    public static final String BASE_URL = "https://www.themealdb.com/api/json/v1/";

    @GET("{api_key}/categories.php")
    Call<CategoryApiResponse> getCategories(@Path("api_key")String apiKey);

    @GET("{api_key}/search.php?s=")
    Call<MealApiResponse> getMeals(@Path("api_key")String apiKey,@Query("s") String filter);
}
