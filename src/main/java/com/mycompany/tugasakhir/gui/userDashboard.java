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
import java.time.LocalDate;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class userDashboard extends JFrame {

    private final user user;
    private JLabel lblPoin;
    private JLabel lblTotalSetor;
    private JLabel lblRewardDitukar;
    private DefaultTableModel modelRiwayat;
    private DefaultTableModel modelKlaim;
    private DefaultTableModel modelReward;
    
    private JPanel contentArea;
    private JButton activeButton;

    public userDashboard(user user) {
        this.user = user;
        setTitle("Dashboard User - Eco Point");
        setSize(1200, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUI();
    }

    // ==================== CLASS ROUNDED COMPONENTS ====================
    
    class RoundedTextField extends JTextField {
        private int radius;
        public RoundedTextField(int radius) {
            this.radius = radius;
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
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
            g2.setColor(new Color(180, 180, 180));
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
        // MAIN PANEL 
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(35, 90, 35));
        main.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // ==================== HEADER PANEL ====================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel brand = new JLabel("ECO POINT");
        brand.setFont(new Font("Segoe UI", Font.BOLD, 32));
        brand.setForeground(Color.WHITE);
        headerPanel.add(brand, BorderLayout.WEST);
        
        main.add(headerPanel, BorderLayout.NORTH);
        
        // ==================== CARD STATISTIK (3 CARD) ====================
        JPanel cardPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        cardPanel.setOpaque(false);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 25, 0));
        
        cardPanel.add(createStatCard("Total Poin", String.valueOf(user.getTotalPoin()), new Color(50, 200, 50)));
        
        lblTotalSetor = new JLabel("0 kg");
        cardPanel.add(createStatCard("Total Setor Sampah", lblTotalSetor, new Color(100, 180, 250)));
        
        lblRewardDitukar = new JLabel("0");
        cardPanel.add(createStatCard("Reward Ditukar", lblRewardDitukar, new Color(255, 180, 80)));
        
        main.add(cardPanel, BorderLayout.NORTH);
        
        // ==================== CONTENT PANEL (Sidebar + Area Konten) ====================
        JPanel contentWrapper = new JPanel(new BorderLayout(15, 0));
        contentWrapper.setOpaque(false);
        
        // SIDEBAR KIRI (Tab Menu dengan Rounded Background)
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(50, 110, 50));
        sidebar.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(70, 130, 70), 1, true),
            BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));
        sidebar.setPreferredSize(new Dimension(220, 0));
        
        // Tombol-tombol menu di sidebar
        JButton btnBeranda = createRoundedSidebarButton("Beranda", new Color(50, 200, 50));
        JButton btnSetor = createRoundedSidebarButton("Setor Sampah", new Color(100, 180, 250));
        JButton btnTukar = createRoundedSidebarButton("Tukar Poin", new Color(255, 180, 80));
        JButton btnRiwayat = createRoundedSidebarButton("Riwayat Setor", new Color(150, 150, 220));
        JButton btnKlaim = createRoundedSidebarButton("Riwayat Klaim", new Color(220, 150, 150));
        
        // Tombol LOGOUT di bawah - Warna MERAH
        JButton btnLogout = createLogoutButton();
        
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnBeranda);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnSetor);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnTukar);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnRiwayat);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnKlaim);
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
        berandaPanel = buildBerandaPanel();
        setorPanel = buildSetorPanel();
        rewardPanel = buildRewardPanel();
        riwayatPanel = buildRiwayatPanel();
        klaimPanel = buildRiwayatKlaimPanel();
        
        contentArea.add(berandaPanel, "BERANDA");
        contentArea.add(setorPanel, "SETOR");
        contentArea.add(rewardPanel, "REWARD");
        contentArea.add(riwayatPanel, "RIWAYAT");
        contentArea.add(klaimPanel, "KLAIM");
        
        contentWrapper.add(sidebar, BorderLayout.WEST);
        contentWrapper.add(contentArea, BorderLayout.CENTER);
        
        main.add(contentWrapper, BorderLayout.CENTER);
        
        // ==================== FOOTER ====================
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(15, 0, 5, 0));
        JLabel footerLabel = new JLabel("Eco Point - Sistem Konversi Sampah Menjadi Poin");
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(new Color(180, 220, 180));
        footer.add(footerLabel);
        main.add(footer, BorderLayout.SOUTH);
        
        add(main);
        
        // Event sidebar buttons
        btnBeranda.addActionListener(e -> showPanel("BERANDA", btnBeranda));
        btnSetor.addActionListener(e -> showPanel("SETOR", btnSetor));
        btnTukar.addActionListener(e -> showPanel("REWARD", btnTukar));
        btnRiwayat.addActionListener(e -> showPanel("RIWAYAT", btnRiwayat));
        btnKlaim.addActionListener(e -> showPanel("KLAIM", btnKlaim));
        
        // Set default active
        showPanel("BERANDA", btnBeranda);
        
        // Update statistik
        updateStatistik();
    }
    
    // Method untuk membuat tombol logout
    private JButton createLogoutButton() {
        JButton btn = new JButton("LOGOUT") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Background merah
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
            int confirm = JOptionPane.showConfirmDialog(userDashboard.this, 
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
                
                // Gambar background rounded
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
                
                // Gambar border untuk button active
                if (this == activeButton) {
                    g2.setColor(new Color(100, 200, 100));
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);
                }
                
                super.paintComponent(g2);
                g2.dispose();
            }
        };
        
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
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
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitle.setForeground(Color.GRAY);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblValue.setForeground(color);
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitle.setForeground(Color.GRAY);
        
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(color);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void updateStatistik() {
        try {
            Connection conn = DatabaseConnect.getConnection();
            
            PreparedStatement ps = conn.prepareStatement(
                "SELECT SUM(berat_kg) as total_berat FROM v_detail_transaksi_lengkap WHERE id_user=? AND status='valid'");
            ps.setString(1, user.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double total = rs.getDouble("total_berat");
                lblTotalSetor.setText(String.format("%.1f kg", total));
            }
            
            ps = conn.prepareStatement(
                "SELECT COUNT(*) as total FROM klaim_reward WHERE id_user=?");
            ps.setString(1, user.getId());
            rs = ps.executeQuery();
            if (rs.next()) {
                lblRewardDitukar.setText(String.valueOf(rs.getInt("total")));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void refreshPoin() {
        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT total_poin FROM usr WHERE id_user=?");
            ps.setString(1, user.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setTotalPoin(rs.getInt("total_poin"));
                if (lblPoin != null) {
                    lblPoin.setText(String.valueOf(user.getTotalPoin()));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ==================== PANEL-PANEL KONTEN ====================
    
    private JPanel berandaPanel, setorPanel, rewardPanel, riwayatPanel, klaimPanel;
    
    private JPanel buildBerandaPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel welcomeCard = new JPanel(new BorderLayout());
        welcomeCard.setBackground(new Color(245, 255, 245));
        welcomeCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 220, 200), 1, true),
            BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        
        JLabel salam = new JLabel("Selamat Datang, " + user.getNamaUser() + "!");
        salam.setFont(new Font("Segoe UI", Font.BOLD, 24));
        salam.setForeground(new Color(35, 90, 35));
        
        JLabel motivasi = new JLabel("Ayo setor sampah dan kumpulkan poin untuk ditukar dengan reward menarik!");
        motivasi.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        motivasi.setForeground(Color.GRAY);
        
        lblPoin = new JLabel(String.valueOf(user.getTotalPoin()));
        lblPoin.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblPoin.setForeground(new Color(50, 200, 50));
        lblPoin.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel poinPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        poinPanel.setOpaque(false);
        poinPanel.add(new JLabel("Total Poin Anda: "));
        poinPanel.add(lblPoin);
        
        welcomeCard.add(salam, BorderLayout.NORTH);
        welcomeCard.add(motivasi, BorderLayout.CENTER);
        welcomeCard.add(poinPanel, BorderLayout.SOUTH);
        
        gbc.gridy = 0;
        p.add(welcomeCard, gbc);
        
        return p;
    }
    
    private JPanel buildSetorPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(new Color(245, 255, 245));
        formCard.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 220, 200), 1, true),
            BorderFactory.createEmptyBorder(30, 35, 30, 35)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JComboBox<jenisLimbah> cbJenis = new JComboBox<>();
        cbJenis.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbJenis.setPreferredSize(new Dimension(350, 42));
        
        RoundedTextField txtBerat = new RoundedTextField(15);
        txtBerat.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBerat.setBackground(Color.WHITE);
        txtBerat.setPreferredSize(new Dimension(350, 42));
        
        JLabel lblEstimasi = new JLabel("Estimasi poin: 0");
        lblEstimasi.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblEstimasi.setForeground(new Color(50, 150, 50));
        
        loadJenisLimbah(cbJenis);
        
        gbc.gridx = 0; gbc.gridy = 0;
        formCard.add(new JLabel("Jenis Sampah:"), gbc);
        gbc.gridx = 1;
        formCard.add(cbJenis, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        formCard.add(new JLabel("Berat (kg):"), gbc);
        gbc.gridx = 1;
        formCard.add(txtBerat, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        formCard.add(lblEstimasi, gbc);
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setOpaque(false);
        
        JButton btnHitung = new JButton("Hitung Estimasi");
        btnHitung.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnHitung.setBackground(new Color(100, 130, 200));
        btnHitung.setForeground(Color.WHITE);
        btnHitung.setFocusPainted(false);
        btnHitung.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnHitung.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JButton btnSetor = new JButton("Setor Sampah");
        btnSetor.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnSetor.setBackground(new Color(50, 150, 50));
        btnSetor.setForeground(Color.WHITE);
        btnSetor.setFocusPainted(false);
        btnSetor.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnSetor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnPanel.add(btnHitung);
        btnPanel.add(btnSetor);
        
        gbc.gridy = 3;
        formCard.add(btnPanel, gbc);
        
        p.add(formCard, BorderLayout.NORTH);
        
        btnHitung.addActionListener(e -> {
            jenisLimbah jenis = (jenisLimbah) cbJenis.getSelectedItem();
            if (jenis == null || txtBerat.getText().isEmpty()) return;

            if (!inputValidator.isValidBerat(txtBerat.getText())) {
                lblEstimasi.setText("Estimasi poin: 0");
                return;
            }

            try {
                double berat = Double.parseDouble(txtBerat.getText());
                int poin = hitungPoin(jenis.getIdJenis(), berat);
                lblEstimasi.setText("Estimasi poin: " + poin);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(p, "Berat harus berupa angka!");
            }
        });
        
        btnSetor.addActionListener(e -> {
            jenisLimbah jenis = (jenisLimbah) cbJenis.getSelectedItem();
            String beratStr = txtBerat.getText().trim();
            
            if (jenis == null || beratStr.isEmpty()) {
                JOptionPane.showMessageDialog(p,
                    "Pilih jenis sampah dan isi berat!",
                    "Field Kosong", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!inputValidator.isValidBerat(beratStr)) {
                JOptionPane.showMessageDialog(p,
                    inputValidator.errorBerat(),
                    "Berat Tidak Valid", JOptionPane.ERROR_MESSAGE);
                txtBerat.requestFocus();
                txtBerat.selectAll();
                return;
            }
            
            double berat = Double.parseDouble(beratStr);
            simpanTransaksi(jenis.getIdJenis(), berat);
            txtBerat.setText("");
            lblEstimasi.setText("Estimasi poin: 0");
            refreshPoin();
            updateStatistik();
        });
        
        return p;
    }
    
    private JPanel buildRewardPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        String[] cols = {"ID Reward", "Nama Reward", "Poin Dibutuhkan"};
        modelReward = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(modelReward);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(32);
        table.setSelectionBackground(new Color(180, 220, 180));
        
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(50, 150, 50));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 38));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        JButton btnTukar = new JButton("Tukar Reward Terpilih");
        btnTukar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTukar.setBackground(new Color(50, 150, 50));
        btnTukar.setForeground(Color.WHITE);
        btnTukar.setFocusPainted(false);
        btnTukar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btnTukar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        loadRewards(modelReward);
        
        btnTukar.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { 
                JOptionPane.showMessageDialog(p, "Pilih reward terlebih dahulu!"); 
                return; 
            }
            String idReward = (String) modelReward.getValueAt(row, 0);
            int poinDibutuhkan = (int) modelReward.getValueAt(row, 2);
            tukarReward(idReward, poinDibutuhkan);
            refreshPoin();
            updateStatistik();
            loadRewards(modelReward);
        });
        
        p.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        bottomPanel.add(btnTukar);
        p.add(bottomPanel, BorderLayout.SOUTH);
        
        return p;
    }
    
    private JPanel buildRiwayatPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblRingkasan = new JLabel("", SwingConstants.CENTER);
        lblRingkasan.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblRingkasan.setForeground(new Color(50, 150, 50));
        lblRingkasan.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        
        String[] cols = {
            "ID Transaksi", "Tanggal", "Jenis Sampah",
            "Kategori", "Berat (kg)", "Poin Didapat", "Status"
        };
        modelRiwayat = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(modelRiwayat);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(50, 150, 50));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        
        table.getColumnModel().getColumn(0).setPreferredWidth(90);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(70);
        table.getColumnModel().getColumn(5).setPreferredWidth(90);
        table.getColumnModel().getColumn(6).setPreferredWidth(80);
        
        table.getColumnModel().getColumn(6).setCellRenderer((TableCellRenderer) new StatusCellRenderer());
        table.setDefaultRenderer(Object.class, (TableCellRenderer) new StripedTableCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefresh.setBackground(new Color(100, 130, 200));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnRefresh.addActionListener(e -> {
            loadRiwayatSetor(modelRiwayat, lblRingkasan);
            updateStatistik();
            refreshPoin();
        });
        
        loadRiwayatSetor(modelRiwayat, lblRingkasan);
        
        p.add(lblRingkasan, BorderLayout.NORTH);
        p.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomPanel.add(btnRefresh);
        p.add(bottomPanel, BorderLayout.SOUTH);
        
        return p;
    }
    
    private JPanel buildRiwayatKlaimPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblInfoPoin = new JLabel("", SwingConstants.CENTER);
        lblInfoPoin.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblInfoPoin.setForeground(new Color(50, 150, 50));
        lblInfoPoin.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        
        String[] cols = {"ID Klaim", "Tanggal", "Nama Reward", "Poin Digunakan"};
        modelKlaim = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(modelKlaim);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(50, 150, 50));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 36));
        
        table.setDefaultRenderer(Object.class, (TableCellRenderer) new StripedTableCellRenderer());
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnRefresh.setBackground(new Color(100, 130, 200));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnRefresh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        btnRefresh.addActionListener(e -> {
            loadRiwayatKlaim(modelKlaim, lblInfoPoin);
            updateStatistik();
            refreshPoin();
        });
        
        loadRiwayatKlaim(modelKlaim, lblInfoPoin);
        
        p.add(lblInfoPoin, BorderLayout.NORTH);
        p.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomPanel.add(btnRefresh);
        p.add(bottomPanel, BorderLayout.SOUTH);
        
        return p;
    }
    
    // ==================== FUNGSI ASLI (TIDAK DIUBAH) ====================
    
    private void loadJenisLimbah(JComboBox<jenisLimbah> cb) {
        try {
            Connection conn = DatabaseConnect.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Jenis_limbah");
            while (rs.next())
                cb.addItem(new jenisLimbah(rs.getString("id_jenis"),
                    rs.getString("nama_jenis"), rs.getString("id_kategori")));
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    private int hitungPoin(String idJenis, double berat) {
        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT poin_per_kg FROM Rule_konversi WHERE id_jenis=?");
            ps.setString(1, idJenis);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return (int)(rs.getInt("poin_per_kg") * berat);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
    
    private void simpanTransaksi(String idJenis, double berat) {
        try {
            CallableStatement cs = DatabaseConnect.getConnection()
                .prepareCall("{CALL sp_setor_sampah(?, ?, ?, ?)}");
            cs.setString(1, user.getId());
            cs.setString(2, idJenis);
            cs.setDouble(3, berat);
            cs.registerOutParameter(4, Types.VARCHAR);
            cs.execute();
            
            String hasil = cs.getString(4);
            JOptionPane.showMessageDialog(this, hasil);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    
    private void loadRewards(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection conn = DatabaseConnect.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM Reward");
            while (rs.next())
                model.addRow(new Object[]{rs.getString("id_reward"),
                    rs.getString("nama_reward"), rs.getInt("poin_dibutuhkan")});
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    private void tukarReward(String idReward, int poinDibutuhkan) {
        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT total_poin FROM usr WHERE id_user=?");
            ps.setString(1, user.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) user.setTotalPoin(rs.getInt("total_poin"));
        } catch (SQLException e) { e.printStackTrace(); }
        
        if (user.getTotalPoin() < poinDibutuhkan) {
            JOptionPane.showMessageDialog(this,
                "Penukaran Gagal!\n\n" +
                "Poin Anda    : " + user.getTotalPoin() + " poin\n" +
                "Poin dibutuhkan : " + poinDibutuhkan + " poin\n" +
                "Kekurangan   : " + (poinDibutuhkan - user.getTotalPoin()) + " poin\n\n" +
                "Setor lebih banyak sampah untuk menambah poin!",
                "Poin Tidak Mencukupi",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int konfirm = JOptionPane.showConfirmDialog(this,
            "Konfirmasi Penukaran Reward\n\n" +
            "Reward       : " + getNamaReward(idReward) + "\n" +
            "Poin dibutuhkan : " + poinDibutuhkan + " poin\n" +
            "Poin Anda    : " + user.getTotalPoin() + " poin\n" +
            "Sisa poin    : " + (user.getTotalPoin() - poinDibutuhkan) + " poin\n\n" +
            "Lanjutkan penukaran?",
            "Konfirmasi", JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        if (konfirm != JOptionPane.YES_OPTION) return;
        
        try {
            Connection conn = DatabaseConnect.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) AS cnt FROM klaim_reward");
            rs.next();
            String idKlaim = String.format("KL%03d", rs.getInt("cnt") + 1);
            
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO klaim_reward(id_klaim,id_user,id_reward,tanggal) VALUES(?,?,?,?)");
            ps.setString(1, idKlaim);
            ps.setString(2, user.getId());
            ps.setString(3, idReward);
            ps.setString(4, LocalDate.now().toString());
            ps.executeUpdate();
            
            user.kurangPoin(poinDibutuhkan);
            PreparedStatement psRefresh = conn.prepareStatement(
                "SELECT total_poin FROM usr WHERE id_user=?");
            psRefresh.setString(1, user.getId());
            ResultSet rsRefresh = psRefresh.executeQuery();
            if (rsRefresh.next()) {
                user.setTotalPoin(rsRefresh.getInt("total_poin"));
                if (lblPoin != null) lblPoin.setText(String.valueOf(user.getTotalPoin()));
            }
            JOptionPane.showMessageDialog(this, "Reward berhasil ditukar!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal tukar reward: " + e.getMessage());
        }
    }
    
    private String getNamaReward(String idReward) {
        try {
            PreparedStatement ps = DatabaseConnect.getConnection()
                .prepareStatement("SELECT nama_reward FROM Reward WHERE id_reward=?");
            ps.setString(1, idReward);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("nama_reward");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idReward;
    }
    
    private void loadRiwayatSetor(DefaultTableModel model, JLabel lblRingkasan) {
        model.setRowCount(0);
        int totalPoinDidapat   = 0;
        double totalBeratSetor = 0;
        
        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id_transaksi, tanggal, nama_jenis, nama_kategori, " +
                "berat_kg, poin_estimasi, poin_final, status " +
                "FROM v_detail_transaksi_lengkap " +
                "WHERE id_user = ? " +
                "ORDER BY tanggal DESC");
            ps.setString(1, user.getId());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String status  = rs.getString("status");
                int poinFinal  = rs.getInt("poin_final");
                int poinEst    = rs.getInt("poin_estimasi");
                double beratKg = rs.getDouble("berat_kg");
                
                if (status.equals("valid")) {
                    totalPoinDidapat  += poinFinal;
                    totalBeratSetor   += beratKg;
                }
                
                String tampilPoin;
                if (status.equals("valid"))         tampilPoin = String.valueOf(poinFinal);
                else if (status.equals("diproses")) tampilPoin = poinEst + " (estimasi)";
                else                                tampilPoin = "- (pending)";
                
                model.addRow(new Object[]{
                    rs.getString("id_transaksi"),
                    rs.getString("tanggal"),
                    rs.getString("nama_jenis"),
                    rs.getString("nama_kategori"),
                    beratKg,
                    tampilPoin,
                    status
                });
            }
            
            if (model.getRowCount() == 0) {
                lblRingkasan.setText("Belum ada riwayat setor sampah.");
                lblRingkasan.setForeground(Color.GRAY);
            } else {
                lblRingkasan.setText(
                    "Total setor: " + model.getRowCount() + " transaksi  |  " +
                    "Total berat: " + String.format("%.1f", totalBeratSetor) + " kg  |  " +
                    "Total poin didapat: " + totalPoinDidapat + " poin"
                );
                lblRingkasan.setForeground(new Color(50, 150, 50));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblRingkasan.setText("Gagal memuat: " + e.getMessage());
            lblRingkasan.setForeground(Color.RED);
        }
    }
    
    private void loadRiwayatKlaim(DefaultTableModel model, JLabel lblInfoPoin) {
        model.setRowCount(0);
        int totalPoinDigunakan = 0;
        
        try {
            Connection conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id_klaim, tanggal, nama_reward, poin_dibutuhkan " +
                "FROM v_klaim_reward " +
                "WHERE id_user = ? " +
                "ORDER BY tanggal DESC");
            ps.setString(1, user.getId());
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int poin = rs.getInt("poin_dibutuhkan");
                totalPoinDigunakan += poin;
                model.addRow(new Object[]{
                    rs.getString("id_klaim"),
                    rs.getString("tanggal"),
                    rs.getString("nama_reward"),
                    poin
                });
            }
            
            if (model.getRowCount() == 0) {
                lblInfoPoin.setText("Belum ada riwayat penukaran reward.");
                lblInfoPoin.setForeground(Color.GRAY);
            } else {
                lblInfoPoin.setText(
                    "Total reward ditukar: " + model.getRowCount() +
                    " kali  |  Total poin digunakan: " + totalPoinDigunakan +
                    "  |  Sisa poin: " + user.getTotalPoin()
                );
                lblInfoPoin.setForeground(new Color(50, 150, 50));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            lblInfoPoin.setText("Gagal memuat riwayat klaim.");
            lblInfoPoin.setForeground(Color.RED);
        }
    }
    
    // ==================== CUSTOM RENDERER ====================
    
    class StripedTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable 
                table, Object value,
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
            label.setFont(new Font("Segoe UI", Font.BOLD, 11));
            
            if (!isSelected) {
                if (status.equalsIgnoreCase("valid")) {
                    label.setBackground(new Color(100, 200, 100));
                    label.setForeground(Color.WHITE);
                } else if (status.equalsIgnoreCase("diproses")) {
                    label.setBackground(new Color(100, 180, 250));
                    label.setForeground(Color.WHITE);
                } else if (status.equalsIgnoreCase("pending")) {
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
