INSERT INTO users (name, email)
VALUES ('new_user', 'new_user@example.com');

INSERT INTO users (name, email)
VALUES ('booker', 'booker@example.com');

INSERT INTO users (name, email)
VALUES ('user_3', 'user_3@example.com');

INSERT INTO items (name, description, available, owner_id)
VALUES ('new_item', 'description_new_item', true, 1);

INSERT INTO items (name, description, available, owner_id)
VALUES ('item_2', 'description_item_2', true, 3);

INSERT INTO items (name, description, available, owner_id)
VALUES ('item_3', 'description_item_3', true, 3);

INSERT INTO items (name, description, available, owner_id)
VALUES ('item_4', 'description_item_4', false, 3);

INSERT INTO bookings (start_time, end_time, item_id, booker_id, status)
VALUES ('2024-06-03 00:00:00', '2024-06-07 00:00:00', 1, 2, 'APPROVED');

INSERT INTO bookings (start_time, end_time, item_id, booker_id, status)
VALUES ('2025-02-03 00:00:00', '2025-02-07 00:00:00', 2, 2, 'REJECTED');

INSERT INTO bookings (start_time, end_time, item_id, booker_id, status)
VALUES ('2022-05-01 00:00:00', '2022-06-07 00:00:00', 3, 2, 'APPROVED');

INSERT INTO bookings (start_time, end_time, item_id, booker_id, status)
VALUES ('2025-07-01 00:00:00', '2025-07-07 00:00:00', 3, 1, 'WAITING');


INSERT INTO comments(item_id, author_id, text,created)
VALUES (1, 2, 'comment text','2025-02-07 00:00:00');

INSERT INTO requests(description, requestor_id, created)
VALUES ('request_description', 1, '2025-02-07 00:00:00');