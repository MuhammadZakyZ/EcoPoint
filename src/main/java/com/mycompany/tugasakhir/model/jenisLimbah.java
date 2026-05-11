/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.model;

/**
 *
 * @author asus
 */
public class jenisLimbah {
    private String idJenis;
    private String namaJenis;
    private String idKategori;

    public jenisLimbah(String idJenis, String namaJenis, String idKategori) {
        this.idJenis    = idJenis;
        this.namaJenis  = namaJenis;
        this.idKategori = idKategori;
    }

    public String getIdJenis()    { return idJenis; }
    public String getNamaJenis()  { return namaJenis; }
    public String getIdKategori() { return idKategori; }

    @Override
    public String toString() { return namaJenis; }
}
