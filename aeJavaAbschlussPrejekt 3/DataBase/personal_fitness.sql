-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 02. Jul 2021 um 09:05
-- Server-Version: 10.4.19-MariaDB
-- PHP-Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `personal_fitness`
--
CREATE DATABASE IF NOT EXISTS `personal_fitness` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `personal_fitness`;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `fitnessplan`
--

CREATE TABLE `fitnessplan` (
  `_id` int(11) NOT NULL,
  `name_id` int(11) NOT NULL,
  `datum` date NOT NULL,
  `activity` double NOT NULL,
  `gewicht` double NOT NULL,
  `kcal` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `fitnessplan`
--

INSERT INTO `fitnessplan` (`_id`, `name_id`, `datum`, `activity`, `gewicht`, `kcal`) VALUES
(4, 1, '2021-06-22', 7, 99, 2000),
(6, 1, '2021-06-22', 12.85655, 90, 500),
(10, 1, '2021-06-24', 20, 120, 3000),
(13, 1, '2021-06-25', 4, 150, 2000),
(14, 1, '2021-06-26', 5, 120, 2000),
(15, 7, '2021-06-26', 50, 89, 2000),
(16, 7, '2021-06-26', 5, 90, 500),
(17, 1, '2021-07-01', 2, 123, 2500),
(18, 1, '2021-07-01', 13, 130.5, 1300),
(19, 1, '2021-07-01', 11.11, 105.8, 55),
(21, 1, '2021-07-01', 7.8, 123, 125),
(23, 1, '2021-07-01', 12.5, 120, 1235.5),
(24, 1, '2021-07-01', 55.55, 55.9, 1254.9),
(25, 1, '2021-07-01', 13.5, 123, 1500.5),
(26, 1, '2021-07-01', 12.5, 123, 1254.8),
(28, 1, '2021-07-01', 5.5, 124, 3333);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `person`
--

CREATE TABLE `person` (
  `_id` int(11) NOT NULL,
  `name` text NOT NULL,
  `geschlecht` text NOT NULL,
  `jahrealt` int(11) NOT NULL,
  `groesse` double NOT NULL,
  `gewicht` double NOT NULL,
  `bmi` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Daten für Tabelle `person`
--

INSERT INTO `person` (`_id`, `name`, `geschlecht`, `jahrealt`, `groesse`, `gewicht`, `bmi`) VALUES
(1, 'Hugo', 'm', 50, 180, 80, 0),
(4, 'Maya', 'w', 33, 167, 133, 47.68905303166123),
(5, 'Astrid', 'w', 55, 180, 120, 37.03703703703704),
(6, 'Dirk', 'm', 55, 180, 150, 46.29629629629629),
(7, 'Hansi', 'm', 77, 180, 80, 24.691358024691358),
(8, 'Roland', 'm', 2, 60, 25, 69.44444444444444),
(9, 'Helga', 'w', 33, 123, 123, 81.30081300813009),
(10, 'Clara', 'w', 33, 173, 55, 18.376825152861773);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `fitnessplan`
--
ALTER TABLE `fitnessplan`
  ADD PRIMARY KEY (`_id`) USING BTREE,
  ADD KEY `fitnessplan_ibfk_1` (`name_id`);

--
-- Indizes für die Tabelle `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`_id`),
  ADD UNIQUE KEY `name` (`name`) USING HASH;

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `fitnessplan`
--
ALTER TABLE `fitnessplan`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT für Tabelle `person`
--
ALTER TABLE `person`
  MODIFY `_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `fitnessplan`
--
ALTER TABLE `fitnessplan`
  ADD CONSTRAINT `fitnessplan_ibfk_1` FOREIGN KEY (`name_id`) REFERENCES `person` (`_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
