package com.adyen.android.assignment.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adyen.android.assignment.api.ApiState
import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * This ViewModel class manages data and actions related to Astronomy Pictures for the application.
 */
class MainViewModel: ViewModel()  {

    // StateFlow representing the current API state (Loading, Success, or Error)
    private val _apodApiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val apodApiState: StateFlow<ApiState> = _apodApiState.asStateFlow()

    // StateFlow representing the list of recently fetched Astronomy Pictures
    private val _recentPictures = MutableStateFlow<List<AstronomyPicture>>(emptyList())
    val recentPictures: StateFlow<List<AstronomyPicture>> = _recentPictures.asStateFlow()

    // StateFlow representing the currently selected Astronomy Picture
    private val _selectedPicture = MutableStateFlow<AstronomyPicture?>(null)
    val selectedPicture: StateFlow<AstronomyPicture?> = _selectedPicture.asStateFlow()

    init {
        fetchPictures()
    }

    //on retry, fetch pictures again
    fun retry() {
        fetchPictures()
    }

    // Set the selected picture
    fun selectPicture(astronomyPicture: AstronomyPicture) {
        _selectedPicture.value = astronomyPicture
    }

    // Reset the selected picture
    fun resetSelectedPicture() {
        _selectedPicture.value = null
    }

    // Sort the list of pictures by title
    fun sortTitleWise(){
        //todo: upcoming feature
        _recentPictures.value = _recentPictures.value.sortedBy { it.title }
    }

    // Sort the list of pictures by date
    fun sortDateWise(){
        //todo: upcoming feature
        _recentPictures.value = _recentPictures.value.sortedByDescending { it.date }
    }

    fun addFavourite(astronomyPicture: AstronomyPicture){
        //todo: upcoming feature
    }

    fun removeFavourite(astronomyPicture: AstronomyPicture){
        //todo: upcoming feature
    }

    // Fetch the list of Astronomy Pictures from the API
    private fun fetchPictures() {
        val apiInterface = PlanetaryService.instance
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //loading state before calling API
                _apodApiState.value = ApiState.Loading
                val response = apiInterface.getPictures() // API call
                if (response.isSuccessful && response.body() != null && response.body()!!.isNotEmpty()) {
                    //success state with data fetched from API
                    _apodApiState.value = ApiState.Success(response.body())
                    _recentPictures.value = response.body()!!.filter { it.mediaType == "image" }.sortedBy { it.title }
                } else {
                    //error state with error message
                    _apodApiState.value = ApiState.Error(Constants.ERROR_TITLE_EXCEPTION, Constants.ERROR_MESSAGE_EXCEPTION)
                }
            } catch (e: Exception) {
                //error state with exception message
                Log.d("Exception", "Exception: $e")
                _apodApiState.value =
                    when (e) {
                        //used separate error messages for network related exception
                        is IOException -> ApiState.Error(Constants.ERROR_TITLE_NETWORK, Constants.ERROR_MESSAGE_NETWORK)
                        else -> ApiState.Error(Constants.ERROR_TITLE_EXCEPTION, Constants.ERROR_MESSAGE_EXCEPTION)
                    }
            }
        }
    }
}