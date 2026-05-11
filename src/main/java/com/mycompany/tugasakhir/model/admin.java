/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.model;

/**
 *
 * @author asus
 */
public class admin extends baseEntity {
    private String namaAdmin;
    private String username;
    private String password;

    public admin(String idAdmin, String namaAdmin, String username, String password) {
        super(idAdmin);
        this.namaAdmin = namaAdmin;
        this.username  = username;
        this.password  = password;
    }

    public String getNamaAdmin() { return namaAdmin; }
    public String getUsername()  { return username; }
    public String getPassword()  { return password; }

    @Override public String getRole()        { return "ADMIN"; }
    @Override public String getDisplayName() { return namaAdmin; }
}
