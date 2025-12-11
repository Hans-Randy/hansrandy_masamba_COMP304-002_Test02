package com.example.hansrandy_masamba_comp304_002_test02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.hansrandy_masamba_comp304_002_test02.data.AppDatabase
import com.example.hansrandy_masamba_comp304_002_test02.repository.StockRepository
import com.example.hansrandy_masamba_comp304_002_test02.ui.theme.Hansrandy_masamba_COMP304002_Test02Theme
import com.example.hansrandy_masamba_comp304_002_test02.viewmodel.StockViewModel
import com.example.hansrandy_masamba_comp304_002_test02.viewmodel.StockViewModelFactory

class DisplayActivity : ComponentActivity() {
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
                    DisplayScreen(
                        viewModel = viewModel,
                        onBackClick = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayScreen(
    viewModel: StockViewModel,
    onBackClick: () -> Unit
) {
    val selectedStock by viewModel.selectedStock.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (selectedStock != null) {
            Text(
                text = "Stock Information",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Text(
                text = "Stock Symbol: ${selectedStock?.stockSymbol}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Company Name: ${selectedStock?.companyName}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Stock Quote: ${selectedStock?.stockQuote}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Shares Sold: ${selectedStock?.sharesSold}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Back")
            }
        } else {
            Text(
                text = "No stock information available",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onBackClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Back")
            }
        }
    }
}
