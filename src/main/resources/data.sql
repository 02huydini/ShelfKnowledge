INSERT INTO role (name) VALUES ('USER'), ('ADMIN');

INSERT INTO account (login_name, password) VALUES ('user', '{noop}123456');
INSERT INTO account (login_name, password) VALUES ('admin', '{noop}admin123');

INSERT INTO AccountRole (account_id, role_id) VALUES (1, 1), (2, 2);