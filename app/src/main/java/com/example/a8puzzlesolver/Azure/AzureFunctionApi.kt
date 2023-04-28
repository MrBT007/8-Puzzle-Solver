package com.example.a8puzzlesolver.Azure

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AzureFunctionApi {

    data class RequestBody(
        @SerializedName("initialState") val initialState: List<String>,
        @SerializedName("goalState") val goalState: List<String>,
        val algoNumber: Int
    )

    data class ResponseBody(
        val success: Boolean,
        val result: String?,
        val message: String?,
        val path: List<List<List<Int>>>?,
        val steps: Int?,
        val executionTime: Double?
    )


    @POST("api/HttpTrigger1")
    fun compute(@Body requestBody: RequestBody, @Query("code") functionCode: String): Call<ResponseBody>
}