/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.model;

/**
 *
 * @author asus
 */

public class petugas extends baseEntity {
    private String namaPetugas;
    private String username;
    private String password;

    public petugas(String idPetugas, String namaPetugas, String username, String password) {
        super(idPetugas);
        this.namaPetugas = namaPetugas;
        this.username    = username;
        this.password    = password;
    }

    public String getNamaPetugas() { return namaPetugas; }
    public String getUsername()    { return username; }
    public String getPassword()    { return password; }

    @Override public String getRole()        { return "PETUGAS"; }
    @Override public String getDisplayName() { return namaPetugas; }
}
