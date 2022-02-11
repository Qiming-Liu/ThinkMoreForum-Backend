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

INSERT INTO img (img_url, img_hash)
VALUES ('default_profile_img.png', 'be00b93b972c577de11bb7d37268fefc');