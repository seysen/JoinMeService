create table users
(
    id         bigserial,
    name       varchar(65) not null,
    surname    varchar(65) not null,
    created_dt timestamp   not null,
    email      varchar(30) not null,
    link_ava   varchar(3000),
    password   varchar(255) not null,
    updated_dt timestamp,
    primary key (id)
);

create table events
(
    id          bigserial,
    name        varchar(65) not null,
    created_dt  timestamp   not null,
    creator_id  bigint      not null references users (id) ON DELETE CASCADE,
    description varchar(3000),
    link_ava    varchar(3000),
    updated_dt  timestamp,
    primary key (id)
);

create table users_events
(
    id       bigserial,
    event_id bigint not null references events (id) ON DELETE CASCADE,
    user_id  bigint not null references users (id) ON DELETE CASCADE,
    primary key (id)
);

create table categories
(
    id   bigserial,
    name varchar(30) not null,
    primary key (id)
);

create table events_categories
(
    id          bigserial,
    category_id bigint not null references categories (id) ON DELETE CASCADE,
    event_id    bigint not null references events (id) ON DELETE CASCADE,
    primary key (id)
);

create table messages
(
    id         bigserial,
    text       varchar(3000) not null,
    created_dt timestamp     not null,
    updated_dt timestamp,
    event_id   bigint        not null references events (id) ON DELETE CASCADE,
    user_id    bigint        not null references users (id) ON DELETE CASCADE,
    primary key (id)
);

create table photos
(
    id         bigserial,
    created_dt timestamp     not null,
    link       varchar(3000) not null,
    updated_dt timestamp,
    event_id   bigint        not null references events (id) ON DELETE CASCADE,
    user_id    bigint        not null references users (id) ON DELETE CASCADE,
    primary key (id)
);

create table likes_events
(
    id       bigserial,
    is_like  boolean,
    event_id bigint not null references events (id) ON DELETE CASCADE,
    user_id  bigint not null references users (id) ON DELETE CASCADE,
    primary key (id)
);

create table likes_photos
(
    id       bigserial,
    is_like  boolean not null,
    photo_id bigint  not null references photos (id) ON DELETE CASCADE,
    user_id  bigint  not null references users (id) ON DELETE CASCADE,
    primary key (id)
);




