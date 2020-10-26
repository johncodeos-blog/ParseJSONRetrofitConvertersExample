package com.example.parsejsonretrofitgsonexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parsejsonretrofitgsonexample.arrayJSON.ArrayJSONActivity
import com.example.parsejsonretrofitgsonexample.nestedJSON.NestedJSONActivity
import com.example.parsejsonretrofitgsonexample.simpleJSON.SimpleJSONActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simple_json_button.setOnClickListener {
            val intent = Intent(this@MainActivity, SimpleJSONActivity::class.java)
            this@MainActivity.startActivity(intent)
        }

        array_json_button.setOnClickListener {
            val intent = Intent(this@MainActivity, ArrayJSONActivity::class.java)
            this@MainActivity.startActivity(intent)
        }

        nested_json_button.setOnClickListener {
            val intent = Intent(this@MainActivity, NestedJSONActivity::class.java)
            this@MainActivity.startActivity(intent)
        }
    }
}