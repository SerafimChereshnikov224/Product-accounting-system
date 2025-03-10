INSERT INTO Supplier (name) VALUES
('Supplier A'),
('Supplier B'),
('Supplier C');

INSERT INTO Product (name, type) VALUES
('Golden Apple', 'APPLE'),
('Red Apple', 'APPLE');

INSERT INTO Product (name, type) VALUES
('Dushes Pear', 'PEAR'),
('Green Pear', 'PEAR');


INSERT INTO price_list (product_id, start_date, end_date, price) VALUES
(1, '2024-01-01', '2024-03-31', 2.50),
(1, '2024-04-01', '2024-06-30', 2.70),
(2, '2024-01-01', '2024-03-31', 3.00),
(2, '2024-04-01', '2024-06-30', 3.20),
(3, '2024-01-01', '2024-03-31', 1.10),
(3, '2024-04-01', '2024-06-30', 1.15),
(4, '2024-01-01', '2024-03-31', 1.25),
(4, '2024-04-01', '2024-06-30', 1.28);

INSERT INTO delivery (date, supplier_id) VALUES
('2024-02-15', 1),
('2024-01-24', 2),
('2024-03-15', 3),
('2024-03-28', 1),
('2024-04-12', 2),
('2024-05-09', 3),
('2024-06-01', 1);

INSERT INTO delivery_unit (weight, delivery_id, product_id) VALUES
(100, 1, 3),
(85, 2, 4),
(110, 3, 1),
(76, 4, 1),
(99, 5, 2),
(104, 6, 3),
(130, 7, 4),
(65, 1, 2),
(45, 2, 1),
(200, 3, 1),
(140, 4, 2),
(120, 5, 3),
(110, 6, 4),
(112, 7, 2);




