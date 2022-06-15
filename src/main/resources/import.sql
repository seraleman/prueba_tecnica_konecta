INSERT INTO categories(name) values('Refrigerados');
INSERT INTO categories(name) values('Pasteles');
INSERT INTO categories(name) values('Refresco');
INSERT INTO categories(name) values('Fritos');
INSERT INTO categories(name) values('Mecato');

INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Yogurt', 'Alpina', 2100, 150, 4, 8,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Pastel', 'Pan de trigo', 100, 150, 14, 2,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Papas', 'Margarita', 2300, 110, 34, 6,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Chocolatina', 'Jet', 800, 15, 44, 1,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Burbuja', 'Jet', 900, 37, 44, 8,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Gaseosa', 'Cocacola', 3500, 250, 24, 1,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Helado', 'Yomyom', 4500, 250, 4, 8,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Chiclets', 'Adams', 200, 5, 44, 2,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Doritos', 'Pepsico', 2300, 100, 44, 8,'2022-06-15');

INSERT INTO sales(created, product_id, quantity) values ('2022-06-16', 4, 1);
INSERT INTO sales(created, product_id, quantity) values ('2022-06-16', 24, 1);
INSERT INTO sales(created, product_id, quantity) values ('2022-06-16', 34, 1);
INSERT INTO sales(created, product_id, quantity) values ('2022-06-16', 54, 1);