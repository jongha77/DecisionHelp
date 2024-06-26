package com.example.decisionhelp.Network

import com.example.decisionhelp.Model.UserViewModel
import com.example.decisionhelp.Model.VoterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkModule {

    val appModule = module {
        single { provideRetrofit() }
        single { provideApiService(get()) }
        single { UserRepository(get()) }
        single { VoterRepository(get()) }
        viewModel { UserViewModel(get()) }
        viewModel { VoterViewModel(get()) }
    }
    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://172.30.1.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}