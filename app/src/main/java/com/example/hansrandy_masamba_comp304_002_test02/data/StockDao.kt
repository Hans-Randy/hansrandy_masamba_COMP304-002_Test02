package com.example.hansrandy_masamba_comp304_002_test02.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stockInfo: StockInfo)

    @Update
    suspend fun update(stockInfo: StockInfo)

    @Delete
    suspend fun delete(stockInfo: StockInfo)

    @Query("SELECT * FROM stock_info WHERE stockSymbol = :symbol")
    suspend fun getStockBySymbol(symbol: String): StockInfo?

    @Query("SELECT stockSymbol FROM stock_info")
    fun getAllStockSymbols(): Flow<List<String>>

    @Query("SELECT * FROM stock_info")
    fun getAllStocks(): Flow<List<StockInfo>>
}
