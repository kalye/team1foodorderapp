CREATE TABLE `team1orderdb`.`Category` (
`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(100) NOT NULL,
`imageUrl` VARCHAR(200) NULL,
PRIMARY KEY (`id`));

CREATE TABLE `team1orderdb`.`menu_item` (
`id` INT NOT NULL AUTO_INCREMENT,
`category_id` INT NOT NULL,
`name` VARCHAR(100) NOT NULL,
`description` VARCHAR(200) NOT NULL,
`hasToppings` TINYINT(1) NULL,
`hasSide` TINYINT(1) NULL,
`customizable` TINYINT(1) NULL,
`price` DECIMAL(3,2) NOT NULL,
`imageUrl` VARCHAR(200) NULL,
PRIMARY KEY (`id`),
INDEX `category_id_idx` (`category_id` ASC),
CONSTRAINT `category_id`
FOREIGN KEY (`category_id`)
REFERENCES `team1orderdb`.`Category` (`id`)
ON DELETE NO ACTION
ON UPDATE NO ACTION);

CREATE TABLE `team1orderdb`.`toppings` (
`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(100) NOT NULL,
`imageUrl` VARCHAR(200) NULL,
`price` DECIMAL(3,2) NOT NULL,
PRIMARY KEY (`id`));

CREATE TABLE `team1orderdb`.`sides` (
`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(100) NOT NULL,
`imageUrl` VARCHAR(200) NULL,
`price` DECIMAL(3,2) NOT NULL,
PRIMARY KEY (`id`));

CREATE TABLE `team1orderdb`.`customizable_item` (
`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(100) NOT NULL,
`imageUrl` VARCHAR(200) NULL,
`price` DECIMAL(3,2) NOT NULL,
PRIMARY KEY (`id`));

CREATE TABLE `team1orderdb`.`menu_customizable_item` (
`menu_id` INT NOT NULL,
`c_item_id` INT NOT NULL,
PRIMARY KEY (`menu_id`, `c_item_id`),
CONSTRAINT `menu_id`
FOREIGN KEY (`menu_id`)
REFERENCES `team1orderdb`.`menu_item` (`id`)
ON DELETE NO ACTION
ON UPDATE NO ACTION,
CONSTRAINT `c_item_id`
FOREIGN KEY (`c_item_id`)
REFERENCES `team1orderdb`.`customizable_item` (`id`)
ON DELETE NO ACTION
ON UPDATE NO ACTION);

CREATE TABLE `team1orderdb`.`sides_menu` (
`id` INT NOT NULL AUTO_INCREMENT,
`side_id` INT NOT NULL,
`menu_id` INT NOT NULL,
PRIMARY KEY (`id`),
CONSTRAINT `c_menu_id`
FOREIGN KEY (`id`)
REFERENCES `team1orderdb`.`menu_item` (`id`)
ON DELETE NO ACTION
ON UPDATE NO ACTION,
CONSTRAINT `c_side_id`
FOREIGN KEY (`id`)
REFERENCES `team1orderdb`.`sides` (`id`)
ON DELETE NO ACTION
ON UPDATE NO ACTION);


CREATE TABLE `team1orderdb`.`menu_toppings` (
`menu_id` INT NOT NULL,
`topping_id` INT NOT NULL,
PRIMARY KEY (`menu_id`, `topping_id`),
INDEX `topping_id_idx` (`topping_id` ASC),
CONSTRAINT `c_menu_id_1`
FOREIGN KEY (`menu_id`)
REFERENCES `team1orderdb`.`menu_item` (`id`)
ON DELETE NO ACTION
ON UPDATE NO ACTION,
CONSTRAINT `c_topping_id_1`
FOREIGN KEY (`topping_id`)
REFERENCES `team1orderdb`.`toppings` (`id`)
ON DELETE NO ACTION
ON UPDATE NO ACTION);