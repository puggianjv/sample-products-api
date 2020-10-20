INSERT IGNORE INTO supplier(id, name, creation, last_update)
VALUES (1, 'Dia', now(), now() ),
       (2, 'Carrefour', now(), now()),
       (3, 'Extra', now(), now());