package com.ifsul.jet.appjetcomposemvvmcomjava.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.Meal;
import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.MealApiResponse;
import com.ifsul.jet.appjetcomposemvvmcomjava.utils.MealUiState;
import com.ifsul.jet.appjetcomposemvvmcomjava.ws.ApiCliente;
import com.ifsul.jet.appjetcomposemvvmcomjava.ws.RetrofitClient;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealViewModel extends ViewModel {

    private final MutableLiveData<MealUiState> uiState = new MutableLiveData<>(new MealUiState.Loading());

    public MealViewModel() {
        // You can fetch initial data here, or wait until the UI requests it
        buscarRefeicoes("");
    }

    public LiveData<MealUiState> getUiState() {
        return uiState;
    }

    // You can pass a search query or a category name to this method
    public void buscarRefeicoes(String searchQuery) {
        uiState.postValue(new MealUiState.Loading());

        // Assuming you have a method like searchMeals(String query) in your ApiCliente
        Call<MealApiResponse> wsCall = RetrofitClient.getInstance().getMyApi().getMeals(ApiCliente.API_KEY,searchQuery);

        wsCall.enqueue(new Callback<MealApiResponse>() {
            @Override
            public void onResponse(Call<MealApiResponse> call, Response<MealApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Meal> realMeals = response.body().meals();

                    // TheMealDB API returns null for the meals array if no results are found
                    if (realMeals != null && !realMeals.isEmpty()) {
                        uiState.postValue(new MealUiState.Success(realMeals));
                    } else {
                        uiState.postValue(new MealUiState.Error("No meals found."));
                    }
                } else {
                    uiState.postValue(new MealUiState.Error("Failed to fetch meals. Error code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<MealApiResponse> call, Throwable t) {
                Log.e("VM_MEAL", "Error fetching meals: " + t.getLocalizedMessage(), t);
                String errorMsg = t.getLocalizedMessage() != null ? t.getLocalizedMessage() : "Unknown network error";
                uiState.postValue(new MealUiState.Error(errorMsg));
            }
        });
    }
}