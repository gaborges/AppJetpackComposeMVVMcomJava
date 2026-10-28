package com.ifsul.jet.appjetcomposemvvmcomjava.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.Category;
import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.CategoryApiResponse;
import com.ifsul.jet.appjetcomposemvvmcomjava.utils.CategoryUiState;
import com.ifsul.jet.appjetcomposemvvmcomjava.ws.ApiCliente;
import com.ifsul.jet.appjetcomposemvvmcomjava.ws.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModelJava extends ViewModel {

    private final MutableLiveData<CategoryUiState> uiState = new MutableLiveData<>(new CategoryUiState.Loading());
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public CategoryViewModelJava() {
        fetchCategories();
    }

    public LiveData<CategoryUiState> getUiState() {
        return uiState;
    }

    public void fetchCategories() {
        uiState.postValue(new CategoryUiState.Loading());

        // fazer a chamada de webservice
        // wget  https://www.themealdb.com/api/json/v1/1/categories.php
        Call<CategoryApiResponse> wsCall = RetrofitClient.getInstance().getMyApi().getCategories(ApiCliente.API_KEY);

        executor.execute(() -> {
            try {
                //Thread.sleep(1500);

                List<Category> mockList = new ArrayList<>(Arrays.asList(
                        new Category("1", "Beef", "https://www.themealdb.com/images/category/beef.png", "Beef description"),
                        new Category("2", "Chicken", "https://www.themealdb.com/images/category/chicken.png", "Chicken description"),
                        new Category("3", "Dessert", "https://www.themealdb.com/images/category/dessert.png", "Dessert description")
                ));

                wsCall.enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<CategoryApiResponse> call, Response<CategoryApiResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Just use the real data directly
                            List<Category> realCategories = response.body().categories();
                            uiState.postValue(new CategoryUiState.Success(realCategories));
                        } else {
                            uiState.postValue(new CategoryUiState.Error("Failed to fetch data"));
                        }
                    }

                    @Override
                    public void onFailure(Call<CategoryApiResponse> call, Throwable t) {
                        Log.e("VM_CATEGORY", t.getLocalizedMessage(), t);
                        uiState.postValue(new CategoryUiState.Error(t.getLocalizedMessage()));
                    }
                });

            } catch (Exception e) {
                String errorMessage = e.getLocalizedMessage() != null ? e.getLocalizedMessage() : "Failed to load categories";
                uiState.postValue(new CategoryUiState.Error(errorMessage));
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executor.shutdown();
    }
}
