DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS favorite_items;

CREATE TABLE clients (
    client_id int (11) unsigned  AUTO_INCREMENT,
    first_name varchar (300)  DEFAULT '',
    last_name varchar (300)  DEFAULT '',
    email varchar (300)  DEFAULT '',
    password varchar (300)  DEFAULT '',
    address varchar (300)  DEFAULT '',
    PRIMARY KEY (client_id)
);

CREATE TABLE items (
    item_id int (11) unsigned NOT NULL AUTO_INCREMENT,
    item_name varchar (300) NOT NULL DEFAULT '',
    item_description varchar (300) NOT NULL DEFAULT '',
    item_picture_link varchar (300) NOT NULL DEFAULT '',
    item_price int (11)unsigned NOT NULL DEFAULT 0,
    item_quantity int (11) unsigned NOT NULL DEFAULT 100
);


CREATE TABLE orders (
    order_id int (11) unsigned   AUTO_INCREMENT,
    order_date varchar (11)   DEFAULT '',
    client_id int (11) unsigned   DEFAULT '',
    item_id_list varchar (300)   DEFAULT '',
    delivery_address varchar (300)   DEFAULT '',
    total_price int (11) DEFAULT 0,
    order_status varchar (10) DEFAULT 'TEMPORAL',
    PRIMARY KEY (order_id),
    FOREIGN KEY (client_id) REFERENCES clients(client_id)
);

CREATE TABLE favorite_items (
    id int (11) unsigned AUTO_INCREMENT,
    client_id int (11) unsigned DEFAULT '',
    item_id int (11) unsigned DEFAULT '',
    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES clients(client_id)
);

INSERT INTO clients (first_name,last_name,email,password, address) VALUES
('tuvia', 'shragay', 'tuvia15215@gmail.com', '111', 'Elkana'),
('merav', 'shragay', 'merav.shragay@gmail.com', '222','Elkana');

INSERT INTO items (item_name, item_description, item_picture_link, item_price) VALUES
('nutra nuggets 10 Kg', 'canine food for old dogs', 'https://nutranuggets.com/wp-content/uploads/2023/03/nutra-nuggets-international-products-1680x576-030123.png', 185),
('dogli 3 kg', 'run-over bits of roadkill', 'https://www.purina.co.il/sites/site.prod1.purina.co.il/files/12467025_7613036288958_Enlarge.jpg', 17);

INSERT INTO orders (order_date,client_id,item_id_list,delivery_address,total_price,order_status)VALUES
('10/10/1933', 1,'1 2 1 1 1 2 1 2 2', 'elkana', 120, 'CLOSED'),
('10/10/1933', 1,'1 2 2', 'elkana', 120, 'CLOSED'),
('10/10/1933', 1,'1 2 2', 'elkana', 120, 'CLOSED'),
('02/02/1933', 2,'2 2 1 2 2 2 2 2 2 2 2 2', 'elkana', 190, 'TEMPORAL');

