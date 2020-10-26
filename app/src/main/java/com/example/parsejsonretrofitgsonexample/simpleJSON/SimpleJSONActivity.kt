package com.example.parsejsonretrofitgsonexample.simpleJSON

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.parsejsonretrofitgsonexample.APIService
import com.example.parsejsonretrofitgsonexample.R
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_simple_json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SimpleJSONActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_json)
        // Clean TextViews
        json_results_textview.text = ""
        employee_id_textview.text = ""
        employee_name_textview.text = ""
        employee_salary_textview.text = ""
        employee_age_textview.text = ""

        parseJSON()
    }


    private fun parseJSON() {

        // .addConverterFactory(GsonConverterFactory.create()) for Gson converter
        // .addConverterFactory(MoshiConverterFactory.create()) for Moshi converter
        // .addConverterFactory(Json.asConverterFactory("application/json".toMediaType())) for Kotlinx Serialization converter
        // .addConverterFactory(JacksonConverterFactory.create()) for Jackson converter

        // Create Retrofit
        val retrofit = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            // Do the GET request and get response
            val response = service.getEmployee()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(response.body())
                    Log.d("Pretty Printed JSON :", prettyJson)
                    json_results_textview.text = prettyJson

                    // ID
                    val id = response.body()?.employeeId ?: "N/A"
                    Log.d("ID: ", id)
                    employee_id_textview.text = id

                    // Employee Name
                    val employeeName = response.body()?.employeeName ?: "N/A"
                    Log.d("Employee Name: ", employeeName)
                    employee_name_textview.text = employeeName

                    // Employee Salary
                    val employeeSalary = response.body()?.employeeSalary ?: "N/A"
                    Log.d("Employee Salary: ", employeeSalary)
                    employee_salary_textview.text = "$ $employeeSalary"

                    // Employee Age
                    val employeeAge = response.body()?.employeeAge ?: "N/A"
                    Log.d("Employee Age: ", employeeAge)
                    employee_age_textview.text = employeeAge

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
}
