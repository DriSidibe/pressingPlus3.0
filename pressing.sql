-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 22, 2022 at 09:49 PM
-- Server version: 5.7.24
-- PHP Version: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pressing`
--

-- --------------------------------------------------------

--
-- Table structure for table `caisse`
--

CREATE TABLE `caisse` (
  `montant` varchar(100) DEFAULT NULL,
  `id_caisse` int(11) NOT NULL,
  `id_proprietaire` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `caisse`
--

INSERT INTO `caisse` (`montant`, `id_caisse`, `id_proprietaire`) VALUES
('6550', 10, 27),
('0', 11, 28);

-- --------------------------------------------------------

--
-- Table structure for table `depot`
--

CREATE TABLE `depot` (
  `nom_client` varchar(100) DEFAULT NULL,
  `prenom_client` varchar(100) DEFAULT NULL,
  `tel_client` varchar(255) DEFAULT NULL,
  `date_depot` varchar(255) DEFAULT NULL,
  `date_retrait` varchar(255) DEFAULT NULL,
  `montant` varchar(255) DEFAULT NULL,
  `divers` varchar(255) DEFAULT NULL,
  `regler` varchar(255) DEFAULT NULL,
  `id_depot` int(11) NOT NULL,
  `type_lavage` varchar(255) DEFAULT NULL,
  `id_proprietaire` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `depot`
--

INSERT INTO `depot` (`nom_client`, `prenom_client`, `tel_client`, `date_depot`, `date_retrait`, `montant`, `divers`, `regler`, `id_depot`, `type_lavage`, `id_proprietaire`) VALUES
('gramboute', 'bassory', '0675664533', '2022-6-22', '2022-6-22', '4400', '0', 'true', 10, 'lavage complet à sec', 27),
('tuo', 'pelegnan', '0789665453', '2022-6-22', '2022-6-22', '6550', '0', 'true', 11, 'lavage complet à sec', 27);

-- --------------------------------------------------------

--
-- Table structure for table `historie`
--

CREATE TABLE `historie` (
  `id_proprietaire` int(255) NOT NULL,
  `montant` varchar(255) NOT NULL,
  `date_decharge` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `historie`
--

INSERT INTO `historie` (`id_proprietaire`, `montant`, `date_decharge`) VALUES
(27, '4400', '2022-6-22');

-- --------------------------------------------------------

--
-- Table structure for table `liste_vetement`
--

CREATE TABLE `liste_vetement` (
  `nom_vetement` varchar(100) DEFAULT NULL,
  `nbr_vetement` varchar(100) DEFAULT NULL,
  `id_depot` int(11) NOT NULL,
  `id_proprietaire` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `liste_vetement`
--

INSERT INTO `liste_vetement` (`nom_vetement`, `nbr_vetement`, `id_depot`, `id_proprietaire`) VALUES
('Pantalon jeans', '0', 10, 27),
('Pantalon tissu', '1', 10, 27),
('Autre pantalon', '0', 10, 27),
('Chemise', '3', 10, 27),
('Tee-shirt', '0', 10, 27),
('Chaussure', '3', 10, 27),
('Veste', '2', 10, 27),
('Pantalon jeans', '4', 11, 27),
('Pantalon tissu', '2', 11, 27),
('Autre pantalon', '3', 11, 27),
('Chemise', '1', 11, 27),
('Tee-shirt', '0', 11, 27),
('Chaussure', '2', 11, 27),
('Veste', '2', 11, 27);

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur`
--

CREATE TABLE `utilisateur` (
  `id_utilisateur` int(255) NOT NULL,
  `nom_utilisateur` varchar(255) NOT NULL,
  `prenom_utilisateur` varchar(255) NOT NULL,
  `identifiant_utilisateur` varchar(255) NOT NULL,
  `mdp_utilisateur` varchar(255) NOT NULL,
  `mail_utilisateur` varchar(255) NOT NULL,
  `tel_utilisateur` varchar(255) NOT NULL,
  `nom_pressing` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `utilisateur`
--

INSERT INTO `utilisateur` (`id_utilisateur`, `nom_utilisateur`, `prenom_utilisateur`, `identifiant_utilisateur`, `mdp_utilisateur`, `mail_utilisateur`, `tel_utilisateur`, `nom_pressing`) VALUES
(27, 'sidibe', 'drissa', 'darker', 'maman123', 'dsidibe653@gmail.com', '0556884867', 'pressingPlus'),
(28, 'gramboute', 'bassory', 'bassory', 'bassory123', 'gb653@gmail.com', '0989776567', 'pressing');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `caisse`
--
ALTER TABLE `caisse`
  ADD PRIMARY KEY (`id_caisse`);

--
-- Indexes for table `depot`
--
ALTER TABLE `depot`
  ADD PRIMARY KEY (`id_depot`);

--
-- Indexes for table `utilisateur`
--
ALTER TABLE `utilisateur`
  ADD PRIMARY KEY (`id_utilisateur`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `caisse`
--
ALTER TABLE `caisse`
  MODIFY `id_caisse` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `depot`
--
ALTER TABLE `depot`
  MODIFY `id_depot` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `utilisateur`
--
ALTER TABLE `utilisateur`
  MODIFY `id_utilisateur` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
