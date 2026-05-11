/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.model;

/**
 *
 * @author asus
 */


public class user extends baseEntity {
    private String namaUser;  
    private String alamat;
    private String noHp;
    private int totalPoin;
    private String username;
    private String password;

    public user(String idUser, String namaUser, String alamat, String noHp,
                int totalPoin, String username, String password) {
        super(idUser);
        this.namaUser = namaUser;
        this.alamat = alamat;
        this.noHp = noHp;
        this.totalPoin = totalPoin;
        this.username = username;
        this.password = password;
    }

    public String getNamaUser()    { return namaUser; }
    public String getAlamat()      { return alamat; }
    public String getNoHp()        { return noHp; }
    public int getTotalPoin()      { return totalPoin; }
    public String getUsername()    { return username; }
    public String getPassword()    { return password; }

    public void setNamaUser(String n)  { this.namaUser = n; }
    public void setAlamat(String a)    { this.alamat = a; }
    public void setNoHp(String h)      { this.noHp = h; }
    public void setTotalPoin(int p)    { this.totalPoin = p; }
    public void setUsername(String u)  { this.username = u; }
    public void setPassword(String p)  { this.password = p; }

    public void tambahPoin(int poin)   { this.totalPoin += poin; }
    public void kurangPoin(int poin)   { this.totalPoin -= poin; }

    @Override public String getRole()        { return "USER"; }
    @Override public String getDisplayName() { return namaUser; }
}