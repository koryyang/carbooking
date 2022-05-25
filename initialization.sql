/*
 Navicat Premium Data Transfer

 Source Server         : 本机
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : car_booking

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 25/05/2022 10:59:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_car
-- ----------------------------
DROP TABLE IF EXISTS `t_car`;
CREATE TABLE `t_car`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `model` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created_at` datetime(0) NOT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_car
-- ----------------------------
INSERT INTO `t_car` VALUES ('1d105df4cf5130c8d77483c6534cc526', 'BMW', '2022-05-25 09:01:45', NULL);
INSERT INTO `t_car` VALUES ('9582181e355fc0a8c92d060729eefd78', 'BMW', '2022-05-25 09:01:45', NULL);
INSERT INTO `t_car` VALUES ('9ce10c45bf779aea307a57aa6ee5841f', 'toyota', '2022-05-25 09:01:44', NULL);
INSERT INTO `t_car` VALUES ('a826deacbb6af72beafc1fc00494412f', 'toyota', '2022-05-25 09:01:45', NULL);

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `car_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `book_start_date` date NOT NULL,
  `book_end_date` date NOT NULL,
  `created_at` datetime(0) NOT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('05dea5c565fd9d6aaf6566c758e22681', '9ce10c45bf779aea307a57aa6ee5841f', '6d7275baf0758197749c7a901fb55bb9', '2022-05-27', '2022-05-29', '2022-05-25 10:13:33', NULL);
INSERT INTO `t_order` VALUES ('502db4b82ca73716f35015398d7317b3', '9582181e355fc0a8c92d060729eefd78', '6d7275baf0758197749c7a901fb55bb9', '2022-05-26', '2022-05-28', '2022-05-25 10:13:12', NULL);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `account` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created_at` datetime(0) NOT NULL,
  `updated_at` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('6d7275baf0758197749c7a901fb55bb9', 'yanglingyu', '123456', '2022-05-25 09:05:07', NULL);

SET FOREIGN_KEY_CHECKS = 1;
