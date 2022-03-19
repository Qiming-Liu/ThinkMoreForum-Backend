CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE img
(
    id  uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    url text NOT NULL UNIQUE,
    md5 text NOT NULL UNIQUE
);

CREATE TABLE roles
(
    id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    role_name  varchar(20) NOT NULL UNIQUE,
    permission text        NOT NULL
);

CREATE TABLE users
(
    id                   uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    username             varchar(20)  NOT NULL UNIQUE,
    password             text,
    email                varchar(255) NOT NULL UNIQUE,
    head_img_url         text                     DEFAULT '/logo.png',
    profile_img_url      text                     DEFAULT '/logo.png',
    role_id              uuid         NOT NULL references roles (id),
    last_login_timestamp timestamp with time zone DEFAULT now(),
    create_timestamp     timestamp with time zone DEFAULT now()
);

CREATE TABLE oauth
(
    id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    users_id   uuid        NOT NULL references users (id),
    oauth_type varchar(20) NOT NULL,
    openid     text        NOT NULL
);

CREATE TABLE notification
(
    id               uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    users_id         uuid NOT NULL references users (id),
    img_url          text                     DEFAULT '/logo.png',
    context          text NOT NULL,
    viewed           boolean                  DEFAULT false,
    create_timestamp timestamp with time zone DEFAULT now()
);

CREATE TABLE category
(
    id           uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    pin_post_id  uuid,
    head_img_url text             DEFAULT '/logo.png',
    type         int,
    color        varchar(7)  NOT NULL,
    title        varchar(20) NOT NULL UNIQUE,
    description  varchar(60),
    post_count   int              DEFAULT 0,
    sort_order   int         NOT NULL UNIQUE
);

CREATE TABLE post
(
    id               uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    post_users_id    uuid           NOT NULL references users (id),
    category_id      uuid references category (id),
    head_img_url     text                     DEFAULT '/logo.png',
    title            varchar(60)    NOT NULL,
    context          varchar(65535) NOT NULL,
    view_count       int                      DEFAULT 0,
    follow_count     int                      DEFAULT 0,
    comment_count    int                      DEFAULT 0,
    visibility       boolean                  DEFAULT true,
    create_timestamp timestamp with time zone DEFAULT now()
);

CREATE TABLE comment
(
    id                uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    comment_users_id  uuid          NOT NULL references users (id),
    post_id           uuid          NOT NULL references post (id),
    parent_comment_id uuid references comment (id),
    context           varchar(5000) NOT NULL,
    visibility        boolean                  DEFAULT true,
    create_timestamp  timestamp with time zone DEFAULT now()
);

CREATE TABLE follow_post
(
    id               uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    users_id         uuid NOT NULL references users (id),
    post_id          uuid NOT NULL references post (id),
    create_timestamp timestamp with time zone DEFAULT now()
);

CREATE TABLE follow_user
(
    id                uuid PRIMARY KEY         DEFAULT gen_random_uuid(),
    users_id          uuid NOT NULL references users (id),
    followed_users_id uuid NOT NULL references users (id),
    create_timestamp  timestamp with time zone DEFAULT now()
);

ALTER TABLE "category"
    ADD FOREIGN KEY ("pin_post_id") REFERENCES "post" ("id");