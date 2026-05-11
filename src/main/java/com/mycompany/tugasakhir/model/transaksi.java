/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.model;

/**
 *
 * @author asus
 */
public class transaksi {
    private String idTransaksi;
    private String idUser;
    private String idPetugas;
    private String tanggal;
    private String status;  // "pending" / "valid"
    private String idAdmin;

    public transaksi(String idTransaksi, String idUser, String idPetugas,
                     String tanggal, String status, String idAdmin) {
        this.idTransaksi = idTransaksi;
        this.idUser      = idUser;
        this.idPetugas   = idPetugas;
        this.tanggal     = tanggal;
        this.status      = status;
        this.idAdmin     = idAdmin;
    }

    public String getIdTransaksi() { return idTransaksi; }
    public String getIdUser()      { return idUser; }
    public String getIdPetugas()   { return idPetugas; }
    public String getTanggal()     { return tanggal; }
    public String getStatus()      { return status; }
    public String getIdAdmin()     { return idAdmin; }

    public void setStatus(String status)   { this.status = status; }
    public void setIdAdmin(String idAdmin) { this.idAdmin = idAdmin; }
    public void setIdPetugas(String id)    { this.idPetugas = id; }
}
