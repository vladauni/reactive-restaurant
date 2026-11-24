-- Таблиці

CREATE TABLE clients
(
    id         bigserial              NOT NULL,
    first_name character varying(100) NOT NULL,
    last_name  character varying(100) NOT NULL,
    phone      character varying(20)
);

CREATE TABLE administrators
(
    id         bigserial              NOT NULL,
    first_name character varying(100) NOT NULL,
    last_name  character varying(100) NOT NULL
);

CREATE TABLE menu_items
(
    id    bigserial              NOT NULL,
    name  character varying(200) NOT NULL,
    price numeric(12, 2)         NOT NULL
);

CREATE TABLE orders
(
    id               bigserial             NOT NULL,
    client_id        bigint                NOT NULL,
    administrator_id bigint,
    status           character varying(20) NOT NULL DEFAULT 'NEW',
    created_at       timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE order_items
(
    order_id     bigint                 NOT NULL,
    menu_item_id bigint                 NOT NULL,
    name         character varying(200) NOT NULL,
    quantity     integer                NOT NULL,
    price        numeric(12, 2)         NOT NULL
);

CREATE TABLE bills
(
    id               bigserial      NOT NULL,
    order_id         bigint         NOT NULL,
    administrator_id bigint         NOT NULL,
    amount           numeric(12, 2) NOT NULL,
    paid             boolean        NOT NULL DEFAULT false,
    issued_at        timestamp      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE payments
(
    id      bigserial             NOT NULL,
    bill_id bigint                NOT NULL,
    amount  numeric(12, 2)        NOT NULL,
    method  character varying(20) NOT NULL,
    paid_at timestamp             NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Первинні ключі
ALTER TABLE clients
    ADD PRIMARY KEY (id);
ALTER TABLE administrators
    ADD PRIMARY KEY (id);
ALTER TABLE menu_items
    ADD PRIMARY KEY (id);
ALTER TABLE orders
    ADD PRIMARY KEY (id);
ALTER TABLE order_items
    ADD PRIMARY KEY (order_id, menu_item_id);
ALTER TABLE bills
    ADD PRIMARY KEY (id);
ALTER TABLE payments
    ADD PRIMARY KEY (id);

-- Зовнішні ключі

-- Клієнт робить замовлення
ALTER TABLE orders
    ADD CONSTRAINT fk_orders_client_id
        FOREIGN KEY (client_id) REFERENCES clients (id);

-- Адміністратор підтверджує замовлення (NULL, поки не підтверджено)
ALTER TABLE orders
    ADD CONSTRAINT fk_orders_administrator_id
        FOREIGN KEY (administrator_id) REFERENCES administrators (id);

-- Позиції замовлення з меню
ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_order_id
        FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_items_menu_item_id
        FOREIGN KEY (menu_item_id) REFERENCES menu_items (id);

-- Адміністратор виставляє рахунок за замовлення
ALTER TABLE bills
    ADD CONSTRAINT fk_bills_order_id
        FOREIGN KEY (order_id) REFERENCES orders (id);

ALTER TABLE bills
    ADD CONSTRAINT fk_bills_administrator_id
        FOREIGN KEY (administrator_id) REFERENCES administrators (id);

-- Клієнт оплачує рахунок
ALTER TABLE payments
    ADD CONSTRAINT fk_payments_bill_id
        FOREIGN KEY (bill_id) REFERENCES bills (id);

-- Обмеження цілісності

ALTER TABLE orders
    ADD CONSTRAINT chk_orders_status
        CHECK (status IN ('NEW', 'CONFIRMED', 'IN_KITCHEN', 'READY', 'BILLED', 'PAID'));

ALTER TABLE order_items
    ADD CONSTRAINT chk_order_items_quantity CHECK (quantity > 0);

ALTER TABLE order_items
    ADD CONSTRAINT chk_order_items_price CHECK (price >= 0);

ALTER TABLE menu_items
    ADD CONSTRAINT chk_menu_items_price CHECK (price >= 0);

ALTER TABLE bills
    ADD CONSTRAINT uq_bills_order_id UNIQUE (order_id);

ALTER TABLE bills
    ADD CONSTRAINT chk_bills_amount CHECK (amount >= 0);

ALTER TABLE payments
    ADD CONSTRAINT chk_payments_amount CHECK (amount > 0);

-- Індекси на зовнішні ключі

CREATE INDEX idx_orders_client_id ON orders (client_id);
CREATE INDEX idx_orders_administrator_id ON orders (administrator_id);
CREATE INDEX idx_order_items_menu_item_id ON order_items (menu_item_id);
CREATE INDEX idx_bills_administrator_id ON bills (administrator_id);
CREATE INDEX idx_payments_bill_id ON payments (bill_id);