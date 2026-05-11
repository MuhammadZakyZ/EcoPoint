/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.tugasakhir;

/**
 *
 * @author asus
 */

import com.mycompany.tugasakhir.gui.loginFrame;
import javax.swing.SwingUtilities;

public class TugasAkhir {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new loginFrame().setVisible(true));
    }
}