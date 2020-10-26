package com.example.parsejsonretrofitgsonexample

import com.example.parsejsonretrofitgsonexample.arrayJSON.ArrayJSONModel
import com.example.parsejsonretrofitgsonexample.nestedJSON.NestedJSONModel
import com.example.parsejsonretrofitgsonexample.simpleJSON.SimpleJSONModel
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    /*
       Simple JSON
    */

    @GET("/johncodeos-blog/ParseJSONRetrofitConvertersExample/main/simple.json")
    suspend fun getEmployee(): Response<SimpleJSONModel>


    /*
       Array JSON
    */

    @GET("/johncodeos-blog/ParseJSONRetrofitConvertersExample/main/array.json")
    suspend fun getEmployees(): Response<List<ArrayJSONModel>>


    /*
       Nested JSON
    */

    @GET("/johncodeos-blog/ParseJSONRetrofitConvertersExample/main/nested.json")
    suspend fun getEmployeesNested(): Response<NestedJSONModel>
}