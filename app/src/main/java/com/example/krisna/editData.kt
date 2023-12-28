package com.example.krisna

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class editData : AppCompatActivity() {

    private lateinit var etKodeBuku: EditText
    private lateinit var etNamaBuku: EditText
    private lateinit var etPenerbit: EditText
    private lateinit var btnSimpan: Button

    private lateinit var ref: DatabaseReference
    private var kodeBuku: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editdata_act)

        etKodeBuku = findViewById(R.id.etKodeBuku)
        etNamaBuku = findViewById(R.id.etNamaBuku)
        etPenerbit = findViewById(R.id.etPenerbit)
        btnSimpan = findViewById(R.id.simpanBtn)

        // Mendapatkan kode buku dari Intent
        kodeBuku = intent.getStringExtra("kodeBuku") ?: ""

        // Inisialisasi DatabaseReference
        ref = FirebaseDatabase.getInstance().getReference("buku")

        // Mengambil data buku dari Firebase berdasarkan kode buku
        fetchData()

        btnSimpan.setOnClickListener {
            updateData()
        }
    }

    private fun fetchData() {
        // Mendapatkan data buku dari Firebase berdasarkan kode buku
        ref.child(kodeBuku).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val buku = snapshot.getValue(Buku::class.java)

                    // Menampilkan data di EditText
                    buku?.let {
                        etKodeBuku.setText(it.idBuku)
                        etNamaBuku.setText(it.nmBuku)
                        etPenerbit.setText(it.penerbit)
                    }
                } else {
                    showToast("Data buku tidak ditemukan")
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Gagal mengambil data: ${error.message}")
            }
        })
    }

    private fun updateData() {
        val kodeBuku = etKodeBuku.text.toString().trim()
        val namaBuku = etNamaBuku.text.toString().trim()
        val penerbit = etPenerbit.text.toString().trim()

        if (kodeBuku.isEmpty() || namaBuku.isEmpty() || penerbit.isEmpty()) {
            showToast("Harap isi semua field")
            return
        }

        // Update data di Firebase
        val updatedBuku = Buku(kodeBuku, namaBuku, penerbit)

        ref.child(kodeBuku).setValue(updatedBuku).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                showToast("Data berhasil diubah")
                finish()
            } else {
                showToast("Gagal mengubah data")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
