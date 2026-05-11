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
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class petugasDashboard extends JFrame {

    private final petugas petugas;
    private DefaultTableModel tableModel;
    private JTable table;

    public petugasDashboard(petugas petugas) {
        this.petugas = petugas;
        setTitle("Dashboard Petugas - Eco Point");
        setSize(950, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUI();
    }

    // Class untuk JButton dengan sudut melengkung
    class RoundedButton extends JButton {
        private int radius;
        
        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
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
        // SATU PANEL UTAMA dengan background hijau
        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(new Color(35, 90, 35));
        main.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        // ==================== NORTH PANEL ====================
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.setOpaque(false);
        northPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel brand = new JLabel("ECO POINT", SwingConstants.CENTER);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 28));
        brand.setForeground(Color.WHITE);
        northPanel.add(brand, BorderLayout.NORTH);
        
        JLabel subtitle = new JLabel("Dashboard Petugas - " + petugas.getNamaPetugas(), SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(220, 255, 220));
        northPanel.add(subtitle, BorderLayout.SOUTH);
        
        main.add(northPanel, BorderLayout.NORTH);
        
        // ==================== CENTER PANEL ====================
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setOpaque(false);
        
        // Label tabel
        JLabel lblTable = new JLabel("Daftar Transaksi Menunggu Proses");
        lblTable.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTable.setForeground(Color.WHITE);
        centerPanel.add(lblTable, BorderLayout.NORTH);
        
        // Kolom tabel
        String[] cols = {"ID Transaksi", "ID User", "Tanggal", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setSelectionBackground(new Color(180, 220, 180));
        table.setShowGrid(true);
        table.setGridColor(new Color(200, 200, 200));
        
        // Header tabel
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(30, 80, 30));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        
        // Lebar kolom
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        
        // Renderer status
        table.getColumnModel().getColumn(3).setCellRenderer(new StatusCellRenderer());
        
        // Striped rows
        table.setDefaultRenderer(Object.class, new StripedTableCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        main.add(centerPanel, BorderLayout.CENTER);
        
        // ==================== SOUTH PANEL (TOMbol) ====================
        JPanel southPanel = new JPanel(new BorderLayout(0, 10));
        southPanel.setOpaque(false);
        
        // Panel tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        buttonPanel.setOpaque(false);
        
        RoundedButton btnProses = new RoundedButton("PROSES TRANSAKSI", 30);
        btnProses.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnProses.setBackground(new Color(40, 150, 40));
        btnProses.setForeground(Color.WHITE);
        btnProses.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnProses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnProses.setBackground(new Color(50, 170, 50));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnProses.setBackground(new Color(40, 150, 40));
            }
        });
        
        RoundedButton btnRefresh = new RoundedButton("REFRESH", 30);
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setBackground(Color.WHITE);
        btnRefresh.setForeground(new Color(35, 90, 35));
        btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnRefresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRefresh.setBackground(new Color(240, 255, 240));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRefresh.setBackground(Color.WHITE);
            }
        });
        
        RoundedButton btnLogout = new RoundedButton("LOGOUT", 30);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setBackground(new Color(180, 60, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(new Color(200, 70, 70));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogout.setBackground(new Color(180, 60, 60));
            }
        });
        
        buttonPanel.add(btnProses);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnLogout);
        
        // Footer
        JLabel footer = new JLabel("Eco Point - Sistem Konversi Sampah Menjadi Poin", SwingConstants.CENTER);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footer.setForeground(new Color(180, 220, 180));
        
        southPanel.add(buttonPanel, BorderLayout.CENTER);
        southPanel.add(footer, BorderLayout.SOUTH);
        southPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        
        main.add(southPanel, BorderLayout.SOUTH);
        
        add(main);
        
        // Load data
        loadTransaksiPending();
        
        // ==================== EVENT LISTENER (FUNGSI ASLI) ====================
        btnProses.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { 
                JOptionPane.showMessageDialog(this, "Pilih transaksi terlebih dahulu!"); 
                return; 
            }
            String idTr = (String) tableModel.getValueAt(row, 0);
            prosesTransaksi(idTr);
            loadTransaksiPending();
        });
        
        btnRefresh.addActionListener(e -> loadTransaksiPending());
        
        btnLogout.addActionListener(e -> { 
            new loginFrame().setVisible(true); 
            dispose(); 
        });
    }
    
    // ==================== FUNGSI ASLI (TIDAK DIUBAH) ====================
    
    private void loadTransaksiPending() {
        tableModel.setRowCount(0);
        try {
            Connection conn = DatabaseConnect.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(
                "SELECT id_transaksi, id_user, tanggal, status FROM Transaksi_limbah WHERE status='pending'");
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id_transaksi"),
                    rs.getString("id_user"),
                    rs.getString("tanggal"),
                    rs.getString("status")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) { 
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
        }
    }
    
    private void prosesTransaksi(String idTr) {
        try {
            Connection conn = DatabaseConnect.getConnection();
            
            // Update poin final = estimasi untuk setiap detail
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE Detail_transaksi SET poin_final=poin_estimasi WHERE id_transaksi=?");
            ps.setString(1, idTr);
            ps.executeUpdate();
            ps.close();
            
            // Update status dan assign petugas
            ps = conn.prepareStatement(
                "UPDATE Transaksi_limbah SET status='diproses', id_petugas=? WHERE id_transaksi=?");
            ps.setString(1, petugas.getId());
            ps.setString(2, idTr);
            ps.executeUpdate();
            ps.close();
            
            JOptionPane.showMessageDialog(this, "Transaksi " + idTr + " berhasil diproses!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal memproses: " + e.getMessage());
        }
    }
    
    // ==================== CUSTOM RENDERER ====================
    
    class StripedTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE);
                } else {
                    c.setBackground(new Color(245, 250, 245));
                }
            }
            setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
            return c;
        }
    }
    
    class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = value != null ? value.toString() : "";
            
            JLabel label = (JLabel) c;
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            
            if (!isSelected) {
                if (status.equalsIgnoreCase("pending")) {
                    label.setBackground(new Color(255, 200, 100));
                    label.setForeground(new Color(120, 60, 0));
                } else {
                    label.setBackground(new Color(200, 200, 200));
                    label.setForeground(Color.BLACK);
                }
                label.setOpaque(true);
            }
            
            label.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
            return label;
        }
    }
}