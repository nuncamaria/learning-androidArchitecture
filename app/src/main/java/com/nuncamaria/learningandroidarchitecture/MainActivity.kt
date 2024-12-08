package com.nuncamaria.learningandroidarchitecture

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.nuncamaria.learningandroidarchitecture.core.navigation.CoinListAdaptiveNavigationPane
import com.nuncamaria.learningandroidarchitecture.ui.theme.CryptoTrackerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoTrackerTheme {

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        @OptIn(ExperimentalMaterial3Api::class)
                        Column {
                            CenterAlignedTopAppBar(
                                title = { Text(text = "Crypto Bro Tracker") }
                            )
                            HorizontalDivider()
                        }
                    }
                ) { innerPadding ->

                    CoinListAdaptiveNavigationPane(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
