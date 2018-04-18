/*
Navicat MySQL Data Transfer

Source Server         : my
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : transaction

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-16 10:24:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for transaction_fail
-- ----------------------------
DROP TABLE IF EXISTS `transaction_fail`;
CREATE TABLE `transaction_fail` (
  `id` varchar(64) NOT NULL,
  `transaction_id` varchar(64) DEFAULT NULL COMMENT '事务ID',
  `operation_id` varchar(64) DEFAULT NULL COMMENT '操作ID',
  `fail_cause` text DEFAULT NULL COMMENT '失败原因',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for transaction_log
-- ----------------------------
DROP TABLE IF EXISTS `transaction_log`;
CREATE TABLE `transaction_log` (
  `id` varchar(64) NOT NULL,
  `operation_id` varchar(64) DEFAULT NULL COMMENT '当前操作ID',
  `name` varchar(50) DEFAULT NULL COMMENT '事务名称',
  `status` smallint(6) DEFAULT NULL COMMENT '状态:1未完成,2已完成,3失败',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for transaction_operation
-- ----------------------------
DROP TABLE IF EXISTS `transaction_operation`;
CREATE TABLE `transaction_operation` (
  `id` varchar(64) NOT NULL,
  `no` smallint(6) DEFAULT NULL COMMENT '序号',
  `transaction_id` varchar(64) DEFAULT NULL COMMENT '事务ID',
  `topic` varchar(50) DEFAULT NULL COMMENT 'mq队列名称',
  `name` varchar(50) DEFAULT NULL,
  `operation` smallint(6) DEFAULT NULL COMMENT '操作标识',
  `params` text COMMENT '参数',
  `status` smallint(6) DEFAULT NULL COMMENT '状态:0未执行,1执行中,2成功,3失败',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
