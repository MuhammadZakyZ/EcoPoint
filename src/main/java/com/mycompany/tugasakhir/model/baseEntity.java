/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.model;

/**
 *
 * @author asus
 */
public abstract class baseEntity {
    protected String id;

    public baseEntity(String id) {
        this.id = id;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    // Abstraction: setiap entitas wajib implement ini
    public abstract String getRole();
    public abstract String getDisplayName();

    @Override
    public String toString() {
        return "[" + getRole() + "] " + getDisplayName();
    }
}
