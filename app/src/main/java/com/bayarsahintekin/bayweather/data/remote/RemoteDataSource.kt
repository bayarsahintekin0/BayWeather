package com.bayarsahintekin.bayweather.data.remote

import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val productService: IService
) : BaseDataSource() {
    suspend fun getWeathersByLocation(lon: Long, lat: Long) =
        getResult { productService.getWeathersByLocation(lng = lon ,lat = lat) }
}