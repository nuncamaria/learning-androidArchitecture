package com.nuncamaria.learningandroidarchitecture.di

import com.nuncamaria.learningandroidarchitecture.core.data.network.HttpClientFactory
import com.nuncamaria.learningandroidarchitecture.crypto.data.network.CoinDataSourceImpl
import com.nuncamaria.learningandroidarchitecture.crypto.domain.datasource.CoinDataSource
import com.nuncamaria.learningandroidarchitecture.crypto.ui.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::CoinDataSourceImpl).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)
}