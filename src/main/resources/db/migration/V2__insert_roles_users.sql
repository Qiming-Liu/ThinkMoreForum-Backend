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

INSERT INTO users (username, password, email, profile_img_id, role_id, last_login_timestamp)
VALUES ('Alan', '$argon2id$v=19$m=4096,t=3,p=1$gL/kMr7KOOCqFFDDbUwzBg$yav+sp5kn7Y6RB/zLJYAKsDvPzDVnbUZbblCJLee9Yw',
        'prossliu@gmail.com', null, (SELECT id FROM roles WHERE role_name = 'admin'), now());

INSERT INTO users (username, password, email, profile_img_id, role_id, last_login_timestamp)
VALUES ('admin', '$argon2id$v=19$m=4096,t=3,p=1$gL/kMr7KOOCqFFDDbUwzBg$yav+sp5kn7Y6RB/zLJYAKsDvPzDVnbUZbblCJLee9Yw',
        'admin@forum', null, (SELECT id FROM roles WHERE role_name = 'admin'), now());

INSERT INTO users (username, password, email, profile_img_id, role_id, last_login_timestamp)
VALUES ('moderator', '$argon2id$v=19$m=4096,t=3,p=1$gL/kMr7KOOCqFFDDbUwzBg$yav+sp5kn7Y6RB/zLJYAKsDvPzDVnbUZbblCJLee9Yw',
        'alfred.minjiang@gmail.com', null, (SELECT id FROM roles WHERE role_name = 'moderator'), now());

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
VALUES ('https://www.thinkmoreapp.com/logo.png', 'f5a379af58f34f549c22f508a60ba04f');
