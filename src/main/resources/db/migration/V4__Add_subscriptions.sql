create table user_subscriptions (
    channel_id int8 not null references usr,
    subscribers_id int8 not null references usr,
    primary key(channel_id, subscribers_id)
);