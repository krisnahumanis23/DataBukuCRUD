package com.example.krisna

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class deleteData : AppCompatActivity() {

    private lateinit var btnDelete: Button
    private lateinit var kodeBuku: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deletedata_act)

        btnDelete = findViewById(R.id.deleteBtn)
        kodeBuku = findViewById(R.id.etKodeBuku)

        btnDelete.setOnClickListener {
            val kodeBuku = kodeBuku.text.toString()
            deleteRecord(kodeBuku)
        }
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("buku").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Data berhasil dihapus!", Toast.LENGTH_LONG).show()
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Error ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}
