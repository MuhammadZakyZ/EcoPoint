/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.model;

/**
 *
 * @author asus
 */
public class reward {
    private String idReward;
    private String namaReward;
    private int poinDibutuhkan;

    public reward(String idReward, String namaReward, int poinDibutuhkan) {
        this.idReward       = idReward;
        this.namaReward     = namaReward;
        this.poinDibutuhkan = poinDibutuhkan;
    }

    public String getIdReward()     { return idReward; }
    public String getNamaReward()   { return namaReward; }
    public int getPoinDibutuhkan()  { return poinDibutuhkan; }

    @Override public String toString() {
        return namaReward + " (" + poinDibutuhkan + " poin)";
    }
}
