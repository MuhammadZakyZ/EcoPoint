-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 08, 2026 at 12:31 AM
-- Server version: 8.0.30
-- PHP Version: 8.2.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eco_point`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_daftar_user` (IN `p_nama` VARCHAR(100), IN `p_alamat` TEXT, IN `p_no_hp` VARCHAR(20), IN `p_username` VARCHAR(50), IN `p_password` VARCHAR(100), OUT `p_status_out` VARCHAR(100))   BEGIN
    DECLARE v_username_exists INT DEFAULT 0;
    DECLARE v_max             INT DEFAULT 0;
    DECLARE v_new_id          VARCHAR(10);

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_status_out = 'ERROR: Terjadi kesalahan saat mendaftar.';
    END;

    START TRANSACTION;

    -- Cek username sudah dipakai
    SELECT COUNT(*) INTO v_username_exists
    FROM usr WHERE username = p_username;

    IF v_username_exists > 0 THEN
        ROLLBACK;
        SET p_status_out = 'ERROR: Username sudah digunakan.';
    ELSE
        -- Generate ID aman pakai MAX
        SELECT COALESCE(MAX(CAST(SUBSTRING(id_user, 2) AS UNSIGNED)), 0)
        INTO v_max FROM usr;
        SET v_new_id = CONCAT('U', LPAD(v_max + 1, 3, '0'));

        INSERT INTO usr(id_user, nama_user, alamat, no_hp, total_poin, username, password)
        VALUES (v_new_id, p_nama, p_alamat, p_no_hp, 0, p_username, p_password);

        COMMIT;
        SET p_status_out = CONCAT('SUCCESS: Akun berhasil dibuat dengan ID ', v_new_id);
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_edit_petugas` (IN `p_id_petugas` VARCHAR(10), IN `p_nama` VARCHAR(100), IN `p_username` VARCHAR(50), IN `p_password` VARCHAR(100), OUT `p_status_out` VARCHAR(100))   BEGIN
    DECLARE v_exists          INT DEFAULT 0;
    DECLARE v_username_exists INT DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_status_out = 'ERROR: Terjadi kesalahan saat mengedit petugas.';
    END;

    START TRANSACTION;

    -- Cek petugas ada
    SELECT COUNT(*) INTO v_exists
    FROM Petugas WHERE id_petugas = p_id_petugas;

    IF v_exists = 0 THEN
        ROLLBACK;
        SET p_status_out = 'ERROR: Petugas tidak ditemukan.';
    ELSE
        -- Cek username sudah dipakai petugas LAIN
        SELECT COUNT(*) INTO v_username_exists
        FROM Petugas
        WHERE username = p_username AND id_petugas != p_id_petugas;

        IF v_username_exists > 0 THEN
            ROLLBACK;
            SET p_status_out = 'ERROR: Username sudah digunakan petugas lain.';
        ELSE
            UPDATE Petugas
            SET nama_petugas = p_nama,
                username     = p_username,
                password     = p_password
            WHERE id_petugas = p_id_petugas;

            COMMIT;
            SET p_status_out = CONCAT('SUCCESS: Data petugas ', p_id_petugas, ' berhasil diperbarui.');
        END IF;
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_edit_user` (IN `p_id_user` VARCHAR(10), IN `p_nama` VARCHAR(100), IN `p_alamat` TEXT, IN `p_no_hp` VARCHAR(20), OUT `p_status_out` VARCHAR(100))   BEGIN
    DECLARE v_exists INT DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_status_out = 'ERROR: Terjadi kesalahan saat mengedit user.';
    END;

    START TRANSACTION;

    SELECT COUNT(*) INTO v_exists
    FROM usr WHERE id_user = p_id_user;

    IF v_exists = 0 THEN
        ROLLBACK;
        SET p_status_out = 'ERROR: User tidak ditemukan.';
    ELSE
        UPDATE usr
        SET nama_user = p_nama,
            alamat    = p_alamat,
            no_hp     = p_no_hp
        WHERE id_user = p_id_user;

        COMMIT;
        SET p_status_out = CONCAT('SUCCESS: Data user ', p_id_user, ' berhasil diperbarui.');
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_setor_sampah` (IN `p_id_user` VARCHAR(10), IN `p_id_jenis` VARCHAR(10), IN `p_berat_kg` DOUBLE, OUT `p_status_out` VARCHAR(100))   BEGIN
    DECLARE v_max_tr    INT DEFAULT 0;
    DECLARE v_max_det   INT DEFAULT 0;
    DECLARE v_id_tr     VARCHAR(10);
    DECLARE v_id_det    VARCHAR(10);
    DECLARE v_poin_pkg  INT DEFAULT 0;
    DECLARE v_estimasi  INT DEFAULT 0;

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_status_out = 'ERROR: Terjadi kesalahan saat menyetor sampah.';
    END;

    START TRANSACTION;
    SELECT COALESCE(MAX(CAST(SUBSTRING(id_transaksi, 2) AS UNSIGNED)), 0)
    INTO v_max_tr FROM Transaksi_limbah;
    SET v_id_tr = CONCAT('T', LPAD(v_max_tr + 1, 3, '0'));
    
    SELECT COALESCE(MAX(CAST(SUBSTRING(id_detail, 2) AS UNSIGNED)), 0)
    INTO v_max_det FROM Detail_transaksi;
    SET v_id_det = CONCAT('D', LPAD(v_max_det + 1, 3, '0'));

    -- Ambil poin per kg dari rule konversi
    SELECT COALESCE(poin_per_kg, 0) INTO v_poin_pkg
    FROM Rule_konversi WHERE id_jenis = p_id_jenis;

    -- Hitung estimasi poin
    SET v_estimasi = FLOOR(v_poin_pkg * p_berat_kg);

    -- Insert transaksi
    INSERT INTO Transaksi_limbah(id_transaksi, id_user, id_petugas, tanggal, status, id_admin)
    VALUES (v_id_tr, p_id_user, NULL, CURDATE(), 'pending', NULL);

    -- Insert detail transaksi
    INSERT INTO Detail_transaksi(id_detail, id_transaksi, id_jenis, berat_kg, poin_estimasi, poin_final)
    VALUES (v_id_det, v_id_tr, p_id_jenis, p_berat_kg, v_estimasi, 0);

    COMMIT;
    SET p_status_out = CONCAT('SUCCESS: Sampah berhasil disetor! ID Transaksi: ', v_id_tr,
        '. Estimasi poin: ', v_estimasi);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_tambah_petugas` (IN `p_nama` VARCHAR(100), IN `p_username` VARCHAR(50), IN `p_password` VARCHAR(100), OUT `p_status_out` VARCHAR(100))   BEGIN
    DECLARE v_username_exists INT DEFAULT 0;
    DECLARE v_max             INT DEFAULT 0;
    DECLARE v_new_id          VARCHAR(10);

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_status_out = 'ERROR: Terjadi kesalahan saat menambah petugas.';
    END;

    START TRANSACTION;
    SELECT COUNT(*) INTO v_username_exists
    FROM Petugas WHERE username = p_username;

    IF v_username_exists > 0 THEN
        ROLLBACK;
        SET p_status_out = 'ERROR: Username sudah digunakan.';
    ELSE
        -- Generate ID aman pakai MAX
        SELECT COALESCE(MAX(CAST(SUBSTRING(id_petugas, 2) AS UNSIGNED)), 0)
        INTO v_max FROM Petugas;
        SET v_new_id = CONCAT('P', LPAD(v_max + 1, 3, '0'));

        INSERT INTO Petugas(id_petugas, nama_petugas, username, password)
        VALUES (v_new_id, p_nama, p_username, p_password);

        COMMIT;
        SET p_status_out = CONCAT('SUCCESS: Petugas ditambahkan dengan ID ', v_new_id);
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_tukar_reward` (IN `p_id_user` VARCHAR(10), IN `p_id_reward` VARCHAR(10), OUT `p_status_out` VARCHAR(100))   BEGIN
    DECLARE v_reward_exists INT DEFAULT 0;
    DECLARE v_id_klaim VARCHAR(10);

    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SET p_status_out = 'ERROR: Terjadi kesalahan saat menukar reward.';
    END;

    START TRANSACTION;

    SELECT COUNT(*) 
    INTO v_reward_exists
    FROM Reward 
    WHERE id_reward = p_id_reward;

    IF v_reward_exists = 0 THEN
        ROLLBACK;
        SET p_status_out = 'ERROR: Reward tidak ditemukan.';
    ELSE
        
        SELECT CONCAT('KL', LPAD(
            (SELECT COALESCE(MAX(CAST(SUBSTRING(id_klaim, 3) AS UNSIGNED)), 0) + 1 
             FROM Klaim_reward),
        3, '0'))
        INTO v_id_klaim;

        INSERT INTO Klaim_reward(id_klaim, id_user, id_reward, tanggal)
        VALUES (v_id_klaim, p_id_user, p_id_reward, CURDATE());

        COMMIT;

        SET p_status_out = CONCAT('SUCCESS: Reward berhasil ditukar! ID Klaim: ', v_id_klaim);
    END IF;

END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id_admin` varchar(7) NOT NULL,
  `nama_admin` varchar(30) DEFAULT NULL,
  `username` varchar(15) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `nama_admin`, `username`, `password`) VALUES
