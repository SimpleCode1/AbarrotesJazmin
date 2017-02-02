-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-08-2016 a las 04:34:17
-- Versión del servidor: 5.7.13-log
-- Versión de PHP: 7.0.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tienda`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulo`
--

CREATE TABLE `articulo` (
  `id_articulo` int(5) NOT NULL,
  `codigo` varchar(20) NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `stock` int(8) NOT NULL,
  `costo` float NOT NULL,
  `venta` float NOT NULL,
  `utilidad` float NOT NULL,
  `categoria` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `articulo`
--

INSERT INTO `articulo` (`id_articulo`, `codigo`, `nombre`, `stock`, `costo`, `venta`, `utilidad`, `categoria`) VALUES
(1, '4353', 'Coca-Cola', 3, 8, 10, 2, 'Refrescos'),
(2, 'Gudu', 'Paleta', 11, 4, 5, 1, 'Paletas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `boleta`
--

CREATE TABLE `boleta` (
  `num_boleta` int(10) NOT NULL,
  `pre_total` float NOT NULL,
  `fecha` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `boleta`
--

INSERT INTO `boleta` (`num_boleta`, `pre_total`, `fecha`) VALUES
(3, 50, '07/08/2016'),
(4, 50, '07/08/2016'),
(5, 70, '07/08/2016'),
(6, 20, '08/08/2016');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id` int(2) UNSIGNED NOT NULL,
  `categoria` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `categoria`) VALUES
(1, 'Paletas'),
(6, 'Papas');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalleboleta`
--

CREATE TABLE `detalleboleta` (
  `num_boleta` int(10) NOT NULL,
  `id_articulo` int(5) NOT NULL,
  `categoria` varchar(30) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `costo` float NOT NULL,
  `venta` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `detalleboleta`
--

INSERT INTO `detalleboleta` (`num_boleta`, `id_articulo`, `categoria`, `cantidad`, `costo`, `venta`) VALUES
(3, 1, 'Refrescos', 5, 10, 50),
(4, 1, 'Refrescos', 5, 10, 50),
(5, 1, 'Refrescos', 5, 10, 50),
(5, 2, 'Paletas', 4, 5, 20),
(6, 1, 'Refrescos', 2, 10, 20);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int(2) UNSIGNED NOT NULL,
  `nombre` varchar(30) NOT NULL,
  `paterno` varchar(30) NOT NULL,
  `materno` varchar(30) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `usuario` varchar(30) NOT NULL,
  `clave` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `nombre`, `paterno`, `materno`, `telefono`, `usuario`, `clave`) VALUES
(1, 'administrador', 'administrador', 'administrador', '7731530825', 'administrador', '12345'),
(4, 'Cesar', 'Lemus', 'Guerrero', '7731530825', 'lemuxx', '12345');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulo`
--
ALTER TABLE `articulo`
  ADD PRIMARY KEY (`id_articulo`,`codigo`,`categoria`);

--
-- Indices de la tabla `boleta`
--
ALTER TABLE `boleta`
  ADD PRIMARY KEY (`num_boleta`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detalleboleta`
--
ALTER TABLE `detalleboleta`
  ADD PRIMARY KEY (`num_boleta`,`id_articulo`),
  ADD KEY `id_articulo` (`id_articulo`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `articulo`
--
ALTER TABLE `articulo`
  MODIFY `id_articulo` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int(2) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int(2) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalleboleta`
--
ALTER TABLE `detalleboleta`
  ADD CONSTRAINT `detalleboleta_ibfk_1` FOREIGN KEY (`num_boleta`) REFERENCES `boleta` (`num_boleta`) ON UPDATE CASCADE,
  ADD CONSTRAINT `detalleboleta_ibfk_2` FOREIGN KEY (`id_articulo`) REFERENCES `articulo` (`id_articulo`) ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
