/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.model;

/**
 *
 * @author asus
 */
public class ruleKonversi {
    private String idRule;
    private String idJenis;
    private int poinPerKg;

    public ruleKonversi(String idRule, String idJenis, int poinPerKg) {
        this.idRule    = idRule;
        this.idJenis   = idJenis;
        this.poinPerKg = poinPerKg;
    }

    public String getIdRule()  { return idRule; }
    public String getIdJenis() { return idJenis; }
    public int getPoinPerKg()  { return poinPerKg; }
}
