-- 0. 시퀀스 생성
CREATE SEQUENCE IF NOT EXISTS seq_snp_file_uid START WITH 1 INCREMENT BY 1;

-- 1. snp_bbs (게시판 마스터)
CREATE TABLE IF NOT EXISTS `snp_bbs` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `bbs_tp` varchar(32) NOT NULL,
    `bbs_nm` varchar(50) NOT NULL,
    `use_at` char(1) NOT NULL DEFAULT 'Y',
    `delete_at` char(1) NOT NULL DEFAULT 'N',
    `reg_uid` bigint(20) DEFAULT NULL,
    `reg_dt` timestamp NULL DEFAULT NULL,
    `updt_uid` bigint(20) DEFAULT NULL,
    `updt_dt` timestamp NULL DEFAULT NULL
    );

-- 2. snp_bbs_cl (게시판 분류)
CREATE TABLE IF NOT EXISTS `snp_bbs_cl` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `cl_nm` varchar(100) NOT NULL,
    `upper_cl` bigint(20) DEFAULT NULL,
    `bbs_uid` bigint(20) NOT NULL,
    `sort_no` int(11) DEFAULT 0,
    `delete_at` char(1) NOT NULL DEFAULT 'N',
    `reg_uid` bigint(20) DEFAULT NULL,
    `reg_dt` datetime DEFAULT current_timestamp(),
    `updt_uid` bigint(20) DEFAULT NULL,
    `updt_dt` datetime DEFAULT current_timestamp(),
    CONSTRAINT `snp_bbs_cl_ibfk_1` FOREIGN KEY (`upper_cl`) REFERENCES `snp_bbs_cl` (`uid`),
    CONSTRAINT `snp_bbs_cl_ibfk_2` FOREIGN KEY (`bbs_uid`) REFERENCES `snp_bbs` (`uid`)
    );

-- 3. snp_social_user (소셜 계정 정보)
CREATE TABLE IF NOT EXISTS `snp_social_user` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `social_tp` varchar(32) NOT NULL,
    `user_id` varchar(50) NOT NULL
    );

-- 4. snp_user (사용자 마스터)
CREATE TABLE IF NOT EXISTS `snp_user` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` varchar(50) NOT NULL,
    `password` varchar(256) NOT NULL,
    `user_email` varchar(32) NOT NULL,
    `user_nm` varchar(32) NOT NULL,
    `social_uid` bigint(20) DEFAULT NULL,
    `user_role` varchar(32) NOT NULL,
    `last_conn_dt` timestamp NOT NULL,
    `last_pwd_chng_dt` timestamp NOT NULL,
    `pwd_fail_cnt` int(11) NOT NULL DEFAULT 0,
    `is_locked` char(1) NOT NULL DEFAULT 'N',
    `is_enable` char(1) NOT NULL DEFAULT 'Y',
    CONSTRAINT `fk_user_social` FOREIGN KEY (`social_uid`) REFERENCES `snp_social_user` (`uid`) ON DELETE SET NULL
    );

-- 5. snp_board (게시글)
CREATE TABLE IF NOT EXISTS `snp_board` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `bbs_uid` bigint(20) NOT NULL,
    `cl_uid` bigint(20) DEFAULT NULL,
    `title` varchar(125) NOT NULL,
    `content` text NOT NULL,
    `thumbnail_src` varchar(1000) DEFAULT NULL,
    `file_uid` bigint(20) DEFAULT NULL,
    `use_at` char(1) NOT NULL DEFAULT 'N',
    `delete_at` char(1) NOT NULL DEFAULT 'N',
    `reg_uid` bigint(20) DEFAULT NULL,
    `reg_dt` timestamp NULL DEFAULT NULL,
    `updt_uid` bigint(20) DEFAULT NULL,
    `updt_dt` timestamp NULL DEFAULT NULL,
    CONSTRAINT `snp_board_ibfk_1` FOREIGN KEY (`bbs_uid`) REFERENCES `snp_bbs` (`uid`) ON DELETE CASCADE
    );

-- 6. snp_file (파일 마스터 - 시퀀스 방식)
CREATE TABLE IF NOT EXISTS `snp_file` (
    `uid` bigint(20) NOT NULL,
    `file_id` varchar(1000) NOT NULL,
    `file_nm` varchar(1000) NOT NULL,
    `org_file_nm` varchar(1000) DEFAULT NULL,
    `file_url` varchar(1000) DEFAULT NULL,
    `file_path` varchar(1000) NOT NULL,
    `file_type` varchar(32) DEFAULT NULL,
    `extension` varchar(32) NOT NULL,
    `storage_tp` varchar(32) NOT NULL,
    `file_size` bigint(20) DEFAULT 0,
    `down_cnt` int(11) DEFAULT 0,
    `delete_at` char(1) NOT NULL DEFAULT 'N',
    `reg_uid` bigint(20) DEFAULT NULL,
    `reg_dt` timestamp NULL DEFAULT NULL,
    `updt_uid` bigint(20) DEFAULT NULL,
    `updt_dt` timestamp NULL DEFAULT NULL,
    PRIMARY KEY (`uid`, `file_id`),
    CONSTRAINT `fk_snp_file_reg_user` FOREIGN KEY (`reg_uid`) REFERENCES `snp_user` (`uid`) ON DELETE SET NULL
    );

-- 7. snp_file_log (파일 이력)
CREATE TABLE IF NOT EXISTS `snp_file_log` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `file_uid` bigint(20) NOT NULL,
    `file_id` varchar(1000) NOT NULL,
    `reg_uid` bigint(20) DEFAULT NULL,
    `reg_dt` timestamp NOT NULL DEFAULT current_timestamp(),
    CONSTRAINT `fk_snp_file_log_user` FOREIGN KEY (`reg_uid`) REFERENCES `snp_user` (`uid`) ON DELETE SET NULL
    );

-- 8. snp_tag (태그 마스터)
CREATE TABLE IF NOT EXISTS `snp_tag` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `tag_name` varchar(100) NOT NULL
    );

-- 9. snp_board_tag (게시글-태그 매핑)
CREATE TABLE IF NOT EXISTS `snp_board_tag` (
    `bbsctt_uid` bigint(20) NOT NULL,
    `tag_uid` bigint(20) NOT NULL,
    PRIMARY KEY (`bbsctt_uid`, `tag_uid`),
    CONSTRAINT `snp_board_tag_ibfk_1` FOREIGN KEY (`bbsctt_uid`) REFERENCES `snp_board` (`uid`) ON DELETE CASCADE,
    CONSTRAINT `snp_board_tag_ibfk_2` FOREIGN KEY (`tag_uid`) REFERENCES `snp_tag` (`uid`) ON DELETE CASCADE
    );

-- 10. snp_comment (댓글)
CREATE TABLE IF NOT EXISTS `snp_comment` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `board_uid` bigint(20) NOT NULL,
    `content` text NOT NULL,
    `delete_at` char(1) NOT NULL DEFAULT 'N',
    `reg_uid` bigint(20) DEFAULT NULL,
    `reg_dt` timestamp NULL DEFAULT NULL,
    `updt_uid` bigint(20) DEFAULT NULL,
    `updt_dt` timestamp NULL DEFAULT NULL,
    CONSTRAINT `snp_comment_ibfk_1` FOREIGN KEY (`board_uid`) REFERENCES `snp_board` (`uid`) ON DELETE CASCADE
    );

-- 11. snp_login_hist (로그인 이력)
CREATE TABLE IF NOT EXISTS `snp_login_hist` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_email` varchar(32) NOT NULL,
    `login_dt` timestamp NULL DEFAULT current_timestamp(),
    `access_ip` varchar(50) DEFAULT NULL,
    `access_user_agent` varchar(300) DEFAULT NULL,
    `login_access_tp` varchar(32) DEFAULT NULL,
    `login_access_description` varchar(1024) NOT NULL
    );

