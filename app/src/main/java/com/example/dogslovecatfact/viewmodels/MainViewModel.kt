package com.example.dogslovecatfact.viewmodels

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogsloveDog.api.DogApi
import com.example.dogslovecatfact.api.CatFactApi
import com.example.dogslovecatfact.api.RetrofitClients
import com.example.dogslovecatfact.models.CatFact
import com.example.dogslovecatfact.models.Dog
import com.example.dogslovecatfact.models.Pet
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel: ViewModel() {
    val catApi = CatFactApi(RetrofitClients.provideRetrofit("https://cat-fact.herokuapp.com/"))
    val dogFactApi = DogApi(RetrofitClients.provideRetrofit("https://api.thedogapi.com/v1/"))

    val catFact: MutableLiveData<CatFact> = MutableLiveData()
    val dog: MutableLiveData<Dog> = MutableLiveData()
    val dogBreedId: MutableLiveData<String> = MutableLiveData()
    val dogBreed: MediatorLiveData<String> = MediatorLiveData()
    val catFactText: MediatorLiveData<String> = MediatorLiveData()

    init {
        dogBreed.addSource(dog) {
            it?.let {
                dogBreed.value = it.breed
            }
        }

        catFactText.addSource(catFact) {
            it?.let {
                catFactText.value = it.fact
            }
        }
    }

    fun joining() {
        val observables = arrayListOf(
                catApi.getCatFact("58e009550aac31001185ed12"),
                dogFactApi.getDogBreed(dogBreedId.value ?: "2")
        )
        Observable.mergeDelayError(observables)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Observer<Pet> {
                var _cat: CatFact? = null
                var _dog: Dog? = null
                override fun onComplete() {
                    _cat?.let {
                        catFact.postValue(_cat)
                    }
                    _dog?.let {
                        dog.postValue(_dog)
                    }
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(pet: Pet) {
                    when (pet) {
                        is CatFact -> {
                            _cat = pet
                        }
                        is Dog -> {
                            _dog = pet
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    dogBreed.postValue(e.message)
                }
            })
    }

    fun chaining() {
//        val observables = arrayListOf(
//                dogFactApi.getDogBreed(dogBreedId.value ?: "2"),
//                catApi.getCatFact("58e009550aac31001185ed12")
//        )
//        Observable.concatDelayError(observables)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object: Observer<Pet> {
//                    override fun onComplete() {
//                    }
//
//                    override fun onSubscribe(d: Disposable) {
//                    }
//
//                    override fun onNext(pet: Pet) {
//                        when (pet) {
//                            is CatFact -> {
//                                catFact.postValue(pet)
//                            }
//                            is Dog -> {
//                                dog.postValue(pet)
//                            }
//                        }
//                    }
//
//                    override fun onError(e: Throwable) {
//                        dogBreed.postValue(e.message)
//                    }
//                })

        dogFactApi.getDogBreed(dogBreedId.value ?: "2")
            .subscribeOn(Schedulers.io())
            .flatMap { _dog ->
                dog.postValue(_dog)
                //If the next request depends on the previous request
                catApi.getCatFact("58e009550aac31001185ed12")
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                catFact.postValue(it)
            }
    }

    fun clear() {
        dogBreed.value = ""
        catFactText.value = ""
    }

}