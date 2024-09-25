CREATE TABLE IF NOT EXISTS trainers
(
   id_trainer INT PRIMARY KEY,
   name VARCHAR NOT NULL,
   img VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
   username VARCHAR UNIQUE NOT NULL,
   email VARCHAR UNIQUE NOT NULL,
   password VARCHAR NOT NULL,
   id_trainer INT,
   CONSTRAINT user_trainer_fk FOREIGN KEY(id_trainer) REFERENCES trainers (id_trainer)
);

INSERT INTO trainers
(id_trainer, name, img)
VALUES
('1', 'N', 'https://archives.bulbagarden.net/media/upload/d/d1/VSN_Masters.png'),
('2', 'Red', 'https://archives.bulbagarden.net/media/upload/2/2c/VSRed_Masters.png'),
('3', 'Blue', 'https://archives.bulbagarden.net/media/upload/5/55/VSBlue_Masters.png'),
('4', 'Giovanni', 'https://archives.bulbagarden.net/media/upload/7/71/VSGiovanni_Masters.png'),
('5', 'Leaf', 'https://archives.bulbagarden.net/media/upload/6/65/VSLeaf_Masters.png'),
('6', 'Lance', 'https://archives.bulbagarden.net/media/upload/e/ed/VSLance_Masters.png'),
('7', 'Gold', 'https://archives.bulbagarden.net/media/upload/1/1c/VSEthan_Masters.png'),
('8', 'Kris', 'https://archives.bulbagarden.net/media/upload/f/fb/VSKris_Masters.png'),
('9', 'Silver', 'https://archives.bulbagarden.net/media/upload/b/b5/VSSilver_Masters.png'),
('0', 'MissingNo', 'https://upload.wikimedia.org/wikipedia/commons/3/3b/MissingNo.svg');


