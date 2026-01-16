-- snipp_link.snp_bbs definition

CREATE TABLE `snp_bbs` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '게시판 UID',
  `bbs_tp` varchar(32) NOT NULL COMMENT '게시판 유형 코드',
  `bbs_nm` varchar(50) NOT NULL COMMENT '게시판명',
  `use_at` char(1) NOT NULL DEFAULT 'Y' COMMENT '사용 여부',
  `delete_at` char(1) NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  `reg_uid` bigint(20) DEFAULT NULL COMMENT '생성자 UID',
  `reg_dt` timestamp NULL DEFAULT NULL COMMENT '생성 일시',
  `updt_uid` bigint(20) DEFAULT NULL COMMENT '수정자 UID',
  `updt_dt` timestamp NULL DEFAULT NULL COMMENT '수정 일시',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_login_hist definition

CREATE TABLE `snp_login_hist` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_email` varchar(32) NOT NULL COMMENT '유저 이메일',
  `login_dt` timestamp NULL DEFAULT current_timestamp() COMMENT '로그인 일시',
  `access_ip` varchar(50) DEFAULT NULL COMMENT '로그인 ip',
  `access_user_agent` varchar(300) DEFAULT NULL COMMENT '로그인 사용자 에이전트',
  `login_access_tp` varchar(32) DEFAULT NULL COMMENT '로그인 성공/실패',
  `login_access_description` varchar(1024) NOT NULL COMMENT '로그인 성공/실패 사유',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_shorten_url definition

CREATE TABLE `snp_shorten_url` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '짧은 URL UID',
  `origin_url` text NOT NULL COMMENT '원본 URL',
  `short_url` varchar(255) NOT NULL COMMENT '짧은 URL',
  `created_at` timestamp NOT NULL COMMENT '짧은 URL 생성 일시',
  `expires_at` timestamp NOT NULL COMMENT '짧은 URL 만료 일시',
  `is_public` char(1) NOT NULL DEFAULT 'Y' COMMENT '공개 여부 (true: 모두 접근 가능, false: 권한 사용자만)',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `short_url` (`short_url`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_social_user definition

CREATE TABLE `snp_social_user` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '유저 소셜 UID',
  `social_tp` varchar(32) NOT NULL COMMENT '소셜 유형 코드',
  `user_id` varchar(50) NOT NULL COMMENT '소셜 ID',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_tag definition

CREATE TABLE `snp_tag` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(100) NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_verify_code definition

CREATE TABLE `snp_verify_code` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `verify_code` varchar(32) NOT NULL,
  `user_email` varchar(32) NOT NULL,
  `is_expired` char(1) NOT NULL DEFAULT 'N',
  `created_at` timestamp NOT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_bbs_cl definition

CREATE TABLE `snp_bbs_cl` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT,
  `cl_nm` varchar(100) NOT NULL,
  `upper_cl` bigint(20) DEFAULT NULL,
  `bbs_uid` bigint(20) NOT NULL,
  `sort_no` int(11) DEFAULT 0,
  `delete_at` char(1) NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  `reg_uid` bigint(20) DEFAULT NULL,
  `reg_dt` datetime DEFAULT current_timestamp(),
  `updt_uid` bigint(20) DEFAULT NULL,
  `updt_dt` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`uid`),
  KEY `upper_cl` (`upper_cl`),
  KEY `idx_snp_bbs_cl_bbs_uid` (`bbs_uid`),
  CONSTRAINT `snp_bbs_cl_ibfk_1` FOREIGN KEY (`upper_cl`) REFERENCES `snp_bbs_cl` (`uid`),
  CONSTRAINT `snp_bbs_cl_ibfk_2` FOREIGN KEY (`bbs_uid`) REFERENCES `snp_bbs` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_board definition

CREATE TABLE `snp_board` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '게시글 UID',
  `bbs_uid` bigint(20) NOT NULL COMMENT '게시판 UID',
  `cl_uid` bigint(20) DEFAULT NULL COMMENT '분류 UID',
  `title` varchar(125) NOT NULL COMMENT '게시글 제목',
  `content` text NOT NULL COMMENT '게시글 내용',
  `thumbnail_src` varchar(1000) DEFAULT NULL COMMENT '썸네일src',
  `use_at` char(1) NOT NULL DEFAULT 'N' COMMENT '사용여부',
  `delete_at` char(1) NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  `reg_uid` bigint(20) DEFAULT NULL COMMENT '생성자 UID',
  `reg_dt` timestamp NULL DEFAULT NULL COMMENT '생성 일시',
  `updt_uid` bigint(20) DEFAULT NULL COMMENT '수정자 UID',
  `updt_dt` timestamp NULL DEFAULT NULL COMMENT '수정 일시',
  PRIMARY KEY (`uid`),
  KEY `idx_snp_board_bbs_uid` (`bbs_uid`),
  CONSTRAINT `snp_board_ibfk_1` FOREIGN KEY (`bbs_uid`) REFERENCES `snp_bbs` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_board_tag definition

CREATE TABLE `snp_board_tag` (
  `bbsctt_uid` bigint(20) NOT NULL,
  `tag_uid` bigint(20) NOT NULL,
  PRIMARY KEY (`bbsctt_uid`,`tag_uid`),
  KEY `tag_uid` (`tag_uid`),
  CONSTRAINT `snp_board_tag_ibfk_1` FOREIGN KEY (`bbsctt_uid`) REFERENCES `snp_board` (`uid`) ON DELETE CASCADE,
  CONSTRAINT `snp_board_tag_ibfk_2` FOREIGN KEY (`tag_uid`) REFERENCES `snp_tag` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_comment definition

CREATE TABLE `snp_comment` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '댓글 UID',
  `board_uid` bigint(20) NOT NULL COMMENT '게시글 UID',
  `content` text NOT NULL COMMENT '댓글 내용',
  `delete_at` char(1) NOT NULL DEFAULT 'N' COMMENT '삭제 여부',
  `reg_uid` bigint(20) DEFAULT NULL COMMENT '생성자 UID',
  `reg_dt` timestamp NULL DEFAULT NULL COMMENT '생성 일시',
  `updt_uid` bigint(20) DEFAULT NULL COMMENT '수정자 UID',
  `updt_dt` timestamp NULL DEFAULT NULL COMMENT '수정 일시',
  PRIMARY KEY (`uid`),
  KEY `board_uid` (`board_uid`),
  CONSTRAINT `snp_comment_ibfk_1` FOREIGN KEY (`board_uid`) REFERENCES `snp_board` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_user definition

CREATE TABLE `snp_user` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '유저 UID',
  `user_id` varchar(50) NOT NULL COMMENT '유저 ID',
  `password` varchar(256) NOT NULL COMMENT '유저 비밀번호',
  `user_email` varchar(32) NOT NULL COMMENT '유저 이메일',
  `user_nm` varchar(32) NOT NULL COMMENT '유저 이름',
  `social_uid` bigint(20) DEFAULT NULL COMMENT '유저 소셜 UID',
  `user_role` varchar(32) NOT NULL COMMENT '유저 권한',
  `last_conn_dt` timestamp NOT NULL COMMENT '최종 접속 일시',
  `last_pwd_chng_dt` timestamp NOT NULL COMMENT '최종 비밀번호 변경 일시',
  `pwd_fail_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '비밀번호 실패 횟수',
  `is_locked` char(1) NOT NULL DEFAULT 'N' COMMENT '계정 잠김 여부',
  `is_enable` char(1) NOT NULL DEFAULT 'Y' COMMENT '계정 활성화 여부',
  PRIMARY KEY (`uid`),
  KEY `fk_user_social` (`social_uid`),
  CONSTRAINT `fk_user_social` FOREIGN KEY (`social_uid`) REFERENCES `snp_social_user` (`uid`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_shorten_url_access definition

CREATE TABLE `snp_shorten_url_access` (
  `shorten_url_uid` bigint(20) NOT NULL COMMENT '짧은 URL UID',
  `user_uid` bigint(20) NOT NULL COMMENT '유저 UID',
  PRIMARY KEY (`shorten_url_uid`,`user_uid`),
  KEY `idx_snp_shorten_url_access_user_uid` (`user_uid`),
  CONSTRAINT `snp_shorten_url_access_ibfk_1` FOREIGN KEY (`shorten_url_uid`) REFERENCES `snp_shorten_url` (`uid`) ON DELETE CASCADE,
  CONSTRAINT `snp_shorten_url_access_ibfk_2` FOREIGN KEY (`user_uid`) REFERENCES `snp_user` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- snipp_link.snp_shorten_url_access_log definition

CREATE TABLE `snp_shorten_url_access_log` (
  `uid` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '짧은 URL 접근로그 UID',
  `short_url` varchar(255) NOT NULL COMMENT '짧은 URL',
  `user_uid` bigint(20) DEFAULT NULL COMMENT '유저 UID',
  `access_ip` varchar(50) NOT NULL COMMENT '접근 사용자 IP',
  `access_user_agent` varchar(300) NOT NULL COMMENT '접근 사용자 에이전트',
  `access_date` timestamp NOT NULL COMMENT '접근 일시',
  `access_tp` varchar(32) NOT NULL COMMENT '접근 성공 여부',
  PRIMARY KEY (`uid`),
  KEY `idx_snp_shorten_url_access_log_user_uid` (`user_uid`),
  CONSTRAINT `snp_shorten_url_access_log_ibfk_1` FOREIGN KEY (`user_uid`) REFERENCES `snp_user` (`uid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;