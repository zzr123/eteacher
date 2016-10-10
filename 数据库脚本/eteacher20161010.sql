/*
Navicat MySQL Data Transfer

Source Server         : 公司台式机
Source Server Version : 50540
Source Host           : 192.168.1.114:3306
Source Database       : eteacher2

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2016-10-10 13:15:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_app`
-- ----------------------------
DROP TABLE IF EXISTS `t_app`;
CREATE TABLE `t_app` (
  `A_ID` varchar(10) NOT NULL COMMENT 'id',
  `APPKEY` varchar(20) DEFAULT NULL COMMENT 'APPKEY',
  `VALUE` varchar(20) DEFAULT NULL COMMENT '组织/机构/程序',
  PRIMARY KEY (`A_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_app
-- ----------------------------
INSERT INTO `t_app` VALUES ('1', '20161001_ITEACHER', 'UP教学助手');

-- ----------------------------
-- Table structure for `t_class`
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE `t_class` (
  `CLASS_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '班级表主键',
  `MAJOR_ID` varchar(10) DEFAULT NULL COMMENT '专业ID',
  `CLASS_NAME` varchar(80) DEFAULT NULL COMMENT '班级名称',
  `GRADE` varchar(4) DEFAULT NULL COMMENT '年级 （如2016）',
  `CLASS_TYPE` varchar(20) DEFAULT NULL COMMENT '类型（研究生、本科、专科、中专、技校）',
  `SCHOOL_ID` varchar(10) DEFAULT NULL COMMENT '该班级所属学校ID',
  PRIMARY KEY (`CLASS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_class
-- ----------------------------
INSERT INTO `t_class` VALUES ('1', '020101', '测试班级1', '2014', '本科', null);
INSERT INTO `t_class` VALUES ('2', '010103K', '测试班级2', '2016', '本科', null);
INSERT INTO `t_class` VALUES ('3aIuuYCNQt', '010102', '2016级逻辑学', '2016', '本科', null);
INSERT INTO `t_class` VALUES ('NCG4iCUGy0', '020101', '经济学1班', '2016', null, null);
INSERT INTO `t_class` VALUES ('p74GYIXJnV', '010103K', '测试班级a', '2016', '本科', null);

-- ----------------------------
-- Table structure for `t_config`
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config` (
  `KEY` varchar(20) NOT NULL DEFAULT '' COMMENT '配置表主键',
  `VALUE` varchar(80) DEFAULT NULL COMMENT '配置项的值',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_config
-- ----------------------------

-- ----------------------------
-- Table structure for `t_course`
-- ----------------------------
DROP TABLE IF EXISTS `t_course`;
CREATE TABLE `t_course` (
  `COURSE_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '课程表主键',
  `TERM_ID` varchar(10) DEFAULT NULL COMMENT '学期ID',
  `COURSE_NAME` varchar(80) DEFAULT NULL COMMENT '课程名称',
  `INTRODUCTION` varchar(400) DEFAULT NULL COMMENT '课程简介',
  `CLASS_HOURS` int(5) DEFAULT NULL COMMENT '学时',
  `MAJOR_ID` varchar(10) DEFAULT NULL COMMENT '专业ID',
  `TEACHING_METHOD_ID` varchar(20) DEFAULT NULL COMMENT '授课方式ID',
  `COURSE_TYPE_ID` varchar(20) DEFAULT NULL COMMENT '课程类型ID',
  `EXAMINATION_MODE_ID` varchar(20) DEFAULT NULL COMMENT '考核方式ID',
  `FORMULA` varchar(40) DEFAULT NULL COMMENT '工作量公式',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '所属用户ID',
  `REMIND_TIME` int(3) DEFAULT NULL COMMENT '课程提醒时间',
  PRIMARY KEY (`COURSE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_course
-- ----------------------------
INSERT INTO `t_course` VALUES ('DDJHAT0SKb', 'eyYAM09nIF', '数据结构与算法', '', '22', '020202', '', '专业基础课', '--请选择考核方式--', '', 'Qsq73xbQDS', null);
INSERT INTO `t_course` VALUES ('qJTKcpG0Mc', 'eyYAM09nIF', 'java编译原理', '这是一门计算机课程', '1', '010102', '理论', '公共选修课', '考查课', '', 'Qsq73xbQDS', null);

-- ----------------------------
-- Table structure for `t_course_cell`
-- ----------------------------
DROP TABLE IF EXISTS `t_course_cell`;
CREATE TABLE `t_course_cell` (
  `CT_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '课程单元主键',
  `CI_ID` varchar(10) DEFAULT NULL COMMENT '所属课程项ID',
  `WEEKDAY` varchar(20) DEFAULT NULL COMMENT '星期几',
  `LESSON_NUMBER` varchar(20) DEFAULT NULL COMMENT '第几节课（多节课小数点隔开，如：2.3）',
  `LOCATION` varchar(40) DEFAULT NULL COMMENT '教学楼',
  `CLASSROOM` varchar(5) DEFAULT NULL COMMENT '教室',
  PRIMARY KEY (`CT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_course_cell
-- ----------------------------
INSERT INTO `t_course_cell` VALUES ('Hih9fKpUh6', 'DDJHAT0SKb', '2,5', '3,4', '多媒体教室', null);
INSERT INTO `t_course_cell` VALUES ('RpFqkrANsl', 'DDJHAT0SKb', '2,5', '3,4', '多媒体教室', null);
INSERT INTO `t_course_cell` VALUES ('Ss4zDCgvDf', 'qJTKcpG0Mc', '5', '5,6', '教学B2楼', null);

-- ----------------------------
-- Table structure for `t_course_class`
-- ----------------------------
DROP TABLE IF EXISTS `t_course_class`;
CREATE TABLE `t_course_class` (
  `CC_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '课程班级关联表主键',
  `COURSE_ID` varchar(10) DEFAULT NULL COMMENT '课程ID',
  `CLASS_ID` varchar(10) DEFAULT NULL COMMENT '班级ID',
  PRIMARY KEY (`CC_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_course_class
-- ----------------------------
INSERT INTO `t_course_class` VALUES ('2DDNERa3gZ', 'qJTKcpG0Mc', '1');

-- ----------------------------
-- Table structure for `t_course_file(abandon,与t_file表重复)`
-- ----------------------------
DROP TABLE IF EXISTS `t_course_file(abandon,与t_file表重复)`;
CREATE TABLE `t_course_file(abandon,与t_file表重复)` (
  `File_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '资源表主键',
  `DATA_ID` varchar(10) DEFAULT NULL COMMENT '文件所属数据ID',
  `FILE_NAME` varchar(100) DEFAULT NULL COMMENT '资源原始名称',
  `SERVER_NAME` varchar(10) DEFAULT NULL COMMENT '服务器存储名称',
  `IS_COURSE_FILE` int(2) DEFAULT NULL COMMENT '是否为课程资源   01：是   02：不是',
  `VOCABULARY_ID` varchar(2) DEFAULT NULL COMMENT '课程资源类型ID',
  `FILE_AUTH` varchar(2) DEFAULT NULL COMMENT '资源权限（01公开 02不公开）',
  PRIMARY KEY (`File_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_course_file(abandon,与t_file表重复)
-- ----------------------------
INSERT INTO `t_course_file(abandon,与t_file表重复)` VALUES ('bb4abZWKkS', 'qJTKcpG0Mc', 'Keep You In My Heart v5(1).mp3', 'ThowHSB6ga', null, '01', '01');
INSERT INTO `t_course_file(abandon,与t_file表重复)` VALUES ('icvdRNf0Wn', 'DDJHAT0SKb', 'TODO.txt', '1i4gx9lVHB', null, '01', '01');
INSERT INTO `t_course_file(abandon,与t_file表重复)` VALUES ('w3qphYG0PH', 'qJTKcpG0Mc', '颈椎健康操.mp4', 'focg2j7w4o', null, '01', '01');

-- ----------------------------
-- Table structure for `t_course_item`
-- ----------------------------
DROP TABLE IF EXISTS `t_course_item`;
CREATE TABLE `t_course_item` (
  `CI_ID` varchar(10) NOT NULL COMMENT '课程项ID',
  `REPEAT_TYPE` varchar(2) DEFAULT NULL COMMENT '重复类型（01 天 02 周）',
  `REPEAT_NUMBER` int(3) DEFAULT NULL COMMENT '重复数字',
  `START_WEEK` int(3) DEFAULT NULL COMMENT '起始周',
  `END_WEEK` int(3) DEFAULT NULL COMMENT '结束周',
  `START_DAY` varchar(10) DEFAULT NULL COMMENT '起始日期',
  `END_DAY` varchar(10) DEFAULT NULL COMMENT '结束日期',
  `COURSE_ID` varchar(10) DEFAULT NULL COMMENT '所属课程ID',
  PRIMARY KEY (`CI_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_course_item
-- ----------------------------

-- ----------------------------
-- Table structure for `t_course_score_private`
-- ----------------------------
DROP TABLE IF EXISTS `t_course_score_private`;
CREATE TABLE `t_course_score_private` (
  `CSP_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '课程成绩组成表主键',
  `COURSE_ID` varchar(10) DEFAULT NULL,
  `SCORE_NAME` varchar(20) DEFAULT NULL COMMENT '成绩组成名称',
  `SCORE_PERCENT` decimal(5,2) DEFAULT NULL COMMENT '成绩组成百分比',
  `CS_ORDER` int(11) DEFAULT NULL,
  `SCORE_POINT_ID` varchar(10) DEFAULT NULL COMMENT '分制',
  `STATUS` int(2) DEFAULT NULL COMMENT '成绩组成项的性质。  01：替换上一次成绩   02：新增一次该项成绩',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '用户ID',
  `CS_ID` varchar(10) DEFAULT NULL COMMENT '共有表主键',
  PRIMARY KEY (`CSP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_course_score_private
-- ----------------------------
INSERT INTO `t_course_score_private` VALUES ('3e6xCAJiWl', 'oivGF6JsOP', '期末', null, '2', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('4GYFTUISie', 'DDJHAT0SKb', '气质', '25.00', '0', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('8ZP0CFEaYr', 'oivGF6JsOP', '平时', null, '0', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('9jx9381J2J', '1uZ4NjUwPV', '期末', null, '2', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('AZszApX5V0', 'qJTKcpG0Mc', '期中', '20.00', '1', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('BG6vhpRsmq', 'oivGF6JsOP', '期中', null, '1', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('bSX0UjvtTZ', 'pc8tAC1Vkq', '平时', null, '0', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('DJg65ln2kK', 'pc8tAC1Vkq', '期末', null, '2', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('EfRqIn5ycS', 'jZloRzcLZY', '期末', null, '2', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('FfLl8TtShy', 'qJTKcpG0Mc', '劳动', '10.00', '3', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('gESJnSu4dS', 'GpkrUlUx5t', '期中', '20.00', '1', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('hJxpSrkSal', 'kwDfYtfhnP', '期末', null, '2', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('hLZdZn33gn', 'DDJHAT0SKb', '体重', '50.00', '2', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('iPylES9fzU', 'Lhu14i9fn0', '平时', null, '0', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('iXLvkw95Yu', '1uZ4NjUwPV', '平时', null, '0', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('KlwTGVJJpk', 'pc8tAC1Vkq', '期中', null, '1', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('lh8WNSN9eC', 'DDJHAT0SKb', '长相', '15.00', '1', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('LLtRHeSXYg', 'Lhu14i9fn0', '期中', null, '1', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('lUxUOMejYN', 'jZloRzcLZY', '平时', null, '0', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('N4GBLKEt3X', 'qJTKcpG0Mc', '平时', '30.00', '0', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('ngqND4qy4O', 'DDJHAT0SKb', '身高', '10.00', '3', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('OkBhUrivGA', 'GpkrUlUx5t', '期末', '60.00', '2', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('pRNQCEBhmx', '1uZ4NjUwPV', '期中', null, '1', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('QFEtR9G5YJ', 'kwDfYtfhnP', '平时', null, '0', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('sZvOOdDBDt', 'qJTKcpG0Mc', '期末', '40.00', '2', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('TblD2hiYPU', 'Lhu14i9fn0', '期末', null, '2', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('tOqU8jRGmh', 'kwDfYtfhnP', '期中', null, '1', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('UJIG2KEeLm', 'jZloRzcLZY', '期中', null, '1', null, null, null, null);
INSERT INTO `t_course_score_private` VALUES ('ZA4Lvv4RIu', 'GpkrUlUx5t', '平时', '20.00', '0', null, null, null, null);

-- ----------------------------
-- Table structure for `t_course_score_pubic`
-- ----------------------------
DROP TABLE IF EXISTS `t_course_score_pubic`;
CREATE TABLE `t_course_score_pubic` (
  `CS_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '课程成绩组成表主键',
  `COURSE_ID` varchar(10) DEFAULT NULL,
  `SCORE_NAME` varchar(20) DEFAULT NULL COMMENT '成绩组成名称',
  `SCORE_PERCENT` decimal(5,2) DEFAULT NULL COMMENT '成绩组成百分比',
  `CS_ORDER` int(11) DEFAULT NULL,
  `SCORE_POINT_ID` varchar(10) DEFAULT NULL COMMENT '分制',
  `STATUS` int(2) DEFAULT NULL COMMENT '成绩组成项的性质。  01：替换上一次成绩   02：新增一次该项成绩',
  PRIMARY KEY (`CS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_course_score_pubic
-- ----------------------------

-- ----------------------------
-- Table structure for `t_custom_data`
-- ----------------------------
DROP TABLE IF EXISTS `t_custom_data`;
CREATE TABLE `t_custom_data` (
  `CD_ID` varchar(10) NOT NULL DEFAULT '',
  `USER_ID` varchar(10) DEFAULT NULL,
  `DATA_TYPE` varchar(10) DEFAULT NULL,
  `DATA_LABEL` varchar(10) DEFAULT NULL,
  `DATA_VALUE` varchar(20) DEFAULT NULL,
  `DATA_ID` varchar(10) DEFAULT NULL,
  `IS_CUSTOM` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`CD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_custom_data
-- ----------------------------

-- ----------------------------
-- Table structure for `t_dictionary2_private`
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary2_private`;
CREATE TABLE `t_dictionary2_private` (
  `DP_ID` varchar(10) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '公共字典表主键',
  `TYPE` int(2) DEFAULT NULL COMMENT '字典表类型。 01：课程类型   02：授课方式  03：考核类型  04：职称  05:职务  06：课程资源类型 ',
  `CODE` int(5) DEFAULT NULL COMMENT '类型编码',
  `PARENT_CODE` int(5) DEFAULT NULL COMMENT '父类的类型编码',
  `VALUE` varchar(20) DEFAULT NULL COMMENT '名称，值',
  `LEVEL` int(2) DEFAULT NULL COMMENT '层级，到根节点的层级',
  `STATUS` int(2) DEFAULT '1' COMMENT '状态码。  01可用，02不可用',
  `CREATE_TIME` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '用户ID',
  `DICTIONARY_ID` varchar(10) NOT NULL COMMENT '引用的公共表的主键',
  PRIMARY KEY (`DP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dictionary2_private
-- ----------------------------
INSERT INTO `t_dictionary2_private` VALUES ('', '1', '1', null, '课程类型', '1', '1', null, null, '');

-- ----------------------------
-- Table structure for `t_dictionary2_public`
-- ----------------------------
DROP TABLE IF EXISTS `t_dictionary2_public`;
CREATE TABLE `t_dictionary2_public` (
  `DICTIONARY_ID` varchar(10) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '字典表主键',
  `TYPE` int(2) DEFAULT NULL COMMENT '字典表类型。 01：课程类型   02：授课方式  03：考核类型  04：职称  05:职务  06：课程资源类型',
  `CODE` int(5) DEFAULT NULL COMMENT '类型编码',
  `PARENT_CODE` int(5) DEFAULT NULL COMMENT '父类的类型编码',
  `VALUE` varchar(20) DEFAULT NULL COMMENT '名称，值',
  `LEVEL` int(2) DEFAULT NULL COMMENT '层级，到根节点的层级',
  `STATUS` int(2) DEFAULT '1' COMMENT '状态码。  01可用，02不可用',
  `CREATE_TIME` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `SCHOOL_ID` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`DICTIONARY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dictionary2_public
-- ----------------------------
INSERT INTO `t_dictionary2_public` VALUES ('1', '1', '1', null, '课程类型', '1', '1', null, '');
INSERT INTO `t_dictionary2_public` VALUES ('10', '1', '10', '1', '专业基础课', '2', '1', null, '4');
INSERT INTO `t_dictionary2_public` VALUES ('11', '1', '11', '1', '公共必修课', '2', '1', null, '4');
INSERT INTO `t_dictionary2_public` VALUES ('12', '2', '12', '2', '上机', '2', '1', null, null);
INSERT INTO `t_dictionary2_public` VALUES ('13', '2', '13', '2', '实验', '2', '1', null, null);
INSERT INTO `t_dictionary2_public` VALUES ('2', '2', '2', null, '授课方式', '1', '1', null, null);
INSERT INTO `t_dictionary2_public` VALUES ('3', '3', '3', null, '考核类型', '1', '1', null, null);
INSERT INTO `t_dictionary2_public` VALUES ('4', '4', '4', null, '职称', '1', '1', null, null);
INSERT INTO `t_dictionary2_public` VALUES ('5', '5', '5', null, '职务', '1', '1', null, null);
INSERT INTO `t_dictionary2_public` VALUES ('6', '6', '6', null, '课程资源类型', '1', '1', null, null);
INSERT INTO `t_dictionary2_public` VALUES ('7', '7', '7', null, '电子邮箱', '1', '1', null, null);
INSERT INTO `t_dictionary2_public` VALUES ('8', '8', '8', null, '联系电话', '1', '1', null, null);
INSERT INTO `t_dictionary2_public` VALUES ('9', '9', '9', null, 'IM', '1', '1', null, null);

-- ----------------------------
-- Table structure for `t_file`
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE `t_file` (
  `File_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '资源表主键',
  `DATA_ID` varchar(10) DEFAULT NULL COMMENT '文件所属数据ID',
  `FILE_NAME` varchar(100) DEFAULT NULL COMMENT '资源原始名称',
  `SERVER_NAME` varchar(10) DEFAULT NULL COMMENT '服务器存储名称',
  `IS_COURSE_FILE` int(2) DEFAULT NULL COMMENT '是否为课程资源   01：是   02：不是',
  `VOCABULARY_ID` varchar(2) DEFAULT NULL COMMENT '课程资源类型ID',
  `FILE_AUTH` varchar(2) DEFAULT NULL COMMENT '资源权限（01公开 02不公开）',
  PRIMARY KEY (`File_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_file
-- ----------------------------
INSERT INTO `t_file` VALUES ('bb4abZWKkS', 'qJTKcpG0Mc', 'Keep You In My Heart v5(1).mp3', 'ThowHSB6ga', '1', '01', '01');
INSERT INTO `t_file` VALUES ('icvdRNf0Wn', 'qJTKcpG0Mc', 'TODO.txt', '1i4gx9lVHB', '1', '01', '01');
INSERT INTO `t_file` VALUES ('w3qphYG0PH', '1', '颈椎健康操.mp4', 'focg2j7w4o', '1', '01', '01');

-- ----------------------------
-- Table structure for `t_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
  `LOG_ID` varchar(10) NOT NULL COMMENT '日志ID',
  `STU_ID` varchar(10) DEFAULT NULL COMMENT '学生id',
  `NOTICE_ID` varchar(10) DEFAULT NULL COMMENT '通知ID',
  `TIME` varchar(20) DEFAULT NULL COMMENT '时间',
  `TYPE` int(2) DEFAULT NULL COMMENT '日志类型。    01：通知查看日志',
  PRIMARY KEY (`LOG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_major`
-- ----------------------------
DROP TABLE IF EXISTS `t_major`;
CREATE TABLE `t_major` (
  `MAJOR_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '专业表主键',
  `MAJOR_NAME` varchar(80) DEFAULT NULL COMMENT '专业名称',
  `PARENT_ID` varchar(10) DEFAULT NULL COMMENT '父专业ID',
  PRIMARY KEY (`MAJOR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_major
-- ----------------------------
INSERT INTO `t_major` VALUES ('01', '哲学', '0');
INSERT INTO `t_major` VALUES ('0101', '哲学类', '01');
INSERT INTO `t_major` VALUES ('010101', '哲学', '0101');
INSERT INTO `t_major` VALUES ('010102', '逻辑学', '0101');
INSERT INTO `t_major` VALUES ('010103K', '宗教学', '0101');
INSERT INTO `t_major` VALUES ('02', '经济学', '0');
INSERT INTO `t_major` VALUES ('0201', '经济学类', '02');
INSERT INTO `t_major` VALUES ('020101', '经济学', '0201');
INSERT INTO `t_major` VALUES ('020102', '经济统计学', '0201');
INSERT INTO `t_major` VALUES ('0202', '财政学类', '02');
INSERT INTO `t_major` VALUES ('020201K', '财政学', '0202');
INSERT INTO `t_major` VALUES ('020202', '税收学', '0202');
INSERT INTO `t_major` VALUES ('0203', '金融学类', '02');
INSERT INTO `t_major` VALUES ('020301K', '金融学', '0203');
INSERT INTO `t_major` VALUES ('020302', '金融工程', '0203');
INSERT INTO `t_major` VALUES ('020303', '保险学', '0203');
INSERT INTO `t_major` VALUES ('020304', '投资学', '0203');
INSERT INTO `t_major` VALUES ('0204', '经济与贸易类', '02');
INSERT INTO `t_major` VALUES ('020401', '国际经济与贸易类', '0204');
INSERT INTO `t_major` VALUES ('020402', '贸易经济', '0204');

-- ----------------------------
-- Table structure for `t_message`
-- ----------------------------
DROP TABLE IF EXISTS `t_message`;
CREATE TABLE `t_message` (
  `MSG_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '消息表主键',
  `FORM_USER_ID` varchar(10) DEFAULT NULL COMMENT '发送人ID',
  `TO_USER_ID` varchar(10) DEFAULT NULL COMMENT '接受人ID',
  `CONTENT` varchar(400) DEFAULT NULL COMMENT '消息内容',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `IS_HAVE_FILE` bit(2) DEFAULT NULL COMMENT '该消息是否带有附件',
  `PARENT_MSG_ID` varchar(10) DEFAULT NULL COMMENT '对应的messageID',
  PRIMARY KEY (`MSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_message
-- ----------------------------

-- ----------------------------
-- Table structure for `t_note`
-- ----------------------------
DROP TABLE IF EXISTS `t_note`;
CREATE TABLE `t_note` (
  `NOTE_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '笔记表主键',
  `FILE_ID` varchar(10) DEFAULT NULL COMMENT '附件ID',
  `CONTENT` varchar(400) DEFAULT NULL COMMENT '笔记内容',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '创建人ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `IS_KEY` bit(1) DEFAULT b'0' COMMENT '是否标记为重点资源（0:不标记  1：标记）',
  PRIMARY KEY (`NOTE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_note
-- ----------------------------

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `NOTICE_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '通知表主键',
  `COURSE_ID` varchar(10) DEFAULT NULL COMMENT '通知课程ID',
  `TITLE` varchar(80) DEFAULT NULL COMMENT '通知标题',
  `CONTENT` text COMMENT '内容',
  `PUBLISH_TIME` datetime DEFAULT NULL COMMENT '发布时间',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '发布人ID',
  `STATUS` int(2) DEFAULT NULL COMMENT '通知状态',
  PRIMARY KEY (`NOTICE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', 'qJTKcpG0Mc', '测试通知标题', '123', '2016-06-24 11:11:41', '2016-06-16 17:07:27', 'Qsq73xbQDS', null);
INSERT INTO `t_notice` VALUES ('FrBhdpvRTR', 'DDJHAT0SKb', '啊啊', '是', '2016-06-17 09:52:28', '2016-06-17 09:52:28', 'Qsq73xbQDS', null);
INSERT INTO `t_notice` VALUES ('iYM4nLL0IM', 'qJTKcpG0Mc', '啊啊啊啊啊啊啊啊啊', '试试', '2016-06-18 09:52:42', '2016-06-17 09:52:55', 'Qsq73xbQDS', null);
INSERT INTO `t_notice` VALUES ('timz2XxiLO', 'DDJHAT0SKb', 'hhhh', 'hhh', '2016-06-24 10:32:39', '2016-06-24 10:32:39', 'Qsq73xbQDS', null);

-- ----------------------------
-- Table structure for `t_post`
-- ----------------------------
DROP TABLE IF EXISTS `t_post`;
CREATE TABLE `t_post` (
  `POST_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '同学帮表主键',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '创建用户',
  `CONTENT` varchar(400) DEFAULT NULL COMMENT '内容',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `FILE_ID` varchar(10) DEFAULT NULL COMMENT '附件id',
  PRIMARY KEY (`POST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_post
-- ----------------------------
INSERT INTO `t_post` VALUES ('1', '2', '哈哈大家好', '2016-07-06 14:12:26', null);

-- ----------------------------
-- Table structure for `t_post_activity`
-- ----------------------------
DROP TABLE IF EXISTS `t_post_activity`;
CREATE TABLE `t_post_activity` (
  `PA_ID` varchar(10) NOT NULL,
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '用户id',
  `ACTIVITY` int(10) DEFAULT NULL COMMENT '用户热度(点赞+1，评论+2)',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '生成时间',
  `TARGET_ID` varchar(10) DEFAULT NULL COMMENT '使用户产生活动的目标对象',
  PRIMARY KEY (`PA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_post_activity
-- ----------------------------

-- ----------------------------
-- Table structure for `t_post_like`
-- ----------------------------
DROP TABLE IF EXISTS `t_post_like`;
CREATE TABLE `t_post_like` (
  `PL_ID` varchar(10) DEFAULT NULL COMMENT '学生帮点赞表主键',
  `POST_ID` varchar(10) DEFAULT NULL COMMENT '同学帮主键',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '用户ID',
  `CREATE_TIME` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_post_like
-- ----------------------------

-- ----------------------------
-- Table structure for `t_post_reply`
-- ----------------------------
DROP TABLE IF EXISTS `t_post_reply`;
CREATE TABLE `t_post_reply` (
  `PR_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '同学帮回复表主键',
  `POST_ID` varchar(10) DEFAULT NULL COMMENT '同学帮ID',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '回复人ID',
  `PARENT_PR_ID` varchar(10) DEFAULT NULL COMMENT '评论的POST_REPlY的ID（对POST的评论此栏位为空）',
  `CONTENT` varchar(400) DEFAULT NULL COMMENT '回复内容',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`PR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_post_reply
-- ----------------------------

-- ----------------------------
-- Table structure for `t_school`
-- ----------------------------
DROP TABLE IF EXISTS `t_school`;
CREATE TABLE `t_school` (
  `SCHOOL_ID` varchar(10) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '字典表主键',
  `TYPE` int(2) DEFAULT NULL COMMENT '字典表类型。  01：省，02：市，03：学校，04：教学楼',
  `CODE` int(5) DEFAULT NULL COMMENT '类型编码',
  `PARENT_CODE` int(5) DEFAULT NULL COMMENT '父类的类型编码',
  `VALUE` varchar(20) DEFAULT NULL COMMENT '名称，值',
  `LEVEL` int(2) DEFAULT NULL COMMENT '层级，到根节点的层级',
  `STATUS` int(2) DEFAULT '1' COMMENT '状态码。  01可用，02不可用',
  PRIMARY KEY (`SCHOOL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_school
-- ----------------------------
INSERT INTO `t_school` VALUES ('1', '1', '1', null, '河北省', '1', '1');
INSERT INTO `t_school` VALUES ('2', '1', '2', null, '山东省', '1', '1');
INSERT INTO `t_school` VALUES ('3', '2', '3', '1', '石家庄市', '2', '1');
INSERT INTO `t_school` VALUES ('4', '3', '4', '3', '石家庄学院', '3', '1');
INSERT INTO `t_school` VALUES ('5', '2', '5', '2', '青岛市', '2', '1');
INSERT INTO `t_school` VALUES ('6', '3', '6', '5', '青岛大学', '3', '1');

-- ----------------------------
-- Table structure for `t_school_major`
-- ----------------------------
DROP TABLE IF EXISTS `t_school_major`;
CREATE TABLE `t_school_major` (
  `SM_ID` varchar(10) NOT NULL COMMENT '学校-专业关联表id',
  `SCHOOL_ID` varchar(10) DEFAULT NULL,
  `MAJOR_ID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`SM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_school_major
-- ----------------------------

-- ----------------------------
-- Table structure for `t_score`
-- ----------------------------
DROP TABLE IF EXISTS `t_score`;
CREATE TABLE `t_score` (
  `SCORE_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '成绩表主键',
  `STU_ID` varchar(10) DEFAULT NULL COMMENT '学生ID',
  `COURSE_ID` varchar(10) DEFAULT NULL COMMENT '课程ID',
  `CS_ID` varchar(10) DEFAULT NULL COMMENT '课程成绩组成ID',
  `SCORE` varchar(3) DEFAULT NULL COMMENT '分数',
  PRIMARY KEY (`SCORE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_score
-- ----------------------------
INSERT INTO `t_score` VALUES ('1SutOuBGC8', '1', 'qJTKcpG0Mc', null, null);
INSERT INTO `t_score` VALUES ('3OA8LaGVAS', '1', 'qJTKcpG0Mc', null, null);
INSERT INTO `t_score` VALUES ('C77BS0azpZ', '2', 'qJTKcpG0Mc', null, null);
INSERT INTO `t_score` VALUES ('cBqN53Z6pB', '2', 'qJTKcpG0Mc', null, null);
INSERT INTO `t_score` VALUES ('Cyui6REbs7', '3', 'qJTKcpG0Mc', null, null);
INSERT INTO `t_score` VALUES ('eSPloPZWHu', '1', 'qJTKcpG0Mc', null, null);
INSERT INTO `t_score` VALUES ('kgLQs0Muwl', '1', 'qJTKcpG0Mc', null, null);
INSERT INTO `t_score` VALUES ('LgiCLLLsYt', '1', 'qJTKcpG0Mc', null, null);
INSERT INTO `t_score` VALUES ('r21z01NzfZ', '2', 'qJTKcpG0Mc', null, null);
INSERT INTO `t_score` VALUES ('roQUrlG1aj', '3', 'qJTKcpG0Mc', null, null);
INSERT INTO `t_score` VALUES ('SpcquUvRB6', '2', 'qJTKcpG0Mc', null, null);
INSERT INTO `t_score` VALUES ('wSg5Tjkchb', '3', 'qJTKcpG0Mc', null, null);

-- ----------------------------
-- Table structure for `t_sign_in`
-- ----------------------------
DROP TABLE IF EXISTS `t_sign_in`;
CREATE TABLE `t_sign_in` (
  `SIGN_ID` varchar(10) NOT NULL,
  `COURSE_ID` varchar(10) DEFAULT NULL COMMENT '课程id',
  `CURRENTWEEK` varchar(20) DEFAULT NULL COMMENT '第几周',
  `CURRENTCELL` varchar(20) DEFAULT NULL COMMENT '第几节课',
  `STUDENT_ID` varchar(10) DEFAULT NULL COMMENT '学生id',
  `STATUS` int(2) DEFAULT NULL COMMENT '签到状态【0未签到，1已签到】',
  PRIMARY KEY (`SIGN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_sign_in
-- ----------------------------

-- ----------------------------
-- Table structure for `t_status`
-- ----------------------------
DROP TABLE IF EXISTS `t_status`;
CREATE TABLE `t_status` (
  `WS_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '学生作业完成标识主键',
  `WORK_ID` varchar(10) DEFAULT NULL COMMENT '作业ID',
  `STU_ID` varchar(10) DEFAULT NULL COMMENT '学生用户ID',
  PRIMARY KEY (`WS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_status
-- ----------------------------
INSERT INTO `t_status` VALUES ('O4qrxk2p9a', 'roDpTAmJG0', '2');

-- ----------------------------
-- Table structure for `t_student`
-- ----------------------------
DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student` (
  `STU_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '学生表主键',
  `CLASS_ID` varchar(10) DEFAULT NULL COMMENT '班级ID',
  `STU_NO` varchar(40) DEFAULT NULL COMMENT '学号',
  `STU_NAME` varchar(8) DEFAULT NULL COMMENT '姓名',
  `SEX` varchar(2) DEFAULT NULL,
  `SCHOOL_ID` varchar(40) DEFAULT NULL COMMENT '所属学校ID',
  `FACULTY` varchar(40) DEFAULT NULL COMMENT '院系',
  `IM_ID` varchar(20) DEFAULT NULL,
  `PHONE_ID` varchar(80) DEFAULT NULL,
  `EMAIL_ID` varchar(80) DEFAULT NULL,
  `PICTURE` varchar(20) DEFAULT NULL COMMENT '头像文件路径',
  PRIMARY KEY (`STU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_student
-- ----------------------------
INSERT INTO `t_student` VALUES ('2', '1', '1', '梁晨', null, null, null, null, null, null, null);
INSERT INTO `t_student` VALUES ('22', '1', '2', '张博1111', null, null, null, null, null, null, null);
INSERT INTO `t_student` VALUES ('3', '1', '3', '安建国', null, null, null, null, null, null, null);
INSERT INTO `t_student` VALUES ('4', '1', '4', '李斌', null, null, null, null, null, null, null);
INSERT INTO `t_student` VALUES ('5', '1', '5', '李明扬', null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `t_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher` (
  `TEACHER_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '教室表主键',
  `TEACHER_NO` varchar(40) DEFAULT NULL COMMENT '教工号',
  `NAME` varchar(20) DEFAULT NULL COMMENT '姓名',
  `SEX` varchar(2) DEFAULT NULL COMMENT '性别',
  `TITLE_ID` varchar(40) DEFAULT NULL COMMENT '职称ID',
  `POST_ID` varchar(40) DEFAULT NULL COMMENT '职务ID',
  `SCHOOL_ID` varchar(40) DEFAULT NULL COMMENT '学校ID',
  `DEPARTMENT` varchar(40) DEFAULT NULL COMMENT '院系',
  `EMAIL_ID` varchar(100) DEFAULT NULL COMMENT '（多个用‘||’分开）',
  `PHONE_ID` varchar(100) DEFAULT NULL COMMENT '（多个用‘||’分开）',
  `IM_ID` varchar(20) DEFAULT NULL COMMENT 'IM',
  `INTRODUCTION` varchar(400) DEFAULT NULL COMMENT '简介',
  `PICTURE` varchar(20) DEFAULT NULL COMMENT '用户头像所在路径',
  PRIMARY KEY (`TEACHER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_teacher
-- ----------------------------
INSERT INTO `t_teacher` VALUES ('CYXvQXi5Gv', '110', '张三', '1', '高级工程师', '教授', '4', '计算机学院', '', '', '', '这是一个教师信息', null);
INSERT INTO `t_teacher` VALUES ('GevJsmPZP9', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_teacher` VALUES ('nJ5PM5pbnr', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_teacher` VALUES ('pbwkUFmwwi', null, null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `t_teacher` VALUES ('Qsq73xbQDS', '566', '张老师', '1', '高级项目经理', '技术架构师', '001', '', '123@qq.com||caojian@turing.com', '15566667777||03108887775', '88888888', '', null);
INSERT INTO `t_teacher` VALUES ('uIA6rBDTRw', null, null, null, null, null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for `t_term`
-- ----------------------------
DROP TABLE IF EXISTS `t_term`;
CREATE TABLE `t_term` (
  `TERM_ID` varchar(10) NOT NULL COMMENT '学期ID',
  `TERM_NAME` varchar(20) DEFAULT NULL COMMENT '学期名称：2015-2016学年第1学期',
  `START_DATE` varchar(10) DEFAULT NULL,
  `END_DATE` varchar(10) DEFAULT NULL,
  `WEEK_COUNT` int(3) DEFAULT NULL,
  `SCHOOL_ID` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`TERM_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_term
-- ----------------------------
INSERT INTO `t_term` VALUES ('1', '2015-2016学年第一学期', '2015-09-01', '2016-01-11', '19', '001');
INSERT INTO `t_term` VALUES ('2', '2015-2016学年第二学期', '2016-02-23', '2016-06-27', '18', '001');

-- ----------------------------
-- Table structure for `t_term_private`
-- ----------------------------
DROP TABLE IF EXISTS `t_term_private`;
CREATE TABLE `t_term_private` (
  `TP_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '学期表主键',
  `START_DATE` varchar(10) DEFAULT NULL COMMENT '学期开始日期 yyyy-MM-dd',
  `END_DATE` varchar(10) DEFAULT NULL COMMENT '学期结束日期 yyyy-MM-dd',
  `WEEK_COUNT` int(3) DEFAULT NULL COMMENT '周数',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '所属用户ID',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `TREM_ID` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`TP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_term_private
-- ----------------------------
INSERT INTO `t_term_private` VALUES ('Llnh9RMuKq', '2015-09-01', '2016-01-22', '20', 'CYXvQXi5Gv', '2016-10-08 17:34:04', '1');

-- ----------------------------
-- Table structure for `t_textbook`
-- ----------------------------
DROP TABLE IF EXISTS `t_textbook`;
CREATE TABLE `t_textbook` (
  `TEXTBOOK_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '教材教辅表主键',
  `COURSE_ID` varchar(10) DEFAULT NULL COMMENT '课程ID',
  `TEXTBOOK_NAME` varchar(40) DEFAULT NULL COMMENT '名称',
  `AUTHOR` varchar(20) DEFAULT NULL COMMENT '作者',
  `PUBLISHER` varchar(40) DEFAULT NULL COMMENT '出版社',
  `EDITION` varchar(20) DEFAULT NULL COMMENT '版次',
  `ISBN` varchar(40) DEFAULT NULL COMMENT '国际标准书号',
  `TEXTBOOK_TYPE` varchar(2) DEFAULT NULL COMMENT '类型（01 教材 02 教辅）',
  PRIMARY KEY (`TEXTBOOK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_textbook
-- ----------------------------
INSERT INTO `t_textbook` VALUES ('6mLaAJYzAp', 'qJTKcpG0Mc', 'æµè¯ææ1', 'æ²åä¿­', 'äººæ°åºçç¤¾', 'ç¬¬ä¸ç', null, '01');
INSERT INTO `t_textbook` VALUES ('m6k1EUc9s2', 'qJTKcpG0Mc', 'æµè¯æè¾3', 'æ²åä¿­', 'äººæ°åºçç¤¾', '8', null, '02');
INSERT INTO `t_textbook` VALUES ('QAS78ecP1f', 'qJTKcpG0Mc', '测试教材A', '啊', '是', '是', '1', '01');
INSERT INTO `t_textbook` VALUES ('qt9kuj0a8b', 'qJTKcpG0Mc', '测试教辅1', '', '', '', '', '02');
INSERT INTO `t_textbook` VALUES ('qwtmHHdgI0', 'qJTKcpG0Mc', '测试教辅2', '', '', '', '', '02');
INSERT INTO `t_textbook` VALUES ('TaUstGmH3m', 'qJTKcpG0Mc', 'æµè¯æè¾1', 'æ²åä¿­', 'äººæ°åºçç¤¾', 'ç¬¬ä¸ç', null, '02');

-- ----------------------------
-- Table structure for `t_timetable`
-- ----------------------------
DROP TABLE IF EXISTS `t_timetable`;
CREATE TABLE `t_timetable` (
  `TIMETABLE_ID` varchar(10) NOT NULL COMMENT '作息时间表主键',
  `SCHOOL_ID` varchar(10) DEFAULT NULL COMMENT '学校ID',
  `END_TIME` varchar(6) DEFAULT NULL COMMENT '课程结束时间',
  `START_TIME` varchar(6) DEFAULT NULL COMMENT '课程开始时间',
  `LESSON_NUMBER` varchar(4) DEFAULT NULL COMMENT '第几节课',
  PRIMARY KEY (`TIMETABLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_timetable
-- ----------------------------

-- ----------------------------
-- Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `USER_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '注册用户表主键',
  `USER_TYPE` char(2) DEFAULT NULL COMMENT '用户类型(01教师 02学生 03管理员)',
  `ACCOUNT` varchar(11) DEFAULT NULL COMMENT '账号',
  `PASSWORD` varchar(32) DEFAULT NULL COMMENT '密码',
  `EMAIL` varchar(40) DEFAULT NULL COMMENT '绑定安全邮箱',
  `CREATE_TIME` varchar(19) DEFAULT NULL COMMENT '注册日期 yyyy-MM-dd HH:mm:ss',
  `LAST_LOGIN_TIME` varchar(19) DEFAULT NULL COMMENT '上次登录时间 yyyy-MM-dd HH:mm:ss',
  `TOKEN` varchar(40) DEFAULT NULL COMMENT '用户令牌',
  `LAST_ACCESS_TIME` varchar(19) DEFAULT NULL COMMENT '最终成功访问时间 yyyy-MM-dd HH:mm:ss',
  `IMEI` varchar(30) DEFAULT NULL COMMENT '设备唯一标识',
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('2', '02', 'student', '96e79218965eb72c92a549dd5a330112', null, null, null, null, null, null);
INSERT INTO `t_user` VALUES ('4jbIg8b8Wa', '01', '123@qq.com', '202cb962ac59075b964b07152d234b70', '01', null, null, null, null, null);
INSERT INTO `t_user` VALUES ('4SIY5lbFnV', '01', 'abcccc', '4124bc0a9335c27f086f24ba207a4912', '01', null, null, null, null, null);
INSERT INTO `t_user` VALUES ('6nY7pzOa7b', '01', '18222962678', '96e79218965eb72c92a549dd5a330112', null, '2016-09-21 16:09:38', null, null, null, null);
INSERT INTO `t_user` VALUES ('CYXvQXi5Gv', '01', '18222962017', '96e79218965eb72c92a549dd5a330112', null, '2016-09-27 16:21:30', '1475915382825', '07b3b230b1701cad6b3d3de040d78376', '1475915382825', '123456789');
INSERT INTO `t_user` VALUES ('GevJsmPZP9', '01', '18233182074', '654321', null, '2016-09-26 16:28:42', '1475891125550', '767c8b4ece849c800ccd11293a9fc1d4', '1475896109133', '123456789');
INSERT INTO `t_user` VALUES ('kWeZeFP0uO', '01', '17633467435', '96e79218965eb72c92a549dd5a330112', '01', null, null, null, null, null);
INSERT INTO `t_user` VALUES ('lmBmefVwh5', '01', '18633467435', '96e79218965eb72c92a549dd5a330112', null, '2016-06-13 11:35:47', null, null, null, null);
INSERT INTO `t_user` VALUES ('nJ5PM5pbnr', '01', '13333333333', '96e79218965eb72c92a549dd5a330112', null, '2016-07-01 17:20:54', null, null, null, null);
INSERT INTO `t_user` VALUES ('peik6w60Yb', '01', '', 'd41d8cd98f00b204e9800998ecf8427e', '01', null, null, null, null, null);
INSERT INTO `t_user` VALUES ('Qsq73xbQDS', '01', 'super', '96e79218965eb72c92a549dd5a330112', '01', null, null, null, null, null);
INSERT INTO `t_user` VALUES ('Sb1Hf94Dpq', '01', '15566668888', '96e79218965eb72c92a549dd5a330112', '我', null, null, null, null, null);

-- ----------------------------
-- Table structure for `t_user_communication`
-- ----------------------------
DROP TABLE IF EXISTS `t_user_communication`;
CREATE TABLE `t_user_communication` (
  `C_ID` varchar(10) NOT NULL COMMENT '用户通讯信息表主键',
  `USER_ID` varchar(10) DEFAULT NULL COMMENT '用户ID',
  `TYPE` int(1) DEFAULT NULL COMMENT '类型：1：电话  2：邮箱   3：IM ',
  `NAME` varchar(6) DEFAULT NULL COMMENT '名称',
  `VALUE` varchar(20) DEFAULT NULL COMMENT '值',
  `STATUS` int(1) DEFAULT NULL COMMENT '状态。（此处指邮箱是否绑定）',
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_communication
-- ----------------------------

-- ----------------------------
-- Table structure for `t_verify_code`
-- ----------------------------
DROP TABLE IF EXISTS `t_verify_code`;
CREATE TABLE `t_verify_code` (
  `CODE_ID` varchar(11) NOT NULL COMMENT 'ID为手机号',
  `VERIFY_CODE` varchar(8) DEFAULT NULL COMMENT '验证码',
  `IMEI` varchar(40) DEFAULT NULL COMMENT '设备标识',
  `TIME` varchar(20) DEFAULT NULL COMMENT '发送的时间',
  `TYPE` int(2) DEFAULT NULL COMMENT '0:用户注册；1：忘记密码',
  PRIMARY KEY (`CODE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_verify_code
-- ----------------------------

-- ----------------------------
-- Table structure for `t_work`
-- ----------------------------
DROP TABLE IF EXISTS `t_work`;
CREATE TABLE `t_work` (
  `WORK_ID` varchar(10) NOT NULL DEFAULT '' COMMENT '作业表主键',
  `COURSE_ID` varchar(10) DEFAULT NULL COMMENT '课程ID',
  `CONTENT` varchar(400) DEFAULT NULL COMMENT '作业内容',
  `PUBLISH_TIME` datetime DEFAULT NULL COMMENT '发布时间',
  `END_TIME` datetime DEFAULT NULL COMMENT '终止时间',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `REMIND_TIME` varchar(3) DEFAULT NULL COMMENT '作业提醒时间',
  `STATUS` int(3) DEFAULT NULL COMMENT '作业状态(1:发布/待发布  2:草稿)',
  PRIMARY KEY (`WORK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_work
-- ----------------------------
INSERT INTO `t_work` VALUES ('iMldgjZkn9', 'qJTKcpG0Mc', '测试啊啊啊', '2016-07-05 10:35:07', null, '2016-07-04 10:34:57', null, null);
INSERT INTO `t_work` VALUES ('mqAUNlDNi6', 'DDJHAT0SKb', '321654987', '2016-09-23 10:47:44', '2016-09-24 10:35:07', '2016-09-23 10:34:03', '10', '0');
INSERT INTO `t_work` VALUES ('qxEsCNVQCp', 'DDJHAT0SKb', 'ä»å¤©æ²¡æçä½ä¸', '2016-09-23 10:35:07', '2016-09-24 10:35:07', '2016-09-23 10:31:05', '10', '1');
INSERT INTO `t_work` VALUES ('roDpTAmJG0', 'qJTKcpG0Mc', '熟悉虚拟机', '2016-07-05 14:25:47', '2016-07-08 14:25:47', '2016-07-04 10:34:41', null, null);
INSERT INTO `t_work` VALUES ('WCZoIiPsmT', 'DDJHAT0SKb', 'ä»å¤©æ²¡æçä½ä¸', '2016-09-23 10:35:07', '2016-09-24 10:35:07', '2016-09-23 10:32:39', '10', '1');
INSERT INTO `t_work` VALUES ('xvjYsncB9w', 'qJTKcpG0Mc', '测试作业内容啊啊', '2016-07-03 10:39:58', '2016-07-07 10:39:58', '2016-07-04 10:40:09', null, null);
