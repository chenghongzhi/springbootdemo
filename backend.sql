/*
 Navicat Premium Data Transfer

 Source Server         : mac
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : backend

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 27/05/2019 15:24:21
*/

SET NAMES utf8;
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
  `in_time` datetime NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `teacher_name` varchar(25) DEFAULT NULL,
  `student_name` varchar(25) NOT NULL,
  `status` varchar(255) NOT NULL,
  `student_id` int(25) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(25) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES (1, '指导老师');
INSERT INTO `role` VALUES (2, '学生');
INSERT INTO `role` VALUES (3, '答辩组组长');
INSERT INTO `role` VALUES (4, '管理员');
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
INSERT INTO `role_permission` VALUES (4, 4);
INSERT INTO `role_permission` VALUES (4, 5);
INSERT INTO `role_permission` VALUES (4, 6);
INSERT INTO `role_permission` VALUES (4, 7);
INSERT INTO `role_permission` VALUES (4, 8);
INSERT INTO `role_permission` VALUES (4, 9);
INSERT INTO `role_permission` VALUES (4, 10);
INSERT INTO `role_permission` VALUES (4, 11);
INSERT INTO `role_permission` VALUES (4, 12);
INSERT INTO `role_permission` VALUES (1, 12);
COMMIT;

-- ----------------------------
-- Table structure for student_report
-- ----------------------------
DROP TABLE IF EXISTS `student_report`;
CREATE TABLE `student_report` (
  `report_id` int(25) NOT NULL,
  `student_id` int(255) NOT NULL,
  `fileurl` varchar(255) NOT NULL,
  `filepath` varchar(255) NOT NULL,
  `in_time` datetime NOT NULL,
  `code` varchar(255) NOT NULL,
  `report_type` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for teacher_student
-- ----------------------------
DROP TABLE IF EXISTS `teacher_student`;
CREATE TABLE `teacher_student` (
  `teacher_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(25) NOT NULL AUTO_INCREMENT,
  `username` varchar(25) NOT NULL,
  `password` varchar(255) NOT NULL,
  `in_time` timestamp NULL DEFAULT NULL,
  `role_id` int(25) NOT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (28, 'admin', 'df655ad8d3229f3269fad2a8bab59b6c', '2019-05-26 08:55:28', 4, '管理员');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
