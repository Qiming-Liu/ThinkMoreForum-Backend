INSERT INTO roles (role_name, permission)
VALUES ('admin', '{}');

INSERT INTO roles (role_name, permission)
VALUES ('moderator', '{}');

INSERT INTO roles (role_name, permission)
VALUES ('verified_user', '{}');

INSERT INTO roles (role_name, permission)
VALUES ('unverified_user', '{}');

INSERT INTO roles (role_name, permission)
VALUES ('banned_user', '{}');

-- 密码统一是123456
INSERT INTO users (username, password, email, profile_img_id, role_id, last_login_timestamp)
VALUES ('admin', '$argon2id$v=19$m=4096,t=3,p=1$gL/kMr7KOOCqFFDDbUwzBg$yav+sp5kn7Y6RB/zLJYAKsDvPzDVnbUZbblCJLee9Yw',
        'admin@forum.com', null, (SELECT id FROM roles WHERE role_name = 'admin'), now());

INSERT INTO users (username, password, email, profile_img_id, role_id, last_login_timestamp)
VALUES ('moderator', '$argon2id$v=19$m=4096,t=3,p=1$gL/kMr7KOOCqFFDDbUwzBg$yav+sp5kn7Y6RB/zLJYAKsDvPzDVnbUZbblCJLee9Yw',
        'moderator@forum.com', null, (SELECT id FROM roles WHERE role_name = 'moderator'), now());

INSERT INTO users (username, password, email, profile_img_id, role_id, last_login_timestamp)
VALUES ('verified_user',
        '$argon2id$v=19$m=4096,t=3,p=1$gL/kMr7KOOCqFFDDbUwzBg$yav+sp5kn7Y6RB/zLJYAKsDvPzDVnbUZbblCJLee9Yw',
        'verified_user@forum.com', null, (SELECT id FROM roles WHERE role_name = 'verified_user'), now());

INSERT INTO users (username, password, email, profile_img_id, role_id, last_login_timestamp)
VALUES ('unverified_user',
        '$argon2id$v=19$m=4096,t=3,p=1$gL/kMr7KOOCqFFDDbUwzBg$yav+sp5kn7Y6RB/zLJYAKsDvPzDVnbUZbblCJLee9Yw',
        'unverified_user@forum.com', null, (SELECT id FROM roles WHERE role_name = 'unverified_user'), now());

INSERT INTO users (username, password, email, profile_img_id, role_id, last_login_timestamp)
VALUES ('banned_user',
        '$argon2id$v=19$m=4096,t=3,p=1$gL/kMr7KOOCqFFDDbUwzBg$yav+sp5kn7Y6RB/zLJYAKsDvPzDVnbUZbblCJLee9Yw',
        'banned_user@forum.com', null, (SELECT id FROM roles WHERE role_name = 'banned_user'), now());

INSERT INTO img (url, hash)
VALUES ('http://172.17.0.2:9000/img/be00b93b972c577de11bb7d37268fefc?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=VOJHEYSCI52ETMWQXR9S%2F20220212%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220212T050846Z&X-Amz-Expires=604800&X-Amz-Security-Token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3NLZXkiOiJWT0pIRVlTQ0k1MkVUTVdRWFI5UyIsImV4cCI6MTY0NDY0MjY2NSwicGFyZW50IjoiYWRtaW4ifQ.sNyJtL9h11YQaOd_FrbSw--zrB6tyXRJuhhgN64J5AWqXH2hWJaFk9KtMt4qPxmQZ42EjlvxLGbOoNeqQ5MCGQ&X-Amz-SignedHeaders=host&versionId=null&X-Amz-Signature=0e8a3b34d3a8afa3d860e8dfd947b855e88dc1f29d9a3808d15d157514407f8e', 'be00b93b972c577de11bb7d37268fefc');