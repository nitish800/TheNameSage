package com.nitish.thenamegame.model

import com.google.gson.annotations.SerializedName


data class PredictionData(
        val age: String? = "Predecting...",
        val gender:String? = "Predecting...",
        val nation: String? = "Predecting...",
        val joke: String? = "Predecting...",
    )

sealed class PredictionDataState {
    data class Success(val predictionData: PredictionData) : PredictionDataState()
    data class Loading(val isLoading: Boolean = true) : PredictionDataState()
    data class Error(val error: Throwable) : PredictionDataState()
}

data class Nationality(
    @SerializedName("country_id") val countryId: String,
    @SerializedName("probability") val probability: Double
)

data class ApiResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("name") val name: String,
    @SerializedName("country") val country: List<Nationality>
)