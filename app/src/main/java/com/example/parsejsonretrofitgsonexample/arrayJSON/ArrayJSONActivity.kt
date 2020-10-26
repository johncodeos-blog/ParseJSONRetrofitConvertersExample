package com.example.parsejsonretrofitgsonexample.arrayJSON

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
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_array_json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class ArrayJSONActivity : AppCompatActivity() {

    var itemsArray: ArrayList<Cell> = ArrayList()
    lateinit var adapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_array_json)
        // Clean TextViews
        json_results_textview.text = ""

        setupRecyclerView()
        parseJSON()

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        json_results_recyclerview.layoutManager = layoutManager
        json_results_recyclerview.setHasFixedSize(true)
        val dividerItemDecoration =
            DividerItemDecoration(json_results_recyclerview.context, layoutManager.orientation)
        ContextCompat.getDrawable(this, R.drawable.line_divider)?.let { drawable ->
            dividerItemDecoration.setDrawable(drawable)
        }
        json_results_recyclerview.addItemDecoration(dividerItemDecoration)
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
            val response = service.getEmployees()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {

                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(response.body())
                    Log.d("Pretty Printed JSON :", prettyJson)
                    json_results_textview.text = prettyJson

                    val items = response.body()
                    if (items != null) {
                        for (i in 0 until items.count()) {
                            // ID
                            val id = items[i].employeeId ?: "N/A"
                            Log.d("ID: ", id)

                            // Employee Name
                            val employeeName = items[i].employeeName ?: "N/A"
                            Log.d("Employee Name: ", employeeName)

                            // Employee Salary
                            val employeeSalary = items[i].employeeSalary ?: "N/A"
                            Log.d("Employee Salary: ", employeeSalary)

                            // Employee Age
                            val employeeAge = items[i].employeeAge ?: "N/A"
                            Log.d("Employee Age: ", employeeAge)


                            val model =
                                Cell(id, employeeName, "$ $employeeSalary", employeeAge)
                            itemsArray.add(model)

                            adapter = RVAdapter(itemsArray)
                            adapter.notifyDataSetChanged()
                        }
                    }

                    // Pass the Array with data to RecyclerView Adapter
                    json_results_recyclerview.adapter = adapter


                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }
}


