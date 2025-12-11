package com.example.hansrandy_masamba_comp304_002_test02.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hansrandy_masamba_comp304_002_test02.data.StockInfo
import com.example.hansrandy_masamba_comp304_002_test02.repository.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StockViewModel(private val repository: StockRepository) : ViewModel() {

    private val _stockSymbols = MutableStateFlow<List<String>>(emptyList())
    val stockSymbols: StateFlow<List<String>> = _stockSymbols.asStateFlow()

    private val _selectedStock = MutableStateFlow<StockInfo?>(null)
    val selectedStock: StateFlow<StockInfo?> = _selectedStock.asStateFlow()

    init {
        fetchStockSymbols()
    }

    private fun fetchStockSymbols() {
        viewModelScope.launch {
            repository.getAllStockSymbols().collect { symbols ->
                _stockSymbols.value = symbols
            }
        }
    }

    fun insertStock(stockInfo: StockInfo) {
        viewModelScope.launch {
            repository.insertStock(stockInfo)
        }
    }

    fun searchStock(symbol: String) {
        viewModelScope.launch {
            val stock = repository.getStockBySymbol(symbol)
            _selectedStock.value = stock
        }
    }

    fun setSelectedStock(stock: StockInfo?) {
        _selectedStock.value = stock
    }

    fun clearSelectedStock() {
        _selectedStock.value = null
    }
}
