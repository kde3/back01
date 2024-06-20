
drop table TBL_COMMUNITY_FILE;
drop table TBL_COMMUNITY_LIKE;
drop table TBL_COMMUNITY_COMMENT;
drop table TBL_COMMUNITY_POST;
drop table TBL_USER;

drop sequence seq_community_post_no;
drop sequence seq_community_file_no;
drop sequence seq_community_comment_no;

CREATE SEQUENCE seq_community_post_no
    INCREMENT BY 1
    START WITH 1;

CREATE SEQUENCE seq_community_file_no
    INCREMENT BY 1
    START WITH 1;

CREATE SEQUENCE seq_community_comment_no
    INCREMENT BY 1
    START WITH 1;

CREATE TABLE tbl_user (
    user_no     	VARCHAR2(128) 	NOT NULL PRIMARY KEY,
    id              VARCHAR2(20)    NOT NULL,
    password        VARCHAR2(100)   NOT NULL,
    name            VARCHAR2(20)    NOT NULL,
    email           VARCHAR2(20)    NOT NULL,
    phone_number    VARCHAR2(20)    NOT NULL,
    created_at      VARCHAR2(20)   	NOT NULL,
    role            VARCHAR2(10)    NOT NULL
);

CREATE TABLE tbl_community_post (
    post_no     NUMBER  			NOT NULL PRIMARY KEY,
    user_no     VARCHAR2(128)  		NOT NULL,
    title       VARCHAR2(30)    	NOT NULL,
    created_at  VARCHAR2(20)   	    NOT NULL,
    updated_at  VARCHAR2(20)   		NOT NULL,
    is_updated  VARCHAR2(1) 		DEFAULT 'N' NOT NULL CHECK (is_updated IN ('Y', 'N')),
    content     VARCHAR2(2000)    	NOT NULL,
    like_cnt    NUMBER  			DEFAULT 0 NOT NULL,
    cmt_cnt     NUMBER  			DEFAULT 0 NOT NULL,

    CONSTRAINT POST_USER_NO_FK FOREIGN KEY (user_no) REFERENCES tbl_user (user_no) ON DELETE CASCADE
);

CREATE TABLE tbl_community_file (
    file_no     NUMBER  		    NOT NULL PRIMARY KEY,
    post_no     NUMBER  		    NOT NULL,
    file_path   VARCHAR2(255)       NOT NULL,

    CONSTRAINT FILE_POST_NO_FK FOREIGN KEY (post_no) REFERENCES tbl_community_post (post_no) ON DELETE CASCADE
);

CREATE TABLE tbl_community_comment (
    cmt_no      NUMBER  			NOT NULL PRIMARY KEY,
    post_no     NUMBER  			NOT NULL,
    user_no     VARCHAR2(128)  	    NOT NULL,
    content     VARCHAR2(2000)      NOT NULL,
    created_at  VARCHAR2(20)   	    NOT NULL,
    updated_at  VARCHAR2(20)   		NOT NULL,
    is_updated  VARCHAR2(1) 		DEFAULT 'N' NOT NULL CHECK (is_updated IN ('Y', 'N')),

    CONSTRAINT CMT_POST_NO_FK FOREIGN KEY (post_no) REFERENCES tbl_community_post (post_no) ON DELETE CASCADE,
    CONSTRAINT CMT_USER_NO_FK FOREIGN KEY (user_no) REFERENCES tbl_user (user_no) ON DELETE CASCADE
);

CREATE TABLE tbl_community_like (
    post_no     NUMBER  			NOT NULL,
    user_no     VARCHAR2(128)  	    NOT NULL,

    CONSTRAINT LIKE_PK PRIMARY KEY (post_no, user_no),
    CONSTRAINT LIKE_POST_NO_FK FOREIGN KEY (post_no) REFERENCES tbl_community_post (post_no) ON DELETE CASCADE,
    CONSTRAINT LIKE_USER_NO_FK FOREIGN KEY (user_no) REFERENCES tbl_user (user_no) ON DELETE CASCADE
);

-- user data
insert into TBL_USER (USER_NO, ID, PASSWORD, NAME, EMAIL, PHONE_NUMBER, CREATED_AT, ROLE)
values ('f00586b2-e0d0-4a44-a05b-95177a752350', 'aaa', '$2a$10$SPa39qVNqWA.VqMP/WQXOO.mfyWFe74C4tprELbU/s/QI6KN9XPCS', 'aaa', 'aaa@naver.com', '010-0000-0000', '2024-05-12', 'USER');
insert into TBL_USER (USER_NO, ID, PASSWORD, NAME, EMAIL, PHONE_NUMBER, CREATED_AT, ROLE)
values ('cb865a0c-44da-4ede-96f0-83dc1e3ba096', 'kkk', '$2a$10$GFUW.wfJoEbzJbcPv.gL0OCJZBdCG60buq1ckbkDvlXfJ/PP7/1W6', 'kkk', 'kkk@naver.com', '010-0000-0000', '2024-05-12', 'ADMIN');
