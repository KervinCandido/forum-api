CREATE TABLE user (
    id bigint(20) not null auto_increment,
    name varchar(200) not null,
    email varchar(60) not null,
    user_name varchar(20) not null,
    password varchar(200) not null,
    create_date datetime default CURRENT_TIMESTAMP,
    active boolean default true,
    primary key(id)
);

CREATE TABLE question (
    id bigint(20) not null auto_increment,
    description varchar(255) not null,
    create_date datetime not null,
    id_author bigint(20) not null,
    views bigint(20) not null default 0,
    foreign key(id_author) references user(id),
    primary key(id)
);

CREATE TABLE answer (
    id bigint(20) not null auto_increment,
    id_question bigint(20) not null,
    id_author bigint(20) not null,
    content varchar(255),
    create_date datetime not null,
    foreign key (id_question) references question(id),
    foreign key (id_author) references user(id),
    primary key (id)
);

