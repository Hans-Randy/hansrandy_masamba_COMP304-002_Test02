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
import com.example.hansrandy_masamba_comp304_002_test02.ui.theme.Hansrandy_masamba_COMP304002_Test02Theme

class DisplayActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stockInfo = intent.getParcelableExtra<com.example.hansrandy_masamba_comp304_002_test02.data.StockInfo>("STOCK_INFO")

        setContent {
            Hansrandy_masamba_COMP304002_Test02Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DisplayScreen(
                        stockInfo = stockInfo,
                        onBackClick = { finish() }
                    )
                }
            }
        }
    }

    companion object {
        const val EXTRA_STOCK_INFO = "STOCK_INFO"
    }
}

@Composable
fun DisplayScreen(
    stockInfo: com.example.hansrandy_masamba_comp304_002_test02.data.StockInfo?,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (stockInfo != null) {
            Text(
                text = "Stock Information",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Text(
                text = "Stock Symbol: ${stockInfo.stockSymbol}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Company Name: ${stockInfo.companyName}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Stock Quote: ${stockInfo.stockQuote}",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = "Shares Sold: ${stockInfo.sharesSold}",
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
