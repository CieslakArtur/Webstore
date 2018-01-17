use webstore;

CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `description` text COLLATE utf8_polish_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

CREATE TABLE `customer` (
  `customerId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `surname` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `address` varchar(200) COLLATE utf8_polish_ci DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_polish_ci DEFAULT NULL,
  PRIMARY KEY (`customerId`),
  UNIQUE KEY `customerId_UNIQUE` (`customerId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

CREATE TABLE `manufacturer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf32_polish_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf32 COLLATE=utf32_polish_ci;

CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` int(11) NOT NULL,
  `customerId` int(11) NOT NULL,
  `unitsInOrder` int(11) NOT NULL DEFAULT '1',
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `productId_idx` (`productId`),
  CONSTRAINT `customerId` FOREIGN KEY (`id`) REFERENCES `customer` (`customerId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `productId` FOREIGN KEY (`id`) REFERENCES `product` (`productId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

CREATE TABLE `product` (
  `productId` int(11) NOT NULL AUTO_INCREMENT,
  `categoryId` int(11) NOT NULL,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `unitPrice` decimal(7,2) NOT NULL,
  `description` text COLLATE utf8_polish_ci,
  `manufacturerId` int(11) NOT NULL,
  `unitsInStock` int(11) NOT NULL,
  `discontinued` enum('true','false') COLLATE utf8_polish_ci NOT NULL DEFAULT 'false',
  `condition` enum('new','used') COLLATE utf8_polish_ci NOT NULL DEFAULT 'new',
  PRIMARY KEY (`productId`),
  KEY `categoryId_idx` (`categoryId`),
  KEY `manufacturerId_idx` (`manufacturerId`),
  CONSTRAINT `categoryId` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `manufacturerId` FOREIGN KEY (`manufacturerId`) REFERENCES `manufacturer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

CREATE TABLE `images` ( 
`id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT, 
`caption` VARCHAR(45) NOT NULL, 
`img` LONGBLOB NOT NULL, 
PRIMARY KEY(`idpic`) 
)ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
