package com.example.basemvvm.model

import com.google.gson.JsonObject
import io.reactivex.Flowable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

/**
 * 코틀린 컴파일러는 제네릭을 기본적으로  List<String>을 List<? extends String> 형태로 변환합니다.
 * 이런형태의 자동변환을 방지하는 방법은 @JvmSuppressWildcards 입니다
 * 다만 이 어노테이션이 없으면 빌드시에 에러가 나니깐 꼭 넣어야한다
 */
@JvmSuppressWildcards
interface BasicApi {
    @GET
    fun get(
            @Url url: String,
            @QueryMap params: Map<String, Any?> = mapOf(),
            @HeaderMap headers: Map<String, Any?> = mapOf()
    ): Flowable<JsonObject>

    @FormUrlEncoded
    @POST
    fun form(
            @Url url: String,
            @FieldMap params: Map<String, Any?> = mapOf(),
            @HeaderMap headers: Map<String, Any?> = mapOf()

    ): Flowable<JsonObject>

    @POST
    fun post(
            @Url url: String,
            @Body params: Map<String, Any?> = mapOf(),
            @HeaderMap headers: Map<String, Any?> = mapOf()
    ): Flowable<JsonObject>


    @POST
    fun upload(
            @Url url: String,
            @Body body: RequestBody,
            @HeaderMap headers: Map<String, Any?> = mapOf()
    ): Flowable<JsonObject>

    @Streaming
    @GET
    fun download(
            @Url url: String,
            @QueryMap params: Map<String, Any?> = mapOf(),
            @HeaderMap headers: Map<String, Any?> = mapOf()
    ): Flowable<Response<ResponseBody>>

}