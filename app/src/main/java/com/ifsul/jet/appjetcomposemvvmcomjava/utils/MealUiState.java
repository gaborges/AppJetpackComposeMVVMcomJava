package com.ifsul.jet.appjetcomposemvvmcomjava.utils; // Adjust package as needed

import com.ifsul.jet.appjetcomposemvvmcomjava.modelo.Meal;
import java.util.List;

public sealed interface MealUiState permits
        MealUiState.Loading,
        MealUiState.Success,
        MealUiState.Error {

    record Loading() implements MealUiState {}

    record Success(List<Meal> meals) implements MealUiState {}

    record Error(String message) implements MealUiState {}
}