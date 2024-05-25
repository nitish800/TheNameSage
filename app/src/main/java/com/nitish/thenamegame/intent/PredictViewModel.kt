package com.nitish.thenamegame.intent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.nitish.thenamegame.model.ApiResponse
import com.nitish.thenamegame.model.PredictionData
import com.nitish.thenamegame.model.PredictionDataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class PredictViewModel: ViewModel() {

    private val okHttpClient = OkHttpClient()
    private val _viewState = MutableStateFlow<PredictionDataState>(PredictionDataState.Loading())
    val viewState: StateFlow<PredictionDataState> get() = _viewState
    private var name:String = ""

    fun setName(name:String){
        this.name = name
        _viewState.value = PredictionDataState.Loading()
        getAge()
        getGender()
        getNation()
        getJoke()
    }

    fun getName(): String {
        return name
    }
    private fun updateState(update: (PredictionData) -> PredictionData) {
        val currentState = (_viewState.value as? PredictionDataState.Success)?.predictionData ?: PredictionData()
        _viewState.value = PredictionDataState.Success(update(currentState))
    }
    fun getAge(){
        viewModelScope.launch {
            try {
                val responde = withContext(Dispatchers.IO){
                    val Request = Request.Builder()
                        .url("https://api.agify.io/?name=$name")
                        .build()
                    val call =okHttpClient.newCall(Request)
                    val respondBody = call.execute().body?.string()

                    val age = respondBody?.substringAfter("\"age\":")?.substringBefore("}")?.trim()
                    age
                }
                updateState { it.copy(age = responde) }

            }catch (e: Exception){
                Log.e("ageError", e.toString())
                _viewState.value = PredictionDataState.Error(e)
            }
        }
    }
    fun getGender(){
        viewModelScope.launch {
            try {
                val responde = withContext(Dispatchers.IO){
                    Log.e("genderError ind","https://api.genderize.io/?name=$name" )
                    val Request = Request.Builder()
                        .url("https://api.genderize.io/?name=$name")
                        .build()
                    val call =okHttpClient.newCall(Request)
                    val respondBody = call.execute().body?.string()
                    return@withContext respondBody?.substringAfter("\"gender\":\"")?.substringBefore("\"")?.trim()

                }
                updateState { it.copy(gender = responde) }

            }catch (e: Exception){
                Log.e("genderError", e.toString())
                _viewState.value = PredictionDataState.Error(e)
            }
        }
    }

    fun getNation(){
        viewModelScope.launch {
            try {
                val responde = withContext(Dispatchers.IO){
                    val Request = Request.Builder()
                        .url("https://api.nationalize.io/?name=$name")
                        .build()
                    val call =okHttpClient.newCall(Request)
                    val respondBody = call.execute().body?.string()
                    val gson = Gson()
                    val response = gson.fromJson(respondBody, ApiResponse::class.java)
                    response.country.getOrNull(0)?.countryId
                }
                updateState { it.copy(nation = responde) }

            }catch (e: Exception){
                Log.e("ageNationr", e.toString())
                _viewState.value = PredictionDataState.Error(e)
            }
        }
    }

    fun getJoke(){
        viewModelScope.launch {
            try {
                val responde = withContext(Dispatchers.IO){
                    val Request = Request.Builder()
                        .url("https://official-joke-api.appspot.com/random_joke")
                        .build()
                    val call =okHttpClient.newCall(Request)
                    val respondBody = call.execute().body?.string()

                    val set = respondBody?.substringAfter("\"setup\":")?.substringBefore(",")?.trim()
                    val punch = respondBody?.substringAfter("\"punchline\":")?.substringBefore(",")?.trim()
                    set + " \n \n \n" + punch
                }
                updateState { it.copy(joke = responde) }

            }catch (e: Exception){
                Log.e("ageJoke", e.toString())
                _viewState.value = PredictionDataState.Error(e)
            }
        }
    }


}



