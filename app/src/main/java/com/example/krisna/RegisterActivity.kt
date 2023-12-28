package com.example.krisna

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.regist_act)

        databaseRef = FirebaseDatabase.getInstance().getReference("user")

        etUsername = findViewById(R.id.edtUsername)
        etPassword = findViewById(R.id.edtPassword)
        btnRegister = findViewById(R.id.registBtn)

        btnRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString()

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Username dan Password harus diisi.")
            return
        }

        val userId = databaseRef.push().key
        val user = User(userId, username, password)

        if (userId != null) {
            databaseRef.child(userId).setValue(user)
                .addOnCompleteListener {
                    showToast("Pendaftaran berhasil!")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    showToast("Gagal mendaftarkan akun.")
                }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
