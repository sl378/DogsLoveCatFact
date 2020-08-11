package com.example.dogsloveDog.api

import com.example.dogslovecatfact.models.Dog
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

class DogApi(retrofit: Retrofit) {
    val dogApi = retrofit.create(DogApiService::class.java)

    fun getDogBreed(id: String): Observable<Dog> {
        return dogApi.getDogBreed(id.toLong())
    }

    interface DogApiService {
        @GET("breeds/{id}")
        fun getDogBreed(@Path("id") id: Long): Observable<Dog>
    }
}