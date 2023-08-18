package com.ahmadov.koincrypto.di

import com.ahmadov.koincrypto.ViewModel.CryptoViewModel
import com.ahmadov.koincrypto.repository.CryptoDownload
import com.ahmadov.koincrypto.repository.CryptoDownloadImpl
import com.ahmadov.koincrypto.service.CryptoApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule= module {
    single {
        val BASE_URL="https://raw.githubusercontent.com/"
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CryptoApi::class.java)
    }

    single<CryptoDownload> {
        CryptoDownloadImpl(get())

    }

    viewModel {
        CryptoViewModel(get())
    }


}