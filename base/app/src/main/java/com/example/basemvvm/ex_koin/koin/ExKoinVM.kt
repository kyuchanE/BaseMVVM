package com.example.basemvvm.ex_koin.koin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.annimon.stream.Stream
import com.example.basemvvm.base.BaseViewModel
import com.example.basemvvm.model.BasicApi
import com.example.basemvvm.utils.L
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


// Activity 이외의 장소에서 inject 를 하려면 KoinComponent 를 반드시 implement 로 추가해주어야 한다.
//
class ExKoinVM(private val baseApi: BasicApi) : BaseViewModel() {
    private var lottoData = MutableLiveData<JsonObject>()
    private var testData = MutableLiveData<String>()

    val lottoLiveData: LiveData<JsonObject> get() = lottoData
    val testLiveData: LiveData<String> get() = testData

    fun testApi(drwNo: String) {
        addDisposable(
            baseApi.get(
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
                    var testItem: String? = ""
                    Stream.of(it)
                        .map { item ->
                            "로또 번호 : ${item.get("drwtNo1")} , " +
                                    "${item.get("drwtNo2")} , " +
                                    "${item.get("drwtNo3")} , " +
                                    "${item.get("drwtNo4")} , " +
                                    "${item.get("drwtNo5")} , " +
                                    "${item.get("drwtNo6")}"
                        }
                        .forEach { item -> testItem = item }
                    L.d("testItem >> $testItem")
                    testData.postValue(testItem)
                    lottoData.postValue(it.asJsonObject)

                }.doFinally {
                    L.d("testApi Finally ")
                }
                .subscribe()
        )

    }

}

