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
import javax.swing.table.DefaultTableModel;
import javax.swing.border.*;
import java.awt.*;
import java.sql.*;

public class adminDashboard extends JFrame {

    private final admin admin;
    private JPanel contentArea;
    private JButton activeButton;

    public adminDashboard(admin admin) {
        this.admin = admin;
        setTitle("Dashboard Admin - Eco Point");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUI();
    }

    // ==================== CLASS ROUNDED COMPONENTS ====================
    
    class RoundedButton extends JButton {
        private int radius;
        public RoundedButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setOpaque(false);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
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
        // MAIN PANEL - Background hijau solid
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(35, 90, 35));
        main.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // ==================== HEADER PANEL ====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel brand = new JLabel("ECO POINT - ADMIN");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 28));
        brand.setForeground(Color.WHITE);
        headerPanel.add(brand, BorderLayout.WEST);
        
        JLabel adminLabel = new JLabel("Welcome, " + admin.getNamaAdmin());
        adminLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        adminLabel.setForeground(new Color(200, 240, 200));
        headerPanel.add(adminLabel, BorderLayout.EAST);
        
        main.add(headerPanel, BorderLayout.NORTH);
        
        // ==================== CARD STATISTIK (4 CARD) ====================
        JPanel cardPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 25, 0));
        
        cardPanel.add(createStatCard("Total User", loadTotalUser(), new Color(50, 200, 50)));
        cardPanel.add(createStatCard("Total Petugas", loadTotalPetugas(), new Color(100, 180, 250)));
        cardPanel.add(createStatCard("Transaksi Hari Ini", loadTransaksiHariIni(), new Color(255, 180, 80)));
        cardPanel.add(createStatCard("Total Stok Sampah", loadTotalStok() + " kg", new Color(220, 120, 80)));
        
        main.add(cardPanel, BorderLayout.NORTH);
        
        // ==================== CONTENT PANEL (Sidebar + Area Konten) ====================
        JPanel contentWrapper = new JPanel(new BorderLayout(15, 0));
        contentWrapper.setOpaque(false);
        
        // SIDEBAR KIRI (Tab Menu)
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(50, 110, 50));
        sidebar.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(70, 130, 70), 1, true),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        sidebar.setPreferredSize(new Dimension(220, 0));
        
        // Tombol-tombol menu di sidebar
        JButton btnValidasi = createRoundedSidebarButton("Validasi Transaksi", new Color(50, 200, 50));
        JButton btnUser = createRoundedSidebarButton("Kelola User", new Color(100, 180, 250));
        JButton btnPetugas = createRoundedSidebarButton("Kelola Petugas", new Color(255, 180, 80));
        JButton btnStok = createRoundedSidebarButton("Stok Sampah", new Color(150, 150, 220));
        
        // Tombol LOGOUT di bawah - Warna MERAH
        JButton btnLogout = createLogoutButton();
        
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnValidasi);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnUser);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnPetugas);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnStok);
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        sidebar.add(btnLogout);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Area konten utama (card putih)
        contentArea = new JPanel(new CardLayout());
        contentArea.setBackground(Color.WHITE);
        contentArea.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Build semua panel
        JPanel validasiPanel = buildValidasiPanel();
        JPanel userPanel = buildUserPanel();
        JPanel petugasPanel = buildPetugasPanel();
        JPanel stokPanel = buildStokPanel();
        
        contentArea.add(validasiPanel, "VALIDASI");
        contentArea.add(userPanel, "USER");
        contentArea.add(petugasPanel, "PETUGAS");
        contentArea.add(stokPanel, "STOK");
        
        contentWrapper.add(sidebar, BorderLayout.WEST);
        contentWrapper.add(contentArea, BorderLayout.CENTER);
        
        main.add(contentWrapper, BorderLayout.CENTER);
        
        // ==================== FOOTER ====================
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        JLabel footerLabel = new JLabel("Eco Point - Sistem Manajemen Sampah dan Reward");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(180, 220, 180));
        footer.add(footerLabel);
        main.add(footer, BorderLayout.SOUTH);
        
        add(main);
        
        // Event sidebar buttons
        btnValidasi.addActionListener(e -> showPanel("VALIDASI", btnValidasi));
        btnUser.addActionListener(e -> showPanel("USER", btnUser));
        btnPetugas.addActionListener(e -> showPanel("PETUGAS", btnPetugas));
        btnStok.addActionListener(e -> showPanel("STOK", btnStok));
        
        // Set default active
        showPanel("VALIDASI", btnValidasi);
    }
    
    // Method untuk membuat tombol logout
    private JButton createLogoutButton() {
        JButton btn = new JButton("LOGOUT") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(new Color(180, 40, 40));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(240, 60, 60));
                } else {
                    g2.setColor(new Color(200, 50, 50));
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setMaximumSize(new Dimension(200, 50));
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        
        btn.addActionListener(e -> { 
            int confirm = JOptionPane.showConfirmDialog(adminDashboard.this, 
                "Apakah Anda yakin ingin logout?", "Konfirmasi Logout", 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                new loginFrame().setVisible(true); 
                dispose(); 
            }
        });
        
        return btn;
    }
    
    // Method untuk membuat sidebar button dengan rounded background
    private JButton createRoundedSidebarButton(String text, Color iconColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(new Color(40, 90, 40));
                } else if (getModel().isRollover() && this != activeButton) {
                    g2.setColor(new Color(70, 130, 70));
                } else if (this == activeButton) {
                    g2.setColor(new Color(35, 80, 35));
                } else {
                    g2.setColor(new Color(50, 110, 50));
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                if (this == activeButton) {
                    g2.setColor(new Color(100, 200, 100));
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                }
                
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(200, 50));
        btn.setPreferredSize(new Dimension(200, 50));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        
        return btn;
    }
    
    private void showPanel(String panelName, JButton clickedButton) {
        CardLayout cl = (CardLayout) contentArea.getLayout();
        cl.show(contentArea, panelName);
        
        if (activeButton != null) {
            activeButton.repaint();
        }
        
        activeButton = clickedButton;
        activeButton.repaint();
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblTitle.setForeground(Color.GRAY);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblValue.setForeground(color);
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        
        return card;
    }
    
    // ==================== LOAD STATISTIK ====================
    
    private String loadTotalUser() {
        try {
            Connection conn = DatabaseConnect.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM usr");
            if (rs.next()) return String.valueOf(rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
        return "0";
    }
    
    private String loadTotalPetugas() {
        try {
            Connection conn = DatabaseConnect.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM Petugas");
            if (rs.next()) return String.valueOf(rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
        return "0";
    }
    
    private String loadTransaksiHariIni() {
        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) FROM Transaksi_limbah WHERE DATE(tanggal) = CURDATE()");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return String.valueOf(rs.getInt(1));
        } catch (SQLException e) { e.printStackTrace(); }
        return "0";
    }
    
    private double loadTotalStok() {
        try {
            Connection conn = DatabaseConnect.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT SUM(total_berat) FROM stok_limbah");
            if (rs.next()) return rs.getDouble(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    // ==================== VALIDASI TRANSAKSI PANEL ====================
    
    private JPanel buildValidasiPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitle = new JLabel("Validasi Transaksi Sampah");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(35, 90, 35));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        String[] cols = {"ID Transaksi", "Nama User", "No HP", "Tanggal", "Petugas", "Status"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setSelectionBackground(new Color(180, 220, 180));
        
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(50, 150, 50));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        
        loadTransaksiDiproses(model);
        
        JButton btnValidasi = new JButton("Validasi Transaksi");
        JButton btnRefresh = new JButton("Refresh");
        
        btnValidasi.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnValidasi.setBackground(new Color(50, 150, 50));
        btnValidasi.setForeground(Color.WHITE);
        btnValidasi.setFocusPainted(false);
        btnValidasi.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnValidasi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRefresh.setBackground(new Color(100, 130, 200));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnValidasi.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(p, "Pilih transaksi terlebih dahulu!");
                return;
            }
            String idTr = (String) model.getValueAt(row, 0);
            validasiTransaksi(idTr, model);
        });

        btnRefresh.addActionListener(e -> loadTransaksiDiproses(model));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(btnValidasi);
        btnPanel.add(btnRefresh);
        
        p.add(lblTitle, BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(btnPanel, BorderLayout.SOUTH);
        return p;
    }

    private void loadTransaksiDiproses(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection conn = DatabaseConnect.getConnection();
            ResultSet rs = conn.createStatement().executeQuery(
                "SELECT id_transaksi, nama_user, no_hp, tanggal, nama_petugas, status " +
                "FROM v_transaksi_lengkap WHERE status = 'diproses' ORDER BY tanggal DESC");
            while (rs.next())
                model.addRow(new Object[]{
                    rs.getString("id_transaksi"),
                    rs.getString("nama_user"),
                    rs.getString("no_hp"),
                    rs.getString("tanggal"),
                    rs.getString("nama_petugas") != null ? rs.getString("nama_petugas") : "-",
                    rs.getString("status")
                });
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void validasiTransaksi(String idTr, DefaultTableModel model) {
        int konfirm = JOptionPane.showConfirmDialog(this,
            "Validasi transaksi " + idTr + "?\n\n" +
            "Poin akan otomatis ditambahkan ke user dan stok diperbarui.",
            "Konfirmasi Validasi", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (konfirm != JOptionPane.YES_OPTION) return;

        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE Transaksi_limbah SET status='valid', id_admin=? " +
                "WHERE id_transaksi=? AND status='diproses'");
            ps.setString(1, admin.getId());
            ps.setString(2, idTr);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this,
                    "✅ Transaksi " + idTr + " berhasil divalidasi!\n" +
                    "Poin user dan stok sampah telah diperbarui otomatis.");
                loadTransaksiDiproses(model);
                refreshStatistik();
            } else {
                JOptionPane.showMessageDialog(this,
                    "❌ Gagal: transaksi tidak ditemukan atau sudah divalidasi.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void refreshStatistik() {
        // Refresh card statistics
        Component[] components = ((JPanel)((JPanel)getContentPane().getComponent(0)).getComponent(1)).getComponents();
        if (components.length >= 4) {
            ((JLabel)((JPanel)components[0]).getComponent(1)).setText(loadTotalUser());
            ((JLabel)((JPanel)components[1]).getComponent(1)).setText(loadTotalPetugas());
            ((JLabel)((JPanel)components[2]).getComponent(1)).setText(loadTransaksiHariIni());
            ((JLabel)((JPanel)components[3]).getComponent(1)).setText(loadTotalStok() + " kg");
        }
    }

    // ==================== KELOLA USER PANEL ====================
    
    private JPanel buildUserPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitle = new JLabel("Kelola Data User");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(35, 90, 35));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        String[] cols = {"ID", "Nama", "Alamat", "No HP", "Poin", "Username"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setSelectionBackground(new Color(180, 220, 180));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(50, 150, 50));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        
        loadUsers(model);

        JButton btnEdit = new JButton("Edit User");
        JButton btnHapus = new JButton("Hapus User");
        JButton btnRefresh = new JButton("Refresh");

        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEdit.setBackground(new Color(70, 130, 180));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnHapus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHapus.setBackground(new Color(200, 70, 70));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setFocusPainted(false);
        btnHapus.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnHapus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRefresh.setBackground(new Color(100, 130, 200));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(p, "Pilih user yang ingin diedit!");
                return;
            }
            String idUser = (String) model.getValueAt(row, 0);
            String nama = (String) model.getValueAt(row, 1);
            String alamat = (String) model.getValueAt(row, 2);
            String noHp = (String) model.getValueAt(row, 3);
            showEditUserDialog(idUser, nama, alamat, noHp, model);
        });

        btnHapus.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(p, "Pilih user yang ingin dihapus!");
                return;
            }
            String idUser = (String) model.getValueAt(row, 0);
            String nama = (String) model.getValueAt(row, 1);

            int ok = JOptionPane.showConfirmDialog(p,
                "Hapus user " + nama + " (" + idUser + ")?\n\nData transaksi user ini juga akan terhapus.",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ok == JOptionPane.YES_OPTION) {
                try {
                    PreparedStatement ps = DatabaseConnect.getConnection()
                        .prepareStatement("DELETE FROM usr WHERE id_user=?");
                    ps.setString(1, idUser);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(p, "✅ User berhasil dihapus.");
                    loadUsers(model);
                    refreshStatistik();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(p, "❌ Gagal hapus: " + ex.getMessage());
                }
            }
        });

        btnRefresh.addActionListener(e -> {
            loadUsers(model);
            refreshStatistik();
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);
        btnPanel.add(btnRefresh);

        p.add(lblTitle, BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(btnPanel, BorderLayout.SOUTH);
        return p;
    }

    private void loadUsers(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            ResultSet rs = DatabaseConnect.getConnection().createStatement()
                .executeQuery("SELECT * FROM usr ORDER BY id_user");
            while (rs.next())
                model.addRow(new Object[]{
                    rs.getString("id_user"),
                    rs.getString("nama_user"),
                    rs.getString("alamat"),
                    rs.getString("no_hp"),
                    rs.getInt("total_poin"),
                    rs.getString("username")
                });
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void showEditUserDialog(String idUser, String nama,
            String alamat, String noHp, DefaultTableModel model) {

        JDialog dialog = new JDialog(this, "Edit User - " + idUser, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtNama = new JTextField(nama, 20);
        JTextField txtAlamat = new JTextField(alamat, 20);
        JTextField txtNoHp = new JTextField(noHp, 20);
        
        txtNama.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtAlamat.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNoHp.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        String[] labels = {"ID User:", "Nama:", "Alamat:", "No HP:"};
        JComponent[] flds = {new JLabel(idUser), txtNama, txtAlamat, txtNoHp};
        
        for (JComponent comp : flds) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setFont(new Font("Segoe UI", Font.BOLD, 13));
                ((JLabel) comp).setForeground(new Color(50, 150, 50));
            }
        }

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(label, gbc);
            gbc.gridx = 1;
            panel.add(flds[i], gbc);
        }

        JButton btnSimpan = new JButton("Simpan");
        JButton btnBatal = new JButton("Batal");
        
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSimpan.setBackground(new Color(50, 150, 50));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnBatal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnBatal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnSimpan.addActionListener(e -> {
            String namaBaru = txtNama.getText().trim();
            String alamatBaru = txtAlamat.getText().trim();
            String noHpBaru = txtNoHp.getText().trim();

            if (!inputValidator.isNotEmpty(namaBaru) || !inputValidator.isValidNama(namaBaru)) {
                String pesan = !inputValidator.isNotEmpty(namaBaru)
                    ? inputValidator.errorRequired("Nama")
                    : inputValidator.errorNama();
                JOptionPane.showMessageDialog(dialog, pesan, "Nama Tidak Valid", JOptionPane.WARNING_MESSAGE);
                txtNama.requestFocus();
                return;
            }

            if (!inputValidator.isNotEmpty(alamatBaru)) {
                JOptionPane.showMessageDialog(dialog, inputValidator.errorRequired("Alamat"), "Field Kosong", JOptionPane.WARNING_MESSAGE);
                txtAlamat.requestFocus();
                return;
            }

            if (!inputValidator.isNotEmpty(noHpBaru) || !inputValidator.isValidNoHp(noHpBaru)) {
                String pesan = !inputValidator.isNotEmpty(noHpBaru)
                    ? inputValidator.errorRequired("No HP")
                    : inputValidator.errorNoHp();
                JOptionPane.showMessageDialog(dialog, pesan, "No HP Tidak Valid", JOptionPane.ERROR_MESSAGE);
                txtNoHp.requestFocus();
                return;
            }

            try {
                CallableStatement cs = DatabaseConnect.getConnection()
                    .prepareCall("{CALL sp_edit_user(?, ?, ?, ?, ?)}");
                cs.setString(1, idUser);
                cs.setString(2, namaBaru);
                cs.setString(3, alamatBaru);
                cs.setString(4, noHpBaru);
                cs.registerOutParameter(5, Types.VARCHAR);
                cs.execute();

                String hasil = cs.getString(5);
                JOptionPane.showMessageDialog(dialog, hasil);

                if (hasil.startsWith("SUCCESS")) {
                    loadUsers(model);
                    refreshStatistik();
                    dialog.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBatal.addActionListener(e -> dialog.dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(btnSimpan);
        btnPanel.add(btnBatal);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ==================== KELOLA PETUGAS PANEL ====================
    
    private JPanel buildPetugasPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitle = new JLabel("Kelola Data Petugas");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(35, 90, 35));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        String[] cols = {"ID", "Nama Petugas", "Username"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setSelectionBackground(new Color(180, 220, 180));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(50, 150, 50));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        
        loadPetugas(model);

        // Form tambah petugas
        JPanel addForm = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        addForm.setBackground(new Color(245, 255, 245));
        addForm.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 220, 200), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JTextField txtNama = new JTextField(15);
        JTextField txtUser = new JTextField(15);
        JPasswordField txtPass = new JPasswordField(15);
        
        txtNama.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        addForm.add(new JLabel("Nama:")); addForm.add(txtNama);
        addForm.add(Box.createHorizontalStrut(10));
        addForm.add(new JLabel("Username:")); addForm.add(txtUser);
        addForm.add(Box.createHorizontalStrut(10));
        addForm.add(new JLabel("Password:")); addForm.add(txtPass);

        JButton btnTambah = new JButton("Tambah Petugas");
        btnTambah.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnTambah.setBackground(new Color(50, 150, 50));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);
        btnTambah.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnTambah.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        addForm.add(btnTambah);
        
        JButton btnEdit = new JButton("Edit Petugas");
        JButton btnHapus = new JButton("Hapus Petugas");
        JButton btnRefresh = new JButton("Refresh");
        
        btnEdit.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnEdit.setBackground(new Color(70, 130, 180));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnEdit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnHapus.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHapus.setBackground(new Color(200, 70, 70));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setFocusPainted(false);
        btnHapus.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnHapus.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRefresh.setBackground(new Color(100, 130, 200));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnTambah.addActionListener(e -> {
            String nama = txtNama.getText().trim();
            String user = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword()).trim();

            if (!inputValidator.isNotEmpty(nama) || !inputValidator.isValidNama(nama)) {
                String pesan = !inputValidator.isNotEmpty(nama) ? inputValidator.errorRequired("Nama") : inputValidator.errorNama();
                JOptionPane.showMessageDialog(p, pesan, "Nama Tidak Valid", JOptionPane.WARNING_MESSAGE);
                txtNama.requestFocus();
                return;
            }

            if (!inputValidator.isNotEmpty(user) || !inputValidator.isValidUsername(user)) {
                String pesan = !inputValidator.isNotEmpty(user) ? inputValidator.errorRequired("Username") : inputValidator.errorUsername();
                JOptionPane.showMessageDialog(p, pesan, "Username Tidak Valid", JOptionPane.WARNING_MESSAGE);
                txtUser.requestFocus();
                return;
            }

            if (!inputValidator.isNotEmpty(pass) || !inputValidator.isValidPassword(pass)) {
                String pesan = !inputValidator.isNotEmpty(pass) ? inputValidator.errorRequired("Password") : inputValidator.errorPassword();
                JOptionPane.showMessageDialog(p, pesan, "Password Tidak Valid", JOptionPane.WARNING_MESSAGE);
                txtPass.requestFocus();
                return;
            }

            try {
                CallableStatement cs = DatabaseConnect.getConnection()
                    .prepareCall("{CALL sp_tambah_petugas(?, ?, ?, ?)}");
                cs.setString(1, nama);
                cs.setString(2, user);
                cs.setString(3, pass);
                cs.registerOutParameter(4, Types.VARCHAR);
                cs.execute();

                String hasil = cs.getString(4);
                JOptionPane.showMessageDialog(p, hasil);

                if (hasil.startsWith("SUCCESS")) {
                    txtNama.setText("");
                    txtUser.setText("");
                    txtPass.setText("");
                    loadPetugas(model);
                    refreshStatistik();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(p, "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEdit.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(p, "Pilih petugas yang ingin diedit!");
                return;
            }
            String idPetugas = (String) model.getValueAt(row, 0);
            String namaPetugas = (String) model.getValueAt(row, 1);
            String userPetugas = (String) model.getValueAt(row, 2);
            showEditPetugasDialog(idPetugas, namaPetugas, userPetugas, model);
        });

        btnHapus.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(p, "Pilih petugas yang ingin dihapus!");
                return;
            }
            String idPetugas = (String) model.getValueAt(row, 0);
            String namaPetugas = (String) model.getValueAt(row, 1);

            int ok = JOptionPane.showConfirmDialog(p,
                "Hapus petugas " + namaPetugas + " (" + idPetugas + ")?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (ok == JOptionPane.YES_OPTION) {
                try {
                    PreparedStatement ps = DatabaseConnect.getConnection()
                        .prepareStatement("DELETE FROM Petugas WHERE id_petugas=?");
                    ps.setString(1, idPetugas);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(p, "✅ Petugas berhasil dihapus.");
                    loadPetugas(model);
                    refreshStatistik();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(p, "❌ Gagal hapus: " + ex.getMessage());
                }
            }
        });

        btnRefresh.addActionListener(e -> {
            loadPetugas(model);
            refreshStatistik();
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(btnEdit);
        btnPanel.add(btnHapus);
        btnPanel.add(btnRefresh);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(addForm, BorderLayout.NORTH);
        
        p.add(lblTitle, BorderLayout.NORTH);
        p.add(topPanel, BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(btnPanel, BorderLayout.SOUTH);
        return p;
    }

    private void loadPetugas(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            ResultSet rs = DatabaseConnect.getConnection().createStatement()
                .executeQuery("SELECT * FROM Petugas ORDER BY id_petugas");
            while (rs.next())
                model.addRow(new Object[]{
                    rs.getString("id_petugas"),
                    rs.getString("nama_petugas"),
                    rs.getString("username")
                });
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void showEditPetugasDialog(String idPetugas, String nama,
            String username, DefaultTableModel model) {

        JDialog dialog = new JDialog(this, "Edit Petugas - " + idPetugas, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtNama = new JTextField(nama, 20);
        JTextField txtUser = new JTextField(username, 20);
        JPasswordField txtPass = new JPasswordField(20);
        
        txtNama.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtUser.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtPass.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        String[] labels = {"ID Petugas:", "Nama:", "Username:", "Password Baru:"};
        JComponent[] flds = {new JLabel(idPetugas), txtNama, txtUser, txtPass};
        
        for (JComponent comp : flds) {
            if (comp instanceof JLabel) {
                ((JLabel) comp).setFont(new Font("Segoe UI", Font.BOLD, 13));
                ((JLabel) comp).setForeground(new Color(50, 150, 50));
            }
        }

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            gbc.gridx = 0;
            gbc.gridy = i;
            panel.add(label, gbc);
            gbc.gridx = 1;
            panel.add(flds[i], gbc);
        }

        JButton btnSimpan = new JButton("Simpan");
        JButton btnBatal = new JButton("Batal");
        
        btnSimpan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSimpan.setBackground(new Color(50, 150, 50));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnBatal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnBatal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnSimpan.addActionListener(e -> {
            String namaBaru = txtNama.getText().trim();
            String userBaru = txtUser.getText().trim();
            String passBaru = new String(txtPass.getPassword()).trim();

            if (!inputValidator.isNotEmpty(namaBaru) || !inputValidator.isValidNama(namaBaru)) {
                String pesan = !inputValidator.isNotEmpty(namaBaru) ? inputValidator.errorRequired("Nama") : inputValidator.errorNama();
                JOptionPane.showMessageDialog(dialog, pesan, "Nama Tidak Valid", JOptionPane.WARNING_MESSAGE);
                txtNama.requestFocus();
                return;
            }

            if (!inputValidator.isNotEmpty(userBaru) || !inputValidator.isValidUsername(userBaru)) {
                String pesan = !inputValidator.isNotEmpty(userBaru) ? inputValidator.errorRequired("Username") : inputValidator.errorUsername();
                JOptionPane.showMessageDialog(dialog, pesan, "Username Tidak Valid", JOptionPane.WARNING_MESSAGE);
                txtUser.requestFocus();
                return;
            }

            if (!inputValidator.isNotEmpty(passBaru) || !inputValidator.isValidPassword(passBaru)) {
                String pesan = !inputValidator.isNotEmpty(passBaru) ? inputValidator.errorRequired("Password") : inputValidator.errorPassword();
                JOptionPane.showMessageDialog(dialog, pesan, "Password Tidak Valid", JOptionPane.WARNING_MESSAGE);
                txtPass.requestFocus();
                return;
            }

            try {
                CallableStatement cs = DatabaseConnect.getConnection()
                    .prepareCall("{CALL sp_edit_petugas(?, ?, ?, ?, ?)}");
                cs.setString(1, idPetugas);
                cs.setString(2, namaBaru);
                cs.setString(3, userBaru);
                cs.setString(4, passBaru);
                cs.registerOutParameter(5, Types.VARCHAR);
                cs.execute();

                String hasil = cs.getString(5);
                JOptionPane.showMessageDialog(dialog, hasil);

                if (hasil.startsWith("SUCCESS")) {
                    loadPetugas(model);
                    dialog.dispose();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(dialog, "❌ Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBatal.addActionListener(e -> dialog.dispose());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setOpaque(false);
        btnPanel.add(btnSimpan);
        btnPanel.add(btnBatal);

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        panel.add(btnPanel, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ==================== STOK SAMPAH PANEL ====================
    
    private JPanel buildStokPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitle = new JLabel("Stok Sampah");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(35, 90, 35));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        String[] cols = {"ID Stok", "Jenis Sampah", "Kategori", "Total Berat (kg)"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(32);
        
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(50, 150, 50));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnRefresh.setBackground(new Color(100, 130, 200));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnRefresh.addActionListener(e -> {
            loadStok(model);
            refreshStatistik();
        });
        
        loadStok(model);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.setOpaque(false);
        btnPanel.add(btnRefresh);

        p.add(lblTitle, BorderLayout.NORTH);
        p.add(new JScrollPane(table), BorderLayout.CENTER);
        p.add(btnPanel, BorderLayout.SOUTH);
        return p;
    }

    private void loadStok(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            ResultSet rs = DatabaseConnect.getConnection().createStatement()
                .executeQuery(
                    "SELECT * FROM v_stok_lengkap ORDER BY nama_kategori, nama_jenis");
            while (rs.next())
                model.addRow(new Object[]{
                    rs.getString("id_stok"),
                    rs.getString("nama_jenis"),
                    rs.getString("nama_kategori"),
                    String.format("%.2f", rs.getDouble("total_berat"))
                });
        } catch (SQLException e) { e.printStackTrace(); }
    }
}