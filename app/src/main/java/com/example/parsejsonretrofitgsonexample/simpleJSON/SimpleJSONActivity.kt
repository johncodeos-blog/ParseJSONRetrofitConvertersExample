package com.example.parsejsonretrofitgsonexample.simpleJSON

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.parsejsonretrofitgsonexample.APIService
import com.example.parsejsonretrofitgsonexample.R
import com.example.parsejsonretrofitgsonexample.databinding.ActivitySimpleJsonBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SimpleJSONActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySimpleJsonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleJsonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Clean TextViews
        binding.jsonResultsTextview.text = ""
        binding.employeeIdTextview.text = ""
        binding.employeeNameTextview.text = ""
        binding.employeeSalaryTextview.text = ""
        binding.employeeAgeTextview.text = ""

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
                    binding.jsonResultsTextview.text = prettyJson

                    // ID
                    val id = response.body()?.employeeId ?: "N/A"
                    Log.d("ID: ", id)
                    binding.employeeIdTextview.text = id

                    // Employee Name
                    val employeeName = response.body()?.employeeName ?: "N/A"
                    Log.d("Employee Name: ", employeeName)
                    binding.employeeNameTextview.text = employeeName

                    // Employee Salary
                    val employeeSalary = response.body()?.employeeSalary ?: "N/A"
                    Log.d("Employee Salary: ", employeeSalary)
                    binding.employeeSalaryTextview.text = "$ $employeeSalary"

                    // Employee Age
                    val employeeAge = response.body()?.employeeAge ?: "N/A"
                    Log.d("Employee Age: ", employeeAge)
                    binding.employeeAgeTextview.text = employeeAge

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
}
