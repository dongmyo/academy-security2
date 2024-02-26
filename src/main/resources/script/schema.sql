-- TODO #2: DDL 및 기반 데이터 제공
CREATE TABLE IF NOT EXISTS `Members` (
    `member_id`   BIGINT  NOT NULL auto_increment,
    `name`        VARCHAR(50)  NOT NULL,
    `pwd`         VARCHAR(100) NOT NULL,

    PRIMARY KEY(`member_id`)
);

CREATE TABLE IF NOT EXISTS `Authorities` (
    `member_id`   BIGINT  NOT NULL,
    `authority`   VARCHAR(50)  NOT NULL,

    PRIMARY KEY(`member_id`)
);

MERGE INTO `Members` key ( `member_id` ) VALUES ( 1, 'student', '12345' );
MERGE INTO `Members` key ( `member_id` ) VALUES ( 2, 'teacher', '67890' );

MERGE INTO `Authorities` key ( `member_id` ) VALUES ( 1, 'ROLE_STUDENT' );
MERGE INTO `Authorities` key ( `member_id` ) VALUES ( 2, 'ROLE_TEACHER' );
