--admin users
INSERT INTO user(username, password, is_admin, enabled, date_created)
values ('user-admin', 'randompassword', 1, 1, CURRENT_TIMESTAMP);
INSERT INTO user(username, password, is_admin, enabled, date_created)
values ('user-disabled', 'randompassword', 1, 0, CURRENT_TIMESTAMP);

--client users
INSERT INTO user(username, password, is_admin, enabled, date_created)
values ('client@nrtl.com', 'randompassword', 0, 1, CURRENT_TIMESTAMP);
INSERT INTO user(username, password, is_admin, enabled, date_created)
values ('candidate@nrtl.com', 'randompassword', 0, 1, CURRENT_TIMESTAMP);
INSERT INTO user(username, password, is_admin, enabled, date_created)
values ('benediktas@nrtl.com', 'randompassword', 0, 1, CURRENT_TIMESTAMP);

--pizza list
INSERT INTO pizza(name, price)
values ('Capricciosa', 7.99),
       ('Bismarck', 7.99),
       ('Pepperoni', 4.99),
       ('Bolognese', 5.99),
       ('Margherita', 7.99),
       ('Rustica', 6.99),
       ('Calzone', 7.99),
       ('Mimosa', 11.99),
       ('Funghi', 12.99),
       ('Tirolese', 12.99);

--dummy order
INSERT INTO order_(client_id, address, date_created)
values ((SELECT id FROM user WHERE username = 'client@nrtl.com'), 'Rinktines g. 5, Vilnius', CURRENT_TIMESTAMP),
       ((SELECT id FROM user WHERE username = 'client@nrtl.com'), 'Traku g. 1, Vilnius', CURRENT_TIMESTAMP),
       ((SELECT id FROM user WHERE username = 'client@nrtl.com'), 'Vilniaus g. 2, Vilnius', CURRENT_TIMESTAMP),
       ((SELECT id FROM user WHERE username = 'client@nrtl.com'), 'Kauno g. 3, Vilnius', CURRENT_TIMESTAMP),
       ((SELECT id FROM user WHERE username = 'client@nrtl.com'), 'Tilto g. 5, Vilnius', CURRENT_TIMESTAMP),
       ((SELECT id FROM user WHERE username = 'client@nrtl.com'), 'Gedimino pr. 11, Vilnius', CURRENT_TIMESTAMP),
       ((SELECT id FROM user WHERE username = 'benediktas@nrtl.com'), 'Benedikto g. 5, Vilnius', CURRENT_TIMESTAMP),
       ((SELECT id FROM user WHERE username = 'benediktas@nrtl.com'), 'Benedikto g. 5, VILNIUS', CURRENT_TIMESTAMP),
       ((SELECT id FROM user WHERE username = 'benediktas@nrtl.com'), 'Turgaus g. 12, Vilnius', CURRENT_TIMESTAMP);

INSERT INTO order_pizza(pizza_id, order_id)
values ((SELECT id FROM pizza WHERE name = 'Capricciosa'), (SELECT id FROM order_ WHERE address = 'Rinktines g. 5, Vilnius')),
       ((SELECT id FROM pizza WHERE name = 'Bismarck'), (SELECT id FROM order_ WHERE address = 'Rinktines g. 5, Vilnius')),
       ((SELECT id FROM pizza WHERE name = 'Pepperoni'), (SELECT id FROM order_ WHERE address = 'Rinktines g. 5, Vilnius')),
       ((SELECT id FROM pizza WHERE name = 'Bolognese'), (SELECT id FROM order_ WHERE address = 'Traku g. 1, Vilnius')),
       ((SELECT id FROM pizza WHERE name = 'Margherita'), (SELECT id FROM order_ WHERE address = 'Traku g. 1, Vilnius')),
       ((SELECT id FROM pizza WHERE name = 'Rustica'), (SELECT id FROM order_ WHERE address = 'Vilniaus g. 2, Vilnius')),
       ((SELECT id FROM pizza WHERE name = 'Calzone'), (SELECT id FROM order_ WHERE address = 'Vilniaus g. 2, Vilnius')),
       ((SELECT id FROM pizza WHERE name = 'Bismarck'), (SELECT id FROM order_ WHERE address = 'Kauno g. 3, Vilnius')),
       ((SELECT id FROM pizza WHERE name = 'Mimosa'), (SELECT id FROM order_ WHERE address = 'Tilto g. 5, Vilnius')),
       ((SELECT id FROM pizza WHERE name = 'Funghi'), (SELECT id FROM order_ WHERE address = 'Gedimino pr. 11, Vilnius')),
       ((SELECT id FROM pizza WHERE name = 'Pepperoni'), (SELECT id FROM order_ WHERE address = 'Benedikto g. 5, Vilnius')),
       ((SELECT id FROM pizza WHERE name = 'Tirolese'), (SELECT id FROM order_ WHERE address = 'Benedikto g. 5, VILNIUS')),
       ((SELECT id FROM pizza WHERE name = 'Funghi'), (SELECT id FROM order_ WHERE address = 'Turgaus g. 12, Vilnius'));