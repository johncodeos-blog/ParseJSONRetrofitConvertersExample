package com.example.parsejsonretrofitgsonexample.nestedJSON

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parsejsonretrofitgsonexample.APIService
import com.example.parsejsonretrofitgsonexample.Cell
import com.example.parsejsonretrofitgsonexample.R
import com.example.parsejsonretrofitgsonexample.RVAdapter
import com.example.parsejsonretrofitgsonexample.databinding.ActivityNestedJsonBinding
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class NestedJSONActivity : AppCompatActivity() {

    var itemsArray: ArrayList<Cell> = ArrayList()
    lateinit var adapter: RVAdapter
    private lateinit var binding: ActivityNestedJsonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNestedJsonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        // Clean TextViews
        binding.jsonResultsTextview.text = ""

        setupRecyclerView()
        parseJSON()
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.jsonResultsRecyclerview.layoutManager = layoutManager
        binding.jsonResultsRecyclerview.setHasFixedSize(true)
        val dividerItemDecoration =
            DividerItemDecoration(binding.jsonResultsRecyclerview.context, layoutManager.orientation)
        ContextCompat.getDrawable(this, R.drawable.line_divider)
            ?.let { drawable -> dividerItemDecoration.setDrawable(drawable) }
        binding.jsonResultsRecyclerview.addItemDecoration(dividerItemDecoration)
    }

    @SuppressLint("LongLogTag")
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
            val response = service.getEmployeesNested()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(response.body())

                    Log.d("Pretty Printed JSON :", prettyJson)
                    binding.jsonResultsTextview.text = prettyJson

                    val items = response.body()?.data
                    if (items != null) {
                        for (i in 0 until items.count()) {
                            // ID
                            val id = items[i].employeeId ?: "N/A"
                            Log.d("ID: ", id)

                            // Employee Name
                            val employeeName = items[i].employee?.name ?: "N/A"
                            Log.d("Employee Name: ", employeeName)

                            // Employee Salary in USD
                            val employeeSalaryUSD = items[i].employee?.salary?.usd ?: 0
                            Log.d("Employee Salary in USD: ", employeeSalaryUSD.toString())

                            // Employee Salary in EUR
                            val employeeSalaryEUR = items[i].employee?.salary?.eur ?: 0
                            Log.d("Employee Salary in EUR: ", employeeSalaryEUR.toString())

                            // Employee Age
                            val employeeAge = items[i].employee?.age ?: "N/A"
                            Log.d("Employee Age: ", employeeAge)

                            val model =
                                Cell(
                                    id,
                                    employeeName,
                                    "$ $employeeSalaryUSD / € $employeeSalaryEUR",
                                    employeeAge
                                )
                            itemsArray.add(model)

                            adapter = RVAdapter(itemsArray)
                            adapter.notifyDataSetChanged()
                        }
                    }

                    binding.jsonResultsRecyclerview.adapter = adapter

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
}