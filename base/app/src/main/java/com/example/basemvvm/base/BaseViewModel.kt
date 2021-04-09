package com.example.basemvvm.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    /**
     * RxJava 의 observing 을 위한 부분.
     * addDisposable 을 이용하여 추가하기만 하면 된다
     * Model 에서 들어오는 Single<>과 같은 RxJava 객체들의 Observing 을 위한 부분입니다.
     */
    private val compositeDisposable = CompositeDisposable()

    /**
     *  Ex
     *  addDisposable(model.requestToServer(senderInfo)
     * .subscribeOn(Schedulers.io())
     * .observeOn(AndroidSchedulers.mainThread())
     * .subscribe({
     * // 성공적인 응답
     * }, {
     * // 에러
     * }))
     */
    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    /**
     * ViewModel 은 View 와의 생명주기를 공유하기 때문에
     * View 가 부서질 때 ViewMode l의 onCleared()가 호출되게 되며,
     * 그에 따라 옵저버블들이 전부 클리어 되게 됩니다.
     */
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}