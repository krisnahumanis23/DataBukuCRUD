package com.example.krisna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_act)

        // Dapatkan referensi ke tombol
        val myButton: Button = findViewById(R.id.openBtn)

        // Tambahkan fungsi onClick
        myButton.setOnClickListener {
            // Panggil fungsi atau tambahkan kode yang ingin dijalankan saat tombol diklik
            onButtonClick()
        }
    }

    private fun onButtonClick() {
        val intent = Intent(this, CRUDActivity::class.java)
        startActivity(intent)
    }
}