package com.example.basemvvm.di

import org.koin.dsl.module

// Module 에서 제공할 의존성(객체)들의 클래스 구조를 선언

class AA {
    fun name() : String = "AA"
}

class BB(aa: AA) {
    fun name() : String = "BB"
}

// Module 은 객체를 제공하는 명세를 의미
// Koin 에서 Module 은 모든 구성요소를 선언하는 공간, 즉 Koin 으로 제공할 객체를 명세하는 곳

// Koin dsl "module"
val moduleA = module {
    // Dependencies 작성 (제공할 객체)

    // 싱글톤으로 생성해서 의존성주입 (App 수명주기 동안 단일 인스턴스)
    // 인스턴스(객체)가 지연초기화 아닌, 모듈 선언과 동시에 즉시 생성이 필요한 경우엔 ? createAtStart 속성
    // Koin 은 기본적으로 지연초기화(lazy init)가 기본값으로 모든 객체 생성은 요청 시점에 생성 (single + factory 모두)
    // createAtStart 생성과 동시에 초기화 (Single 에서만 의미가 있는 효과)
    single(createdAtStart = true) {
        AA()
    }
}

val moduleB = module {
    //  요청(Inject, get) 시점마다 새로운 인스턴스를 생성(Dagger 의 Provider 개념)
    //      BB 객체 생성자 파라미터로 get() 사용, (위에서 선언한 싱글톤의 AA를 자동으로 의존성주입)
    factory {
        // Koin Scope "get"
        BB(get())
    }
}



class A
class B {
    fun name(): String = "B"
}

class C {
    fun name(): String = "C"
}

// TypeQulifier - 타입 한정자
//
val typeQualifierModule = module {

    factory { A() }

    // B와 C 클래스 인스턴스는 A 인스턴스에 범위가 잡혀있습니다.
    // A 인스턴스 존재 범위내에만 의존성 주입이 가능을 의미
    scope<A> {
        scoped { B() }
        scoped { C() }
    }

}