-- 12. snp_shorten_url (단축 URL 마스터)
CREATE TABLE IF NOT EXISTS `snp_shorten_url` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `origin_url` text NOT NULL,
    `short_url` varchar(255) NOT NULL UNIQUE,
    `created_at` timestamp NOT NULL,
    `expires_at` timestamp NOT NULL,
    `is_public` char(1) NOT NULL DEFAULT 'Y'
    );

-- 13. snp_shorten_url_access (단축 URL 접근 권한 매핑 - 누락됐던 것)
CREATE TABLE IF NOT EXISTS `snp_shorten_url_access` (
    `shorten_url_uid` bigint(20) NOT NULL,
    `user_uid` bigint(20) NOT NULL,
    PRIMARY KEY (`shorten_url_uid`, `user_uid`),
    CONSTRAINT `snp_shorten_url_access_ibfk_1` FOREIGN KEY (`shorten_url_uid`) REFERENCES `snp_shorten_url` (`uid`) ON DELETE CASCADE,
    CONSTRAINT `snp_shorten_url_access_ibfk_2` FOREIGN KEY (`user_uid`) REFERENCES `snp_user` (`uid`) ON DELETE CASCADE
    );

-- 14. snp_shorten_url_access_log (단축 URL 접근 로그)
CREATE TABLE IF NOT EXISTS `snp_shorten_url_access_log` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `short_url` varchar(255) NOT NULL,
    `user_uid` bigint(20) DEFAULT NULL,
    `access_ip` varchar(50) NOT NULL,
    `access_user_agent` varchar(300) NOT NULL,
    `access_date` timestamp NOT NULL,
    `access_tp` varchar(32) NOT NULL,
    CONSTRAINT `snp_shorten_url_access_log_ibfk_1` FOREIGN KEY (`user_uid`) REFERENCES `snp_user` (`uid`) ON DELETE CASCADE
    );

-- 15. snp_verify_code (인증 코드)
CREATE TABLE IF NOT EXISTS `snp_verify_code` (
    `uid` bigint(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `verify_code` varchar(32) NOT NULL,
    `user_email` varchar(32) NOT NULL,
    `is_expired` char(1) NOT NULL DEFAULT 'N',
    `created_at` timestamp NOT NULL
    );

-- 컬럼 보정용
ALTER TABLE `snp_board` ADD COLUMN IF NOT EXISTS `file_uid` bigint(20) DEFAULT NULL AFTER `thumbnail_src`;