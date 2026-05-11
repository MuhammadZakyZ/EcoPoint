/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.model;

/**
 *
 * @author asus
 */
public class kategoriLimbah {
    private String idKategori;
    private String namaKategori;

    public kategoriLimbah(String idKategori, String namaKategori) {
        this.idKategori   = idKategori;
        this.namaKategori = namaKategori;
    }

    public String getIdKategori()   { return idKategori; }
    public String getNamaKategori() { return namaKategori; }

    @Override public String toString() { return namaKategori; }
}
