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

 Date: 14/10/2018 12:56:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for zj_alipay_config
-- ----------------------------
DROP TABLE IF EXISTS `zj_alipay_config`;
CREATE TABLE `zj_alipay_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `charset` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gatewayUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `notifyUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `privateKey` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `publicKey` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `returnUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `signType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sysServiceProviderId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_alipay_config
-- ----------------------------
INSERT INTO `zj_alipay_config` VALUES (1, '2016091700532697', 'utf-8', 'JSON', 'https://openapi.alipaydev.com/gateway.do', 'http://xiswl.xyz/aliPay/notify', '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', 'http://xiswl.xyz/aliPay/return', 'RSA2', '2088102176044281');

-- ----------------------------
-- Table structure for zj_dict
-- ----------------------------
DROP TABLE IF EXISTS `zj_dict`;
CREATE TABLE `zj_dict`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NULL DEFAULT NULL,
  `field_detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `fteld_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `field_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `table_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `updateTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_dict
-- ----------------------------
INSERT INTO `zj_dict` VALUES (1, '2018-10-05 14:04:47', '启用', 'enabled', '1', 'zj_user', '2018-10-05 14:10:53');
INSERT INTO `zj_dict` VALUES (2, '2018-10-05 14:10:45', '禁用', 'enabled', '0', 'zj_user', '2018-10-05 14:10:45');
INSERT INTO `zj_dict` VALUES (3, '2018-10-05 14:12:46', '使用ssl发送邮件', 'sslEnable', '1', 'zj_email_config', '2018-10-05 14:12:46');
INSERT INTO `zj_dict` VALUES (4, '2018-10-05 14:13:26', '不使用ssl发送邮件', 'sslEnable', '0', 'zj_email_config', '2018-10-05 14:13:26');
INSERT INTO `zj_dict` VALUES (5, '2018-10-05 14:14:09', '顶级菜单', 'pid', '0', 'zj_menu', '2018-10-05 14:14:09');
INSERT INTO `zj_dict` VALUES (6, '2018-10-05 14:15:18', '内链菜单', 'iframe', '0', 'zj_menu', '2018-10-05 14:15:18');
INSERT INTO `zj_dict` VALUES (7, '2018-10-05 14:15:39', '外链菜单', 'iframe', '1', 'zj_menu', '2018-10-05 14:15:39');
INSERT INTO `zj_dict` VALUES (8, '2018-10-05 14:16:36', '系统菜单', 'sys', '1', 'zj_menu', '2018-10-05 14:16:36');
INSERT INTO `zj_dict` VALUES (9, '2018-10-05 14:16:55', '普通菜单', 'sys', '0', 'zj_menu', '2018-10-05 14:16:55');
INSERT INTO `zj_dict` VALUES (11, '2018-10-06 12:31:03', '启动', 'status', '0', 'zj_job', '2018-10-06 12:31:03');
INSERT INTO `zj_dict` VALUES (12, '2018-10-06 12:31:19', '暂停', 'status', '1', 'zj_job', '2018-10-06 12:31:19');
INSERT INTO `zj_dict` VALUES (13, '2018-10-06 12:31:36', '成功', 'status', '0', 'zj_job_log', '2018-10-06 12:31:36');
INSERT INTO `zj_dict` VALUES (14, '2018-10-06 12:31:49', '失败', 'status', '1', 'zj_job_log', '2018-10-06 12:31:49');

-- ----------------------------
-- Table structure for zj_email_config
-- ----------------------------
DROP TABLE IF EXISTS `zj_email_config`;
CREATE TABLE `zj_email_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fromUser` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pass` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `port` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sslEnable` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_email_config
-- ----------------------------
INSERT INTO `zj_email_config` VALUES (1, 'auaur@sina.com', 'smtp.sina.com', '21BC734311FFD2BA881D34A08DAB61AE', '465', 'auaur', b'1');

-- ----------------------------
-- Table structure for zj_job
-- ----------------------------
DROP TABLE IF EXISTS `zj_job`;
CREATE TABLE `zj_job`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `baen_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `cron_expression` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `method_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updateTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_job
-- ----------------------------
INSERT INTO `zj_job` VALUES (1, 'myTaskTest', '0/5 * * * * ?', 'test', '测试', '带参数测试', '1', '2018-10-06 12:45:19');
INSERT INTO `zj_job` VALUES (2, 'myTaskTest', '0/6 * * * * ?', 'test1', '', '不带参数测试', '1', '2018-10-06 12:46:13');

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
  `iframe` bit(1) NULL DEFAULT NULL,
  `sys` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_cbhg0bi3f1emxkhqqtvca9btx`(`soft`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_menu
-- ----------------------------
INSERT INTO `zj_menu` VALUES (5, 'layui-icon-chart', 0, 1, '系统监控', 10, '', '2018-08-29 11:40:17', '2018-09-27 13:24:25', 4, b'0', b'1');
INSERT INTO `zj_menu` VALUES (6, 'layui-icon-username', 7, 2, '用户管理', 2, '/user/index', '2018-08-29 11:40:17', '2018-10-03 08:59:35', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (7, 'layui-icon-set-sm', 0, 1, '系统管理', 1, '', '2018-08-29 11:40:17', '2018-10-05 14:03:40', 6, b'0', b'1');
INSERT INTO `zj_menu` VALUES (8, 'layui-icon-template', 7, 2, '菜单管理', 5, '/menu/index', '2018-08-29 11:40:17', '2018-10-05 14:02:44', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (9, 'layui-icon-survey', 5, 2, '系统日志', 13, '/sysLog/index', '2018-08-29 11:40:17', '2018-10-03 09:04:22', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (10, 'layui-icon-password', 7, 2, '权限管理', 4, '/permission/index', '2018-08-29 11:40:17', '2018-10-03 08:59:50', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (11, 'layui-icon-top', 7, 2, '角色管理', 3, '/role/index', '2018-08-29 11:40:17', '2018-10-03 08:59:23', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (13, 'layui-icon-picture-fine', 18, 2, 'SM.MS图床', 44, '/picture/index', '2018-09-20 11:40:53', '2018-10-03 08:56:50', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (14, 'layui-icon-username', 5, 2, '在线用户', 12, '/online/index', '2018-09-21 16:09:38', '2018-10-03 09:01:22', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (16, 'layui-icon-console', 5, 2, 'Redis终端', 16, '/redis/terminal', '2018-09-22 11:18:21', '2018-10-03 09:05:11', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (17, 'layui-icon-file', 7, 2, '接口文档', 7, '/swagger/index', '2018-09-23 09:16:17', '2018-10-03 08:57:26', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (18, 'layui-icon-util', 0, 1, '第三方工具', 40, '', '2018-09-24 23:46:02', '2018-10-03 09:21:46', 5, b'0', b'1');
INSERT INTO `zj_menu` VALUES (19, 'layui-icon-code-circle', 18, 2, '百度UEditor', 45, '/ueditor/index', '2018-09-24 23:46:47', '2018-10-03 08:54:27', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (21, 'layui-icon-chart', 5, 2, 'SQL监控', 15, '/druid/index.html', '2018-09-27 13:24:25', '2018-10-03 08:57:49', 0, b'1', b'1');
INSERT INTO `zj_menu` VALUES (22, 'layui-icon-edit', 18, 2, '邮件工具', 41, '/email/index', '2018-09-28 07:32:57', '2018-10-02 16:26:01', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (24, 'layui-icon-dollar', 18, 2, '支付宝工具', 42, '/aliPay/index', '2018-09-30 15:16:43', '2018-10-02 16:27:09', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (25, 'layui-icon-upload', 18, 2, '七牛云存储', 43, '/qiNiu/index', '2018-10-02 11:03:27', '2018-10-02 16:26:42', 0, b'0', b'1');
INSERT INTO `zj_menu` VALUES (27, 'layui-icon-read', 7, 2, '字典管理', 6, '/dict/index', '2018-10-05 14:03:40', '2018-10-05 14:03:40', 0, b'0', b'0');
INSERT INTO `zj_menu` VALUES (28, 'layui-icon-log', 0, 1, '任务调度', 20, '', '2018-10-06 10:37:11', '2018-10-06 11:55:59', 2, b'0', b'0');
INSERT INTO `zj_menu` VALUES (29, 'layui-icon-radio', 28, 2, '定时任务', 21, '/job/index', '2018-10-06 10:38:06', '2018-10-06 10:38:06', 0, b'0', b'0');
INSERT INTO `zj_menu` VALUES (30, 'layui-icon-survey', 28, 2, '调度日志', 22, '/jobLog/index', '2018-10-06 11:55:59', '2018-10-06 11:55:59', 0, b'0', b'0');

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
INSERT INTO `zj_menus_roles` VALUES (5, 1);
INSERT INTO `zj_menus_roles` VALUES (6, 1);
INSERT INTO `zj_menus_roles` VALUES (7, 1);
INSERT INTO `zj_menus_roles` VALUES (8, 1);
INSERT INTO `zj_menus_roles` VALUES (9, 1);
INSERT INTO `zj_menus_roles` VALUES (10, 1);
INSERT INTO `zj_menus_roles` VALUES (11, 1);
INSERT INTO `zj_menus_roles` VALUES (13, 1);
INSERT INTO `zj_menus_roles` VALUES (14, 1);
INSERT INTO `zj_menus_roles` VALUES (16, 1);
INSERT INTO `zj_menus_roles` VALUES (17, 1);
INSERT INTO `zj_menus_roles` VALUES (18, 1);
INSERT INTO `zj_menus_roles` VALUES (19, 1);
INSERT INTO `zj_menus_roles` VALUES (21, 1);
INSERT INTO `zj_menus_roles` VALUES (22, 1);
INSERT INTO `zj_menus_roles` VALUES (24, 1);
INSERT INTO `zj_menus_roles` VALUES (25, 1);
INSERT INTO `zj_menus_roles` VALUES (27, 1);
INSERT INTO `zj_menus_roles` VALUES (28, 1);
INSERT INTO `zj_menus_roles` VALUES (29, 1);
INSERT INTO `zj_menus_roles` VALUES (30, 1);
INSERT INTO `zj_menus_roles` VALUES (5, 3);
INSERT INTO `zj_menus_roles` VALUES (6, 3);
INSERT INTO `zj_menus_roles` VALUES (7, 3);
INSERT INTO `zj_menus_roles` VALUES (8, 3);
INSERT INTO `zj_menus_roles` VALUES (9, 3);
INSERT INTO `zj_menus_roles` VALUES (10, 3);
INSERT INTO `zj_menus_roles` VALUES (11, 3);
INSERT INTO `zj_menus_roles` VALUES (13, 3);
INSERT INTO `zj_menus_roles` VALUES (14, 3);
INSERT INTO `zj_menus_roles` VALUES (16, 3);
INSERT INTO `zj_menus_roles` VALUES (17, 3);
INSERT INTO `zj_menus_roles` VALUES (18, 3);
INSERT INTO `zj_menus_roles` VALUES (19, 3);
INSERT INTO `zj_menus_roles` VALUES (21, 3);

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
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_permission
-- ----------------------------
INSERT INTO `zj_permission` VALUES (1, 'admin', '超级管理员', '2018-08-29 15:15:12', '2018-08-31 00:00:00', 0);
INSERT INTO `zj_permission` VALUES (2, 'user:select', '用户查询', '2018-08-29 15:15:12', '2018-08-31 00:00:00', 4);
INSERT INTO `zj_permission` VALUES (3, 'log:all', '日志管理', '2018-08-29 15:15:12', '2018-09-21 11:50:10', 0);
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
INSERT INTO `zj_permission` VALUES (28, 'picture:upload', '图片上传', '2018-08-29 15:15:12', '2018-10-05 13:59:21', 26);
INSERT INTO `zj_permission` VALUES (29, 'picture:delete', '图片删除', '2018-09-21 11:53:46', '2018-10-05 13:48:32', 26);
INSERT INTO `zj_permission` VALUES (30, 'user:logout', '踢出用户', '2018-09-21 16:46:59', '2018-09-21 16:46:59', 4);
INSERT INTO `zj_permission` VALUES (32, 'dict:all', '字典管理', '2018-10-05 14:00:38', '2018-10-05 14:00:38', 0);
INSERT INTO `zj_permission` VALUES (33, 'dict:select', '字典查询', '2018-10-05 14:00:59', '2018-10-05 14:00:59', 32);
INSERT INTO `zj_permission` VALUES (34, 'dict:add', '新增字典', '2018-10-05 14:01:19', '2018-10-05 14:01:19', 32);
INSERT INTO `zj_permission` VALUES (35, 'dict:update', '更新字典', '2018-10-05 14:01:33', '2018-10-05 14:01:33', 32);
INSERT INTO `zj_permission` VALUES (36, 'dict:delete', '删除字典', '2018-10-05 14:01:46', '2018-10-05 14:01:46', 32);
INSERT INTO `zj_permission` VALUES (37, 'job:all', '任务调度', '2018-10-06 12:28:06', '2018-10-06 12:28:06', 0);
INSERT INTO `zj_permission` VALUES (38, 'job:select', '任务查询', '2018-10-06 12:28:39', '2018-10-06 12:28:39', 37);
INSERT INTO `zj_permission` VALUES (39, 'job:log', '任务日志查询', '2018-10-06 12:28:59', '2018-10-06 12:29:06', 37);
INSERT INTO `zj_permission` VALUES (40, 'job:add', '新增任务', '2018-10-06 12:29:28', '2018-10-06 12:29:28', 37);
INSERT INTO `zj_permission` VALUES (41, 'job:status', '任务状态修改', '2018-10-06 12:29:45', '2018-10-06 12:29:45', 37);
INSERT INTO `zj_permission` VALUES (42, 'job:update', '更新任务', '2018-10-06 12:30:00', '2018-10-06 12:30:00', 37);
INSERT INTO `zj_permission` VALUES (43, 'job:delete', '删除任务', '2018-10-06 12:30:16', '2018-10-06 12:30:16', 37);

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
INSERT INTO `zj_permissions_roles` VALUES (3, 2);
INSERT INTO `zj_permissions_roles` VALUES (3, 3);
INSERT INTO `zj_permissions_roles` VALUES (3, 9);
INSERT INTO `zj_permissions_roles` VALUES (3, 14);
INSERT INTO `zj_permissions_roles` VALUES (3, 20);
INSERT INTO `zj_permissions_roles` VALUES (3, 23);
INSERT INTO `zj_permissions_roles` VALUES (3, 26);

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_picture
-- ----------------------------
INSERT INTO `zj_picture` VALUES (1, '2018-09-21 12:01:57', '1', '200', '8.49KB   ', 'https://i.loli.net/2018/09/21/5ba46d305018d.jpg', '200', 1, 'avatar');

-- ----------------------------
-- Table structure for zj_qiniu_config
-- ----------------------------
DROP TABLE IF EXISTS `zj_qiniu_config`;
CREATE TABLE `zj_qiniu_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accessKey` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `secretKey` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `zone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_qiniu_config
-- ----------------------------
INSERT INTO `zj_qiniu_config` VALUES (1, 'IlBRNaLvTMqkK2PDsNC4f88lvVqbvSOqq090wmoD', 'aurora', 'http://pfywktugt.bkt.clouddn.com', 'inSvt0XaIe1d_4zVcL4IjRg6V91SbO1-hkGRQJgz', '公开', '华东');

-- ----------------------------
-- Table structure for zj_qiniu_content
-- ----------------------------
DROP TABLE IF EXISTS `zj_qiniu_content`;
CREATE TABLE `zj_qiniu_content`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bucket` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `size` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `updateTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

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
INSERT INTO `zj_role` VALUES (1, '2018-08-23 09:13:54', '超级管理员', '系统所有权', '2018-08-23 09:14:02');
INSERT INTO `zj_role` VALUES (3, '2018-09-03 15:17:31', '测试', '用于测试', '2018-10-05 13:48:43');

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
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of zj_user
-- ----------------------------
INSERT INTO `zj_user` VALUES (1, 'https://www.zhengjie.me/images/avatar.jpg', '2018-08-23 09:11:56', 'zhengjie@tom.com', 1, '2018-10-14 12:50:54', '36318d4cc46eb68050b8b81ebc620f05', 'aurora');
INSERT INTO `zj_user` VALUES (2, 'https://i.loli.net/2018/08/16/5b75166b3157a.jpg', '2018-10-03 09:16:20', 'everyone@aurora.xyz', 1, '2018-10-05 11:29:44', '36318d4cc46eb68050b8b81ebc620f05', 'everyone');

-- ----------------------------
-- Table structure for zj_user_pictures
-- ----------------------------
DROP TABLE IF EXISTS `zj_user_pictures`;
CREATE TABLE `zj_user_pictures`  (
  `User_id` bigint(20) NOT NULL,
  `pictures_id` bigint(20) NOT NULL,
  PRIMARY KEY (`User_id`, `pictures_id`) USING BTREE,
  UNIQUE INDEX `UK_su439svglheh4cpcy3h15de8`(`pictures_id`) USING BTREE,
  CONSTRAINT `FKk0qsq0yn0r0644b8p0gw9tkh9` FOREIGN KEY (`User_id`) REFERENCES `zj_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKqiip97le6392cqwv77u3a71i` FOREIGN KEY (`pictures_id`) REFERENCES `zj_picture` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
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
INSERT INTO `zj_users_roles` VALUES (2, 3);

SET FOREIGN_KEY_CHECKS = 1;
