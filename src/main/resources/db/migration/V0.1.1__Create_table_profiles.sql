CREATE TABLE profile (
    id bigint(20) not null auto_increment,
    name varchar(60) not null,
    primary key(id)
);

CREATE TABLE user_profile (
    user_id bigint(20) not null,
    profile_id bigint(20) not null,
    foreign key (user_id) references user(id),
    foreign key (profile_id) references profile(id),
    primary key (user_id, profile_id)
);
