/*
 Navicat Premium Data Transfer

 Source Server         : wsd
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : security

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 06/05/2023 09:44:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for clazz
-- ----------------------------
DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `className` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `classSize` int NOT NULL,
  `headTeacherName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `headTeacherEmail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `headTeacherPhone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `grade` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `classDescription` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of clazz
-- ----------------------------
INSERT INTO `clazz` VALUES (1, '一年级一班', 46, '小吴', 'xiaowu@qq.com', '13487654321', '一年级', '小陈的一年级一班多次获得学科竞赛奖项');
INSERT INTO `clazz` VALUES (2, '一年级二班', 34, '小周', 'xiaozhou@qq.com', '18198761234', '一年级', '小王的一年级二班学科全面');
INSERT INTO `clazz` VALUES (3, '二年级一班', 32, '小徐', 'xiaoxu@qq.com', '13387654321', '二年级', '小李的二年级一班高考成绩优异');
INSERT INTO `clazz` VALUES (4, '二年级二班', 30, '小蔡', 'xiaocai@qq.com', '18098761234', '二年级', '小张的二年级二班英语竞赛冠军');
INSERT INTO `clazz` VALUES (5, '三年级一班', 28, '小陈', 'xiaochen@qq.com', '13287654321', '三年级', '小王的三年级一班备战高考');
INSERT INTO `clazz` VALUES (6, '三年级二班', 26, '小王', 'xiaowang@qq.com', '17998761234', '三年级', '小刘的三年级二班全面复习');
INSERT INTO `clazz` VALUES (7, '四年级一班', 30, '小飞', 'xiaofei@qq.com', '17629049261', '四年级', '小赵的四年级一班真好');
INSERT INTO `clazz` VALUES (8, '四年级二班', 28, '小明', 'xiaoming@qq.com', '13987654321', '四年级', '小李的四年级二班学科全面');
INSERT INTO `clazz` VALUES (9, '四年级三班', 32, '小红', 'xiaohong@qq.com', '18698761234', '四年级', '小张的四年级三班英语竞赛冠军');
INSERT INTO `clazz` VALUES (10, '四年级四班', 26, '小刚', 'xiaogang@qq.com', '13587654321', '四年级', '小王的四年级四班备战期末考试');
INSERT INTO `clazz` VALUES (11, '五年级一班', 30, '小翔', 'xiaoxiang@qq.com', '18198761234', '五年级', '小陈的五年级一班高考成绩优异');
INSERT INTO `clazz` VALUES (12, '五年级二班', 32, '小雨', 'xiaoyu@qq.com', '13687654321', '五年级', '小赵的五年级二班多次获得学科竞赛奖项');
INSERT INTO `clazz` VALUES (13, '五年级三班', 34, '小露', 'xiaolu@qq.com', '18298761234', '五年级', '小王的五年级三班学科全面');
INSERT INTO `clazz` VALUES (14, '五年级四班', 28, '小云', 'xiaoyun@qq.com', '13787654321', '五年级', '小刘的五年级四班全面复习');
INSERT INTO `clazz` VALUES (15, '六年级一班', 26, '小杨', 'xiaoyang@qq.com', '18398761234', '六年级', '小张的六年级一班英语竞赛冠军');
INSERT INTO `clazz` VALUES (16, '六年级二班', 30, '小吕', 'xiaolv@qq.com', '13887654321', '六年级', '小王的六年级二班备战期末考试');

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `gradeId` int NOT NULL AUTO_INCREMENT COMMENT '班级id',
  `gradeName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '年级名称',
  `gradeDirector` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '年级主任',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `gradeDescription` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '年级描述',
  PRIMARY KEY (`gradeId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (2, '六年级', '小王', 'xiaowang@email.com', '13922223333', '二年级这个班的孩子们乐于助人');
INSERT INTO `grade` VALUES (3, '三年级', '小李', 'xiaoli@email.com', '13733334444', '三年级这个班的孩子们非常勤奋');
INSERT INTO `grade` VALUES (4, '四年级', '小刘', 'xiaoliu@email.com', '13644445555', '四年级这个班的孩子们团结友爱');
INSERT INTO `grade` VALUES (5, '五年级', '小陈', 'xiaochen@email.com', '13555556666', '五年级这个班的孩子们兴趣广泛');
INSERT INTO `grade` VALUES (6, '六年级', '小赵', 'xiaozhao@email.com', '13466667777', '六年级这个班的孩子们成绩优异');
INSERT INTO `grade` VALUES (7, '一年级', '小孙', 'xiaosun@email.com', '13377778888', '一年级这个班的孩子们热爱运动');
INSERT INTO `grade` VALUES (8, '二年级', '小钱', 'xiaoqian@email.com', '13288889999', '二年级这个班的孩子们积极参与社会实践');
INSERT INTO `grade` VALUES (9, '三年级', '小周', 'xiaozhou@email.com', '13199990000', '三年级这个班的孩子们有很强的团队合作能力');
INSERT INTO `grade` VALUES (10, '四年级', '小吴', 'xiaowu@email.com', '13000001111', '四年级这个班的孩子们热衷公益事业');
INSERT INTO `grade` VALUES (11, '五年级', '小郑', 'xiaozheng@email.com', '13911112222', '五年级这个班的孩子们善于表达');
INSERT INTO `grade` VALUES (12, '六年级', '小王', 'xiaowang@email.com', '13822223333', '六年级这个班的孩子们具有创新精神');
INSERT INTO `grade` VALUES (13, '一年级', '小李', 'xiaoli@email.com', '13733334444', '一年级这个班的孩子们乐于助人');
INSERT INTO `grade` VALUES (14, '二年级', '小赵', 'xiaozhao@email.com', '13644445555', '二年级这个班的孩子们团结友爱');
INSERT INTO `grade` VALUES (15, '三年级', '小陈', 'xiaochen@email.com', '13555556666', '三年级这个班的孩子们兴趣广泛');
INSERT INTO `grade` VALUES (16, '四年级', '小孙', 'xiaosun@email.com', '13466667777', '四年级这个班的孩子们成绩优异');
INSERT INTO `grade` VALUES (17, '五年级', '小钱', 'xiaoqian@email.com', '13377778888', '五年级这个班的孩子们热爱运动');
INSERT INTO `grade` VALUES (29, '二年级', '小小李', 'xiaaoli2@qq.com', '17629049261', 'aaaaaaaaa');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int NULL DEFAULT NULL,
  `pattern` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '/admin/**');
INSERT INTO `menu` VALUES (2, '/teacher/**');
INSERT INTO `menu` VALUES (3, '/user/**');

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role`  (
  `id` int NULL DEFAULT NULL,
  `mid` int NULL DEFAULT NULL,
  `rid` int NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_role
-- ----------------------------
INSERT INTO `menu_role` VALUES (1, 1, 1);
INSERT INTO `menu_role` VALUES (2, 2, 1);
INSERT INTO `menu_role` VALUES (3, 3, 1);
INSERT INTO `menu_role` VALUES (4, 2, 2);
INSERT INTO `menu_role` VALUES (5, 3, 2);
INSERT INTO `menu_role` VALUES (6, 3, 3);

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins`  (
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `series` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
INSERT INTO `persistent_logins` VALUES ('admin', '/t1g0jYWDoSZmdfWTonwGw==', 'nnu+05qzATTMSU++wRWUVQ==', '2023-05-05 20:07:41');
INSERT INTO `persistent_logins` VALUES ('admin', '0eVhKy4PQeaTfgVJIpoq7Q==', 'GGmKHGIqplvkmoUMSNmLRQ==', '2023-05-05 15:25:18');
INSERT INTO `persistent_logins` VALUES ('admin', '7aXlHp2OtlNtMHgh0hY2Kw==', 'D24MycnXBuZIC9imx17B2A==', '2023-05-05 19:55:11');
INSERT INTO `persistent_logins` VALUES ('admin', '9wnEXM8THuidhEbW0L3LrQ==', '2wQ50CKOFYIO/ImwL0gDkw==', '2023-05-05 19:57:16');
INSERT INTO `persistent_logins` VALUES ('admin', 'ABM/xRtm+ifnImNEDN3HLg==', '6LNdOtLfTW9WqJ5p9duKIQ==', '2023-05-05 17:16:49');
INSERT INTO `persistent_logins` VALUES ('admin', 'Bc0rG6LL7Ht+mmiBlik5kg==', 'Mgi/DPmyWiOY/NQeVWLCtA==', '2023-05-05 20:09:33');
INSERT INTO `persistent_logins` VALUES ('admin', 'dv15y41wu1El35RUpNZCRQ==', '5Hsjr0+Y7cwAxilOlpdKEw==', '2023-05-05 19:58:40');
INSERT INTO `persistent_logins` VALUES ('admin', 'FLQCsUsJU/rxd4iq5UaI9w==', 'mkj5vZHnz5VP9rBxKV5HvA==', '2023-05-05 17:20:04');
INSERT INTO `persistent_logins` VALUES ('admin', 'LDFLAtZJAwEKZFyF1sJCLw==', 'gCvVZHbodr7QkeQpPT9RlQ==', '2023-05-05 17:28:42');
INSERT INTO `persistent_logins` VALUES ('admin', 'OKtJME5gQaMJ6mctcXx+3g==', '1uHBjtIftIVxdcjyUd9Vxw==', '2023-05-05 17:31:19');
INSERT INTO `persistent_logins` VALUES ('admin', 'vl7qdfZbGIkB9X0poo0Jhg==', 'QTIAkRm7FvqnoZdmFSACyg==', '2023-05-05 17:33:09');
INSERT INTO `persistent_logins` VALUES ('admin', 'xdK0zkqpUtMkVz5cegIaHA==', 'pqcsJRyrrk82d6nTiCf5jw==', '2023-05-05 19:49:50');
INSERT INTO `persistent_logins` VALUES ('admin', 'ZiTq5C0tINHsdMdeF9HoHQ==', 'JSfvs5mo5GpKOJzsAJRvkg==', '2023-05-05 17:25:18');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `name_zh` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色中文名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_ADMIN', '系统管理员');
INSERT INTO `role` VALUES (2, 'ROLE_TEACHER', '教职工');
INSERT INTO `role` VALUES (3, 'ROLE_USER', '普通用户');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '{noop}123456' COMMENT '密码',
  `noEnabled` int NULL DEFAULT 1 COMMENT '是否启用',
  `noAccountNonExpired` int NULL DEFAULT 1 COMMENT '账户是否过期',
  `noAccountNonLocked` int NULL DEFAULT 1 COMMENT '账户是否锁定',
  `noCredentialsNonExpired` int NULL DEFAULT 1 COMMENT '凭证是否过期',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `email` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '^_^' COMMENT '头像',
  `userType` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '{bcrypt}$2a$10$b8mE301cfdi384O69bjs2u2XIt0kTNd40txnPEkNGnlCb432sp54e', 1, 1, 1, 1, '男', '1234577689@qq.com', '17629049261', '陕西省渭南市前进路中学', '^_^', '1');
INSERT INTO `user` VALUES (2, 'teacher', '{bcrypt}$2a$10$/sXUFyMkSMOmdQCiN0hyF.bjW/zE2lm3C9zAFKI8vfG/6XO1Bkk6u', 1, 1, 1, 1, '男', '1234577689@qq.com', '17629049261', '陕西省渭南市前进路中学', '^_^', '2');
INSERT INTO `user` VALUES (3, 'coder-itl', '{bcrypt}$2a$10$hARmjZCV5sATDv5YwesLNesRB.EC1hVWLkwOeetf31qGKrcnSaGlK', 1, 1, 1, 1, '男', '1234577689@qq.com', '17629049261', '陕西省渭南市前进路中学', '^_^', '3');
INSERT INTO `user` VALUES (4, 'test', '{noop}test', 1, 1, 1, 1, '女', '1234577689@qq.com', '17629049261', '陕西省渭南市前进路中学', '^_^', '1');
INSERT INTO `user` VALUES (5, 'madongmei', '{noop}madongmei', 1, 1, 1, 1, '女', '1234577689@qq.com', '17629049261', '陕西省渭南市前进路中学', '^_^', '2');
INSERT INTO `user` VALUES (6, 'mabaoguo', '{noop}mabaoguo', 1, 1, 1, 1, '女', '1234577689@qq.com', '17629049261', '陕西省渭南市前进路中学', '^_^', '3');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NULL DEFAULT NULL,
  `rid` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `uid`(`uid` ASC) USING BTREE,
  INDEX `rid`(`rid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1);
INSERT INTO `user_role` VALUES (2, 1, 2);
INSERT INTO `user_role` VALUES (3, 1, 3);
INSERT INTO `user_role` VALUES (4, 2, 2);
INSERT INTO `user_role` VALUES (5, 2, 3);
INSERT INTO `user_role` VALUES (6, 3, 3);

SET FOREIGN_KEY_CHECKS = 1;
