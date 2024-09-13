package com.example.moviesdbapplication.core

import android.graphics.pdf.PdfDocument.Page
import com.google.gson.annotations.SerializedName

data class WrappedResponse<T>(
    var code: Int,
    @SerializedName("page") var page: Int,
    @SerializedName("results") var data: T? = null
)