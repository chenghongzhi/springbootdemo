/*
 Navicat Premium Data Transfer

 Source Server         : 阿里云
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 120.78.185.50:3306
 Source Schema         : backend

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 09/05/2019 17:03:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of permission
-- ----------------------------
BEGIN;
INSERT INTO `permission` VALUES (1, '用户', 'user', 0);
INSERT INTO `permission` VALUES (2, '实验报告', 'report', 0);
INSERT INTO `permission` VALUES (3, '系统', 'system', 0);
INSERT INTO `permission` VALUES (4, '用户增加', 'user:add', 1);
INSERT INTO `permission` VALUES (5, '用户修改', 'user:edit', 1);
INSERT INTO `permission` VALUES (6, '用户删除', 'user:delete', 1);
INSERT INTO `permission` VALUES (7, '用户查询', 'user:list', 1);
INSERT INTO `permission` VALUES (8, '报告增加', 'report:add', 2);
INSERT INTO `permission` VALUES (9, '报告修改', 'report:edit', 2);
INSERT INTO `permission` VALUES (10, '报告删除', 'report:delete', 2);
INSERT INTO `permission` VALUES (11, '报告查询', 'report:list', 2);
INSERT INTO `permission` VALUES (12, '报告审核', 'report:check', 2);
COMMIT;

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `id` int(25) NOT NULL AUTO_INCREMENT,
  `fileurl` varchar(255) NOT NULL,
  `filepath` varchar(255) NOT NULL,
  `in_time` datetime NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `teacher_name` varchar(25) DEFAULT NULL,
  `student_name` varchar(25) NOT NULL,
  `code` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report
-- ----------------------------
BEGIN;
INSERT INTO `report` VALUES (3, 'localhost:8080/static/upload/实验5、磁盘管理.docx', '/Users/chenghongzhi/GitHub/demo/src/main/resources/static/upload/实验5、磁盘管理-1557298360107.docx', '2019-05-08 10:18:37', '123456					', '11', 'admin', 'admin', '', '已查看');
INSERT INTO `report` VALUES (4, 'localhost:8080/static/upload/实验5、磁盘管理-1557298627557.docx', '/Users/chenghongzhi/GitHub/demo/src/main/resources/static/upload/实验5、磁盘管理-1557298627557.docx', '2019-05-08 08:21:17', '113', '', 'admin', 'admin', '', '未查看');
INSERT INTO `report` VALUES (5, '/report/download/0b607ed717684029a3f132af5f7fb474', '/Users/chenghongzhi/GitHub/demo/src/main/resources/static/upload/实验5、磁盘管理-1557304734561.docx', '2019-05-08 08:50:33', '作业提交', '好的', 'admin', 'test', '0b607ed717684029a3f132af5f7fb474', '未查看');
INSERT INTO `report` VALUES (6, '/report/download/c51816371fd4468198a5be1b07261e25', '/Users/chenghongzhi/GitHub/demo/src/main/resources/static/upload/实验5、磁盘管理-1557309714258.docx', '2019-05-08 10:05:02', '', '好的', 'teacher', 'test', 'c51816371fd4468198a5be1b07261e25', '未查看');
COMMIT;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(25) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, '老师');
INSERT INTO `role` VALUES (2, '学生');
INSERT INTO `role` VALUES (3, '管理员');
COMMIT;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
BEGIN;
INSERT INTO `role_permission` VALUES (1, 11);
INSERT INTO `role_permission` VALUES (2, 8);
INSERT INTO `role_permission` VALUES (2, 9);
INSERT INTO `role_permission` VALUES (2, 11);
INSERT INTO `role_permission` VALUES (3, 4);
INSERT INTO `role_permission` VALUES (3, 5);
INSERT INTO `role_permission` VALUES (3, 6);
INSERT INTO `role_permission` VALUES (3, 7);
INSERT INTO `role_permission` VALUES (3, 8);
INSERT INTO `role_permission` VALUES (3, 9);
INSERT INTO `role_permission` VALUES (3, 10);
INSERT INTO `role_permission` VALUES (3, 11);
INSERT INTO `role_permission` VALUES (3, 12);
INSERT INTO `role_permission` VALUES (1, 12);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(25) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(255) NOT NULL,
  `in_time` timestamp NULL DEFAULT NULL,
  `role_id` int(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (9, 'admin', 'df655ad8d3229f3269fad2a8bab59b6c', '2019-05-08 05:05:48', 3);
INSERT INTO `user` VALUES (14, 'test', 'cbce3bcfb2e685657f30b435a5503190', '2019-05-08 05:48:14', 2);
INSERT INTO `user` VALUES (15, 'teacher', 'd77953eb641804921d0cd1e705a1e29f', '2019-05-08 09:58:48', 1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
