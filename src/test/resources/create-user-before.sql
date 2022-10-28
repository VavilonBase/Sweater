delete from user_role;
delete from usr;

insert into usr(id, active, username, password)
values (1, true, 'dru', '$2a$08$j40t0yqR15agN17cS0dljOJtZ.HcYT1KX0il4Ifdpa8dqBvLGRxCS'),
       (2, true, 'mike', '$2a$08$j40t0yqR15agN17cS0dljOJtZ.HcYT1KX0il4Ifdpa8dqBvLGRxCS');

insert into user_role(user_id, roles)
values (1, 'ADMIN'), (1, 'USER'),
       (2, 'USER');