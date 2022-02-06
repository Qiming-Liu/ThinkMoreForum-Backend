INSERT INTO roles (id, role_name, permission)
VALUES (uuid_generate_v1mc(), 'admin', '{}');

-- 密码123456
INSERT INTO users (username, password, email, profile_img_id, role_id, last_login_timestamp)
VALUES ('admin', '$argon2id$v=19$m=4096,t=3,p=1$gL/kMr7KOOCqFFDDbUwzBg$yav+sp5kn7Y6RB/zLJYAKsDvPzDVnbUZbblCJLee9Yw',
        'asd2@asd.com', null, (SELECT id FROM roles WHERE role_name = 'admin'), now());