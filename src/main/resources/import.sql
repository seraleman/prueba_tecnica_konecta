INSERT INTO categories(name) values('Lácteo');
INSERT INTO categories(name) values('Pastelería');
INSERT INTO categories(name) values('Refresco');
INSERT INTO categories(name) values('Fritos');
INSERT INTO categories(name) values('Saludable');

INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Yogurt', 'Alpina', 2100, 150, 4, 8,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Yogurt', 'Alpina', 2100, 150, 4, 2,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Yogurt', 'Alpina', 2100, 150, 14, 6,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Yogurt', 'Alpina', 2100, 150, 14, 1,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Yogurt', 'Alpina', 2100, 150, 24, 8,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Yogurt', 'Alpina', 2100, 150, 24, 1,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Yogurt', 'Alpina', 2100, 150, 34, 8,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Yogurt', 'Alpina', 2100, 150, 34, 2,'2022-06-15');
INSERT INTO products(name, reference, price, weight, category_id, stock, created) values ('Yogurt', 'Alpina', 2100, 150, 44, 8,'2022-06-15');

INSERT INTO sales(created, product_id) values ('2022-06-16', 4);
INSERT INTO sales(created, product_id) values ('2022-06-16', 24);
INSERT INTO sales(created, product_id) values ('2022-06-16', 34);
INSERT INTO sales(created, product_id) values ('2022-06-16', 54);