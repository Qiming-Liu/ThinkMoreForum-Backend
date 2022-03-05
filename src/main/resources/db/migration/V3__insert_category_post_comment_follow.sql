INSERT INTO category (type, color, title, post_count, sort_order)
VALUES (0, '#5048E5', 'Default Category', 1, 0);

INSERT INTO category (type, color, title, post_count, sort_order)
VALUES (1, '#111827', 'Second Category', 2, 1);

INSERT INTO category (color, title, post_count, sort_order)
VALUES ('#5048E5', 'Third Category', 0, 2);

INSERT INTO post (post_users_id, category_id, title, context, view_count, follow_count, comment_count, visibility)
VALUES ((SELECT id FROM users WHERE username = 'admin'),
        (SELECT id FROM category WHERE title = 'Default Category'),
        'admin post title', '<p>admin post context</p>', 0, 1, 2, true);

INSERT INTO post (post_users_id, category_id, title, context, view_count, follow_count, comment_count, visibility)
VALUES ((SELECT id FROM users WHERE username = 'moderator'),
        (SELECT id FROM category WHERE title = 'Second Category'),
        'moderator post title', '<p>moderator post context</p>', 0, 0, 0, true);

INSERT INTO post (post_users_id, category_id, title, context, view_count, follow_count, comment_count, visibility)
VALUES ((SELECT id FROM users WHERE username = 'verified_user'),
        (SELECT id FROM category WHERE title = 'Second Category'),
        'verified_user post title', '<p>verified_user post context</p>', 0, 0, 0, true);

INSERT INTO post_comment (post_users_id, post_id, parent_comment_id, context, visibility)
VALUES ((SELECT id FROM users WHERE username = 'verified_user'),
        (SELECT id FROM post WHERE title = 'admin post title'),
        null,
        '<p>verified_user post_comment context</p>', true);

INSERT INTO post_comment (post_users_id, post_id, parent_comment_id, context, visibility)
VALUES ((SELECT id FROM users WHERE username = 'verified_user'),
        (SELECT id FROM post WHERE title = 'admin post title'),
        (SELECT id FROM post_comment WHERE context = '<p>verified_user post_comment context</p>'),
        '<p>verified_user embedded post_comment context</p>', true);

INSERT INTO follow_post (users_id, post_id)
VALUES ((SELECT id FROM users WHERE username = 'admin'),
        (SELECT id FROM post WHERE title = 'admin post title'));

INSERT INTO follow_user (users_id, followed_users_id)
VALUES ((SELECT id FROM users WHERE username = 'admin'),
        (SELECT id FROM users WHERE username = 'verified_user'));
