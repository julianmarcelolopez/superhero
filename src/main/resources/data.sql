DROP TABLE IF EXISTS SUPERHERO;

CREATE TABLE SUPERHERO (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) UNIQUE NOT NULL,
  active BOOLEAN 
);

INSERT INTO SUPERHERO (name, active) VALUES
  ('Spiderman', true),
  ('Superman', true),
  ('Batman',  true);