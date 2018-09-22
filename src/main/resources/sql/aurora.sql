/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50559
 Source Host           : localhost:3306
 Source Schema         : aurora

 Target Server Type    : MySQL
 Target Server Version : 50559
 File Encoding         : 65001

 Date: 22/09/2018 13:06:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for zj_menu
-- ----------------------------
DROP TABLE IF EXISTS `zj_menu`;
CREATE TABLE `zj_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ico` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `soft` bigint(20) NOT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createTime` datetime NULL DEFAULT NULL,
  `updateTime` datetime NULL DEFAULT NULL,
  `level_number` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_cbhg0bi3f1emxkhqqtvca9btx`(`soft`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_menu
-- ----------------------------
INSERT INTO `zj_menu` VALUES (1, 'layui-icon-unlink', 0, 1, '异常页', 40, '', '2018-08-29 11:40:17', '2018-09-20 11:44:06', 3);
INSERT INTO `zj_menu` VALUES (2, 'layui-icon-tree', 1, 2, '403', 41, '/exception/403', '2018-08-29 11:40:17', '2018-09-20 11:36:24', 0);
INSERT INTO `zj_menu` VALUES (3, 'layui-icon-tree', 1, 2, '404', 42, '/exception/404', '2018-08-29 11:40:17', '2018-09-20 11:36:32', 0);
INSERT INTO `zj_menu` VALUES (4, 'layui-icon-tree', 1, 2, '500', 43, '/exception/500', '2018-08-29 11:40:17', '2018-09-20 11:36:40', 0);
INSERT INTO `zj_menu` VALUES (5, 'layui-icon-chart', 0, 1, '系统监控', 10, '', '2018-08-29 11:40:17', '2018-09-22 11:18:21', 4);
INSERT INTO `zj_menu` VALUES (6, 'layui-icon-tree', 7, 2, '用户管理', 2, '/user/index', '2018-08-29 11:40:17', '2018-09-04 07:40:25', 0);
INSERT INTO `zj_menu` VALUES (7, 'layui-icon-util', 0, 1, '系统管理', 1, '', '2018-08-29 11:40:17', '2018-09-20 19:02:05', 4);
INSERT INTO `zj_menu` VALUES (8, 'layui-icon-tree', 7, 2, '菜单管理', 6, '/menu/index', '2018-08-29 11:40:17', '2018-09-04 07:40:25', 0);
INSERT INTO `zj_menu` VALUES (9, 'layui-icon-tree', 5, 2, '系统日志', 11, '/sysLog/index', '2018-08-29 11:40:17', '2018-09-22 10:17:02', 0);
INSERT INTO `zj_menu` VALUES (10, 'layui-icon-tree', 7, 2, '权限管理', 4, '/permission/index', '2018-08-29 11:40:17', '2018-09-04 07:40:25', 0);
INSERT INTO `zj_menu` VALUES (11, 'layui-icon-tree', 7, 2, '角色管理', 3, '/role/index', '2018-08-29 11:40:17', '2018-09-04 07:40:25', 0);
INSERT INTO `zj_menu` VALUES (12, 'layui-icon-website', 0, 1, '网络资源', 30, '', '2018-09-20 11:35:56', '2018-09-20 11:40:53', 1);
INSERT INTO `zj_menu` VALUES (13, 'layui-icon-tree', 12, 2, '图床管理', 31, '/picture/index', '2018-09-20 11:40:53', '2018-09-20 11:40:53', 0);
INSERT INTO `zj_menu` VALUES (14, 'layui-icon-tree', 5, 2, '在线用户', 12, '/online/index', '2018-09-21 16:09:38', '2018-09-22 10:17:09', 0);
INSERT INTO `zj_menu` VALUES (16, 'layui-icon-tree', 5, 2, 'Redis终端', 14, '/redis/terminal', '2018-09-22 11:18:21', '2018-09-22 11:37:28', 0);

-- ----------------------------
-- Table structure for zj_menus_roles
-- ----------------------------
DROP TABLE IF EXISTS `zj_menus_roles`;
CREATE TABLE `zj_menus_roles`  (
  `menu_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`menu_id`, `role_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `zj_menus_roles_ibfk_1` FOREIGN KEY (`menu_id`) REFERENCES `zj_menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `zj_menus_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `zj_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_menus_roles
-- ----------------------------
INSERT INTO `zj_menus_roles` VALUES (1, 1);
INSERT INTO `zj_menus_roles` VALUES (2, 1);
INSERT INTO `zj_menus_roles` VALUES (3, 1);
INSERT INTO `zj_menus_roles` VALUES (4, 1);
INSERT INTO `zj_menus_roles` VALUES (5, 1);
INSERT INTO `zj_menus_roles` VALUES (6, 1);
INSERT INTO `zj_menus_roles` VALUES (7, 1);
INSERT INTO `zj_menus_roles` VALUES (8, 1);
INSERT INTO `zj_menus_roles` VALUES (9, 1);
INSERT INTO `zj_menus_roles` VALUES (10, 1);
INSERT INTO `zj_menus_roles` VALUES (11, 1);
INSERT INTO `zj_menus_roles` VALUES (12, 1);
INSERT INTO `zj_menus_roles` VALUES (13, 1);
INSERT INTO `zj_menus_roles` VALUES (14, 1);
INSERT INTO `zj_menus_roles` VALUES (16, 1);
INSERT INTO `zj_menus_roles` VALUES (1, 2);
INSERT INTO `zj_menus_roles` VALUES (2, 2);
INSERT INTO `zj_menus_roles` VALUES (3, 2);
INSERT INTO `zj_menus_roles` VALUES (4, 2);
INSERT INTO `zj_menus_roles` VALUES (5, 2);
INSERT INTO `zj_menus_roles` VALUES (6, 2);
INSERT INTO `zj_menus_roles` VALUES (7, 2);
INSERT INTO `zj_menus_roles` VALUES (8, 2);
INSERT INTO `zj_menus_roles` VALUES (9, 2);
INSERT INTO `zj_menus_roles` VALUES (10, 2);
INSERT INTO `zj_menus_roles` VALUES (11, 2);
INSERT INTO `zj_menus_roles` VALUES (5, 3);
INSERT INTO `zj_menus_roles` VALUES (7, 3);
INSERT INTO `zj_menus_roles` VALUES (9, 3);
INSERT INTO `zj_menus_roles` VALUES (11, 3);
INSERT INTO `zj_menus_roles` VALUES (12, 3);
INSERT INTO `zj_menus_roles` VALUES (13, 3);

-- ----------------------------
-- Table structure for zj_permission
-- ----------------------------
DROP TABLE IF EXISTS `zj_permission`;
CREATE TABLE `zj_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `perms` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createTime` datetime NULL DEFAULT NULL,
  `updateTime` datetime NULL DEFAULT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_djtxn2vldlgrkfk21d155b48i`(`perms`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_permission
-- ----------------------------
INSERT INTO `zj_permission` VALUES (1, 'admin', '超级管理员', '2018-08-29 15:15:12', '2018-08-31 00:00:00', 0);
INSERT INTO `zj_permission` VALUES (2, 'user:select', '用户查询', '2018-08-29 15:15:12', '2018-08-31 00:00:00', 4);
INSERT INTO `zj_permission` VALUES (3, 'log:all', '日志管理', NULL, '2018-09-21 11:50:10', 0);
INSERT INTO `zj_permission` VALUES (4, 'user:all', '用户管理', '2018-08-29 15:15:12', '2018-09-02 23:11:31', 0);
INSERT INTO `zj_permission` VALUES (5, 'user:add', '新增用户', '2018-08-31 15:15:12', '2018-08-31 00:00:00', 4);
INSERT INTO `zj_permission` VALUES (6, 'user:update', '更新用户', '2018-08-31 15:15:12', '2018-08-31 00:00:00', 4);
INSERT INTO `zj_permission` VALUES (7, 'user:lock', '禁用用户', '2018-08-29 15:15:12', '2018-09-02 12:36:31', 4);
INSERT INTO `zj_permission` VALUES (8, 'permission:all', '权限管理', '2018-08-29 15:15:12', '2018-09-02 23:11:04', 0);
INSERT INTO `zj_permission` VALUES (9, 'permission:select', '权限查询', '2018-08-31 14:03:40', '2018-08-31 14:03:40', 8);
INSERT INTO `zj_permission` VALUES (10, 'permission:add', '权限新增', '2018-08-31 14:03:52', '2018-08-31 14:03:52', 8);
INSERT INTO `zj_permission` VALUES (11, 'permission:update', '权限更新', '2018-08-31 14:04:05', '2018-08-31 14:04:05', 8);
INSERT INTO `zj_permission` VALUES (13, 'role:all', '角色管理', '2018-08-29 15:15:12', '2018-09-02 23:10:58', 0);
INSERT INTO `zj_permission` VALUES (14, 'role:select', '角色查询', '2018-08-31 23:19:34', '2018-08-31 23:19:34', 13);
INSERT INTO `zj_permission` VALUES (15, 'role:add', '角色新增', '2018-08-31 23:20:09', '2018-08-31 23:20:09', 13);
INSERT INTO `zj_permission` VALUES (16, 'role:update', '角色更新', '2018-08-31 23:20:25', '2018-08-31 23:20:25', 13);
INSERT INTO `zj_permission` VALUES (17, 'role:delete', '角色删除', '2018-08-31 23:20:40', '2018-08-31 23:20:40', 13);
INSERT INTO `zj_permission` VALUES (19, 'menu:all', '菜单管理', '2018-09-12 13:11:27', '2018-09-12 13:11:27', 0);
INSERT INTO `zj_permission` VALUES (20, 'menu:add', '新增菜单', '2018-09-12 13:11:58', '2018-09-12 13:11:58', 19);
INSERT INTO `zj_permission` VALUES (21, 'menu:update', '更新菜单', '2018-09-12 13:12:21', '2018-09-12 13:12:21', 19);
INSERT INTO `zj_permission` VALUES (22, 'menu:delete', '删除菜单', '2018-09-12 13:12:43', '2018-09-12 13:12:43', 19);
INSERT INTO `zj_permission` VALUES (23, 'menu:select', '菜单搜索', '2018-09-12 13:15:02', '2018-09-12 13:15:02', 19);
INSERT INTO `zj_permission` VALUES (24, 'permission:delete', '删除权限', '2018-09-21 11:51:13', '2018-09-21 11:51:13', 8);
INSERT INTO `zj_permission` VALUES (25, 'user:delete', '删除用户', '2018-09-21 11:51:41', '2018-09-21 11:51:41', 4);
INSERT INTO `zj_permission` VALUES (26, 'picture:all', '图床管理', '2018-09-21 11:52:10', '2018-09-21 11:52:10', 0);
INSERT INTO `zj_permission` VALUES (27, 'picture:select', '图片查询', '2018-09-21 11:52:25', '2018-09-21 11:52:25', 26);
INSERT INTO `zj_permission` VALUES (28, 'picture:upload', '图片上传', NULL, '2018-09-21 11:53:30', 26);
INSERT INTO `zj_permission` VALUES (29, 'picture:delete', '图片删除', '2018-09-21 11:53:46', '2018-09-21 11:53:46', 26);
INSERT INTO `zj_permission` VALUES (30, 'user:logout', '踢出用户', '2018-09-21 16:46:59', '2018-09-21 16:46:59', 4);

-- ----------------------------
-- Table structure for zj_permissions_roles
-- ----------------------------
DROP TABLE IF EXISTS `zj_permissions_roles`;
CREATE TABLE `zj_permissions_roles`  (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE,
  INDEX `FKoj69ypls2picwdvsx1n0j0rp6`(`permission_id`) USING BTREE,
  CONSTRAINT `FKcdlfc70vro7wbnajmn50wix7w` FOREIGN KEY (`role_id`) REFERENCES `zj_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKoj69ypls2picwdvsx1n0j0rp6` FOREIGN KEY (`permission_id`) REFERENCES `zj_permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_permissions_roles
-- ----------------------------
INSERT INTO `zj_permissions_roles` VALUES (1, 1);
INSERT INTO `zj_permissions_roles` VALUES (2, 2);
INSERT INTO `zj_permissions_roles` VALUES (2, 3);
INSERT INTO `zj_permissions_roles` VALUES (3, 4);
INSERT INTO `zj_permissions_roles` VALUES (3, 8);
INSERT INTO `zj_permissions_roles` VALUES (2, 9);
INSERT INTO `zj_permissions_roles` VALUES (2, 14);

-- ----------------------------
-- Table structure for zj_picture
-- ----------------------------
DROP TABLE IF EXISTS `zj_picture`;
CREATE TABLE `zj_picture`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NULL DEFAULT NULL,
  `deleteUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `height` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `width` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FK5qtt7wql9517j0e2read2pdus`(`user_id`) USING BTREE,
  CONSTRAINT `FK5qtt7wql9517j0e2read2pdus` FOREIGN KEY (`user_id`) REFERENCES `zj_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_picture
-- ----------------------------
INSERT INTO `zj_picture` VALUES (11, '2018-09-21 12:01:57', '1', '200', '8.49KB   ', 'https://i.loli.net/2018/09/21/5ba46d305018d.jpg', '200', 1, 'avatar');
INSERT INTO `zj_picture` VALUES (12, '2018-09-21 15:15:21', 'https://sm.ms/delete/lLAmbyjGnr5W4zo', '200', '4.50KB   ', 'https://i.loli.net/2018/09/21/5ba49a8975588.jpg', '200', 1, '1');

-- ----------------------------
-- Table structure for zj_role
-- ----------------------------
DROP TABLE IF EXISTS `zj_role`;
CREATE TABLE `zj_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createDateTime` datetime NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updateDateTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_role
-- ----------------------------
INSERT INTO `zj_role` VALUES (1, '2018-08-23 09:13:54', '超级管理员', NULL, '2018-08-23 09:14:02');
INSERT INTO `zj_role` VALUES (2, '2018-08-27 19:31:03', '普通用户', '', '2018-09-01 08:31:08');
INSERT INTO `zj_role` VALUES (3, '2018-09-03 15:17:31', '测试', '1', '2018-09-20 17:28:03');

-- ----------------------------
-- Table structure for zj_user
-- ----------------------------
DROP TABLE IF EXISTS `zj_user`;
CREATE TABLE `zj_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `createDateTime` datetime NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `enabled` bigint(20) NULL DEFAULT NULL,
  `lastLoginTime` datetime NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_kpubos9gc2cvtkb0thktkbkes`(`email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_user
-- ----------------------------
INSERT INTO `zj_user` VALUES (1, 'https://www.zhengjie.me/images/avatar.jpg', '2018-08-23 09:11:56', 'zhengjie@tom.com', 1, '2018-09-22 13:00:04', '65a674ce6632479005ea7a9071234cfb', 'aurora');

-- ----------------------------
-- Table structure for zj_user_pictures
-- ----------------------------
DROP TABLE IF EXISTS `zj_user_pictures`;
CREATE TABLE `zj_user_pictures`  (
  `User_id` bigint(20) NOT NULL,
  `pictures_id` bigint(20) NOT NULL,
  PRIMARY KEY (`User_id`, `pictures_id`) USING BTREE,
  UNIQUE INDEX `UK_su439svglheh4cpcy3h15de8`(`pictures_id`) USING BTREE,
  CONSTRAINT `FK11v8vnaup1q9xvsb66xjw7qjs` FOREIGN KEY (`pictures_id`) REFERENCES `zj_picture` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKk0qsq0yn0r0644b8p0gw9tkh9` FOREIGN KEY (`User_id`) REFERENCES `zj_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for zj_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `zj_users_roles`;
CREATE TABLE `zj_users_roles`  (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  CONSTRAINT `zj_users_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `zj_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `zj_users_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `zj_role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_users_roles
-- ----------------------------
INSERT INTO `zj_users_roles` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
