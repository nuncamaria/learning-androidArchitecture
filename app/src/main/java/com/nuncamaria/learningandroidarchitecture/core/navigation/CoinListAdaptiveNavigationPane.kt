package com.nuncamaria.learningandroidarchitecture.core.navigation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nuncamaria.learningandroidarchitecture.core.domain.util.getStringRes
import com.nuncamaria.learningandroidarchitecture.core.ui.util.ObserveAsEvent
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_detail.CoinDetailScreen
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.CoinListAction
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.CoinListEvent
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.CoinListScreen
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.CoinListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun CoinListAdaptiveNavigationPane(
    viewModel: CoinListViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val ctx = LocalContext.current

    ObserveAsEvent(viewModel.event) {
        when (it) {
            is CoinListEvent.Error -> {
                Toast.makeText(
                    ctx,
                    ctx.getString(it.error.getStringRes()),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    val navigator = rememberListDetailPaneScaffoldNavigator<Any>()
    NavigableListDetailPaneScaffold(
        modifier = modifier,
        navigator = navigator,
        listPane = {
            AnimatedPane {
                CoinListScreen(
                    state = state,
                    onAction = { action ->
                        viewModel.onAction(action)

                        when (action) {
                            is CoinListAction.OnCoinClick -> {
                                navigator.navigateTo(pane = ListDetailPaneScaffoldRole.Detail)
                            }

                            CoinListAction.OnRefresh -> TODO()
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                CoinDetailScreen(state = state)
            }
        }
    )
}