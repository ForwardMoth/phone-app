INSERT INTO callers (id, uuid, phone_number)
VALUES (1, 'ee106b0d-27e9-4020-98db-8fd46b454d48', '12345'),
       (2, '3a51cd46-82f8-45fc-a088-98700d7d791e', '111111');


INSERT INTO call_data (uuid, incoming_caller_id, outcoming_caller_id, start_call_time, finish_call_time)
VALUES ('d638ecab-1719-4032-b95e-d5b58a561a15', 1, 2,
        '2025-03-24 21:39:35.54622', '2025-03-25 00:41:47.54622'),
       ('f928d7d9-037c-4e06-9fb7-30459341dc55', 2, 1,
        '2025-04-25 07:01:49.54622', '2025-04-25 08:48:54.54622');