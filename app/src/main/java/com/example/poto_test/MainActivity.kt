package com.example.poto_test

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 버튼 클릭 리스너 설정
        findViewById<Button>(R.id.button1).setOnClickListener {
            val intent = Intent(this, large_1::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            val intent = Intent(this, large_2::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            val intent = Intent(this, large_3::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            val intent = Intent(this, large_4::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button5).setOnClickListener {
            val intent = Intent(this, large_5::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button6).setOnClickListener {
            val intent = Intent(this, standard_1::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button7).setOnClickListener {
            val intent = Intent(this, standard_2::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button8).setOnClickListener {
            val intent = Intent(this, standard_3::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button9).setOnClickListener {
            val intent = Intent(this, standard_4::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.button10).setOnClickListener {
            val intent = Intent(this, standard_5::class.java)
            startActivity(intent)
        }
    }
}


