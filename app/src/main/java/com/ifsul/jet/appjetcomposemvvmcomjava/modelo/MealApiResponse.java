package com.ifsul.jet.appjetcomposemvvmcomjava.modelo;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public record MealApiResponse(
        @SerializedName("meals") List<Meal> meals
) {}