package com.example.basemvvm.di

import com.example.basemvvm.ex_koin.koin.ExKoinVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val activityModule = module {

    // ViewModel의 경우 선언하고 주입 받는 방법이 일반적인 클래스와 조금 다릅니다.
    // viewModel 키워드로 모듈을 등록하면 Koin이 해당 ViewModel을 ViewModelFactory에 등록하고 현재 컴포넌트와 바인딩합니다.
    // 주입 받을 때도 ViewModelFactory에서 해당 ViewModel 객체를 불러옵니다.
//    viewModel {
//        ExKoinVM(get())
//    }

    single {
        ExKoinVM(get())
    }
}