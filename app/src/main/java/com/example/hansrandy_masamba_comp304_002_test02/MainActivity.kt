package com.example.hansrandy_masamba_comp304_002_test02

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.hansrandy_masamba_comp304_002_test02.data.AppDatabase
import com.example.hansrandy_masamba_comp304_002_test02.data.StockInfo
import com.example.hansrandy_masamba_comp304_002_test02.repository.StockRepository
import com.example.hansrandy_masamba_comp304_002_test02.ui.theme.Hansrandy_masamba_COMP304002_Test02Theme
import com.example.hansrandy_masamba_comp304_002_test02.viewmodel.StockViewModel
import com.example.hansrandy_masamba_comp304_002_test02.viewmodel.StockViewModelFactory
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: StockViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val repository = StockRepository(database.stockDao())
        val factory = StockViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[StockViewModel::class.java]

        setContent {
            Hansrandy_masamba_COMP304002_Test02Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        viewModel = viewModel,
                        onDisplayStock = { stock ->
                            viewModel.setSelectedStock(stock)
                            startActivity(Intent(this, DisplayActivity::class.java))
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: StockViewModel,
    onDisplayStock: (StockInfo) -> Unit
) {
    var companyName by remember { mutableStateOf("") }
    var stockSymbol by remember { mutableStateOf("") }
    var stockQuote by remember { mutableStateOf("") }
    var sharesSold by remember { mutableStateOf("") }
    var searchQuery by remember { mutableStateOf("") }

    val stockSymbols by viewModel.stockSymbols.collectAsState()
    var selectedSymbol by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Insert Stock Information",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = companyName,
            onValueChange = { companyName = it },
            label = { Text("Company Name") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = stockSymbol,
            onValueChange = { stockSymbol = it },
            label = { Text("Stock Symbol") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = stockQuote,
            onValueChange = { stockQuote = it },
            label = { Text("Stock Quote") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = sharesSold,
            onValueChange = { sharesSold = it },
            label = { Text("Shares Sold") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                if (companyName.isNotBlank() && stockSymbol.isNotBlank() &&
                    stockQuote.isNotBlank() && sharesSold.isNotBlank()
                ) {
                    val stock = StockInfo(
                        stockSymbol = stockSymbol,
                        companyName = companyName,
                        stockQuote = stockQuote.toDoubleOrNull() ?: 0.0,
                        sharesSold = sharesSold.toIntOrNull() ?: 0
                    )
                    viewModel.insertStock(stock)
                    companyName = ""
                    stockSymbol = ""
                    stockQuote = ""
                    sharesSold = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Insert Stock")
        }

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search Company") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        val context = androidx.compose.ui.platform.LocalContext.current

        Button(
            onClick = {
                if (searchQuery.isNotBlank()) {
                    viewModel.searchStock(searchQuery)
                    context.startActivity(Intent(context, DisplayActivity::class.java))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text("Search & Display")
        }

        Text(
            text = "Stock List:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(stockSymbols) { symbol ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            selectedSymbol = symbol
                            viewModel.searchStock(symbol)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedSymbol == symbol)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = symbol,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        if (selectedSymbol == symbol) {
                            val coroutineScope = rememberCoroutineScope()

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        viewModel.searchStock(symbol)
                                        delay(100)
                                        viewModel.selectedStock.value?.let { stock ->
                                            onDisplayStock(stock)
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("Display Stock Info")
                            }
                        }
                    }
                }
            }
        }
    }
}
