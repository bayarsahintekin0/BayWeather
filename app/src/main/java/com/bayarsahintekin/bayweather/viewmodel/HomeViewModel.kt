package com.bayarsahintekin.bayweather.viewmodel

import androidx.lifecycle.ViewModel
import com.bayarsahintekin.bayweather.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: WeatherRepository) : ViewModel() {

    val weathers = repository.getWeatherByLocation(38.5.toLong(),78.5.toLong())

}