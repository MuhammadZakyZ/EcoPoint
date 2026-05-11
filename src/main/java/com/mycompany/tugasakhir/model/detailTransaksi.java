/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.model;

/**
 *
 * @author asus
 */
public class detailTransaksi {
    private String idDetail;
    private String idTransaksi;
    private String idJenis;
    private double beratKg;
    private int poinEstimasi;
    private int poinFinal;

    public detailTransaksi(String idDetail, String idTransaksi, String idJenis,
                           double beratKg, int poinEstimasi, int poinFinal) {
        this.idDetail     = idDetail;
        this.idTransaksi  = idTransaksi;
        this.idJenis      = idJenis;
        this.beratKg      = beratKg;
        this.poinEstimasi = poinEstimasi;
        this.poinFinal    = poinFinal;
    }

    public String getIdDetail()     { return idDetail; }
    public String getIdTransaksi()  { return idTransaksi; }
    public String getIdJenis()      { return idJenis; }
    public double getBeratKg()      { return beratKg; }
    public int getPoinEstimasi()    { return poinEstimasi; }
    public int getPoinFinal()       { return poinFinal; }

    public void setPoinFinal(int p) { this.poinFinal = p; }
}
