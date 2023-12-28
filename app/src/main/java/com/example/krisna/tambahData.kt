package com.example.krisna

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class tambahData : AppCompatActivity(), View.OnClickListener {

    private lateinit var etKodeBuku: EditText
    private lateinit var etNamaBuku: EditText
    private lateinit var etPenerbit: EditText
    private lateinit var btnSimpan: Button

    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tambahdata_act)

        ref = FirebaseDatabase.getInstance().getReference("buku")

        etKodeBuku = findViewById(R.id.etKodeBuku)
        etNamaBuku = findViewById(R.id.etNamaBuku)
        etPenerbit = findViewById(R.id.etPenerbit)
        btnSimpan = findViewById(R.id.simpanBtn)

        btnSimpan.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.simpanBtn -> {
                saveData()
            }
        }
    }
    //buatlah

    private fun saveData() {
        val kodeBuku = etKodeBuku.text.toString().trim()
        val namaBuku = etNamaBuku.text.toString().trim()
        val penerbit = etPenerbit.text.toString().trim()

        if (kodeBuku.isEmpty() || namaBuku.isEmpty() || penerbit.isEmpty()) {
            etKodeBuku.error = "Isi Kode Buku!"
            etPenerbit.error = "Isi Nama Penerbit!"
            etNamaBuku.error = "Isi Nama Buku!"
            return
        }

        // Gunakan kodeBuku sebagai bagian dari path referensi
        val bukuRef = ref.child(kodeBuku)

        bukuRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Jika data dengan kode buku tersebut sudah ada
                    Toast.makeText(
                        applicationContext,
                        "Data dengan Kode Buku $kodeBuku sudah ada.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // Jika data dengan kode buku tersebut belum ada, tambahkan data baru
                    val buku = Buku(kodeBuku, namaBuku, penerbit)

                    bukuRef.setValue(buku).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(
                                applicationContext,
                                "Data Berhasil Ditambahkan!",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Bersihkan input setelah data berhasil ditambahkan
                            etKodeBuku.text.clear()
                            etNamaBuku.text.clear()
                            etPenerbit.text.clear()
                            finish()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Gagal Menambahkan Data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //buatlah fungsi on cancelled
                
            }
        })
    }
}

