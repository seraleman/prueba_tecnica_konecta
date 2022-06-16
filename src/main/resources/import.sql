INSERT INTO categories(name) values('Refrigerado');
INSERT INTO categories(name) values('Pastelería');
INSERT INTO categories(name) values('Refresco');
INSERT INTO categories(name) values('Frito');
INSERT INTO categories(name) values('Mecato');

INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Yogurt', 'Alpina', 2100, 150, 1, 7,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Pastel', 'Pan de trigo', 100, 150, 2, 15,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Papas', 'Margarita', 2300, 110, 4, 6,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Chocolatina', 'Jet', 800, 15, 5, 10,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Burbuja', 'Jet', 900, 37, 5, 13,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Gaseosa', 'Cocacola', 3500, 250, 3, 10,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Helado', 'Yomyom', 4500, 250, 1, 4,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Chiclets', 'Adams', 200, 5, 5, 20,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Doritos', 'Pepsico', 2300, 100, 5, 15,'2022-06-15');

INSERT INTO sales(created, product_id, quantity) values ('2022-06-16', 1, 3);
INSERT INTO sales(created, product_id, quantity) values ('2022-06-16', 2, 5);
INSERT INTO sales(created, product_id, quantity) values ('2022-06-16', 3, 2);
INSERT INTO sales(created, product_id, quantity) values ('2022-06-16', 4, 1);