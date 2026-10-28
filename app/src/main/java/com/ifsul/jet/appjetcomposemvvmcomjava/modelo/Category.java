package com.ifsul.jet.appjetcomposemvvmcomjava.modelo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public record Category(
        @SerializedName("idCategory") String id,
        @SerializedName("strCategory") String str,
        @SerializedName("strCategoryThumb") String strThumb,
        @SerializedName("strCategoryDescription") String strDescription
) implements Serializable {
}