('A001', 'Admin1', 'admin1', 'admin#123');

-- --------------------------------------------------------

--
-- Table structure for table `detail_transaksi`
--

CREATE TABLE `detail_transaksi` (
  `id_detail` varchar(7) NOT NULL,
  `id_transaksi` varchar(7) DEFAULT NULL,
  `id_jenis` varchar(7) DEFAULT NULL,
  `berat_kg` double DEFAULT NULL,
  `poin_estimasi` int DEFAULT NULL,
  `poin_final` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `detail_transaksi`
--

INSERT INTO `detail_transaksi` (`id_detail`, `id_transaksi`, `id_jenis`, `berat_kg`, `poin_estimasi`, `poin_final`) VALUES
('D001', 'T001', 'J004', 2, 200, 200),
('D002', 'T002', 'J004', 3, 300, 300),
('D003', 'T003', 'J004', 3, 300, 300),
('D004', 'T004', 'J004', 10, 1000, 1000);

-- --------------------------------------------------------

--
-- Table structure for table `jenis_limbah`
--

CREATE TABLE `jenis_limbah` (
  `id_jenis` varchar(7) NOT NULL,
  `nama_jenis` varchar(20) DEFAULT NULL,
  `id_kategori` varchar(7) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `jenis_limbah`
--

INSERT INTO `jenis_limbah` (`id_jenis`, `nama_jenis`, `id_kategori`) VALUES
('J001', 'Plastik', 'KT002'),
('J002', 'Kertas', 'KT002'),
('J003', 'Sisa Makanan', 'KT001'),
('J004', 'Minyak Jelantah', 'KT003');

-- --------------------------------------------------------

--
-- Table structure for table `kategori_limbah`
--

CREATE TABLE `kategori_limbah` (
  `id_kategori` varchar(7) NOT NULL,
  `nama_kategori` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `kategori_limbah`
--

INSERT INTO `kategori_limbah` (`id_kategori`, `nama_kategori`) VALUES
('KT001', 'Organik'),
('KT002', 'Anorganik'),
('KT003', 'Limbah B3');

-- --------------------------------------------------------

--
-- Table structure for table `klaim_reward`
--

CREATE TABLE `klaim_reward` (
  `id_klaim` varchar(7) NOT NULL,
  `id_user` varchar(7) DEFAULT NULL,
  `id_reward` varchar(7) DEFAULT NULL,
  `tanggal` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `klaim_reward`
--

INSERT INTO `klaim_reward` (`id_klaim`, `id_user`, `id_reward`, `tanggal`) VALUES
('KL001', 'U002', 'RW001', '2026-04-05'),
('KL002', 'U002', 'RW002', '2026-04-05'),
('KL003', 'U002', 'RW002', '2026-04-20');

--
-- Triggers `klaim_reward`
--
DELIMITER $$
CREATE TRIGGER `trg_after_klaim_reward` AFTER INSERT ON `klaim_reward` FOR EACH ROW BEGIN
    DECLARE v_poin INT DEFAULT 0;

    SELECT poin_dibutuhkan INTO v_poin
    FROM Reward WHERE id_reward = NEW.id_reward;

    UPDATE usr
    SET total_poin = total_poin - v_poin
    WHERE id_user = NEW.id_user;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_check_poin_before_klaim` BEFORE INSERT ON `klaim_reward` FOR EACH ROW BEGIN
    DECLARE v_poin_user       INT DEFAULT 0;
    DECLARE v_poin_dibutuhkan INT DEFAULT 0;

    SELECT total_poin INTO v_poin_user
    FROM usr WHERE id_user = NEW.id_user;

    SELECT poin_dibutuhkan INTO v_poin_dibutuhkan
    FROM Reward WHERE id_reward = NEW.id_reward;

    IF v_poin_user < v_poin_dibutuhkan THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Poin tidak mencukupi untuk klaim reward ini.';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `petugas`
--

CREATE TABLE `petugas` (
  `id_petugas` varchar(7) NOT NULL,
  `nama_petugas` varchar(30) DEFAULT NULL,
  `username` varchar(15) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `petugas`
--

INSERT INTO `petugas` (`id_petugas`, `nama_petugas`, `username`, `password`) VALUES
('P001', 'Petugas1', 'petugas1', 'petugas123'),
('P002', 'Petugas2', 'petugas2', '12345678');

-- --------------------------------------------------------

--
-- Table structure for table `reward`
--

CREATE TABLE `reward` (
  `id_reward` varchar(7) NOT NULL,
  `nama_reward` varchar(100) DEFAULT NULL,
  `poin_dibutuhkan` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `reward`
--

INSERT INTO `reward` (`id_reward`, `nama_reward`, `poin_dibutuhkan`) VALUES
('RW001', 'Voucher Belanja 10k', 120),
('RW002', 'Pulsa 25k', 250);

-- --------------------------------------------------------

--
-- Table structure for table `rule_konversi`
--

CREATE TABLE `rule_konversi` (
  `id_rule` varchar(7) NOT NULL,
  `id_jenis` varchar(7) DEFAULT NULL,
  `poin_per_kg` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `rule_konversi`
--

INSERT INTO `rule_konversi` (`id_rule`, `id_jenis`, `poin_per_kg`) VALUES
('R001', 'J001', 20),
('R002', 'J002', 15),
('R003', 'J003', 5),
('R004', 'J004', 100);

-- --------------------------------------------------------

--
-- Table structure for table `stok_limbah`
--

CREATE TABLE `stok_limbah` (
  `id_stok` varchar(7) NOT NULL,
  `id_jenis` varchar(10) DEFAULT NULL,
  `total_berat` double DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `stok_limbah`
--

INSERT INTO `stok_limbah` (`id_stok`, `id_jenis`, `total_berat`) VALUES
('S001', 'J001', 150),
('S002', 'J004', 68);

-- --------------------------------------------------------

--
-- Table structure for table `transaksi_limbah`
--

CREATE TABLE `transaksi_limbah` (
  `id_transaksi` varchar(7) NOT NULL,
  `id_user` varchar(7) DEFAULT NULL,
  `id_petugas` varchar(7) DEFAULT NULL,
  `tanggal` date DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `id_admin` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `transaksi_limbah`
--

INSERT INTO `transaksi_limbah` (`id_transaksi`, `id_user`, `id_petugas`, `tanggal`, `status`, `id_admin`) VALUES
('T001', 'U002', 'P001', '2026-04-05', 'valid', 'A001'),
('T002', 'U002', 'P001', '2026-04-05', 'valid', 'A001'),
('T003', 'U002', 'P001', '2026-04-05', 'valid', 'A001'),
('T004', 'U002', 'P001', '2026-04-20', 'valid', 'A001');

--
-- Triggers `transaksi_limbah`
--
DELIMITER $$
CREATE TRIGGER `trg_after_diproses` AFTER UPDATE ON `transaksi_limbah` FOR EACH ROW BEGIN
    IF NEW.status = 'diproses' AND OLD.status = 'pending' THEN
        UPDATE Detail_transaksi
        SET poin_final = poin_estimasi
        WHERE id_transaksi = NEW.id_transaksi;
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_after_validasi_poin` AFTER UPDATE ON `transaksi_limbah` FOR EACH ROW BEGIN
    DECLARE v_total_poin INT DEFAULT 0;

    IF NEW.status = 'valid' AND OLD.status = 'diproses' THEN
        SELECT COALESCE(SUM(poin_final), 0) INTO v_total_poin
        FROM Detail_transaksi
        WHERE id_transaksi = NEW.id_transaksi;

        UPDATE usr
        SET total_poin = total_poin + v_total_poin
        WHERE id_user = NEW.id_user;
    END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `trg_after_validasi_stok` AFTER UPDATE ON `transaksi_limbah` FOR EACH ROW BEGIN
    DECLARE v_id_jenis  VARCHAR(10);
    DECLARE v_berat     DOUBLE;
    DECLARE v_exists    INT;
    DECLARE v_max       INT;
    DECLARE v_new_id    VARCHAR(10);
    DECLARE done        INT DEFAULT FALSE;

    DECLARE cur CURSOR FOR
        SELECT id_jenis, berat_kg
        FROM Detail_transaksi
        WHERE id_transaksi = NEW.id_transaksi;

    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    IF NEW.status = 'valid' AND OLD.status = 'diproses' THEN
        OPEN cur;
        loop_stok: LOOP
            FETCH cur INTO v_id_jenis, v_berat;
            IF done THEN LEAVE loop_stok; END IF;

            SELECT COUNT(*) INTO v_exists
            FROM Stok_limbah WHERE id_jenis = v_id_jenis;

            IF v_exists > 0 THEN
                UPDATE Stok_limbah
                SET total_berat = total_berat + v_berat
                WHERE id_jenis = v_id_jenis;
            ELSE
                -- Generate ID stok pakai MAX
                SELECT COALESCE(MAX(CAST(SUBSTRING(id_stok, 2) AS UNSIGNED)), 0)
                INTO v_max FROM Stok_limbah;
                SET v_new_id = CONCAT('S', LPAD(v_max + 1, 3, '0'));

                INSERT INTO Stok_limbah(id_stok, id_jenis, total_berat)
                VALUES (v_new_id, v_id_jenis, v_berat);
            END IF;
        END LOOP;
        CLOSE cur;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `usr`
--

CREATE TABLE `usr` (
  `id_user` varchar(7) NOT NULL,
  `nama_user` varchar(30) DEFAULT NULL,
  `alamat` text,
  `no_hp` varchar(15) DEFAULT NULL,
  `total_poin` int DEFAULT '0',
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `usr`
--

INSERT INTO `usr` (`id_user`, `nama_user`, `alamat`, `no_hp`, `total_poin`, `username`, `password`) VALUES
('U001', 'Budi Afarizi', 'Surabaya', '085233674361', 10, 'budiMlg', '12345678'),
('U002', 'Siti Amira', 'Malang', '081337891112', 830, 'sitiRah', 'siti1234'),
('U003', 'tester', 'test', '01111111', 0, 'tester1', '123456');

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_detail_transaksi_lengkap`
-- (See below for the actual view)
--
CREATE TABLE `v_detail_transaksi_lengkap` (
`id_detail` varchar(7)
,`id_transaksi` varchar(7)
,`id_user` varchar(7)
,`tanggal` date
,`status` varchar(20)
,`nama_user` varchar(30)
,`nama_jenis` varchar(20)
,`nama_kategori` varchar(20)
,`berat_kg` double
,`poin_estimasi` int
,`poin_final` int
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_klaim_reward`
-- (See below for the actual view)
--
CREATE TABLE `v_klaim_reward` (
`id_klaim` varchar(7)
,`tanggal` date
,`id_user` varchar(7)
,`nama_user` varchar(30)
,`nama_reward` varchar(100)
,`poin_dibutuhkan` int
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_poin_user`
-- (See below for the actual view)
--
CREATE TABLE `v_poin_user` (
`id_user` varchar(7)
,`nama_user` varchar(30)
,`total_poin` int
,`total_poin_masuk` decimal(32,0)
,`total_poin_keluar` decimal(32,0)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_stok_lengkap`
-- (See below for the actual view)
--
CREATE TABLE `v_stok_lengkap` (
`id_stok` varchar(7)
,`nama_jenis` varchar(20)
,`nama_kategori` varchar(20)
,`total_berat` double
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_transaksi_lengkap`
-- (See below for the actual view)
--
CREATE TABLE `v_transaksi_lengkap` (
`id_transaksi` varchar(7)
,`tanggal` date
,`status` varchar(20)
,`id_user` varchar(7)
,`nama_user` varchar(30)
,`no_hp` varchar(15)
,`nama_petugas` varchar(30)
,`nama_admin` varchar(30)
);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_admin`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD PRIMARY KEY (`id_detail`),
  ADD KEY `id_transaksi` (`id_transaksi`),
  ADD KEY `id_jenis` (`id_jenis`);

--
-- Indexes for table `jenis_limbah`
--
ALTER TABLE `jenis_limbah`
  ADD PRIMARY KEY (`id_jenis`),
  ADD KEY `id_kategori` (`id_kategori`);

--
-- Indexes for table `kategori_limbah`
--
ALTER TABLE `kategori_limbah`
  ADD PRIMARY KEY (`id_kategori`);

--
-- Indexes for table `klaim_reward`
--
ALTER TABLE `klaim_reward`
  ADD PRIMARY KEY (`id_klaim`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_reward` (`id_reward`);

--
-- Indexes for table `petugas`
--
ALTER TABLE `petugas`
  ADD PRIMARY KEY (`id_petugas`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `reward`
--
ALTER TABLE `reward`
  ADD PRIMARY KEY (`id_reward`);

--
-- Indexes for table `rule_konversi`
--
ALTER TABLE `rule_konversi`
  ADD PRIMARY KEY (`id_rule`),
  ADD KEY `id_jenis` (`id_jenis`);

--
-- Indexes for table `stok_limbah`
--
ALTER TABLE `stok_limbah`
  ADD PRIMARY KEY (`id_stok`),
  ADD KEY `id_jenis` (`id_jenis`);

--
-- Indexes for table `transaksi_limbah`
--
ALTER TABLE `transaksi_limbah`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `id_user` (`id_user`),
  ADD KEY `id_petugas` (`id_petugas`),
  ADD KEY `id_admin` (`id_admin`);

--
-- Indexes for table `usr`
--
ALTER TABLE `usr`
  ADD PRIMARY KEY (`id_user`),
  ADD UNIQUE KEY `username` (`username`);

-- --------------------------------------------------------

--
-- Structure for view `v_detail_transaksi_lengkap`
--
DROP TABLE IF EXISTS `v_detail_transaksi_lengkap`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_detail_transaksi_lengkap`  AS SELECT `d`.`id_detail` AS `id_detail`, `d`.`id_transaksi` AS `id_transaksi`, `t`.`id_user` AS `id_user`, `t`.`tanggal` AS `tanggal`, `t`.`status` AS `status`, `u`.`nama_user` AS `nama_user`, `j`.`nama_jenis` AS `nama_jenis`, `k`.`nama_kategori` AS `nama_kategori`, `d`.`berat_kg` AS `berat_kg`, `d`.`poin_estimasi` AS `poin_estimasi`, `d`.`poin_final` AS `poin_final` FROM ((((`detail_transaksi` `d` join `transaksi_limbah` `t` on((`d`.`id_transaksi` = `t`.`id_transaksi`))) join `usr` `u` on((`t`.`id_user` = `u`.`id_user`))) join `jenis_limbah` `j` on((`d`.`id_jenis` = `j`.`id_jenis`))) join `kategori_limbah` `k` on((`j`.`id_kategori` = `k`.`id_kategori`))) ;

-- --------------------------------------------------------

--
-- Structure for view `v_klaim_reward`
--
DROP TABLE IF EXISTS `v_klaim_reward`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_klaim_reward`  AS SELECT `kr`.`id_klaim` AS `id_klaim`, `kr`.`tanggal` AS `tanggal`, `u`.`id_user` AS `id_user`, `u`.`nama_user` AS `nama_user`, `r`.`nama_reward` AS `nama_reward`, `r`.`poin_dibutuhkan` AS `poin_dibutuhkan` FROM ((`klaim_reward` `kr` join `usr` `u` on((`kr`.`id_user` = `u`.`id_user`))) join `reward` `r` on((`kr`.`id_reward` = `r`.`id_reward`))) ;

-- --------------------------------------------------------

--
-- Structure for view `v_poin_user`
--
DROP TABLE IF EXISTS `v_poin_user`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_poin_user`  AS SELECT `u`.`id_user` AS `id_user`, `u`.`nama_user` AS `nama_user`, `u`.`total_poin` AS `total_poin`, coalesce(sum(`d`.`poin_final`),0) AS `total_poin_masuk`, coalesce(sum(`r`.`poin_dibutuhkan`),0) AS `total_poin_keluar` FROM ((((`usr` `u` left join `transaksi_limbah` `t` on(((`u`.`id_user` = `t`.`id_user`) and (`t`.`status` = 'valid')))) left join `detail_transaksi` `d` on((`t`.`id_transaksi` = `d`.`id_transaksi`))) left join `klaim_reward` `kr` on((`u`.`id_user` = `kr`.`id_user`))) left join `reward` `r` on((`kr`.`id_reward` = `r`.`id_reward`))) GROUP BY `u`.`id_user`, `u`.`nama_user`, `u`.`total_poin` ;

-- --------------------------------------------------------

--
-- Structure for view `v_stok_lengkap`
--
DROP TABLE IF EXISTS `v_stok_lengkap`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_stok_lengkap`  AS SELECT `s`.`id_stok` AS `id_stok`, `j`.`nama_jenis` AS `nama_jenis`, `k`.`nama_kategori` AS `nama_kategori`, `s`.`total_berat` AS `total_berat` FROM ((`stok_limbah` `s` join `jenis_limbah` `j` on((`s`.`id_jenis` = `j`.`id_jenis`))) join `kategori_limbah` `k` on((`j`.`id_kategori` = `k`.`id_kategori`))) ;

-- --------------------------------------------------------

--
-- Structure for view `v_transaksi_lengkap`
--
DROP TABLE IF EXISTS `v_transaksi_lengkap`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_transaksi_lengkap`  AS SELECT `t`.`id_transaksi` AS `id_transaksi`, `t`.`tanggal` AS `tanggal`, `t`.`status` AS `status`, `u`.`id_user` AS `id_user`, `u`.`nama_user` AS `nama_user`, `u`.`no_hp` AS `no_hp`, `p`.`nama_petugas` AS `nama_petugas`, `a`.`nama_admin` AS `nama_admin` FROM (((`transaksi_limbah` `t` left join `usr` `u` on((`t`.`id_user` = `u`.`id_user`))) left join `petugas` `p` on((`t`.`id_petugas` = `p`.`id_petugas`))) left join `admin` `a` on((`t`.`id_admin` = `a`.`id_admin`))) ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_transaksi`
--
ALTER TABLE `detail_transaksi`
  ADD CONSTRAINT `detail_transaksi_ibfk_1` FOREIGN KEY (`id_transaksi`) REFERENCES `transaksi_limbah` (`id_transaksi`),
  ADD CONSTRAINT `detail_transaksi_ibfk_2` FOREIGN KEY (`id_jenis`) REFERENCES `jenis_limbah` (`id_jenis`);

--
-- Constraints for table `jenis_limbah`
--
ALTER TABLE `jenis_limbah`
  ADD CONSTRAINT `jenis_limbah_ibfk_1` FOREIGN KEY (`id_kategori`) REFERENCES `kategori_limbah` (`id_kategori`);

--
-- Constraints for table `klaim_reward`
--
ALTER TABLE `klaim_reward`
  ADD CONSTRAINT `klaim_reward_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `usr` (`id_user`),
  ADD CONSTRAINT `klaim_reward_ibfk_2` FOREIGN KEY (`id_reward`) REFERENCES `reward` (`id_reward`);

--
-- Constraints for table `rule_konversi`
--
ALTER TABLE `rule_konversi`
  ADD CONSTRAINT `rule_konversi_ibfk_1` FOREIGN KEY (`id_jenis`) REFERENCES `jenis_limbah` (`id_jenis`);

--
-- Constraints for table `stok_limbah`
--
ALTER TABLE `stok_limbah`
  ADD CONSTRAINT `stok_limbah_ibfk_1` FOREIGN KEY (`id_jenis`) REFERENCES `jenis_limbah` (`id_jenis`);

--
-- Constraints for table `transaksi_limbah`
--
ALTER TABLE `transaksi_limbah`
  ADD CONSTRAINT `transaksi_limbah_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `usr` (`id_user`),
  ADD CONSTRAINT `transaksi_limbah_ibfk_2` FOREIGN KEY (`id_petugas`) REFERENCES `petugas` (`id_petugas`),
  ADD CONSTRAINT `transaksi_limbah_ibfk_3` FOREIGN KEY (`id_admin`) REFERENCES `admin` (`id_admin`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
