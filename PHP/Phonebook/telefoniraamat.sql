-- phpMyAdmin SQL Dump
-- version 3.3.5
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1
-- Время создания: Дек 04 2010 г., 08:08
-- Версия сервера: 5.1.49
-- Версия PHP: 5.3.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `telefoniraamat`
--

-- --------------------------------------------------------

--
-- Структура таблицы `entries`
--

CREATE TABLE IF NOT EXISTS `entries` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(25) NOT NULL,
  `familyname` varchar(20) NOT NULL,
  `mobile_phone` varchar(15) NOT NULL,
  `home_phone` varchar(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `entries`
--

INSERT INTO `entries` (`id`, `firstname`, `familyname`, `mobile_phone`, `home_phone`) VALUES
(1, 'Andy', 'Peterson', '53241231', '6783321'),
(2, 'Ben', 'Parker', '53190093', '744910'),
(3, 'James', 'Bread', '5019013', '741231'),
(4, 'Jane', 'Pertoff', '56610921', ''),
(5, 'John', 'Doe', '5245387', ''),
(6, 'Lion', 'Bates', '5712818', ''),
(7, 'Lora', 'Palmer', '56199910', '6713910'),
(8, 'Mary', 'Jameson', '', '6512318'),
(9, 'Oscar', 'Newberry', '', '6651629'),
(10, 'Peter', 'Bens', '54412371', '6512819');
