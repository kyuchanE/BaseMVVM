package com.example.basemvvm.ex_koin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.basemvvm.R
import com.example.basemvvm.utils.L
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.ext.getOrCreateScope
import org.koin.ext.scope

class ExKoinActivity : AppCompatActivity() {

    // inject() 의존성 주입 - Lazy 방식
    val bb_inject1 : BB by inject()	// inject Type 유형 1 - Type by inject()
    val bb_inject2 by inject<BB>()	// inject Type 유형 2 - by inject<Type>()

    // get() 의존성 주입 - 바로 주입 방식
    var bb_get1 : BB = get()		// get Type 유형 1 - Type = get()
    var bb_get2 = get<BB>()		// get Type 유형 2 - get<Type>()

    val scopeA : A = get()
    val scopeForA = scopeA.getOrCreateScope()
    val scopeB : B = scopeForA.get()
    val scopeC : C = scopeForA.get()
//    val scopeB : B = scopeA.scope.get()
//    val scopeC : C = scopeA.scope.get()

    // single, factory 의 의존성 주입 메서드로 by inject() / get() 사용방법 입니다.
    //
    // inject 와 get 방식 둘 다 Type 선언 방식은 2가지로 구분
    //
    //    1) var 변수명: Type = get() 형식 - Koin 이 해당 변수의 Type 을 보고 알맞은 의존성을 주입
    //
    //    2) var 변수명 = get<Type>() 형식 - Koin 이 get()함수에 선언된 Type 에 맞는 의존성을 주입
    //
    // Inject 와 get 방식의 차이
    //
    //    inject - Lazy 방식의 주입, 해당 객체가 사용되는 시점에 의존성 주입
    //
    //    get - 바로 주입, 해당 코드 실행시간에 바로 객체를 주입
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex_koin)

        L.d("bb_inject1 name is ${bb_inject1.name()}")
        L.d("bb_inject2 name is ${bb_inject2.name()}")

        L.d("bb_get1 name is ${bb_get1.name()}")
        L.d("bb_get2 name is ${bb_get2.name()}")

        L.d("scopeB name is ${scopeB.name()}, scope close ? : ${scopeForA.closed}")
        L.d("scopeC name is ${scopeC.name()}")

        scopeForA.close()

        L.d("after close() scopeB name is ${scopeB.name()}, scope close ? : ${scopeForA.closed}")
        L.d("after close() scopeC name is ${scopeC.name()}")

    }
}