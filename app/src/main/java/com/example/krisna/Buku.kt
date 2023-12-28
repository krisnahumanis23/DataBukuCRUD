package com.example.krisna

class Buku(
    var idBuku: String,
    var nmBuku: String,
    var penerbit: String
) {
    // Konstruktor tanpa argumen
    constructor() : this("", "", "")
}
