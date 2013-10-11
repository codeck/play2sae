# --- First database schema

# --- !Ups

create table user (
  email                     varchar(255) not null primary key,
  name                      varchar(255) not null,
  password                  varchar(255) not null
);

create table project (
  id                        bigint not null auto_increment primary key,
  name                      varchar(255) not null,
  folder                    varchar(255) not null
);

create table project_member (
  project_id                bigint not null,
  user_email                varchar(255) not null
);

alter table project_member add constraint fk_project_member_1 foreign key (project_id) references project (id) on delete cascade on update restrict;
alter table project_member add constraint fk_project_member_2 foreign key (user_email) references user (email) on delete cascade on update restrict;

create table task (
  id                        bigint not null auto_increment primary key,
  title                     varchar(255) not null,
  done                      boolean,
  due_date                  timestamp,
  assigned_to               varchar(255),
  project                   bigint not null,
  folder                    varchar(255)
);
alter table task add constraint fk_task_user_1 foreign key (assigned_to) references user (email) on delete set null on update restrict;
alter table task add constraint fk_task_project_1 foreign key (project) references project (id) on delete cascade on update restrict;


# --- !Downs

drop table if exists task;
drop sequence if exists task_seq;
drop table if exists project_member;
drop table if exists project;
drop sequence if exists project_seq;
drop table if exists user;
