package com.example.basemvvm.di

import com.example.basemvvm.model.BasicApi
import com.example.basemvvm.utils.API
import org.koin.dsl.module

val apiModule = module {
    single {
        API.build().create(BasicApi::class.java)
    }
}
