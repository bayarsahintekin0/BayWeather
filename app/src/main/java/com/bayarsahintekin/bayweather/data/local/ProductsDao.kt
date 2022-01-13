package com.bayarsahintekin.bayweather.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bayarsahintekin.bayweather.data.model.WeatherModel

@Dao
interface ProductsDao {

    @Query("SELECT * FROM weathers")
    fun getAllCharacters() : LiveData<List<WeatherModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<WeatherModel>)
}