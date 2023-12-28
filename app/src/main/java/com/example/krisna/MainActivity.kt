package com.example.krisna

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegist: Button
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_act)

        databaseRef = FirebaseDatabase.getInstance().getReference("user")

        etUsername = findViewById(R.id.edtUsername)
        etPassword = findViewById(R.id.edtPassword)
        btnLogin = findViewById(R.id.loginBtn)
        btnRegist = findViewById(R.id.registBtn)

        btnLogin.setOnClickListener {
            login()
        }

        btnRegist.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        val username = etUsername.text.toString().trim()
        val password = etPassword.text.toString()

        Log.d("LoginActivity", "Username: $username, Password: $password")

        if (username.isEmpty() || password.isEmpty()) {
            showToast("Username dan Password harus diisi.")
            return
        }

        databaseRef.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (userSnapshot in dataSnapshot.children) {
                            val user = userSnapshot.getValue(User::class.java)
                            if (user?.password == password) {
                                showToast("Login berhasil!")
                                val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                                startActivity(intent)
                                finish()
                                return
                            }
                        }
                        showToast("Password salah.")
                    } else {
                        showToast("Username tidak ditemukan.")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    showToast("Terjadi kesalahan: ${databaseError.message}")
                }
            })
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
