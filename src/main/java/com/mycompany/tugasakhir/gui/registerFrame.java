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
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;

public class registerFrame extends JFrame {

    private JTextField txtNama, txtAlamat, txtHp, txtUsername;
    private JPasswordField txtPassword;

    public registerFrame() {
        setTitle("Daftar Akun Baru - Eco Point");
        setSize(650, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
            setBorder(BorderFactory.createEmptyBorder(12, 40, 12, 40));
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
        main.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Spacer atas
        gbc.gridy = 0;
        main.add(Box.createRigidArea(new Dimension(0, 20)), gbc);

        // Logo / Brand
        JLabel brand = new JLabel("ECO POINT");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 36));
        brand.setForeground(Color.WHITE);
        brand.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 5, 10);
        main.add(brand, gbc);

        // Sub Brand
        JLabel subBrand = new JLabel("Daftar Akun Baru");
        subBrand.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subBrand.setForeground(new Color(220, 255, 220));
        subBrand.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 30, 10);
        main.add(subBrand, gbc);

        // Field Nama Lengkap
        JLabel lblNama = new JLabel("Nama Lengkap");
        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNama.setForeground(Color.WHITE);
        gbc.gridy = 3;
        gbc.insets = new Insets(8, 10, 3, 10);
        main.add(lblNama, gbc);

        txtNama = new RoundedTextField(20);
        txtNama.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtNama.setBackground(Color.WHITE);
        txtNama.setPreferredSize(new Dimension(400, 45));
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 12, 10);
        main.add(txtNama, gbc);

        // Field Alamat
        JLabel lblAlamat = new JLabel("Alamat");
        lblAlamat.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAlamat.setForeground(Color.WHITE);
        gbc.gridy = 5;
        gbc.insets = new Insets(8, 10, 3, 10);
        main.add(lblAlamat, gbc);

        txtAlamat = new RoundedTextField(20);
        txtAlamat.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtAlamat.setBackground(Color.WHITE);
        txtAlamat.setPreferredSize(new Dimension(400, 45));
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 10, 12, 10);
        main.add(txtAlamat, gbc);

        // Field No HP
        JLabel lblHp = new JLabel("No HP");
        lblHp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblHp.setForeground(Color.WHITE);
        gbc.gridy = 7;
        gbc.insets = new Insets(8, 10, 3, 10);
        main.add(lblHp, gbc);

        txtHp = new RoundedTextField(20);
        txtHp.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtHp.setBackground(Color.WHITE);
        txtHp.setPreferredSize(new Dimension(400, 45));
        gbc.gridy = 8;
        gbc.insets = new Insets(0, 10, 12, 10);
        main.add(txtHp, gbc);

        // Field Username
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsername.setForeground(Color.WHITE);
        gbc.gridy = 9;
        gbc.insets = new Insets(8, 10, 3, 10);
        main.add(lblUsername, gbc);

        txtUsername = new RoundedTextField(20);
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtUsername.setBackground(Color.WHITE);
        txtUsername.setPreferredSize(new Dimension(400, 45));
        gbc.gridy = 10;
        gbc.insets = new Insets(0, 10, 12, 10);
        main.add(txtUsername, gbc);

        // Field Password
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPassword.setForeground(Color.WHITE);
        gbc.gridy = 11;
        gbc.insets = new Insets(8, 10, 3, 10);
        main.add(lblPassword, gbc);

        txtPassword = new RoundedPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setPreferredSize(new Dimension(400, 45));
        gbc.gridy = 12;
        gbc.insets = new Insets(0, 10, 25, 10);
        main.add(txtPassword, gbc);

        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        // Tombol Daftar (rounded)
        RoundedButton btnDaftar = new RoundedButton("DAFTAR", 30);
        btnDaftar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnDaftar.setBackground(Color.WHITE);
        btnDaftar.setForeground(new Color(35, 90, 35));
        btnDaftar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnDaftar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDaftar.setBackground(new Color(240, 255, 240));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDaftar.setBackground(Color.WHITE);
            }
        });

        // Tombol Kembali (rounded)
        RoundedButton btnBack = new RoundedButton("KEMBALI", 30);
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnBack.setBackground(new Color(200, 70, 70));
        btnBack.setForeground(Color.WHITE);
        btnBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBack.setBackground(new Color(220, 80, 80));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBack.setBackground(new Color(200, 70, 70));
            }
        });

        buttonPanel.add(btnDaftar);
        buttonPanel.add(btnBack);

        gbc.gridy = 13;
        gbc.insets = new Insets(10, 10, 20, 10);
        main.add(buttonPanel, gbc);

        // Footer
        JLabel footer = new JLabel("Eco Point - Sistem Konversi Sampah Menjadi Poin");
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(new Color(180, 220, 180));
        footer.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 14;
        gbc.insets = new Insets(15, 10, 0, 10);
        main.add(footer, gbc);

        // Spacer bawah
        gbc.gridy = 15;
        main.add(Box.createRigidArea(new Dimension(0, 20)), gbc);

        add(main);

        // Event listeners (TIDAK DIUBAH - fungsi asli tetap)
        btnDaftar.addActionListener(e -> doDaftar());
        btnBack.addActionListener(e -> {
            new loginFrame().setVisible(true);
            dispose();
        });
        
        // Enter key listener untuk password field
        txtPassword.addActionListener(e -> doDaftar());
    }

    // ==================== FUNGSI ASLI (TIDAK DIUBAH) ====================
    
    private void doDaftar() {
        String nama     = txtNama.getText().trim();
        String alamat   = txtAlamat.getText().trim();
        String hp       = txtHp.getText().trim();
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        // Validasi nama
        if (!inputValidator.isNotEmpty(nama)) {
            JOptionPane.showMessageDialog(this,
                inputValidator.errorRequired("Nama"),
                "Field Kosong", JOptionPane.WARNING_MESSAGE);
            txtNama.requestFocus();
            return;
        } else if (!inputValidator.isValidNama(nama)) {
            JOptionPane.showMessageDialog(this,
                inputValidator.errorNama(),
                "Nama Tidak Valid", JOptionPane.ERROR_MESSAGE);
            txtNama.requestFocus();
            txtNama.selectAll();
            return;
        }
        
        // validasi alamat 
        if (!inputValidator.isNotEmpty(alamat)) {
            JOptionPane.showMessageDialog(this,
                inputValidator.errorRequired("Alamat"),
                "Field Kosong", JOptionPane.WARNING_MESSAGE);
            txtAlamat.requestFocus();
            return;
        }

        // Validasi no HP
        if (!inputValidator.isNotEmpty(hp)) {
            JOptionPane.showMessageDialog(this,
                inputValidator.errorRequired("No HP"),
                "Field Kosong",
                JOptionPane.WARNING_MESSAGE);
            txtHp.requestFocus();
            return;
        } else if (!inputValidator.isValidNoHp(hp)) {
            JOptionPane.showMessageDialog(this,
                inputValidator.errorNoHp(),
                "No HP Tidak Valid", JOptionPane.ERROR_MESSAGE);
            txtHp.requestFocus();
            txtHp.selectAll();
            return;
        }

        // Validasi username
        if (!inputValidator.isNotEmpty(username)) {
            JOptionPane.showMessageDialog(this,
                inputValidator.errorRequired("Username"),
                "Field Kosong", JOptionPane.WARNING_MESSAGE);
            txtUsername.requestFocus();
            return;
        } else if (!inputValidator.isValidUsername(username)) {
            JOptionPane.showMessageDialog(this,
                inputValidator.errorUsername(),
                "Username Tidak Valid", JOptionPane.ERROR_MESSAGE);
            txtUsername.requestFocus();
            txtUsername.selectAll();
            return;
        }

        // Validasi password
        if (!inputValidator.isNotEmpty(password)) {
            JOptionPane.showMessageDialog(this,
                inputValidator.errorRequired("Password"),
                "Field Kosong", JOptionPane.WARNING_MESSAGE);
            txtPassword.requestFocus();
            return;
        } else if (!inputValidator.isValidPassword(password)) {
            JOptionPane.showMessageDialog(this,
                inputValidator.errorPassword(),
                "Password Tidak Valid", JOptionPane.ERROR_MESSAGE);
            txtPassword.requestFocus();
            return;
        }

        try {
            CallableStatement cs = DatabaseConnect.getConnection()
                .prepareCall("{CALL sp_daftar_user(?, ?, ?, ?, ?, ?)}");
            cs.setString(1, nama);
            cs.setString(2, alamat);
            cs.setString(3, hp);
            cs.setString(4, username);
            cs.setString(5, password);
            cs.registerOutParameter(6, Types.VARCHAR);
            cs.execute();

            String hasil = cs.getString(6);
            JOptionPane.showMessageDialog(this, hasil);

            if (hasil.startsWith("SUCCESS")) {
                new loginFrame().setVisible(true);
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}