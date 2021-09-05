package com.example.meteorpedia

import com.example.meteorpedia.api.MeteorsApi
import com.example.meteorpedia.repositories.MeteorsRepository
import com.example.meteorpedia.viewmodels.MeteorsViewmodel
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val viewModels = module {
    single { MeteorsViewmodel() }
    single { CompositeDisposable() }
}

val networkingModule = module {

    fun provideRetrofitClient(): Retrofit {

        val client = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://data.nasa.gov")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single { provideRetrofitClient() }

    single { get<Retrofit>().create(MeteorsApi::class.java)}

    factory { MeteorsRepository(get()) }
}

fun createModuleList() = listOf(networkingModule, viewModels)