CREATE TABLE `pessoa` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `sobrenome` varchar(255) NOT NULL,
  `endereco` varchar(255) NOT NULL,
  `sexo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);