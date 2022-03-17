package com.example.basemvvm.base

import org.json.JSONObject

abstract class BaseEvent {
    protected var message: JSONObject = JSONObject()

    abstract fun getMessage(item: JSONObject)
    fun message(): JSONObject {
        return message
    }
}