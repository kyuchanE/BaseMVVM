package com.example.basemvvm.ex_koin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.basemvvm.base.BaseViewModel
import com.example.basemvvm.utils.L
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ExKoinVM : BaseViewModel() {
    private var lottoData = MutableLiveData<JsonObject>()

    val lottoLiveData: LiveData<JsonObject> get() = lottoData

    fun testApi(drwNo: String) {
        addDisposable(
            getRetrofit()?.get(
                "common.do",
                mutableMapOf(
                    "method" to "getLottoNumber",
                    "drwNo" to drwNo
                ),
                mapOf()
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    L.d("testApi OnError ${it.message}")
                }.doOnNext {
                    L.d("testApi OnNext ${it}")
                    lottoData.postValue(it.asJsonObject)

                }.doFinally {
                    L.d("testApi Finally ")
                }
                .subscribe()
        )

    }

}

