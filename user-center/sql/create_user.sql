

create table user
(
    id           bigint auto_increment comment 'id',
    username     varchar(256)                                                   null comment '用户名',
    userAccount  varchar(256)                                                   null comment '账号',
    avatarUrl    varchar(1024)                                                  null comment '用户头像',
    gender       tinyint                                                        null comment '性别',
    userPassword varchar(512)                                                   not null comment '密码',
    phone        varchar(128)                                                   null comment '电话号码',
    email        varchar(512)                                                   null comment '邮箱',
    userStatus   int      default 0                                             not null comment '状态 0-正常',
    createTime   datetime default CURRENT_TIMESTAMP                             null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP null comment '更新时间',
    isDelete     tinyint  default 0                                             not null,
    constraint user_pk
        primary key (id)
) COMMENT '用户';


create table tag
(
    id           bigint auto_increment comment 'id' primary key,
    tagName      varchar(256)                       null comment '标签名称',
    userId       bigint                             null comment '用户 id',
    parentId     bigint                             null comment '父标签 id',
    isParent     tinyint                            null comment '0 - 不是,1 - 父标签',
    isDelete     tinyint  default 0                 not null comment '是否删除',
    createTime    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
)
    comment '标签';
