create table question
(
	id bigint auto_increment,
	title varchar(100) null,
	description text null,
	tags varchar(256) null,
	gmt_create bigint null,
	gmt_modified bigint null,
	comment_count int default 0 null,
	like_count int default 0 null,
	view_count int default 0 null,
	constraint question_pk
		primary key (id)
);

