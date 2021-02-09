package com.example.parsejsonretrofitgsonexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parsejsonretrofitgsonexample.arrayJSON.ArrayJSONActivity
import com.example.parsejsonretrofitgsonexample.databinding.ActivityMainBinding
import com.example.parsejsonretrofitgsonexample.nestedJSON.NestedJSONActivity
import com.example.parsejsonretrofitgsonexample.simpleJSON.SimpleJSONActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.simpleJsonButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SimpleJSONActivity::class.java)
            this@MainActivity.startActivity(intent)
        }

        binding.arrayJsonButton.setOnClickListener {
            val intent = Intent(this@MainActivity, ArrayJSONActivity::class.java)
            this@MainActivity.startActivity(intent)
        }

        binding.nestedJsonButton.setOnClickListener {
            val intent = Intent(this@MainActivity, NestedJSONActivity::class.java)
            this@MainActivity.startActivity(intent)
        }
    }
}