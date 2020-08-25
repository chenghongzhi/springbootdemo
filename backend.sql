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

 Date: 25/08/2020 18:24:16
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
  `realname` varchar(25) NOT NULL,
  `state` int(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (9, 'admin', 'df655ad8d3229f3269fad2a8bab59b6c', '2019-05-08 05:05:48', 3, '', 0);
INSERT INTO `user` VALUES (16, 'teacher_test', 'aeebafe82b60adda77d291565a82b5f5', '2020-05-16 12:46:12', 1, 'test', 0);
INSERT INTO `user` VALUES (17, 'student', 'd82aabcf4031f248a47fb151bce63b00', '2020-05-16 12:47:11', 2, 'student', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
