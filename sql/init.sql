/*
 Navicat Premium Data Transfer

 Source Server         : demo
 Source Server Type    : MySQL
 Source Server Version : 80024
 Source Host           : localhost:3306
 Source Schema         : jiayun

 Target Server Type    : MySQL
 Target Server Version : 80024
 File Encoding         : 65001

 Date: 19/04/2026 16:09:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activity_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `old_price` decimal(10, 2) NOT NULL,
  `now_price` decimal(10, 2) NOT NULL,
  `create_time` date NOT NULL,
  `end_time` date NOT NULL,
  `status` int NOT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` date NULL DEFAULT NULL,
  `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1800453593136918530 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES (1, '限时秒杀', 2000.00, 1999.00, '2024-06-07', '2024-06-12', 1, 'a.png', '测试数据', NULL, NULL, NULL);
INSERT INTO `activity` VALUES (1800426010764685313, 'test1', 11.00, 111.00, '2024-06-11', '2024-06-13', 1, 'a.png', '111', NULL, NULL, NULL);
INSERT INTO `activity` VALUES (1800453593136918530, '潮汐', 888.00, 666.00, '2024-06-11', '2024-06-13', 1, 'a.png', '11', '2026-03-14', NULL, '1');

-- ----------------------------
-- Table structure for card
-- ----------------------------
DROP TABLE IF EXISTS `card`;
CREATE TABLE `card`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卡名',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '种类  期限卡1 次卡2',
  `price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '价格',
  `old_price` decimal(10, 2) NOT NULL DEFAULT 0.00 COMMENT '之前价格',
  `count` int NULL DEFAULT NULL COMMENT '次卡次数',
  `day` int NOT NULL COMMENT '期限卡和次卡时效',
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卡的上课类型',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述信息',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态 0 1',
  `sort` int NULL DEFAULT 0 COMMENT '顺序',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `create_user` bigint NOT NULL,
  `update_user` bigint NOT NULL,
  `is_deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1843515103811481601 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of card
-- ----------------------------
INSERT INTO `card` VALUES (1, '至尊vip特权卡', '期限卡', 8889.00, 9999.00, NULL, 999, '功夫,搏击', 'a.png', '全部特权', 1, 0, '2023-10-15 17:31:34', '2024-06-11 20:57:02', 1, 1, 0);
INSERT INTO `card` VALUES (2, '优惠20次卡', '次卡', 800.00, 900.00, 20, 365, '功夫', 'a.png', '功夫', 1, 0, '2023-10-15 17:37:28', '2024-06-11 20:58:00', 1, 1, 0);
INSERT INTO `card` VALUES (3, '小额体验卡', '次卡', 30.00, 50.00, 5, 30, '功夫', 'a.png', '功夫', 1, 0, '2023-10-15 17:40:16', '2024-06-11 20:57:25', 1, 1, 0);
INSERT INTO `card` VALUES (1713853101214470145, '超值季卡', '期限卡', 0.09, 600.00, NULL, 120, '搏击,瑜伽', 'a.png', '超值季卡超值季卡超值季卡', 1, 0, '2023-10-16 17:43:16', '2024-06-11 20:58:16', 1, 1, 0);
INSERT INTO `card` VALUES (1716015294999646210, '实惠25次卡', '次卡', 200.00, 200.00, 25, 366, '瑜伽,跳舞', 'a.png', '实惠25次卡实惠25次卡实惠25次卡', 1, 0, '2023-10-22 16:55:03', '2024-06-11 20:56:55', 1, 1, 0);
INSERT INTO `card` VALUES (1716714351942340610, '3天体验卡', '期限卡', 500.00, 600.00, NULL, 3, '功夫,搏击', 'a.png', '3天体验卡3天体验卡3天体验卡', 1, 0, '2023-10-24 15:12:51', '2024-06-11 20:56:46', 1, 1, 0);
INSERT INTO `card` VALUES (1843515103811481601, '测试卡', '期限卡', 1000.00, 1999.00, NULL, 365, 'null', 'a.png', '测试卡测试卡测试卡测试卡测试卡测试卡测试卡测试卡测试卡测试卡', 1, 0, '2024-10-08 12:53:46', '2024-10-08 12:53:54', 1, 1, 0);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名称',
  `sort` int NOT NULL COMMENT '顺序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1803346113869217794 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '零食', 1, '2024-06-27 14:11:01', '2024-06-17 17:46:56', 1, 1);
INSERT INTO `category` VALUES (2, '饮品', 3, '2024-06-20 12:31:58', '2024-06-20 12:32:00', 1, 1);
INSERT INTO `category` VALUES (1803346113869217794, '健身器材', 2, '2024-06-19 16:36:33', '2024-06-19 16:37:35', 1, 1);

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `teacher_id` bigint NULL DEFAULT NULL,
  `name` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名',
  `tname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '老师名字',
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程类型',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态 0 1',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `room_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教室名字',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '单次课价格衡量',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `pmax` int NOT NULL DEFAULT 15 COMMENT '上限人数',
  `pnum` int NOT NULL DEFAULT 0 COMMENT '已选人数',
  `imgs` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '打开素材选择拼接字段素材a.png,b.png',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `create_user` bigint NOT NULL,
  `update_user` bigint NOT NULL,
  `is_deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`, `pmax`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2033163199104184321 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES (1, 1821442154224955394, '中国功夫', '王老师', '功夫,搏击', 1, NULL, '1号教室', 500.00, '2023-10-15 17:59:23', '2050-12-31 19:49:49', 8, 1, '1727090481365.jpg', '2023-10-15 17:59:44', '2024-09-20 13:44:19', 1, 1, 1);
INSERT INTO `classes` VALUES (2, 1821442154224955394, '瑜伽', '王老师', '瑜伽', 1, NULL, '1号教室', 200.00, '2023-10-15 19:57:22', '2050-12-31 19:49:49', 5, 1, '1727090481365.jpg', '2023-10-15 19:57:34', '2024-06-27 21:45:45', 1, 1, 0);
INSERT INTO `classes` VALUES (3, 1821442154224955394, '拳法', '王老师', '功夫', 1, '拳法拳法', '1号教室', 200.00, '2023-10-15 19:57:22', '2050-12-31 19:49:49', 5, 0, '1727090481365.jpg', '2023-10-16 17:57:42', '2023-10-19 10:49:32', 1, 2, 0);
INSERT INTO `classes` VALUES (1837019762945810434, 1821442154224955394, '散打', '王老师', '搏击', 1, '11', '1号教室', 100.00, '2024-09-20 14:43:09', '2050-12-31 19:49:49', 10, 0, '1727090481365.jpg', '2024-09-20 14:43:36', '2024-09-20 14:44:27', 1, 1, 1);
INSERT INTO `classes` VALUES (1843524784294551554, 1843490885988302850, '美美瑜伽', 'Jerry666', '瑜伽', 1, '123123', '1号教室', 100.00, '2024-10-10 00:00:00', '2050-12-31 19:49:49', 9, 0, NULL, '2024-10-08 13:32:14', '2024-10-08 13:45:16', 1, 1, 1);
INSERT INTO `classes` VALUES (1843526851964706818, 1843490885988302850, '塔塔瑜伽', 'Jerry666', '瑜伽,搏击', 1, '塔塔瑜伽塔塔瑜伽塔塔瑜伽塔塔瑜伽', '1号教室', 123.00, '2024-10-11 00:00:00', '2050-12-31 19:49:49', 3, 0, NULL, '2024-10-08 13:40:27', '2024-10-08 13:44:25', 1, 1, 0);
INSERT INTO `classes` VALUES (1845000964277370881, 1821442154224955394, '哈哈瑜伽', '王老师', '瑜伽,跳舞', 1, '55555555', '1号教室', 0.00, '2024-10-01 15:17:56', '2050-12-31 19:49:49', 5, 0, '1728717455207.png', '2024-10-12 15:18:03', '2024-10-12 15:18:03', 1821442154224955394, 1821442154224955394, 0);
INSERT INTO `classes` VALUES (1850436326405386242, 1843490885988302850, '11', 'Jerry666', '瑜伽', 1, '11111111111', '1号教室', 111.00, '2024-10-14 00:00:00', '2050-12-31 19:49:49', 1111, 0, NULL, '2024-10-27 15:16:14', '2024-10-27 15:16:14', 1, 1, 0);
INSERT INTO `classes` VALUES (1850465135355641858, 1800482503736221698, '222', '灰灰', '跳舞', 1, '22222222222222222', '1号教室', 222.00, '2024-10-14 00:00:00', '2050-12-31 19:49:49', 22, 0, NULL, '2024-10-27 17:10:43', '2024-10-27 17:10:43', 1, 1, 0);
INSERT INTO `classes` VALUES (2033163199104184321, 1821442154224955394, '1231313', '王老师', NULL, 1, '123131', '1号教室', 131.00, '2026-03-15 06:00:00', '2026-03-15 07:00:00', 123, 0, '', '2026-03-15 20:47:36', '2026-03-15 20:47:36', 1821442154224955394, 1821442154224955394, 0);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `shop_id` bigint NOT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` date NULL DEFAULT NULL,
  `storage` int NOT NULL,
  `update_time` date NULL DEFAULT NULL,
  `create_user` bigint NULL DEFAULT NULL,
  `update_user` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1851250640582250497, 1803700424054673410, '无敌了,666!', '2024-11-05', 4, '2024-11-05', 1851250640582250497, 1851250640582250497);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` date NULL DEFAULT NULL,
  `update_time` date NULL DEFAULT NULL,
  `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1820986434765570050 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '模拟通知测试', '模拟通知测试', '2024-08-05', '2024-08-05', NULL, NULL);
INSERT INTO `notice` VALUES (1820986434765570050, '测试', '测试通知描述', '2024-08-07', '2024-08-07', NULL, NULL);

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `create_time` datetime NULL DEFAULT NULL,
  `end_time` datetime NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `update_time` datetime NULL DEFAULT NULL,
  `create_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_user` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2044341567611863042 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (1814247585439854593, '2024-07-19 18:35:06', '2024-07-19 18:35:06', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1823563175111458817, '2024-08-14 11:31:56', '2024-08-14 11:31:56', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1832318610823979010, '2024-09-07 15:22:54', '2024-09-07 15:22:54', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1833473301964390402, '2024-09-10 19:51:14', '2024-09-10 19:51:14', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1835557382554431489, '2024-09-16 13:52:38', '2024-09-16 13:52:38', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1835564495129292801, '2024-09-16 14:20:53', '2024-09-16 14:20:53', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836648662764109825, '2024-09-19 14:08:59', '2024-09-19 14:08:59', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836650884210114562, '2024-09-19 14:17:49', '2024-09-19 14:17:49', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836650907907932162, '2024-09-19 14:17:54', '2024-09-19 14:17:54', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836650976388333570, '2024-09-19 14:18:11', '2024-09-19 14:18:11', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836650990246313986, '2024-09-19 14:18:14', '2024-09-19 14:18:14', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836651070403657729, '2024-09-19 14:18:33', '2024-09-19 14:18:33', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836651243766824961, '2024-09-19 14:19:15', '2024-09-19 14:19:15', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836651304504541185, '2024-09-19 14:19:29', '2024-09-19 14:19:29', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836651806487232514, '2024-09-19 14:21:29', '2024-09-19 14:21:29', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836651823608381441, '2024-09-19 14:21:33', '2024-09-19 14:21:33', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836652115192201218, '2024-09-19 14:22:42', '2024-09-19 14:22:42', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1836652746279763970, '2024-09-19 14:25:13', '2024-09-19 14:25:13', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1838898716778442753, '2024-09-25 19:09:54', '2024-09-25 19:09:54', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1845114685536411650, '2024-10-12 22:49:56', '2024-10-12 22:49:56', NULL, NULL, NULL, NULL);
INSERT INTO `order_detail` VALUES (1855506259592208385, '2024-11-10 15:02:21', '2024-11-10 15:02:21', NULL, '2024-11-10 15:02:21', '1851250640582250497', '1851250640582250497');
INSERT INTO `order_detail` VALUES (1855506390957809666, '2024-11-10 15:02:52', '2024-11-10 15:02:52', NULL, '2024-11-10 15:02:52', '1851250640582250497', '1851250640582250497');
INSERT INTO `order_detail` VALUES (1855509054596702209, '2024-11-10 15:13:27', '2024-11-10 15:13:27', NULL, '2024-11-10 15:13:27', '1851250640582250497', '1851250640582250497');
INSERT INTO `order_detail` VALUES (1855511008068526082, '2024-11-10 15:21:13', '2024-11-10 15:21:13', NULL, '2024-11-10 15:21:13', '1851250640582250497', '1851250640582250497');
INSERT INTO `order_detail` VALUES (1855512462091116545, '2024-11-10 15:26:59', '2024-11-10 15:26:59', NULL, '2024-11-10 15:26:59', '1851250640582250497', '1851250640582250497');
INSERT INTO `order_detail` VALUES (1855530872564879361, '2024-11-10 16:40:09', '2024-11-10 16:40:09', NULL, '2024-11-10 16:40:09', '1851250640582250497', '1851250640582250497');
INSERT INTO `order_detail` VALUES (2033085947968606209, '2026-03-15 15:40:38', '2026-03-15 15:40:38', NULL, '2026-03-15 15:40:38', '1851250640582250497', '1851250640582250497');
INSERT INTO `order_detail` VALUES (2034960506535542785, '2026-03-20 19:49:27', '2026-03-20 19:49:27', NULL, '2026-03-20 19:49:27', '1851250640582250497', '1851250640582250497');
INSERT INTO `order_detail` VALUES (2034968706664136705, '2026-03-20 20:22:02', '2026-03-20 20:22:02', NULL, '2026-03-20 20:22:02', '1851250640582250497', '1851250640582250497');
INSERT INTO `order_detail` VALUES (2044341567611863042, '2026-04-15 17:06:26', '2026-04-15 17:06:26', NULL, '2026-04-15 17:06:26', '2043674938850885633', '2043674938850885633');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '下单用户',
  `shop_id` bigint NOT NULL COMMENT '商品编号',
  `shop_count` int NOT NULL COMMENT '商品数量',
  `account` decimal(8, 2) NOT NULL COMMENT '收款金额',
  `status` int NOT NULL COMMENT '订单状态:1.待付款 2已完成',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `shop_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` decimal(10, 2) NOT NULL COMMENT '商品单价',
  `order_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2044341567704137729 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1814247585502769153, 1800482561378541569, 1803700424054673410, 3, 18.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1814247585439854593);
INSERT INTO `orders` VALUES (1823563175216316417, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1823563175111458817);
INSERT INTO `orders` VALUES (1832318611163717633, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1832318610823979010);
INSERT INTO `orders` VALUES (1833473302295740418, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1833473301964390402);
INSERT INTO `orders` VALUES (1835557382785118209, 1800482561378541569, 1804351424964964353, 1, 159.00, 1, '19861407298', '诺特兰德5磅乳清蛋白质粉分离水解乳清增肌', 'a.png', 159.00, 1835557382554431489);
INSERT INTO `orders` VALUES (1835564495188013058, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1835564495129292801);
INSERT INTO `orders` VALUES (1836648662843801602, 1800482561378541569, 1803700424054673410, 2, 12.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836648662764109825);
INSERT INTO `orders` VALUES (1836650884210114563, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836650884210114562);
INSERT INTO `orders` VALUES (1836650907975041026, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836650907907932162);
INSERT INTO `orders` VALUES (1836650976459636737, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836650976388333570);
INSERT INTO `orders` VALUES (1836650990313422849, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836650990246313986);
INSERT INTO `orders` VALUES (1836651070403657730, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836651070403657729);
INSERT INTO `orders` VALUES (1836651243833933825, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836651243766824961);
INSERT INTO `orders` VALUES (1836651304571650049, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836651304504541185);
INSERT INTO `orders` VALUES (1836651806529175553, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836651806487232514);
INSERT INTO `orders` VALUES (1836651823654518785, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836651823608381441);
INSERT INTO `orders` VALUES (1836652115192201219, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836652115192201218);
INSERT INTO `orders` VALUES (1836652746317512706, 1800482561378541569, 1803700424054673410, 1, 6.00, 1, '19861407298', '红牛', 'a.png', 6.00, 1836652746279763970);
INSERT INTO `orders` VALUES (1838898716879106050, 1838860162107035649, 1803700424054673410, 1, 6.00, 1, '198614072981', '红牛', 'a.png', 6.00, 1838898716778442753);
INSERT INTO `orders` VALUES (1845114685595131906, 1821442154224955394, 1803700424054673410, 1, 6.00, 1, '15588879699', '红牛', 'a.png', 6.00, 1845114685536411650);
INSERT INTO `orders` VALUES (2033085947968606210, 1851250640582250497, 1803700424054673410, 1, 6.00, 1, '13947711568', '红牛', 'a.png', 6.00, 2033085947968606209);
INSERT INTO `orders` VALUES (2034960506552320001, 1851250640582250497, 1803700424054673410, 1, 6.00, 1, '13947711568', '红牛', 'a.png', 6.00, 2034960506535542785);
INSERT INTO `orders` VALUES (2034968706680913921, 1851250640582250497, 1835595792446955522, 1, 111.00, 1, '13947711568', '10kg哑铃', 'a.png', 111.00, 2034968706664136705);
INSERT INTO `orders` VALUES (2044341567704137729, 2043674938850885633, 1803700424054673410, 1, 6.00, 1, '15238100012', '红牛', 'a.png', 6.00, 2044341567611863042);

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` bigint NOT NULL COMMENT '资源ID',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '原始文件名',
  `file_md5` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '文件MD5值',
  `file_size` bigint NOT NULL COMMENT '文件大小(字节)',
  `chunk_size` int NOT NULL COMMENT '分片大小(字节)',
  `chunk_count` int NOT NULL COMMENT '分片总数',
  `chunk_uploaded` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '已上传的分片索引，逗号分隔，用于断点续传',
  `object_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'MinIO中的对象名',
  `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '文件类型(mp4/pdf/doc等)',
  `upload_status` int NULL DEFAULT 0 COMMENT '上传状态：0-上传中 1-已完成 2-合并失败',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人ID',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_file_md5`(`file_md5`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '文件资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (2045145371139887106, '数据库系统概论.zip', '4d83a56fc63ac0041d21baf75c69f3ca', 1437758586, 5242880, 275, '0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,160,161,162,163,164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,197,198,199,200,201,202,203,204,205,206,207,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,224,225,226,227,228,229,230,231,232,233,234,235,236,237,238,239,240,241,242,243,244,245,246,247,248,249,250,251,252,253,254,255,256,257,258,259,260,261,262,263,264,265,266,267,268,269,270,271,272,273,274', 'resources/4d83a56fc63ac0041d21baf75c69f3ca/数据库系统概论.zip', 'zip', 1, '2026-04-17 22:20:28', '2026-04-17 22:21:11', NULL, NULL);

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '教室名字',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '地点',
  `people` int NOT NULL COMMENT '容量',
  `status` int NULL DEFAULT 1 COMMENT '状态 0 1',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `create_user` bigint NOT NULL,
  `update_user` bigint NOT NULL,
  `is_deleted` int NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1716026940794994689 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES (1714188709761855489, '1号教室', '艺体大楼102', 100, 1, '2023-10-17 15:56:51', '2024-09-16 16:23:49', 1, 1, 0);
INSERT INTO `room` VALUES (1716026940794994689, '2号教室', '艺体大楼103', 90, 1, '2023-10-22 17:41:20', '2023-10-22 17:41:20', 1, 1, 0);

-- ----------------------------
-- Table structure for shop
-- ----------------------------
DROP TABLE IF EXISTS `shop`;
CREATE TABLE `shop`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shop_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片路径',
  `old_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品原价',
  `new_price` decimal(10, 2) NOT NULL COMMENT '商品现价',
  `status` int NOT NULL COMMENT '状态 0停售 1启售',
  `inventory` int NOT NULL COMMENT '库存数量',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `category_id` bigint NOT NULL COMMENT '分类id',
  `sort` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1835595792446955522 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of shop
-- ----------------------------
INSERT INTO `shop` VALUES (1803700424054673410, '红牛', 'a.png', NULL, 6.00, 1, 10, '11', 2, 1);
INSERT INTO `shop` VALUES (1835595792446955522, '10kg哑铃', 'a.png', 111.00, 111.00, 1, 11, '1', 1803346113869217794, 1);

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '性别',
  `id_number` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证号',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_user` bigint NOT NULL COMMENT '创建人',
  `update_user` bigint NOT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1850901257051336705 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '员工信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_admin
-- ----------------------------
INSERT INTO `sys_admin` VALUES (1, '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', 1, '2023-09-26 17:20:07', '2024-09-16 16:22:26', 1, 1);
INSERT INTO `sys_admin` VALUES (2, '二管', 'erguan', 'e10adc3949ba59abbe56e057f20f883e', '13212312312', '1', '370983200211141832', 1, '2023-10-15 16:55:53', '2024-09-21 21:54:56', 1, 1);
INSERT INTO `sys_admin` VALUES (3, '小管1', 'xiaoguan', 'e10adc3949ba59abbe56e057f20f883e', '15753697852', '0', '370983200112057896', 1, '2023-10-19 11:18:04', '2024-09-21 19:52:47', 1, 1);
INSERT INTO `sys_admin` VALUES (1820663871363371009, '德鲁伊', 'jdbc', 'e10adc3949ba59abbe56e057f20f883e', '15588866666', '1', '111111111111111111', 1, '2024-08-06 11:31:08', '2026-03-14 21:47:48', 1, 1);
INSERT INTO `sys_admin` VALUES (1835595164073107458, '123', 'aaa', 'e10adc3949ba59abbe56e057f20f883e', '19861407298', '1', '111111111111111', 0, '2024-09-16 16:22:45', '2024-09-16 16:22:50', 1, 1);
INSERT INTO `sys_admin` VALUES (1837302634931859458, 'aa', '123', 'e10adc3949ba59abbe56e057f20f883e', '19999999998', '1', '110101101010101100', 1, '2024-09-21 09:27:38', '2024-09-21 09:27:38', 1, 1);
INSERT INTO `sys_admin` VALUES (1837444294349213698, '刘子', 'liuyan', 'e10adc3949ba59abbe56e057f20f883e', '15577889996', '1', '110001101001011101', 1, '2024-09-21 18:50:32', '2024-09-21 18:50:32', 1, 1);
INSERT INTO `sys_admin` VALUES (1837444579108900866, '白宇', 'baiyu', 'e10adc3949ba59abbe56e057f20f883e', '15577885566', '1', '110001101001011101', 1, '2024-09-21 18:51:40', '2024-09-21 18:51:40', 2, 2);
INSERT INTO `sys_admin` VALUES (1837487670343864321, '123', 'admin1', 'e10adc3949ba59abbe56e057f20f883e', '15566688888', '1', '111111111111111111', 1, '2024-09-21 21:42:54', '2024-09-21 21:42:54', 1, 1);
INSERT INTO `sys_admin` VALUES (1837487772122845186, '任兆宇', 'renzhengyu', 'e10adc3949ba59abbe56e057f20f883e', '15555555554', '1', '111111111111111111', 1, '2024-09-21 21:43:18', '2024-09-21 21:43:18', 1, 1);
INSERT INTO `sys_admin` VALUES (1837488483569000449, '吕思浩', 'lvsihao', 'e10adc3949ba59abbe56e057f20f883e', '15555555555', '1', '111111111111111111', 1, '2024-09-21 21:46:08', '2024-09-21 21:46:08', 1837487772122845186, 1837487772122845186);
INSERT INTO `sys_admin` VALUES (1840673662894698498, '张三', 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '15112312312', '1', '151123123121234561', 1, '2024-09-30 16:42:54', '2024-09-30 16:42:54', 1, 1);
INSERT INTO `sys_admin` VALUES (1850856037161050114, '王东东', '222222222', 'e10adc3949ba59abbe56e057f20f883e', '15566663333', '0', '122222555544441111', 1, '2024-10-28 19:04:01', '2024-10-28 19:04:01', 1, 1);
INSERT INTO `sys_admin` VALUES (1850901257051336705, '吴雨晨', '6666666', 'e10adc3949ba59abbe56e057f20f883e', '15322221111', '0', '555555555555555555', 1, '2024-10-28 22:03:42', '2024-10-28 22:03:42', 1, 1);

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `create_user` bigint NOT NULL,
  `update_user` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1714187543644680193 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tags
-- ----------------------------
INSERT INTO `tags` VALUES (1, '功夫', '2023-10-15 17:32:45', '2023-10-15 17:32:48', 1, 1);
INSERT INTO `tags` VALUES (2, '搏击', '2023-10-15 17:33:14', '2023-10-15 17:33:17', 1, 1);
INSERT INTO `tags` VALUES (3, '瑜伽', '2023-10-15 19:56:35', '2023-10-15 19:56:37', 1, 1);
INSERT INTO `tags` VALUES (1714187543644680193, '跳舞', '2023-10-17 15:52:13', '2024-05-16 13:11:21', 1, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint UNSIGNED NOT NULL COMMENT '主键id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户姓名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系方式',
  `sex` int NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `id_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` int NULL DEFAULT 1 COMMENT '状态 0:禁用，1:正常',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型 学生 老师',
  `is_deleted` int NOT NULL DEFAULT 0,
  `update_time` datetime NULL DEFAULT NULL,
  `create_user` bigint NULL DEFAULT NULL,
  `update_user` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1800482503736221698, '灰灰', '12255556666', 0, '2024-06-20', '110', 'a.png', 1, '老师', 0, '2024-06-11 18:57:35', NULL, NULL, NULL);
INSERT INTO `user` VALUES (1800482561378541569, '李某', '19861407298', 1, '2024-06-04', '111111', 'a.png', 1, '会员', 0, '2026-03-14 21:55:04', NULL, 1, NULL);
INSERT INTO `user` VALUES (1806234086663020546, '吴彦祖', '19861407291', 1, '2024-06-05', '111', 'a.png', 1, '会员', 0, '2026-03-14 21:54:36', NULL, 1, NULL);
INSERT INTO `user` VALUES (1821432240635117570, '教java的老登', '15588879666', 0, '2024-08-13', '372323333333333333', 'a.png', 1, '老师', 0, '2024-08-08 14:24:21', NULL, NULL, NULL);
INSERT INTO `user` VALUES (1821442154224955394, '王老师', '15588879699', 0, '2024-08-08', '371919191919191111', 'a.png', 1, '老师', 0, '2024-08-08 15:03:45', NULL, NULL, NULL);
INSERT INTO `user` VALUES (1823983671951093762, '学员3', '15588879690', NULL, NULL, NULL, 'a.png', 1, '会员', 0, '2024-08-15 15:22:50', NULL, NULL, NULL);
INSERT INTO `user` VALUES (1837136317285179393, 'bbcc', '111111', 1, '2024-09-17', '1111111111', 'a.png', 1, '老师', 0, '2024-09-25 16:36:24', NULL, NULL, NULL);
INSERT INTO `user` VALUES (1837755570317185025, '灼雨', '11011', 1, '2024-09-22', '110', 'a.png', 1, '老师', 0, '2026-03-14 23:52:38', NULL, 1, NULL);
INSERT INTO `user` VALUES (1838857998601146369, 'aa', '15588879691', 0, NULL, '11', 'a.png', 0, '会员', 0, '2026-03-14 21:57:20', NULL, 1, NULL);
INSERT INTO `user` VALUES (1838860162107035649, '偷狗刘', '15694445555', 1, '2024-09-26', '111', 'a.png', 1, '会员', 0, '2024-09-25 16:53:06', NULL, NULL, NULL);
INSERT INTO `user` VALUES (1843490885988302850, 'Tom666', '15112312313', 1, '2024-10-01', '123456123456123453', 'a.png', 1, '老师', 0, '2024-10-08 12:26:30', NULL, NULL, NULL);
INSERT INTO `user` VALUES (1843502068342493185, 'asd', '15112312312', 0, '2024-10-01', '123456123456123456', 'a.png', 1, '会员', 0, '2024-10-08 12:01:58', NULL, NULL, NULL);
INSERT INTO `user` VALUES (1850465773359611906, '南老师', '12233336666', 0, '2024-10-29', '111222555566669996', '1730020369290.png', 1, '老师', 0, '2024-10-27 17:13:15', 1, 1, '2024-10-27 17:13:15');
INSERT INTO `user` VALUES (1851250640582250497, '会员13947711568', '13947711568', 0, '2024-10-29', NULL, 'a.png', 1, '会员', 0, '2024-10-29 21:12:02', NULL, NULL, '2024-10-29 21:12:02');
INSERT INTO `user` VALUES (1859070312495218689, '曾佐帅', '15522226663', 0, '2024-11-24', '222111222200003333', NULL, 1, '会员', 0, '2024-11-20 11:04:37', 1, 1, '2024-11-20 11:04:37');
INSERT INTO `user` VALUES (2043674938850885633, '会员15238100012', '15238100012', 0, '2026-04-13', NULL, 'a.png', 1, '会员', 0, '2026-04-13 20:57:30', NULL, NULL, '2026-04-13 20:57:30');

-- ----------------------------
-- Table structure for user_card
-- ----------------------------
DROP TABLE IF EXISTS `user_card`;
CREATE TABLE `user_card`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint NOT NULL COMMENT '用户主键',
  `card_id` bigint NOT NULL COMMENT '卡主键',
  `card_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卡名(冗余字段)',
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '课程权限',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '卡的类型',
  `count` int NULL DEFAULT NULL COMMENT '次卡剩余次数',
  `start_date` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_date` date NULL DEFAULT NULL COMMENT '结束时间',
  `status` int NULL DEFAULT NULL COMMENT '状态 1正常 2次数用尽 3过期',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  `create_user` bigint NOT NULL,
  `update_user` bigint NOT NULL,
  `is_deleted` int NOT NULL DEFAULT 0,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `price` double(255, 0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `card_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1859071292339159041 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_card
-- ----------------------------
INSERT INTO `user_card` VALUES (1844951190337601537, 1838857998601146369, 1716714351942340610, '3天体验卡', '功夫,搏击', '期限卡', NULL, '2024-10-12', '2024-10-15', 1, '2024-10-12 12:00:16', '2024-10-12 12:00:16', 1, 1, 0, '1718110605576.png', 500);
INSERT INTO `user_card` VALUES (1844952370040074241, 1800482561378541569, 1713853101214470145, '超值季卡', '搏击,瑜伽', '期限卡', NULL, '2024-10-12', '2025-02-09', 1, '2024-10-12 12:04:57', '2024-10-12 12:04:57', 1, 1, 0, '1718110694920.png', 0);
INSERT INTO `user_card` VALUES (1845077610057506818, 1800482561378541569, 1, '至尊vip特权卡', '功夫,搏击', '期限卡', NULL, '2024-10-12', '2027-07-08', 1, '2024-10-12 20:22:37', '2024-10-12 20:22:37', 1, 1, 0, '1718110621560.png', 8889);
INSERT INTO `user_card` VALUES (1850425030549135362, 1843502068342493185, 1843515103811481601, '测试卡', 'null', '期限卡', NULL, '2024-10-27', '2025-10-27', 1, '2024-10-27 14:31:21', '2024-10-27 14:31:21', 1, 1, 0, 'a.png', 1000);
INSERT INTO `user_card` VALUES (1852310336391028737, 1851250640582250497, 1716015294999646210, '实惠25次卡', '瑜伽,跳舞', '次卡', 25, '2024-11-01', '2025-11-02', 1, '2024-11-01 19:22:53', '2024-11-01 19:22:53', 1, 1, 0, 'a.png', 200);
INSERT INTO `user_card` VALUES (1852310367982526466, 1851250640582250497, 3, '小额体验卡', '功夫', '次卡', 5, '2024-11-01', '2024-12-01', 1, '2024-11-01 19:23:01', '2024-11-01 19:23:01', 1, 1, 0, 'a.png', 30);
INSERT INTO `user_card` VALUES (1852310385300807681, 1851250640582250497, 1713853101214470145, '超值季卡', '搏击,瑜伽', '期限卡', NULL, '2024-11-01', '2025-03-01', 1, '2024-11-01 19:23:05', '2024-11-01 19:23:05', 1, 1, 0, 'a.png', 0);
INSERT INTO `user_card` VALUES (1852582597973106690, 1851250640582250497, 1716714351942340610, '3天体验卡', '功夫,搏击', '期限卡', NULL, '2024-11-02', '2024-11-05', 1, '2024-11-02 13:24:45', '2024-11-02 13:24:45', 1, 1, 0, 'a.png', 500);
INSERT INTO `user_card` VALUES (1859071273951330306, 1859070312495218689, 1713853101214470145, '超值季卡', '搏击,瑜伽', '期限卡', NULL, '2024-11-20', '2025-03-20', 1, '2024-11-20 11:08:26', '2024-11-20 11:08:26', 1, 1, 0, 'a.png', 0);
INSERT INTO `user_card` VALUES (1859071292339159041, 1859070312495218689, 1, '至尊vip特权卡', '功夫,搏击', '期限卡', NULL, '2024-11-20', '2027-08-16', 1, '2024-11-20 11:08:31', '2024-11-20 11:08:31', 1, 1, 0, 'a.png', 8889);

-- ----------------------------
-- Table structure for user_class
-- ----------------------------
DROP TABLE IF EXISTS `user_class`;
CREATE TABLE `user_class`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `class_id` bigint NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` int NULL DEFAULT 1 COMMENT '状态',
  `create_time` datetime NOT NULL,
  `is_deleted` int NOT NULL DEFAULT 0,
  `tags` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `create_user` bigint NULL DEFAULT NULL,
  `update_user` bigint NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`, `class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1859071335448215553 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_class
-- ----------------------------
INSERT INTO `user_class` VALUES (1723250449183113282, 1800482561378541569, 2, '苏苏33', '瑜伽', 2, '2024-09-20 13:52:00', 0, '瑜伽', '2024-11-03 17:10:01', NULL, 1821442154224955394);
INSERT INTO `user_class` VALUES (1723250449183113284, 1838860162107035649, 3, '偷狗刘', '拳法', 1, '2024-09-25 20:32:10', 0, '功夫', NULL, NULL, NULL);
INSERT INTO `user_class` VALUES (1723250449183113285, 1838860162107035649, 2, '偷狗刘', '瑜伽', 2, '2024-09-25 20:32:13', 0, '瑜伽', '2024-11-03 17:10:08', NULL, 1821442154224955394);
INSERT INTO `user_class` VALUES (1723250449183113286, 1843502068342493185, 1850465135355641858, 'asd', '222', 2, '2024-11-01 17:18:16', 0, '跳舞', '2026-03-14 19:11:07', NULL, 1800482503736221698);
INSERT INTO `user_class` VALUES (1852341590012370945, 1851250640582250497, 1, '会员13947711568', '中国功夫', 2, '2024-11-01 21:27:05', 0, '功夫,搏击', '2026-03-20 19:58:42', 1851250640582250497, 1821442154224955394);
INSERT INTO `user_class` VALUES (1852581836488826882, 1851250640582250497, 1845000964277370881, '会员13947711568', '哈哈瑜伽', 1, '2024-11-02 13:21:44', 0, '瑜伽,跳舞', '2024-11-02 13:21:44', 1851250640582250497, 1851250640582250497);
INSERT INTO `user_class` VALUES (1859071335448215553, 1859070312495218689, 1, '曾佐帅', '中国功夫', 2, '2024-11-20 11:08:41', 0, '功夫,搏击', '2026-03-20 19:58:45', 1859070312495218689, 1821442154224955394);

SET FOREIGN_KEY_CHECKS = 1;
