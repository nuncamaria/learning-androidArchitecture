package com.nuncamaria.learningandroidarchitecture

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nuncamaria.learningandroidarchitecture.core.domain.util.getStringRes
import com.nuncamaria.learningandroidarchitecture.core.ui.util.ObserveAsEvent
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail.CoinDetailScreen
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.CoinListEvent
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.CoinListScreen
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.CoinListViewModel
import com.nuncamaria.learningandroidarchitecture.ui.theme.CryptoTrackerTheme
import org.koin.androidx.compose.koinViewModel

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
                        CenterAlignedTopAppBar(
                            title = {
                                Text(text = "Crypto Bro Tracker")
                            }
                        )
                    }
                ) { innerPadding ->

                    val viewModel = koinViewModel<CoinListViewModel>()
                    val state by viewModel.state.collectAsStateWithLifecycle()

                    ObserveAsEvent(viewModel.event) {
                        when (it) {
                            is CoinListEvent.Error -> {
                                Toast.makeText(
                                    this,
                                    this.getString(it.error.getStringRes()),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    when {
                        state.selectedCoin != null -> CoinDetailScreen(
                            state = state,
                            modifier = Modifier.padding(innerPadding)
                        )

                        else -> CoinListScreen(
                            state = state,
                            onAction = viewModel::onAction,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}
