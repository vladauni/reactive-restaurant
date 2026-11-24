
-- Тестові дані для системи "Ресторан"

INSERT INTO clients (id, first_name, last_name, phone)
VALUES (1, 'Олександр', 'Мельник', '+380501234567'),
       (2, 'Марія', 'Коваленко', '+380672345678'),
       (3, 'Андрій', 'Бондаренко', '+380933456789');

INSERT INTO administrators (id, first_name, last_name)
VALUES (1, 'Олена', 'Ткаченко'),
       (2, 'Петро', 'Савченко');

INSERT INTO menu_items (id, name, price)
VALUES (1, 'Борщ з пампушками', 120.00),
       (2, 'Котлета по-київськи', 220.00),
       (3, 'Салат Цезар', 180.00),
       (4, 'Узвар', 50.00);

INSERT INTO orders (id, client_id, administrator_id, status)
VALUES (1, 1, NULL, 'NEW'),
       (2, 2, 1, 'IN_KITCHEN'),
       (3, 3, 2, 'BILLED'),
       (4, 1, 1, 'PAID');

INSERT INTO order_items (order_id, menu_item_id, name, quantity, price)
VALUES (1, 1, 'Борщ з пампушками', 2, 120.00),
       (1, 4, 'Узвар', 2, 50.00),
       (2, 2, 'Котлета по-київськи', 1, 220.00),
       (2, 3, 'Салат Цезар', 1, 180.00),
       (3, 3, 'Салат Цезар', 1, 180.00),
       (3, 4, 'Узвар', 1, 50.00),
       (4, 1, 'Борщ з пампушками', 1, 120.00),
       (4, 2, 'Котлета по-київськи', 1, 220.00),
       (4, 4, 'Узвар', 1, 50.00);

INSERT INTO bills (id, order_id, administrator_id, amount, paid)
VALUES (1, 3, 2, 230.00, false),
       (2, 4, 1, 390.00, true);

INSERT INTO payments (id, bill_id, amount, method)
VALUES (1, 2, 390.00, 'CARD');


SELECT setval(pg_get_serial_sequence('clients', 'id'), (SELECT MAX(id) FROM clients));
SELECT setval(pg_get_serial_sequence('administrators', 'id'), (SELECT MAX(id) FROM administrators));
SELECT setval(pg_get_serial_sequence('menu_items', 'id'), (SELECT MAX(id) FROM menu_items));
SELECT setval(pg_get_serial_sequence('orders', 'id'), (SELECT MAX(id) FROM orders));
SELECT setval(pg_get_serial_sequence('bills', 'id'), (SELECT MAX(id) FROM bills));
SELECT setval(pg_get_serial_sequence('payments', 'id'), (SELECT MAX(id) FROM payments));