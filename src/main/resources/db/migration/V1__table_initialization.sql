CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE img
(
    id       uuid PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    url  text NOT NULL UNIQUE,
    hash text NOT NULL UNIQUE
);

CREATE TABLE roles
(
    id         uuid PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    role_name  varchar(20) NOT NULL UNIQUE,
    permission text        NOT NULL
);

CREATE TABLE users
(
    id                   uuid PRIMARY KEY                  DEFAULT uuid_generate_v1mc(),
    username             varchar(20)              NOT NULL UNIQUE,
    password             text                     NOT NULL,
    email                varchar(255)             NOT NULL UNIQUE,
    profile_img_id       uuid references img (id),
    role_id              uuid                     NOT NULL references roles (id),
    last_login_timestamp timestamp with time zone NOT NULL,
    create_timestamp     timestamp with time zone NOT NULL DEFAULT now()
);

CREATE TABLE oauth
(
    id         uuid PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    users_id   uuid        NOT NULL references users (id),
    oauth_type varchar(20) NOT NULL,
    openid     text        NOT NULL
);

CREATE TABLE notification
(
    id               uuid PRIMARY KEY                  DEFAULT uuid_generate_v1mc(),
    users_id         uuid                     NOT NULL references users (id),
    icon             varchar(20)              NOT NULL,
    context          text                     NOT NULL,
    viewed           boolean                  NOT NULL,
    create_timestamp timestamp with time zone NOT NULL DEFAULT now()
);

CREATE TABLE category
(
    id             uuid PRIMARY KEY DEFAULT uuid_generate_v1mc(),
    pin_post_id    uuid,
    profile_img_id uuid references img (id),
    color          varchar(7)  NOT NULL,
    title          varchar(20) NOT NULL UNIQUE,
    description    varchar(60),
    post_count     int         NOT NULL,
    sort_order     int         NOT NULL UNIQUE
);

CREATE TABLE post
(
    id               uuid PRIMARY KEY                  DEFAULT uuid_generate_v1mc(),
    post_users_id    uuid                     NOT NULL references users (id),
    category_id      uuid                     NOT NULL references category (id),
    head_img_id      uuid references img (id),
    title            varchar(60)              NOT NULL,
    context          varchar(65535)           NOT NULL,
    view_count       int                      NOT NULL,
    follow_count     int                      NOT NULL,
    comment_count    int                      NOT NULL,
    visibility       boolean                  NOT NULL,
    create_timestamp timestamp with time zone NOT NULL DEFAULT now()
);

CREATE TABLE follow_post
(
    id               uuid PRIMARY KEY                  DEFAULT uuid_generate_v1mc(),
    users_id         uuid                     NOT NULL references users (id),
    post_id          uuid                     NOT NULL references post (id),
    create_timestamp timestamp with time zone NOT NULL DEFAULT now()
);

CREATE TABLE follow_user
(
    id                uuid PRIMARY KEY                  DEFAULT uuid_generate_v1mc(),
    users_id          uuid                     NOT NULL references users (id),
    followed_users_id uuid                     NOT NULL references users (id),
    create_timestamp  timestamp with time zone NOT NULL DEFAULT now()
);

CREATE TABLE post_comment
(
    id                uuid PRIMARY KEY                  DEFAULT uuid_generate_v1mc(),
    post_users_id     uuid                     NOT NULL references users (id),
    post_id           uuid                     NOT NULL references post (id),
    parent_comment_id uuid references post_comment (id),
    context           varchar(65535)           NOT NULL,
    visibility        boolean                  NOT NULL,
    create_timestamp  timestamp with time zone NOT NULL DEFAULT now()
);

ALTER TABLE "category"
    ADD FOREIGN KEY ("pin_post_id") REFERENCES "post" ("id");