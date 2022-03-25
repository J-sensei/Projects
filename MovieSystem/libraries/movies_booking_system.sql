-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 13, 2020 at 05:20 PM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 7.2.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `movies_booking_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `genre`
--

CREATE TABLE `genre` (
  `id` int(11) NOT NULL,
  `genre` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `genre`
--

INSERT INTO `genre` (`id`, `genre`) VALUES
(16, 'Superhero'),
(17, 'Adventure'),
(18, 'Fantasy'),
(19, 'Action'),
(20, 'Sciene Fiction'),
(21, 'Anime'),
(22, 'War'),
(23, 'History'),
(24, 'Romance'),
(25, 'Drama'),
(26, 'Cartoon'),
(27, 'Horror');

-- --------------------------------------------------------

--
-- Table structure for table `movies`
--

CREATE TABLE `movies` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` decimal(11,2) NOT NULL,
  `link` varchar(10000) NOT NULL,
  `length` int(3) NOT NULL,
  `ranking` int(11) NOT NULL,
  `comingsoon` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movies`
--

INSERT INTO `movies` (`id`, `name`, `price`, `link`, `length`, `ranking`, `comingsoon`) VALUES
(10, 'Avengers:End Game', '20.50', 'https://upload.wikimedia.org/wikipedia/en/0/0d/Avengers_Endgame_poster.jpg', 182, 0, 0),
(11, 'Aquaman', '18.70', 'https://i.redd.it/vbl0boh22ca11.jpg', 142, 0, 0),
(12, 'Spider Man:Far From Home', '25.60', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPzzfxN6Ugvq902Ut7A-QkCDOqLzzbiYQ2bPk2pn5D9fBZBrT_', 129, 0, 0),
(13, 'Titanic', '15.70', 'https://upload.wikimedia.org/wikipedia/en/1/19/Titanic_%28Official_Film_Poster%29.png', 269, 0, 0),
(15, 'Captain Marvel', '23.60', 'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQ1bDkDLq-_bteASakhnC1XYwlkErFuqcof7KMhFpRwVhCTh1Vo', 125, 0, 0),
(16, 'The Hobbit', '20.30', 'https://m.media-amazon.com/images/M/MV5BMTcwNTE4MTUxMl5BMl5BanBnXkFtZTcwMDIyODM4OA@@._V1_UX182_CR0,0,182,268_AL_.jpg', 182, 0, 0),
(17, 'Avatar', '18.90', 'https://upload.wikimedia.org/wikipedia/en/b/b0/Avatar-Teaser-Poster.jpg', 162, 0, 0),
(18, 'The Maxtrix', '18.90', 'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcS4jfQQt_0vCA4XSwGiWkffC32Tv2oajdWhaYHHVYylYGJ3v17Q', 150, 0, 0),
(19, 'Batman v Superman: Dawn of Justice ', '18.90', 'https://1.bp.blogspot.com/-6URQNascHZ4/Vvh0ncD6B2I/AAAAAAAABqw/Zyf8P2PYe-4emcdy45aA3Q8MtEWDZkbZA/s320/batman-vs-superman-poster.jpg', 182, 0, 0),
(20, 'Joker', '28.90', 'https://www.vintagemovieposters.co.uk/wp-content/uploads/2020/01/IMG_2893-692x1024.jpeg', 122, 0, 0),
(23, '1917', '20.50', 'http://www.impawards.com/2019/posters/nineteen_seventeen_xlg.jpg', 186, 0, 0),
(24, 'Whethering with You', '18.70', 'https://upload.wikimedia.org/wikipedia/en/6/66/Weathering_with_You_Poster.jpg', 111, 0, 0),
(25, 'Hello World', '16.50', 'https://upload.wikimedia.org/wikipedia/en/a/a8/HELLOWORLD.jpg', 100, 0, 0),
(26, 'PLayer Number One', '24.70', 'https://m.media-amazon.com/images/M/MV5BY2JiYTNmZTctYTQ1OC00YjU4LWEwMjYtZjkwY2Y5MDI0OTU3XkEyXkFqcGdeQXVyNTI4MzE4MDU@._V1_.jpg', 139, 0, 0),
(27, 'Shazam', '23.80', 'https://cps-static.rovicorp.com/2/Open/Warner_Brothers_360/Program/25183817/_derived_jpg_q90_500x500_m0/SHAZAM_2x3.jpg', 132, 0, 0),
(32, 'Frozen 2', '15.60', 'https://lumiere-a.akamaihd.net/v1/images/p_frozen2_18652_f5e1d9da.jpeg', 107, 0, 0),
(33, 'KonoSuba: God\'s Blessing on this Wonderful World! Legend of Crimson', '18.30', 'https://upload.wikimedia.org/wikipedia/en/6/66/KonoSuba_film_poster.jpg', 90, 0, 0),
(34, 'Sonic the Hedgehong', '26.70', 'https://m.media-amazon.com/images/M/MV5BMDk5Yzc4NzMtODUwOS00NTdhLTg2MjEtZTkzZjc0ZWE2MzAwXkEyXkFqcGdeQXVyMTA3MTA4Mzgw._V1_.jpg', 100, 0, 0),
(35, 'Annabelle Comes Home', '25.60', 'https://upload.wikimedia.org/wikipedia/en/5/50/AnnabelleComesHomePoster.jpg', 106, 0, 0),
(36, 'Demon Slayer: Kimetsu no Yaiba the Movie: Mugen Train', '0.00', 'https://upload.wikimedia.org/wikipedia/en/2/21/Kimetsu_no_Yaiba_Mugen_Ressha_Hen_Poster.jpg', 0, 0, 1),
(37, 'Mulan', '0.00', 'https://upload.wikimedia.org/wikipedia/en/1/17/Mulan_%282020_film%29_poster.jpg', 0, 0, 1),
(38, 'Greyhound', '0.00', 'https://pics.filmaffinity.com/Greyhound-142654194-large.jpg', 0, 0, 1),
(39, 'Wonder Women 1984', '0.00', 'https://upload.wikimedia.org/wikipedia/en/thumb/2/21/Wonder_Woman_1984_poster.jpg/220px-Wonder_Woman_1984_poster.jpg', 0, 0, 1),
(40, 'The King\'s Man', '0.00', 'https://upload.wikimedia.org/wikipedia/en/6/67/The_King%27s_Man.jpg', 0, 0, 1),
(41, 'Free Guy', '0.00', 'https://1847884116.rsc.cdn77.org/english/gallery/movies/freeguy/poster1.jpg', 0, 0, 1),
(42, 'The Conjuring 3', '0.00', 'https://m.media-amazon.com/images/M/MV5BNWEyODg1NzktMGZkNS00MjZlLTlmOTAtZTIzNzdhZmIwNzE5XkEyXkFqcGdeQXVyNjIzNzM4NzA@._V1_.jpg', 0, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `movie_genre`
--

CREATE TABLE `movie_genre` (
  `id` int(11) NOT NULL,
  `movie` int(11) NOT NULL,
  `genre` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `movie_genre`
--

INSERT INTO `movie_genre` (`id`, `movie`, `genre`) VALUES
(1, 10, 19),
(2, 10, 17),
(3, 10, 16),
(4, 11, 16),
(5, 11, 18),
(6, 25, 21),
(7, 25, 20),
(8, 24, 21),
(9, 24, 25),
(10, 24, 24),
(11, 23, 22),
(12, 23, 23),
(13, 17, 20),
(14, 17, 17),
(15, 17, 18),
(16, 19, 16),
(17, 19, 17),
(18, 19, 19),
(19, 15, 19),
(20, 15, 16),
(21, 26, 20),
(22, 26, 19),
(23, 26, 17),
(24, 27, 16),
(25, 27, 19),
(26, 20, 25),
(27, 16, 18),
(28, 13, 25),
(29, 13, 24),
(30, 35, 27),
(31, 33, 21),
(32, 33, 18),
(33, 34, 17),
(34, 34, 19),
(35, 34, 20),
(36, 32, 26),
(37, 36, 21),
(38, 37, 19),
(39, 41, 19),
(40, 38, 22),
(41, 42, 27),
(42, 38, 23),
(43, 39, 19),
(44, 39, 16);

-- --------------------------------------------------------

--
-- Table structure for table `payments`
--

CREATE TABLE `payments` (
  `id` int(11) NOT NULL,
  `movie` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  `seat` varchar(2) NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(60) NOT NULL,
  `password` varchar(255) NOT NULL,
  `wallet` decimal(11,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `movies`
--
ALTER TABLE `movies`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `movie_genre`
--
ALTER TABLE `movie_genre`
  ADD PRIMARY KEY (`id`),
  ADD KEY `movie` (`movie`),
  ADD KEY `genre` (`genre`);

--
-- Indexes for table `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `movie` (`movie`),
  ADD KEY `user` (`user`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `genre`
--
ALTER TABLE `genre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `movies`
--
ALTER TABLE `movies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `movie_genre`
--
ALTER TABLE `movie_genre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `payments`
--
ALTER TABLE `payments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `movie_genre`
--
ALTER TABLE `movie_genre`
  ADD CONSTRAINT `movie_genre_ibfk_1` FOREIGN KEY (`movie`) REFERENCES `movies` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `movie_genre_ibfk_2` FOREIGN KEY (`genre`) REFERENCES `genre` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `payments`
--
ALTER TABLE `payments`
  ADD CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`movie`) REFERENCES `movies` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `payments_ibfk_2` FOREIGN KEY (`user`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
