/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tugasakhir.gui;

/**
 *
 * @author asus
 */
import com.mycompany.tugasakhir.DatabaseConnect;
import com.mycompany.tugasakhir.model.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;

public class loginFrame extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;

    public loginFrame() {
        setTitle("Login - Eco Point");
        setSize(650, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUI();
    }

    class RoundedTextField extends JTextField {
        private int radius;
        
        public RoundedTextField(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
        
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(200, 200, 200));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }
    
    class RoundedPasswordField extends JPasswordField {
        private int radius;
        
        public RoundedPasswordField(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
        
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(200, 200, 200));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }
    
    class RoundedButton extends JButton {
        private int radius;
        
        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(12, 80, 12, 80));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            super.paintComponent(g2);
            g2.dispose();
        }
        
        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
            g2.dispose();
        }
    }

    private void buildUI() {
        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(new Color(35, 90, 35));
        main.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridy = 0;
        main.add(Box.createRigidArea(new Dimension(0, 60)), gbc);

        // Logo / Brand
        JLabel brand = new JLabel("ECO POINT");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 42));
        brand.setForeground(Color.WHITE);
        brand.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 5, 10);
        main.add(brand, gbc);

        // Sub Brand
        JLabel subBrand = new JLabel("Konversi Sampah Menjadi Poin");
        subBrand.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subBrand.setForeground(new Color(220, 255, 220));
        subBrand.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 40, 10);
        main.add(subBrand, gbc);

        // Label Username
        JLabel lblUser = new JLabel("Username");
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblUser.setForeground(Color.WHITE);
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 5, 10);
        main.add(lblUser, gbc);

        // Field Username (rounded)
        txtUsername = new RoundedTextField(20);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUsername.setBackground(Color.WHITE);
        txtUsername.setPreferredSize(new Dimension(400, 48));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 15, 10);
        main.add(txtUsername, gbc);

        // Label Password
        JLabel lblPass = new JLabel("Password");
        lblPass.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblPass.setForeground(Color.WHITE);
        gbc.gridy = 5;
        gbc.insets = new Insets(10, 10, 5, 10);
        main.add(lblPass, gbc);

        // Field Password (rounded)
        txtPassword = new RoundedPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setPreferredSize(new Dimension(400, 48));
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 10, 25, 10);
        main.add(txtPassword, gbc);

        // Tombol Login 
        RoundedButton btnLogin = new RoundedButton("MASUK", 30);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setBackground(Color.WHITE);
        btnLogin.setForeground(new Color(35, 90, 35));
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(240, 255, 240));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(Color.WHITE);
            }
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setOpaque(false);
        btnPanel.add(btnLogin);
        gbc.gridy = 7;
        gbc.insets = new Insets(10, 10, 15, 10);
        main.add(btnPanel, gbc);

        // Tombol Daftar
        JButton btnDaftar = new JButton("Belum punya akun? Daftar di sini");
        btnDaftar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnDaftar.setForeground(new Color(255, 255, 200));
        btnDaftar.setBorderPainted(false);
        btnDaftar.setContentAreaFilled(false);
        btnDaftar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnDaftar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDaftar.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDaftar.setForeground(new Color(255, 255, 200));
            }
        });

        JPanel daftarPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        daftarPanel.setOpaque(false);
        daftarPanel.add(btnDaftar);
        gbc.gridy = 8;
        gbc.insets = new Insets(5, 10, 20, 10);
        main.add(daftarPanel, gbc);

        // Footer
        JLabel footer = new JLabel("Eco Point - Sistem Konversi Sampah Menjadi Poin");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(new Color(180, 220, 180));
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 9;
        gbc.insets = new Insets(20, 10, 0, 10);
        main.add(footer, gbc);

        // Spacer bawah
        gbc.gridy = 10;
        main.add(Box.createRigidArea(new Dimension(0, 40)), gbc);

        add(main);

        // Event listeners (TIDAK DIUBAH - tetap asli)
        btnLogin.addActionListener(e -> doLogin());
        txtPassword.addActionListener(e -> doLogin());
        btnDaftar.addActionListener(e -> {
            try {
                new registerFrame().setVisible(true);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal membuka form registrasi: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password harus diisi!");
            return;
        }

        baseEntity entity = authenticate(username, password);
        if (entity == null) {
            JOptionPane.showMessageDialog(this, "Username atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Selamat datang, " + entity.getDisplayName() + "!");
        dispose();

        switch (entity.getRole()) {
            case "ADMIN":   new adminDashboard((admin) entity).setVisible(true);     break;
            case "PETUGAS": new petugasDashboard((petugas) entity).setVisible(true); break;
            case "USER":    new userDashboard((user) entity).setVisible(true);       break;
        }
    }

    private baseEntity authenticate(String username, String password) {
        Connection conn = DatabaseConnect.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM Admin WHERE username=? AND password=?");
            ps.setString(1, username); ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new admin(rs.getString("id_admin"),
                rs.getString("nama_admin"), username, password);

            ps = conn.prepareStatement(
                "SELECT * FROM Petugas WHERE username=? AND password=?");
            ps.setString(1, username); ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) return new petugas(rs.getString("id_petugas"),
                rs.getString("nama_petugas"), username, password);

            ps = conn.prepareStatement(
                "SELECT * FROM usr WHERE username=? AND password=?");
            ps.setString(1, username); ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) return new user(rs.getString("id_user"),
                rs.getString("nama_user"), rs.getString("alamat"),
                rs.getString("no_hp"), rs.getInt("total_poin"), username, password);

        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}