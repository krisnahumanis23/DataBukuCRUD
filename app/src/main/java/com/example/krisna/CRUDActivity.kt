package com.example.krisna

import BukuAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CRUDActivity : AppCompatActivity() {

    private lateinit var ref: DatabaseReference
    private lateinit var bukuList: MutableList<Buku>
    private lateinit var listBuku: RecyclerView
    private lateinit var fabAdd: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.databuku_act)

        ref = FirebaseDatabase.getInstance().getReference("buku")
        listBuku = findViewById(R.id.listBuku)
        bukuList = mutableListOf()
        fabAdd = findViewById(R.id.fabAdd)

        // Tambahkan Layout Manager untuk RecyclerView
        listBuku.layoutManager = LinearLayoutManager(this)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()) {
                    bukuList.clear() // Bersihkan list sebelum menambahkan data baru
                    for (h in p0.children) {
                        val buku = h.getValue(Buku::class.java)
                        if (buku != null) {
                            bukuList.add(buku)
                        }
                    }
                    val adapter = BukuAdapter(applicationContext, bukuList)
                    listBuku.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })

        fabAdd.setOnClickListener {
            showPopupMenu()
        }
    }

    fun showPopupMenu() {
        val popupMenu = PopupMenu(this, fabAdd)
        popupMenu.menuInflater.inflate(R.menu.menu_fab_add, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.tambahBuku -> {
                    // Tindakan yang dilakukan saat memilih menu "Tambah Buku"
                    val intent = Intent(this@CRUDActivity, tambahData::class.java)
                    startActivity(intent)
                    true
                }

                R.id.editBuku -> {
                    // Tindakan yang dilakukan saat memilih menu "Tambah Buku"
                    val intent = Intent(this@CRUDActivity, editData::class.java)
                    startActivity(intent)
                    true
                }

                R.id.deleteBuku -> {
                    // Tindakan yang dilakukan saat memilih menu "Tambah Buku"
                    val intent = Intent(this@CRUDActivity, deleteData::class.java)
                    startActivity(intent)
                    true
                }
                // Tambahkan kasus lainnya sesuai kebutuhan
                else -> false
            }
        }
        popupMenu.show()
    }
}
