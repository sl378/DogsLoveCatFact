package com.example.dogslovecatfact.api

import com.example.dogslovecatfact.models.CatFact
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

class CatFactApi(retrofit: Retrofit) {
    val catApi: CatApiService = retrofit.create(CatApiService::class.java)

    fun getCatFact(id: String): Observable<CatFact> {
        return catApi.getCatFact(id)
    }

    interface CatApiService {
        @GET("facts/{id}")
        fun getCatFact(@Path("id") id: String): Observable<CatFact>
    }
}