package com.example.hansrandy_masamba_comp304_002_test02.repository

import com.example.hansrandy_masamba_comp304_002_test02.data.StockDao
import com.example.hansrandy_masamba_comp304_002_test02.data.StockInfo
import kotlinx.coroutines.flow.Flow

class StockRepository(private val stockDao: StockDao) {

    suspend fun insertStock(stockInfo: StockInfo) {
        stockDao.insert(stockInfo)
    }

    suspend fun updateStock(stockInfo: StockInfo) {
        stockDao.update(stockInfo)
    }

    suspend fun deleteStock(stockInfo: StockInfo) {
        stockDao.delete(stockInfo)
    }

    suspend fun getStockBySymbol(symbol: String): StockInfo? {
        return stockDao.getStockBySymbol(symbol)
    }

    fun getAllStockSymbols(): Flow<List<String>> {
        return stockDao.getAllStockSymbols()
    }

    fun getAllStocks(): Flow<List<StockInfo>> {
        return stockDao.getAllStocks()
    }
}
