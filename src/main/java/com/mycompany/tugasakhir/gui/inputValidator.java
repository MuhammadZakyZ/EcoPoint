/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.gui;

/**
 *
 * @author asus
 */
public class inputValidator {
    // nama hanya huruf dan spasi
    public static boolean isValidNama(String nama) {
        return nama.matches("[a-zA-Z\\s]+");
    }
    
    // nohp hanya boleh angka dan boleh diawali +
    public static boolean isValidNoHp(String noHp) {
        return noHp.matches("\\+?[0-9]{8,15}");
    }
    
    // username tidak bolh spasi dan karakter spesial
    public static boolean isValidUsername(String username) {
        return username.matches("[a-zA-Z0-9._]+");
    }
    
    // password dibuat dengan minimal 6 karakter
    public static boolean isValidPassword(String password) {
        return password.length() >= 6;
    }
    
    // validasi tidak boleh kosong (umum)
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // error khusus field kosong
    public static String errorRequired(String field) {
        return "❌ " + field + " wajib diisi!";
    }
    
    // berat tidak boleh negatif
    public static boolean isValidBerat(String berat) {
        try {
            double b = Double.parseDouble(berat);
            return b > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // error message 
    public static String errorNama() {
        return "❌ Nama tidak valid!\n\n" +
               "Nama hanya boleh mengandung:\n" +
               "✔ Huruf (A-Z, a-z)\n" +
               "✔ Spasi\n\n" +
               "Tidak boleh mengandung:\n" +
               "✘ Angka (0-9)\n" +
               "✘ Simbol (!@#$%^&*...)\n" +
               "✘ Emoji";
    }

    public static String errorNoHp() {
        return "❌ No HP tidak valid!\n\n" +
               "No HP hanya boleh mengandung:\n" +
               "✔ Angka (0-9)\n" +
               "✔ Awalan + (opsional)\n" +
               "✔ Minimal 8 digit, maksimal 15 digit\n\n" +
               "Tidak boleh mengandung:\n" +
               "✘ Huruf\n" +
               "✘ Spasi\n" +
               "✘ Simbol\n" +
               "✘ Emoji";
    }

    public static String errorUsername() {
        return "❌ Username tidak valid!\n\n" +
               "Username hanya boleh mengandung:\n" +
               "✔ Huruf (A-Z, a-z)\n" +
               "✔ Angka (0-9)\n" +
               "✔ Titik (.)\n" +
               "✔ Underscore (_)\n\n" +
               "Tidak boleh mengandung:\n" +
               "✘ Spasi\n" +
               "✘ Simbol (!@#$%^&*...)\n" +
               "✘ Emoji";
    }

    public static String errorPassword() {
        return "❌ Password tidak valid!\n\n" +
               "Password harus:\n" +
               "✔ Minimal 6 karakter";
    }

    public static String errorBerat() {
        return "❌ Berat tidak valid!\n\n" +
               "Berat harus:\n" +
               "✔ Berupa angka\n" +
               "✔ Lebih dari 0\n\n" +
               "Tidak boleh:\n" +
               "✘ Huruf\n" +
               "✘ Negatif\n" +
               "✘ Kosong";
    }
}
