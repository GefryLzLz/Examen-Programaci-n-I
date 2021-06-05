-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 05-06-2021 a las 23:12:53
-- Versión del servidor: 10.4.18-MariaDB
-- Versión de PHP: 8.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `examen`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `IdCliente` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Direccion` varchar(100) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`IdCliente`, `Nombre`, `Direccion`, `estado`) VALUES
(1, 'Juan', '15 Av. lote 34 A zona 11', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dventas`
--

CREATE TABLE `dventas` (
  `IdDVentas` int(11) NOT NULL,
  `IdVentas` int(11) NOT NULL,
  `ID_Prod` int(11) NOT NULL,
  `Cantidad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `ID_FAC` int(150) NOT NULL,
  `IdClientes` int(100) NOT NULL,
  `IdVendedor` int(100) NOT NULL,
  `NIT_PROVEE` int(110) NOT NULL,
  `ID_Prod` int(100) NOT NULL,
  `Cantidad` int(100) NOT NULL,
  `Precio_Uni` int(100) NOT NULL,
  `Fecha` varchar(100) NOT NULL,
  `Total` int(100) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`ID_FAC`, `IdClientes`, `IdVendedor`, `NIT_PROVEE`, `ID_Prod`, `Cantidad`, `Precio_Uni`, `Fecha`, `Total`, `estado`) VALUES
(3, 1, 2, 1222, 1, 3, 300, '2021-06-01', 900, 1),
(4, 1, 2, 2111, 2, 3, 200, '2021-06-01', 1200, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `ID_Prod` int(11) NOT NULL,
  `Nom_Prod` varchar(100) NOT NULL,
  `Des_Prod` varchar(100) NOT NULL,
  `Fech_Adq` date NOT NULL,
  `Fech_Ven` date NOT NULL,
  `Pre_Ven` int(150) NOT NULL,
  `Pre_Com` varchar(150) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`ID_Prod`, `Nom_Prod`, `Des_Prod`, `Fech_Adq`, `Fech_Ven`, `Pre_Ven`, `Pre_Com`, `estado`) VALUES
(1, 'Zapatos', 'Deportivos', '2021-06-01', '2024-06-06', 300, 'Q150', 1),
(2, 'Camisa', 'Camisa de la marca Nike', '2021-06-01', '2028-06-01', 200, 'Q75', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE `proveedores` (
  `IdProveedores` int(11) NOT NULL,
  `Nit` int(100) NOT NULL,
  `RazonSocial` varchar(150) NOT NULL,
  `Nombre` varchar(150) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO `proveedores` (`IdProveedores`, `Nit`, `RazonSocial`, `Nombre`, `estado`) VALUES
(1, 1222, 'ADIDAS INDUSTRIAL', 'ADIDAS', 1),
(2, 2111, 'Nike S.A.', 'Nike', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedor`
--

CREATE TABLE `vendedor` (
  `IdVendedor` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Telefono` varchar(100) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `vendedor`
--

INSERT INTO `vendedor` (`IdVendedor`, `Nombre`, `Telefono`, `estado`) VALUES
(1, 'Mario Lopez', '55663322', 1),
(2, 'Melisaa Paz', '44882266', 1),
(3, 'Juan Perez', '55667788', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `IdVentas` int(11) NOT NULL,
  `NumeroSerie` varchar(12) NOT NULL,
  `IdClientes` int(11) NOT NULL,
  `IdVendedor` int(11) NOT NULL,
  `IdProveedores` int(11) NOT NULL,
  `FechaVenta` date NOT NULL,
  `Monto` int(100) NOT NULL,
  `estado` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`IdCliente`);

--
-- Indices de la tabla `dventas`
--
ALTER TABLE `dventas`
  ADD PRIMARY KEY (`IdDVentas`),
  ADD KEY `IdVentas` (`IdVentas`),
  ADD KEY `ID_Prod` (`ID_Prod`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`ID_FAC`),
  ADD KEY `IdClientes` (`IdClientes`),
  ADD KEY `IdVendedor` (`IdVendedor`),
  ADD KEY `ID_Prod` (`ID_Prod`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`ID_Prod`);

--
-- Indices de la tabla `proveedores`
--
ALTER TABLE `proveedores`
  ADD PRIMARY KEY (`IdProveedores`);

--
-- Indices de la tabla `vendedor`
--
ALTER TABLE `vendedor`
  ADD PRIMARY KEY (`IdVendedor`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`IdVentas`),
  ADD KEY `IdClientes` (`IdClientes`),
  ADD KEY `IdEmpleado` (`IdVendedor`),
  ADD KEY `IdProveedores` (`IdProveedores`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `ID_FAC` int(150) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `dventas`
--
ALTER TABLE `dventas`
  ADD CONSTRAINT `dventas_ibfk_1` FOREIGN KEY (`IdVentas`) REFERENCES `ventas` (`IdVentas`),
  ADD CONSTRAINT `dventas_ibfk_2` FOREIGN KEY (`ID_Prod`) REFERENCES `producto` (`ID_Prod`);

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`IdClientes`) REFERENCES `cliente` (`IdCliente`),
  ADD CONSTRAINT `factura_ibfk_2` FOREIGN KEY (`IdVendedor`) REFERENCES `vendedor` (`IdVendedor`),
  ADD CONSTRAINT `factura_ibfk_3` FOREIGN KEY (`ID_Prod`) REFERENCES `producto` (`ID_Prod`);

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`IdClientes`) REFERENCES `cliente` (`IdCliente`),
  ADD CONSTRAINT `ventas_ibfk_2` FOREIGN KEY (`IdVendedor`) REFERENCES `vendedor` (`IdVendedor`),
  ADD CONSTRAINT `ventas_ibfk_3` FOREIGN KEY (`IdProveedores`) REFERENCES `proveedores` (`IdProveedores`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
