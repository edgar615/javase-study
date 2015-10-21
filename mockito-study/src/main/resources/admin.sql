

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `company_config`
-- ----------------------------
DROP TABLE IF EXISTS `company_config`;
CREATE TABLE `company_config` (
  `CONFIG_ID` int(11) NOT NULL,
  `COMPANY_ID` int(11) DEFAULT NULL,
  `CONFIG_KEY` varchar(16) NOT NULL,
  `CONFIG_VALUE` varchar(16) NOT NULL,
  `DESCRIPTION` varchar(128) DEFAULT NULL,
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CONFIG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='如果COMPANY_ID=-1,则表示是全局配置项';

-- ----------------------------
-- Records of company_config
-- ----------------------------

-- ----------------------------
-- Table structure for `i18n_message`
-- ----------------------------
DROP TABLE IF EXISTS `i18n_message`;
CREATE TABLE `i18n_message` (
  `I18N_ID` int(11) NOT NULL,
  `I18N_KEY` varchar(64) NOT NULL,
  `I18N_VALUE_EN` varchar(64) NOT NULL,
  `I18N_VALUE_ZH_CN` varchar(64) NOT NULL,
  `I18N_VALUE_ZH_TW` varchar(64) NOT NULL,
  `IS_ROOT` tinyint(1) DEFAULT NULL,
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`I18N_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of i18n_message
-- ----------------------------
INSERT INTO `i18n_message` VALUES ('182', 'label.profile.oldpassword', 'Current Password', '原密码', '原密碼', '1', '2014-06-18 16:55:33', '2014-06-28 12:28:15');
INSERT INTO `i18n_message` VALUES ('183', 'label.profile.newpassword', 'New Password', '新密码', '新密碼', '1', '2014-06-18 16:56:59', '2014-06-28 12:28:18');
INSERT INTO `i18n_message` VALUES ('184', 'label.profile.retypepassword', 'Re-type New Password', '重新输入新密码', '重新輸入新密碼', '1', '2014-06-18 16:59:43', '2014-06-28 12:28:34');
INSERT INTO `i18n_message` VALUES ('185', 'header.profile.setnewpassword', 'Sets New Password', '修改密码', '修改密碼', '1', '2014-06-18 17:00:41', '2014-06-28 12:28:35');
INSERT INTO `i18n_message` VALUES ('186', 'header.profile.edit', 'Edit Profile', '个人设置', '個人設置', '1', '2014-06-18 17:01:52', '2014-06-28 12:28:36');
INSERT INTO `i18n_message` VALUES ('187', 'title.profile.edit', 'Edit Profile', '个人设置', '個人設置', '1', '2014-06-18 17:04:21', '2014-06-28 12:28:39');
INSERT INTO `i18n_message` VALUES ('188', 'label.common.lang', 'Language', '语言', '語言', '1', '2014-06-18 17:05:00', '2014-06-28 12:28:40');
INSERT INTO `i18n_message` VALUES ('207', 'msg.delete.success', 'Delete Success', '删除成功', '刪除成功', '1', '2014-06-18 20:30:31', '2014-06-28 12:28:41');
INSERT INTO `i18n_message` VALUES ('212', 'False', 'False', '否', '否', '1', '2014-06-18 21:48:27', '2014-06-28 12:28:42');
INSERT INTO `i18n_message` VALUES ('213', 'True', 'True', '是', '是', '1', '2014-06-18 21:48:45', '2014-06-28 12:28:42');
INSERT INTO `i18n_message` VALUES ('228', 'msg.logout.success', 'Logout Success', '注销成功', '註銷成功', '1', '2014-06-19 16:46:12', '2014-06-28 12:28:43');
INSERT INTO `i18n_message` VALUES ('229', 'msg.logout.failed', 'Logout Failed', '注销失败', '註銷失敗', '1', '2014-06-19 16:51:29', '2014-06-28 12:28:44');
INSERT INTO `i18n_message` VALUES ('235', 'Open an Account', 'Open an Account', '开户', '開戶', '0', '2014-06-27 15:44:41', '2014-06-30 08:59:33');
INSERT INTO `i18n_message` VALUES ('236', 'label.index.delivery', 'Delivery', '押运', '押運', '0', '2014-06-27 15:46:21', '2014-06-28 12:28:48');
INSERT INTO `i18n_message` VALUES ('237', 'label.index.account.cancel', 'Account Cancellation', '销户', '銷戶', '0', '2014-06-27 15:47:55', '2014-06-28 12:28:51');
INSERT INTO `i18n_message` VALUES ('251', 'Price Package Management', 'Price Package Management', '套餐管理', '套餐管理', '0', '2014-06-27 16:04:35', '2014-06-28 12:29:03');
INSERT INTO `i18n_message` VALUES ('256', 'Customer Management', 'Customer Management', '客户管理', '客戶管理', '0', '2014-06-27 16:08:23', '2014-06-28 12:29:07');
INSERT INTO `i18n_message` VALUES ('276', 'Mobile Safe Management', 'Mobile Safe Management', '保管箱管理', '保管箱管理', '0', '2014-06-27 16:43:39', '2014-06-28 12:29:14');
INSERT INTO `i18n_message` VALUES ('285', 'label.sys.i18n.isRoot', 'System Resource', '系统资源', '系統資源', '1', '2014-06-28 12:25:34', '2014-06-28 12:25:34');
INSERT INTO `i18n_message` VALUES ('286', 'label.index.account.new', 'Open an Account', '开户', '開戶', '0', '2014-06-30 09:01:44', '2014-06-30 09:01:44');
INSERT INTO `i18n_message` VALUES ('290', 'Basic Data', 'Basic Data', '基础数据', '基礎數據', '1', '2014-06-30 09:18:54', '2014-06-30 09:18:54');
INSERT INTO `i18n_message` VALUES ('291', 'Company Management', 'Company Management', '运营公司管理', '運營公司管理', '1', '2014-06-30 09:19:12', '2014-06-30 09:19:12');
INSERT INTO `i18n_message` VALUES ('314', 'help.common.number', 'Please enter a positive real number with two decimal places', '请输入有两位小数的正实数', '請輸入有兩位小數的正實數', '1', '2014-06-30 15:06:31', '2014-06-30 15:06:31');
INSERT INTO `i18n_message` VALUES ('315', 'help.common.maxlength8', 'Please enter no more than 8 characters', '请输入一个长度最多是8的字符串', '請輸入長度不大於8的字符串', '1', '2014-06-30 15:09:16', '2014-06-30 15:09:16');
INSERT INTO `i18n_message` VALUES ('317', 'help.common.integer', 'Please enter an integer', '请输入整数', '請輸入整數', '1', '2014-06-30 15:14:43', '2014-06-30 15:14:43');
INSERT INTO `i18n_message` VALUES ('318', 'help.common.positiveInteger', 'Please enter a positive integer', '请输入正整数', '請輸入正整數', '1', '2014-06-30 15:16:54', '2014-06-30 15:16:54');
INSERT INTO `i18n_message` VALUES ('319', 'help.common.maxlength3', 'Please enter no more than 3 characters', '请输入一个长度最多是3的字符串', '請輸入長度不大於3的字符串', '1', '2014-06-30 15:20:06', '2014-06-30 15:20:06');
INSERT INTO `i18n_message` VALUES ('324', 'label.customer.idCardNo', 'ID Card No.', '身份证号', '身份證號', '0', '2014-06-30 16:08:42', '2014-06-30 16:08:42');
INSERT INTO `i18n_message` VALUES ('325', 'help.common.maxlength18', 'Please enter no more than 18 characters', '请输入一个长度最多是18的字符串', '請輸入長度不大於18的字符串', '0', '2014-06-30 16:09:55', '2014-06-30 16:09:55');
INSERT INTO `i18n_message` VALUES ('326', 'label.common.op.submit', 'Submit', '提交', '提交', '1', '2014-06-30 16:19:56', '2014-07-01 12:55:51');
INSERT INTO `i18n_message` VALUES ('327', 'header.customer.info', 'Customer Information', '客户信息', '客戶信息', '0', '2014-06-30 16:21:14', '2014-06-30 16:21:14');
INSERT INTO `i18n_message` VALUES ('328', 'label.customer.customerName', 'Customer Name', '客户名称', '客戶名稱', '0', '2014-06-30 16:21:54', '2014-06-30 16:21:54');
INSERT INTO `i18n_message` VALUES ('329', 'label.customer.mobileNo', 'Mobile No.', '手机号', '手機號', '0', '2014-06-30 16:25:43', '2014-06-30 16:25:43');
INSERT INTO `i18n_message` VALUES ('330', 'label.customer.address', 'Address', '地址', '地址', '0', '2014-06-30 16:40:58', '2014-06-30 16:40:58');
INSERT INTO `i18n_message` VALUES ('332', 'query.commodity.simple.commodityName', 'Please input commodity name', '请输入商品名称', '請輸入商品名稱', '0', '2014-07-01 09:09:17', '2014-07-01 09:10:06');
INSERT INTO `i18n_message` VALUES ('334', 'label.mobilesafe.safeNo', 'Mobile Safe No.', '保管箱编号', '保管箱編號', '0', '2014-07-01 10:14:14', '2014-07-01 10:30:32');
INSERT INTO `i18n_message` VALUES ('335', 'label.mobilesafe.tariff', 'Tariff', '资费', '資費', '0', '2014-07-01 10:22:53', '2014-07-01 10:29:00');
INSERT INTO `i18n_message` VALUES ('336', 'label.mobilesafe.purchaseTime', 'The time of purchase', '购买时间', '購買時間', '0', '2014-07-01 10:24:17', '2014-07-01 10:29:05');
INSERT INTO `i18n_message` VALUES ('337', 'label.mobilesafe.list', 'Mobile Safe List', '保管箱列表', '保管箱列表', '0', '2014-07-01 10:29:49', '2014-07-01 10:29:49');
INSERT INTO `i18n_message` VALUES ('342', 'label.customer.nocustomer', 'The customer does not exist', '客户不存在', '客戶不存在', '0', '2014-07-01 10:47:52', '2014-07-01 10:47:52');
INSERT INTO `i18n_message` VALUES ('350', 'label.common.op.enquiry', 'Enquiry', '查询', '查詢', '1', '2014-07-01 12:50:17', '2014-07-01 12:50:17');
INSERT INTO `i18n_message` VALUES ('360', 'Task List', 'Task List', '任务列表', '任務列表', '1', '2014-07-02 11:54:44', '2014-07-02 13:23:56');
INSERT INTO `i18n_message` VALUES ('364', 'title.order.task', 'Task Management', '任务管理', '任務管理', '0', '2014-07-02 13:42:18', '2014-07-02 13:42:18');
INSERT INTO `i18n_message` VALUES ('365', 'header.order.task.list', 'Task List', '任务列表', '任務列表', '0', '2014-07-02 13:42:53', '2014-07-02 13:42:53');
INSERT INTO `i18n_message` VALUES ('366', 'label.order.task.state', 'Task Status', '任务状态', '任務狀態', '0', '2014-07-02 13:44:36', '2014-07-02 13:44:36');
INSERT INTO `i18n_message` VALUES ('367', 'label.order.task.type', 'Task Type', '任务类型', '任務類型', '0', '2014-07-02 14:06:00', '2014-07-02 14:06:00');
INSERT INTO `i18n_message` VALUES ('368', 'label.order.task.allocationTime', 'Allocation Time', '分配时间', '分配時間', '0', '2014-07-02 14:32:33', '2014-07-02 14:32:33');
INSERT INTO `i18n_message` VALUES ('369', 'Task Handle', 'Task Handle', '任务处理', '任務處理', '0', '2014-07-02 14:56:22', '2014-07-02 14:56:22');
INSERT INTO `i18n_message` VALUES ('370', 'label.order.task.handle', 'Task Handle', '任务处理', '任務處理', '0', '2014-07-02 14:58:00', '2014-07-02 14:58:00');
INSERT INTO `i18n_message` VALUES ('373', 'title.order.task.handle', 'Task Handle', '任务处理', '任務處理', '0', '2014-07-02 15:21:04', '2014-07-02 15:21:04');
INSERT INTO `i18n_message` VALUES ('374', 'header.order.task.handle', 'Task Handle', '任务处理', '任務處理', '0', '2014-07-02 15:21:56', '2014-07-02 15:21:56');
INSERT INTO `i18n_message` VALUES ('375', 'label.order.task.comment', 'Comment', '备注', '備註', '0', '2014-07-02 15:24:25', '2014-07-02 15:24:25');
INSERT INTO `i18n_message` VALUES ('376', 'header.order.task.info', 'Task Information', '任务详情', '任務詳情', '0', '2014-07-02 16:08:15', '2014-07-02 16:08:15');
INSERT INTO `i18n_message` VALUES ('377', 'Today', 'Today', '今天', '今天', '1', '2014-07-03 17:26:49', '2014-07-03 17:26:49');
INSERT INTO `i18n_message` VALUES ('378', 'Yesterday', 'Yesterday', '昨天', '昨天', '1', '2014-07-03 17:27:10', '2014-07-03 17:27:10');
INSERT INTO `i18n_message` VALUES ('379', 'Last 7 Days', 'Last 7 Days', '最近7天', '最近7天', '1', '2014-07-03 17:27:31', '2014-07-03 17:27:31');
INSERT INTO `i18n_message` VALUES ('380', 'Last 30 Days', 'Last 30 Days', '最近30天', '最近30天', '0', '2014-07-03 17:27:50', '2014-07-03 17:27:50');
INSERT INTO `i18n_message` VALUES ('381', 'This Month', 'This Month', '本月', '本月', '1', '2014-07-03 17:28:09', '2014-07-03 17:28:09');
INSERT INTO `i18n_message` VALUES ('382', 'Last Month', 'Last Month', '上月', '上月', '1', '2014-07-03 17:28:26', '2014-07-03 17:28:26');
INSERT INTO `i18n_message` VALUES ('383', 'label.common.op.accept', 'Accept', '确定', '確定', '1', '2014-07-03 20:41:20', '2014-07-03 20:42:00');
INSERT INTO `i18n_message` VALUES ('384', 'label.common.from', 'From', '从', '從', '1', '2014-07-03 20:52:16', '2014-07-03 20:52:16');
INSERT INTO `i18n_message` VALUES ('385', 'label.common.to', 'To', '到', '到', '1', '2014-07-03 20:52:58', '2014-07-03 20:52:58');
INSERT INTO `i18n_message` VALUES ('386', 'label.common.range', 'Custom Range', '自定义范围', '自定義範圍', '1', '2014-07-03 20:54:50', '2014-07-03 20:54:50');
INSERT INTO `i18n_message` VALUES ('389', 'label.common.op.view', 'View', '查看', '查看', '1', '2014-07-04 13:15:48', '2014-07-04 13:15:48');
INSERT INTO `i18n_message` VALUES ('390', 'Customer List', 'Customer List', '客户列表', '客戶列表', '0', '2014-07-04 13:59:52', '2014-07-04 13:59:52');
INSERT INTO `i18n_message` VALUES ('393', 'View Customer', 'View Customer', '查看客户', '查看客戶', '0', '2014-07-04 14:00:32', '2014-07-04 14:00:32');
INSERT INTO `i18n_message` VALUES ('395', 'Edit Customer', 'Edit Customer', '修改客户', '修改客戶', '0', '2014-07-04 14:00:59', '2014-07-04 14:00:59');
INSERT INTO `i18n_message` VALUES ('399', 'title.customer', 'Customer Management', '客户管理', '客戶管理', '0', '2014-07-04 14:42:45', '2014-07-04 14:42:45');
INSERT INTO `i18n_message` VALUES ('400', 'header.customer.list', 'Customer List', '客户列表', '客戶列表', '0', '2014-07-04 14:43:16', '2014-07-04 14:43:16');
INSERT INTO `i18n_message` VALUES ('401', 'label.customer.state', 'Customer Status', '客户状态', '客戶狀態', '0', '2014-07-04 14:47:55', '2014-07-04 14:47:55');
INSERT INTO `i18n_message` VALUES ('402', 'label.common.jumpTo', 'Jump to', '跳转到', '跳轉到', '1', '2014-07-04 15:54:26', '2014-07-04 15:54:26');
INSERT INTO `i18n_message` VALUES ('403', 'title.customer.edit', 'Edit Customer', '修改客户', '修改客戶', '0', '2014-07-04 17:25:42', '2014-07-04 17:25:42');
INSERT INTO `i18n_message` VALUES ('522', 'label.customer.arrears.amount', 'The amount of arrears', '欠费金额', '欠費金額', '0', '2014-07-07 14:49:19', '2014-07-07 14:49:19');
INSERT INTO `i18n_message` VALUES ('533', 'Model Management', 'Model Management', '型号管理', '型號管理', '0', '2014-07-07 16:06:36', '2014-07-07 16:06:36');
INSERT INTO `i18n_message` VALUES ('551', 'label.sys.i18n.key', 'Key', '键', '鍵', '1', '2014-06-13 22:30:12', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('552', 'label.common.lang.English', 'English', '英语', '英語', '1', '2014-06-13 22:33:03', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('553', 'label.common.lang.zh_CN', 'Simplified Chinese', '简体中文', '簡體中文', '1', '2014-06-13 22:34:17', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('554', 'label.common.lang.zh_TW', 'Traditional Chinese', '繁体中文', '繁體中文', '1', '2014-06-13 22:34:45', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('555', 'label.common.op.delete', 'Delete', '删除所选', '刪除所選', '1', '2014-06-13 22:36:04', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('557', 'label.common.op.edit', 'Edit', '修改', '修改', '1', '2014-06-13 22:40:48', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('558', 'label.sys.i18n.add', 'Add i18n', '新增i18n', '新增i18n', '1', '2014-06-13 22:42:31', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('559', 'label.sys.i18n.savetojson', 'Save JSON', '保存JSON', '保存JSON', '1', '2014-06-13 22:43:25', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('560', 'query.sys.i18n.simple.i18nkey', 'Please input the key of i18n', '请输入i18n的键', '請輸入i18n的鍵', '1', '2014-06-13 22:49:13', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('561', 'header.sys.i18n.list', 'I18n List', 'i18n列表', 'i18n列表', '1', '2014-06-13 22:51:03', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('562', 'title.common.home', 'Home', '首页', '首頁', '1', '2014-06-13 22:52:22', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('563', 'title.sys.i18n', 'I18n Mangement', 'i18n管理', 'i18n管理', '1', '2014-06-13 22:53:38', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('564', 'title.sys.i18n.add', 'Add i18n', '新增i18n', '新增i18n', '1', '2014-06-13 23:21:56', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('565', 'header.sys.i18n.info', 'I18n  Info', 'i18n信息', 'i18n信息', '1', '2014-06-13 23:23:37', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('566', 'label.common.op.save', 'Save', '保存', '保存', '1', '2014-06-13 23:27:42', '2014-07-01 12:55:11');
INSERT INTO `i18n_message` VALUES ('567', 'label.common.op.cancel', 'Cancel', '取消', '取消', '1', '2014-06-13 23:28:42', '2014-07-01 12:51:44');
INSERT INTO `i18n_message` VALUES ('568', 'title.sys.i18n.edit', 'Edit i18n', '修改i18n', '修改i18n', '1', '2014-06-13 23:29:59', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('569', 'help.common.required', 'This field is required', '必填项', '必填項', '1', '2014-06-13 23:34:10', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('570', 'help.common.maxlength64', 'Please enter no more than 64 characters', '请输入一个长度最多是64的字符串', '請輸入長度不大於64的字符串', '1', '2014-06-14 00:11:55', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('571', 'help.common.maxlength32', 'Please enter no more than 32 characters', '请输入一个长度最多是32的字符串', '請輸入長度不大於32的字符串', '1', '2014-06-14 00:12:41', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('572', 'help.common.maxlength16', 'Please enter no more than 16 characters', '请输入一个长度最多是16的字符串', '請輸入長度不大於16的字符串', '1', '2014-06-14 00:13:16', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('573', 'help.sys.i18n.key.unique', 'Key already exists', '键已经存在', '鍵已經存在', '1', '2014-06-14 00:17:08', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('574', 'title.sys.job', 'Job Managment', '作业管理', '作業管理', '1', '2014-06-14 19:19:21', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('575', 'header.sys.job.list', 'Job List', '作业列表', '作業列表', '1', '2014-06-14 19:22:23', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('576', 'label.sys.job.add', 'Add Job', '新增作业', '新增作業', '1', '2014-06-14 19:24:47', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('577', 'query.sys.job.simple.classname', 'Please input the className of job', '请输入作业类', '請輸入作業類', '1', '2014-06-14 19:27:35', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('578', 'label.sys.job.name', 'Job Name', '作业名称', '作業名稱', '1', '2014-06-14 19:30:43', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('579', 'label.sys.job.enable', 'Is Enable', '是否启用', '是否啟用', '1', '2014-06-14 19:33:08', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('580', 'label.sys.job.cron', 'CronTrigger', '触发时间', '觸發時間', '1', '2014-06-14 19:35:03', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('581', 'label.sys.job.classname', 'Class Name', '作业类', '作業類', '1', '2014-06-14 19:35:37', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('582', 'title.sys.job.add', 'Add Job', '新增作业', '新增作業', '1', '2014-06-15 17:02:38', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('583', 'header.sys.job.info', 'Job Information', '作业信息', '作業信息', '1', '2014-06-15 17:03:35', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('584', 'title.sys.job.edit', 'Edit Job', '修改作业', '修改作業', '1', '2014-06-15 17:04:28', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('585', 'help.sys.job.classname.unique', 'Class Name already exists', '作业类已存在', '作業類已存在', '1', '2014-06-15 17:10:16', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('586', 'help.sys.job.classname.pattern', 'Only letter or .', '只能是字母和.的组合', '只能是字母和.的組合', '1', '2014-06-15 17:14:12', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('587', 'title.sys.user', 'User Management', '用户管理', '用戶管理', '1', '2014-06-15 17:27:45', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('588', 'header.sys.user.info', 'User Information', '用户信息', '用戶信息', '1', '2014-06-15 17:30:10', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('589', 'title.sys.user.add', 'Add User', '新增用户', '新增用戶', '1', '2014-06-15 17:31:23', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('590', 'title.sys.user.edit', 'Edit User', '修改用户', '修改用戶', '1', '2014-06-15 17:31:56', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('591', 'header.sys.user.list', 'User List', '用户列表', '用戶列表', '1', '2014-06-15 17:32:50', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('592', 'label.sys.user.add', 'Add User', '新增用户', '新增用戶', '1', '2014-06-15 17:33:40', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('593', 'query.sys.user.simple.username', 'Please input username', '请输入用户名', '請輸入用戶名', '1', '2014-06-15 17:35:15', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('594', 'label.sys.user.username', 'Username', '用户名', '用戶名', '1', '2014-06-15 17:36:04', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('595', 'label.sys.user.fullname', 'Full Name', '姓名', '姓名', '1', '2014-06-15 17:37:07', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('596', 'label.sys.user.password', 'Password', '密码', '密碼', '1', '2014-06-15 17:38:16', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('597', 'label.sys.user.email', 'Email', '电子邮箱', '電子郵箱', '1', '2014-06-15 17:39:20', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('598', 'help.common.email', 'Please enter a valid email', '请输入合法的电子邮箱', '請輸入合法的電子郵箱', '1', '2014-06-15 17:40:58', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('599', 'help.sys.user.username.unique', 'Username already exists', '用户名已经存在', '用戶名已經存在', '1', '2014-06-15 17:42:36', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('600', 'help.sys.user.username.length', 'Please enter a value between 3 and 16 characters long', '请输入一个长度在3和16之间的字符串', '請輸入長度不大於3和16的字符串', '1', '2014-06-15 17:45:37', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('601', 'help.sys.user.password.length', 'Please enter a value between 6 and 16 characters long', '请输入一个长度在6和16之间的字符串', '請輸入長度不大於6和16的字符串', '1', '2014-06-15 17:47:27', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('602', 'help.sys.user.username.pattern', 'Only letter or number or _,start with letter', '以字母开头，由字母、数字、下划线组成', '以字母開頭，由字母、數字、下劃線組成', '1', '2014-06-15 17:49:24', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('603', 'label.sys.user.role', 'Role', '角色', '角色', '1', '2014-06-15 17:50:51', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('604', 'header.sys.role.list', 'Role List', '角色列表', '角色列表', '1', '2014-06-15 18:00:15', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('605', 'header.sys.role.info', 'Role Information', '角色信息', '角色信息', '1', '2014-06-15 18:00:59', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('606', 'title.sys.role.add', 'Add Role', '新增角色', '新增角色', '1', '2014-06-15 18:02:02', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('607', 'title.sys.role.edit', 'Edit Role', '修改角色', '修改角色', '1', '2014-06-15 18:02:50', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('608', 'title.sys.role', 'Role Management', '角色管理', '角色管理', '1', '2014-06-15 18:03:32', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('609', 'label.sys.role.rolename', 'Role Name', '角色名称', '角色名稱', '1', '2014-06-15 18:04:43', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('610', 'label.sys.role.rolecode', 'Role Code', '角色编码', '角色編碼', '1', '2014-06-15 18:05:38', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('611', 'label.sys.role.add', 'Add Role', '新增角色', '新增角色', '1', '2014-06-15 18:07:05', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('612', 'label.sys.role.permission', 'Set Permissions', '设置权限', '設置權限', '1', '2014-06-15 18:07:51', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('613', 'query.sys.role.simple.rolename', 'Please input rolename', '请输入角色名', '請輸入角色名', '1', '2014-06-15 18:10:02', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('614', 'label.sys.resource.name', 'Resource Name', '资源名称', '資源名稱', '1', '2014-06-15 18:11:40', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('615', 'label.sys.route.name', 'Route Name', '路由名称', '路由名稱', '1', '2014-06-15 18:12:45', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('616', 'label.sys.menu.name', 'Menu Name', '菜单名称', '菜單名稱', '1', '2014-06-15 18:14:17', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('617', 'title.sys.route', 'Route Management', '路由管理', '路由管理', '1', '2014-06-15 18:16:33', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('618', 'title.sys.route.add', 'Add Route', '新增路由', '新增路由', '1', '2014-06-15 18:17:26', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('619', 'title.sys.route.edit', 'Edit Route', '修改路由', '修改路由', '1', '2014-06-15 18:18:08', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('620', 'header.sys.route.info', 'Route Info', '路由信息', '路由信息', '1', '2014-06-15 18:19:01', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('621', 'header.sys.route.list', 'Route List', '路由列表', '路由列表', '1', '2014-06-15 18:19:32', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('622', 'label.sys.route.url', 'URL', 'URL', 'URL', '1', '2014-06-15 18:20:41', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('623', 'label.sys.route.add', 'Add Route', '新增路由', '新增路由', '1', '2014-06-15 18:22:45', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('624', 'label.sys.route.relate', 'Relate Resource', '关联资源', '關聯資源', '1', '2014-06-15 18:23:53', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('625', 'query.sys.route.simple.name', 'Please input route name', '请输入路由名称', '請輸入路由名稱', '1', '2014-06-15 18:25:35', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('626', 'title.sys.route.relate', 'Relate Resource', '关联资源', '關聯資源', '1', '2014-06-15 18:28:00', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('627', 'header.sys.route.relate', 'Relate Resource', '关联资源', '關聯資源', '1', '2014-06-15 18:28:45', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('628', 'label.sys.resource.url', 'Resource URL', '资源地址', '資源地址', '1', '2014-06-15 18:29:28', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('629', 'help.sys.route.url', 'Start with / , eg:  /sys/route/list or /sys/route/edit/:route_id', '以/开头，如： /sys/route/list 或 /sys/route/edit/:route_id', '以/開頭，如： /sys/route/list 或/sys/route/edit/:route_id', '1', '2014-06-15 18:32:25', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('630', 'help.common.maxlength128', 'Please enter no more than 128 characters', '请输入一个长度最多是128的字符串', '請輸入長度不大於128的字符串', '1', '2014-06-15 18:34:03', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('631', 'help.sys.route.url.pattern', 'Only url', '请输入合法的地址', '請輸入合法的地址', '1', '2014-06-15 18:35:04', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('632', 'title.sys.menu', 'Menu Management', '菜单管理', '菜單管理', '1', '2014-06-15 18:39:55', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('633', 'title.sys.menu.add', 'Add Menu', '新增菜单', '新增菜單', '1', '2014-06-15 18:40:40', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('634', 'title.sys.menu.edit', 'Edit Menu', '修改菜单', '修改菜單', '1', '2014-06-15 18:41:29', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('635', 'header.sys.menu.info', 'Menu Information', '菜单信息', '菜單信息', '1', '2014-06-15 18:42:04', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('636', 'header.sys.menu.list', 'Menu List', '菜单列表', '菜單列表', '1', '2014-06-15 18:42:53', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('637', 'label.sys.menu.add', 'Add Menu', '新增菜单', '新增菜單', '1', '2014-06-15 18:43:46', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('638', 'label.sys.menu.addchild', 'Add Child', '新增下级菜单', '新增下級菜單', '1', '2014-06-15 18:44:29', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('639', 'label.sys.menu.relate', 'Relate Route', '关联路由', '關聯路由', '1', '2014-06-15 18:45:12', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('640', 'label.sys.menu.path', 'Menu URL', '菜单地址', '菜單地址', '1', '2014-06-15 18:46:15', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('641', 'label.sys.menu.sorted', 'Sorted', '排序', '排序', '1', '2014-06-15 18:50:01', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('642', 'help.common.int', 'Please enter only integer', '请输入整数', '請輸入整數', '1', '2014-06-15 18:51:22', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('643', 'help.sys.menu.sorted.max', 'Please enter a value less than or equal to 9999', '请输入一个最大为9999的值', '請輸入一個最大為9999的值', '1', '2014-06-15 18:53:29', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('644', 'help.sys.menu.sorted.min', 'Please enter a value greater than or equal to 0', '请输入一个最小为0的值', '請輸入一個最小為0的值', '1', '2014-06-15 18:54:46', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('645', 'label.sys.menu.icon', 'Icon', '图标', '圖標', '1', '2014-06-15 18:55:58', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('646', 'label.sys.role.menu.permission', 'Menu Permission', '菜单权限', '菜單權限', '1', '2014-06-15 18:58:41', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('647', 'label.sys.role.route.permission', 'Route Permission', '路由权限', '路由權限', '1', '2014-06-15 18:59:13', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('648', 'label.sys.role.resource.permission', 'Resource Permission', '资源权限', '資源權限', '1', '2014-06-15 18:59:46', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('649', 'title.sys.resource', 'Resource Management', '资源管理', '資源管理', '1', '2014-06-15 20:35:13', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('650', 'header.sys.resource.list', 'Resource List', '资源列表', '資源列表', '1', '2014-06-15 20:36:27', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('651', 'label.sys.resource.method', 'Request Method', '请求方法', '請求方法', '1', '2014-06-15 20:37:36', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('652', 'query.sys.resource.simple.url', 'Please input url', '请输入URL', '請輸入URL', '1', '2014-06-15 20:38:49', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('653', 'title.sys.dict', 'Dictory Management', '字典管理', '字典管理', '1', '2014-06-15 20:40:51', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('654', 'header.sys.dict.list', 'Dictory List', '字典列表', '字典列表', '1', '2014-06-15 20:41:55', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('655', 'label.sys.dict.add', 'Add Dictory', '新增字典', '新增字典', '1', '2014-06-15 20:43:15', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('656', 'label.sys.dict.addchild', 'Add Dictory Item', '新增字典子项', '新增字典子項', '1', '2014-06-15 20:44:27', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('657', 'label.sys.dict.child', 'Dictory Item Management', '字典子项管理', '字典子項管理', '1', '2014-06-15 20:45:36', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('658', 'title.sys.dict.child', 'Dictory Item Management', '字典子项管理', '字典子項管理', '1', '2014-06-15 20:46:51', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('659', 'title.sys.dict.child.add', 'Add Dictory Item', '新增字典子项', '新增字典子項', '1', '2014-06-15 20:48:59', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('660', 'title.sys.dict.child.edit', 'Edit Dictory Item', '修改字典子项', '修改字典子項', '1', '2014-06-15 20:49:37', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('661', 'header.sys.dict.info', 'Dictory Information', '字典信息', '字典信息', '1', '2014-06-15 20:50:20', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('662', 'label.sys.dict.name', 'Dictory Name', '字典名称', '字典名稱', '1', '2014-06-15 20:51:36', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('663', 'label.sys.dict.code', 'Dictory Code', '字典编码', '字典編碼', '1', '2014-06-15 20:52:18', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('664', 'query.sys.dict.simple.code', 'Please input Dcitory Code', '请输入字典编码', '請輸入字典編碼', '1', '2014-06-15 20:54:04', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('665', 'help.sys.dict.code.pattern', 'Only letter or number or underscore', '只能是字母、数字、下划线的组合', '只能是字母、數字、下劃線的組合', '1', '2014-06-15 20:55:22', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('666', 'help.sys.dict.code.unique', 'Code already exists', '字典编码已经存在', '字典编码已经存在', '1', '2014-06-15 20:56:16', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('667', 'label.index.menu.list', 'Menu List', '菜单列表', '菜單列表', '1', '2014-06-15 21:02:08', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('669', 'label.index.user.logout', 'Logout', '注销', '註銷', '1', '2014-06-15 21:06:25', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('670', 'label.index.user.profile.edit', 'Edit Profile', '设置偏好', '設置偏好', '1', '2014-06-15 21:10:44', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('671', 'msg.save.success', 'Save success', '保存成功', '保存成功', '1', '2014-06-15 21:11:53', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('672', 'User Management', 'User Management', '用户管理', '用戶管理', '1', '2014-06-15 22:06:53', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('673', 'User List', 'User List', '用户列表', '用戶列表', '1', '2014-06-15 22:08:02', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('674', 'Add User', 'Add User', '新增用户', '新增用戶', '1', '2014-06-15 22:11:52', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('675', 'Edit User', 'Edit User', '修改用户', '修改用戶', '1', '2014-06-15 22:12:53', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('676', 'View User', 'View User', '查看用户', '查看用戶', '1', '2014-06-15 22:13:34', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('677', 'Route List', 'Route List', '路由列表', '路由列表', '1', '2014-06-18 09:17:46', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('678', 'Add Route', 'Add Route', '新增路由', '新增路由', '1', '2014-06-18 09:18:10', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('679', 'Edit Route', 'Edit Route', '修改路由', '修改路由', '1', '2014-06-18 09:18:29', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('680', 'Dictory List', 'Dictory List', '字典列表', '字典列表', '1', '2014-06-18 09:18:51', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('681', 'Add Dictory', 'Add Dictory', '新增字典', '新增字典', '1', '2014-06-18 09:19:22', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('682', 'Edit Dictory', 'Edit Dictory', '修改字典', '修改字典', '1', '2014-06-18 09:19:50', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('683', 'Dictory Item List', 'Dictory Item List', '字典子项列表', '字典子項列表', '1', '2014-06-18 09:20:10', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('684', 'Add Dictory Item', 'Add Dictory Item', '新增字典子项', '新增字典子項', '1', '2014-06-18 09:20:32', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('685', 'Edit Dictory Item', 'Edit Dictory Item', '修改字典子项', '修改字典子項', '1', '2014-06-18 09:20:54', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('686', 'Role List', 'Role List', '角色列表', '角色列表', '1', '2014-06-18 09:21:44', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('687', 'Add Role', 'Add Role', '新增角色', '新增角色', '1', '2014-06-18 09:22:00', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('688', 'Edit Role', 'Edit Role', '修改角色', '修改角色', '1', '2014-06-18 09:22:20', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('689', 'Set Role Permission', 'Set Role Permission', '角色权限配置', '角色權限配置', '1', '2014-06-18 09:22:40', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('690', 'Menu List', 'Menu List', '菜单列表', '菜單列表', '1', '2014-06-18 09:23:01', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('691', 'Add Menu', 'Add Menu', '新增菜单', '新增菜單', '1', '2014-06-18 09:23:20', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('692', 'Edit Menu', 'Edit Menu', '修改菜单', '修改菜單', '1', '2014-06-18 09:23:39', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('693', 'Add Menu Child', 'Add Menu Child', '新增子菜单', '新增子菜單', '1', '2014-06-18 09:24:01', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('694', 'Reource List', 'Reource List', '资源列表', '資源列表', '1', '2014-06-18 09:24:16', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('695', 'Edit Resource', 'Edit Resource', '修改资源', '修改資源', '1', '2014-06-18 09:24:33', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('696', 'Relate Resource', 'Relate Resource', '设置路由资源的关联', '設置路由資源的關聯', '1', '2014-06-18 09:25:06', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('697', 'Ralate Route', 'Ralate Route', '设置菜单路由关联', '設置菜單路由關聯', '1', '2014-06-18 09:25:32', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('698', 'Job List', 'Job List', '作业列表', '作業列表', '1', '2014-06-18 09:25:48', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('699', 'Add Job', 'Add Job', '新增作业', '新增作業', '1', '2014-06-18 09:26:05', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('700', 'Edit Job', 'Edit Job', '修改作业', '修改作業', '1', '2014-06-18 09:26:27', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('701', 'i18n List', 'i18n List', 'i18n列表', 'i18n列表', '1', '2014-06-18 09:26:45', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('702', 'Add i18n', 'Add i18n', '新增i18n', '新增i18n', '1', '2014-06-18 09:27:02', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('703', 'Edit i18n', 'Edit i18n', '修改i18n', '修改i18n', '1', '2014-06-18 09:27:28', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('704', 'System Management', 'System Management', '系统管理', '系統管理', '1', '2014-06-18 09:27:59', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('705', 'Dictory Management', 'Dictory Management', '字典管理', '字典管理', '1', '2014-06-18 09:28:24', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('706', 'Menu Management', 'Menu Management', '菜单管理', '菜單管理', '1', '2014-06-18 09:28:42', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('707', 'Route Management', 'Route Management', '路由管理', '路由管理', '1', '2014-06-18 09:29:06', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('708', 'Role Management', 'Role Management', '角色管理', '角色管理', '1', '2014-06-18 09:29:25', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('709', 'Resource Managemen', 'Resource Management', '资源管理', '資源管理', '1', '2014-06-18 09:30:04', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('710', 'Job Management', 'Job Management', '作业管理', '作業管理', '1', '2014-06-18 09:30:18', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('711', 'i18n Management', 'i18n Management', 'i18n管理', 'i18n管理', '1', '2014-06-18 09:30:36', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('712', 'label.common.page', 'Page', '页', '頁', '1', '2014-06-18 10:21:46', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('713', 'Query Dictory', 'Query Dictory', '查询字典', '查詢字典', '1', '2014-06-18 11:12:07', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('714', 'Create Dictory', 'Create Dictory', '创建字典', '創建字典', '1', '2014-06-18 11:13:19', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('715', 'Update Dictory', 'Update Dictory', '更新字典', '更新字典', '1', '2014-06-18 11:14:20', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('716', 'Delete Dictory', 'Delete Dictory', '删除字典', '刪除字典', '1', '2014-06-18 11:14:48', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('717', 'Query the items Of Dictory', 'Query the items Of Dictory', '查询字典子项', '查詢字典子項', '1', '2014-06-18 11:15:54', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('718', 'Check Unique Dictory', 'Check Unique Dictory', '校验字典码是否唯一', '校驗字典碼是否唯一', '1', '2014-06-18 11:16:45', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('719', 'Create i18n', 'Create i18n', '创建i18n', '創建i18n', '1', '2014-06-18 11:43:55', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('720', 'Update i18n', 'Update i18n', '更新i18n', '更新i18n', '1', '2014-06-18 11:44:23', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('721', 'Query i18n', 'Query i18n', '查询i18n', '查詢i18n', '1', '2014-06-18 11:44:59', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('722', 'Delete i18n', 'Delete i18n', '删除i18n', '刪除i18n', '1', '2014-06-18 11:45:27', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('723', 'Check Unique i18n', 'Check Unique i18n', '校验i18n是否唯一', '校驗i18n是否唯一', '1', '2014-06-18 11:46:04', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('724', 'Generate i18n JSON', 'Generate i18n JSON', '生成i18n的JSON', '生成i18n的JSON', '1', '2014-06-18 11:46:48', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('725', 'Create Job', 'Create Job', '创建作业', '創建作業', '1', '2014-06-18 11:47:24', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('726', 'Update Job', 'Update Job', '更新作业', '更新作業', '1', '2014-06-18 11:47:47', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('727', 'Query Job', 'Query Job', '查询Job', '查詢Job', '1', '2014-06-18 11:48:35', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('728', 'Delete Job', 'Delete Job', '删除作业', '刪除作業', '1', '2014-06-18 11:49:03', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('729', 'Check Unique Job', 'Check Unique Job', '校验作业的唯一性', '校驗作業的唯一性', '1', '2014-06-18 11:49:30', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('730', 'Ralate menu and route', 'Ralate menu and route', '保存菜单和路由的关联', '保存菜單和路由的關聯', '1', '2014-06-18 11:51:18', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('731', 'Query relation of menu and route', 'Query relation of menu and route', '查询菜单和路由的关联', '查詢菜單和路由的關聯', '1', '2014-06-18 11:51:53', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('732', 'Delete Menu', 'Delete Menu', '删除菜单', '刪除菜單', '1', '2014-06-18 11:52:09', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('733', 'Query Menu', 'Query Menu', '查询菜单', '查詢菜單', '1', '2014-06-18 11:52:28', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('734', 'Update Menu', 'Update Menu', '更新菜单', '更新菜單', '1', '2014-06-18 11:52:45', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('735', 'Create Menu', 'Create Menu', '创建菜单', '創建菜單', '1', '2014-06-18 11:53:05', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('736', 'Query the roles of user', 'Query the roles of user', '查询用户的角色', '查詢用戶的角色', '1', '2014-06-18 11:54:55', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('737', 'Check Unique Username', 'Check Unique Username', '校验用户名的唯一性', '校驗用戶名的唯一性', '1', '2014-06-18 11:55:21', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('738', 'Delete User', 'Delete User', '删除用户', '刪除用戶', '1', '2014-06-18 11:55:40', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('739', 'Query User', 'Query User', '查询用户', '查詢用戶', '1', '2014-06-18 11:55:55', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('740', 'Update User', 'Update User', '更新用户', '更新用戶', '1', '2014-06-18 11:56:15', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('741', 'Create User', 'Create User', '创建用户', '創建用戶', '1', '2014-06-18 11:56:31', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('742', 'Relate resource and route', 'Relate resource and route', '保存路由与资源的关系', '保存路由與資源的關係', '1', '2014-06-18 12:25:31', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('743', 'Query relation of resource and route', 'Query relation of resource and route', '查询路由与资源的关系', '查詢路由與資源的關係', '1', '2014-06-18 12:26:00', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('744', 'Query Route', 'Query Route', '查询路由', '查詢路由', '1', '2014-06-18 12:26:19', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('745', 'Delete Route', 'Delete Route', '删除路由', '刪除路由', '1', '2014-06-18 12:26:38', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('746', 'Update Route', 'Update Route', '更新路由', '更新路由', '1', '2014-06-18 12:26:52', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('747', 'Create Route', 'Create Route', '创建路由', '創建路由', '1', '2014-06-18 12:27:14', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('748', 'Create Role', 'Create Role', '创建角色', '創建角色', '1', '2014-06-18 12:28:35', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('749', 'Update Role', 'Update Role', '更新角色', '更新角色', '1', '2014-06-18 12:28:55', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('750', 'Delete Role', 'Delete Role', '删除角色', '刪除角色', '1', '2014-06-18 12:29:15', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('751', 'Query Resource', 'Query Resource', '查询资源', '查詢資源', '1', '2014-06-18 12:32:50', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('752', 'Save Route Permisison', 'Save Route Permisison', '保存路由授权', '保存路由授權', '1', '2014-06-18 12:33:47', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('753', 'Save Resource Permisison', 'Save Resource Permisison', '保存资源授权', '保存資源授權', '1', '2014-06-18 12:34:09', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('754', 'Save Menu Permisison', 'Save Menu Permisison', '保存菜单授权', '保存菜單授權', '1', '2014-06-18 12:34:49', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('755', 'Query Menu Permisison', 'Query Menu Permisison', '查询菜单授权', '查詢菜單授權', '1', '2014-06-18 12:35:28', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('756', 'Query Route Permisison', 'Query Route Permisison', '查询路由授权', '查詢路由授權', '1', '2014-06-18 12:35:56', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('757', 'Query Resource Permisison', 'Query Resource Permisison', '查询角色的资源授权', '查詢角色的資源授權', '1', '2014-06-18 12:36:29', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('758', 'UnLogin User', 'UnLogin User', '未登录用户', '未登錄用戶', '1', '2014-06-18 12:37:07', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('759', 'UnAuthorized User', 'UnAuthorized User', '未授权用户', '未授權用戶', '1', '2014-06-18 12:37:44', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('760', 'Logout', 'Logout', '注销', '註銷', '1', '2014-06-18 12:38:32', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('761', 'Login', 'Login', '登录', '登錄', '1', '2014-06-18 12:38:46', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('762', '400', '400', '400', '400', '1', '2014-06-18 12:39:34', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('763', '404', '404', '404', '404', '1', '2014-06-18 12:39:43', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('764', '405', '405', '405', '405', '1', '2014-06-18 12:39:59', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('765', '500', '500', '500', '500', '1', '2014-06-18 12:40:06', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('766', 'Query User Profile', 'Query User Profile', '查询个人设置', '查詢個人設置', '1', '2014-06-18 12:41:27', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('767', 'Query Routes', 'Query Routes', '查询路由', '查詢路由', '1', '2014-06-18 12:41:43', '2014-06-28 12:29:58');
INSERT INTO `i18n_message` VALUES ('808', 'help.common.delete.confirm', 'Confirm Delete?', '确认删除？', '确认删除？', '1', '2014-07-08 17:20:14', '2014-07-08 17:20:14');
INSERT INTO `i18n_message` VALUES ('809', 'help.common.maxlength13', 'Please enter no more than 13 characters', '请输入一个长度最多是13的字符串', '請輸入長度不大於13的字符串', '1', '2014-07-09 13:55:01', '2014-07-09 13:55:01');
INSERT INTO `i18n_message` VALUES ('810', 'title.customer.view', 'View Customer', '查看客户', '查看客戶', '0', '2014-07-09 14:38:48', '2014-07-09 14:38:48');
INSERT INTO `i18n_message` VALUES ('2168', 'label.sys.permission', 'Permission', '权限', '權限', '1', '2014-08-05 16:35:12', '2014-08-05 16:35:12');
INSERT INTO `i18n_message` VALUES ('2218', 'label.sys.permission.add', 'Add Permission', '新增权限', '新增權限', '1', '2014-08-05 16:53:47', '2014-08-05 16:53:47');
INSERT INTO `i18n_message` VALUES ('2219', 'label.common.op.del', 'Delete', '删除', '刪除', '1', '2014-08-05 16:54:44', '2014-08-05 16:54:44');
INSERT INTO `i18n_message` VALUES ('2220', 'title.sys.permission.add', 'Add Permission', '新增权限', '新增權限', '1', '2014-08-05 16:57:15', '2014-08-05 16:57:15');
INSERT INTO `i18n_message` VALUES ('2221', 'header.sys.permission.info', 'Permission', '权限', '權限', '1', '2014-08-05 16:57:33', '2014-08-05 16:57:33');
INSERT INTO `i18n_message` VALUES ('2222', 'label.sys.permission.name', 'Name', '名称', '名稱', '1', '2014-08-05 16:57:57', '2014-08-05 16:57:57');
INSERT INTO `i18n_message` VALUES ('2231', 'label.common.op.all', 'Select All', '全选', '全選', '1', '2014-08-05 17:10:36', '2014-08-05 17:10:36');
INSERT INTO `i18n_message` VALUES ('2287', 'Query Role', 'Query Role', '查询角色', '查詢角色', '1', '2014-08-06 11:01:52', '2014-08-06 11:01:52');
INSERT INTO `i18n_message` VALUES ('2288', 'Set Permission', 'Set Permission', '设置权限', '設置權限', '1', '2014-08-06 11:02:12', '2014-08-06 11:02:12');
INSERT INTO `i18n_message` VALUES ('2289', 'Edit Permission', 'Edit Permission', '修改权限', '修改權限', '1', '2014-08-06 11:03:19', '2014-08-06 11:03:19');
INSERT INTO `i18n_message` VALUES ('2290', 'Add Permission', 'Add Permission', '新增权限', '新增權限', '1', '2014-08-06 11:03:37', '2014-08-06 11:03:37');
INSERT INTO `i18n_message` VALUES ('2291', 'View Menu', 'View Menu', '查看菜单', '查看菜單', '1', '2014-08-06 11:03:58', '2014-08-06 11:03:58');
INSERT INTO `i18n_message` VALUES ('2292', 'Save Permisison', 'Save Permisison', '授权', '授權', '1', '2014-08-06 11:04:27', '2014-08-06 11:04:27');
INSERT INTO `i18n_message` VALUES ('2293', 'Query Permisison', 'Query Permisison', '查询授权', '查詢授權', '1', '2014-08-06 11:04:56', '2014-08-06 11:04:56');
INSERT INTO `i18n_message` VALUES ('2294', 'View Role', 'View Role', '查看角色', '查看角色', '1', '2014-08-06 11:05:11', '2014-08-06 11:05:11');
INSERT INTO `i18n_message` VALUES ('2298', 'help.sys.menu.permission.unique', 'Permission already exists', '权限字符串已经存在', '權限字符串已經存在', '1', '2014-08-06 11:17:16', '2014-08-06 11:18:10');
INSERT INTO `i18n_message` VALUES ('2304', 'title.sys.company', 'Company Management', '运营公司管理', '運營公司管理', '1', '2014-08-06 13:54:32', '2014-08-06 13:54:32');
INSERT INTO `i18n_message` VALUES ('2305', 'header.sys.company.list', 'Company List', '公司列表', '公司列表', '1', '2014-08-06 13:55:30', '2014-08-06 13:55:30');
INSERT INTO `i18n_message` VALUES ('2435', 'Company List', 'Company List', '运营公司列表', '運營公司列表', '1', '2014-08-06 16:02:14', '2014-08-06 16:02:14');
INSERT INTO `i18n_message` VALUES ('2436', 'Add Company', 'Add Company', '新增运营公司', '新增運營公司', '1', '2014-08-06 16:02:34', '2014-08-06 16:02:34');
INSERT INTO `i18n_message` VALUES ('2441', 'title.sys.company.add', 'Add Company', '新增公司', '新增公司', '1', '2014-08-06 16:12:15', '2014-08-06 16:12:15');
INSERT INTO `i18n_message` VALUES ('2442', 'header.sys.company.info', 'Company Information', '公司信息', '公司信息', '1', '2014-08-06 16:12:38', '2014-08-06 16:12:38');
INSERT INTO `i18n_message` VALUES ('2445', 'label.sys.company.name', 'Company Name', '公司名称', '公司名稱', '1', '2014-08-06 16:18:40', '2014-08-06 16:18:40');
INSERT INTO `i18n_message` VALUES ('2446', 'label.sys.company.companyCode', 'Company Code', '公司代码', '公司代碼', '1', '2014-08-06 16:19:14', '2014-08-06 16:19:14');
INSERT INTO `i18n_message` VALUES ('2447', 'header.sys.company.admin.info', 'Administrator Information', '管理员信息', '管理員信息', '1', '2014-08-06 16:44:23', '2014-08-06 16:44:23');

-- ----------------------------
-- Table structure for `sequence`
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `stub` char(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_stub` (`stub`)
) ENGINE=MyISAM AUTO_INCREMENT=2491 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('2490', 'a');

-- ----------------------------
-- Table structure for `sys_company`
-- ----------------------------
DROP TABLE IF EXISTS `sys_company`;
CREATE TABLE `sys_company` (
  `COMPANY_ID` int(11) NOT NULL,
  `COMPANY_NAME` varchar(64) NOT NULL,
  `COMPANY_CODE` varchar(8) NOT NULL,
  `IS_DEL` tinyint(1) NOT NULL,
  PRIMARY KEY (`COMPANY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_company
-- ----------------------------
INSERT INTO `sys_company` VALUES ('2449', '永安保全', '001', '1');
INSERT INTO `sys_company` VALUES ('2450', '永安保全', '111', '1');
INSERT INTO `sys_company` VALUES ('2451', '永安保全', '002', '1');
INSERT INTO `sys_company` VALUES ('2453', '永安保全', '0001', '0');
INSERT INTO `sys_company` VALUES ('2457', '香港卫安', '0002', '0');

-- ----------------------------
-- Table structure for `sys_dict`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `DICT_CODE` varchar(32) NOT NULL,
  `DICT_NAME` varchar(32) NOT NULL,
  `PARENT_CODE` varchar(32) NOT NULL DEFAULT '-1',
  `SORTED` int(11) NOT NULL DEFAULT '9999',
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`DICT_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('BOOL', 'BOOL', '-1', '9999', '2014-05-29 11:00:21', '2014-06-15 21:13:14');
INSERT INTO `sys_dict` VALUES ('en', 'label.common.lang.English', 'LANG', '9999', '2014-06-18 17:10:34', '2014-06-18 20:34:40');
INSERT INTO `sys_dict` VALUES ('false', 'False', 'BOOL', '9999', '2014-05-29 11:00:40', '2014-06-15 21:13:34');
INSERT INTO `sys_dict` VALUES ('LANG', 'Language', '-1', '9999', '2014-06-18 17:10:17', '2014-06-18 17:10:17');
INSERT INTO `sys_dict` VALUES ('ROLE_CODE', 'Role Code', '-1', '9999', '2014-05-24 17:55:43', '2014-06-15 21:13:25');
INSERT INTO `sys_dict` VALUES ('ROLE_CODE_ADMIN', 'ADMIN', 'ROLE_CODE', '9999', '2014-05-24 17:56:16', '2014-06-15 21:13:54');
INSERT INTO `sys_dict` VALUES ('ROLE_CODE_USER', 'USER', 'ROLE_CODE', '9999', '2014-05-24 17:56:00', '2014-06-15 21:13:59');
INSERT INTO `sys_dict` VALUES ('true', 'True', 'BOOL', '9999', '2014-05-29 11:00:33', '2014-06-15 21:13:39');
INSERT INTO `sys_dict` VALUES ('zh_CN', 'label.common.lang.zh_CN', 'LANG', '9999', '2014-06-18 17:11:41', '2014-06-18 20:34:48');
INSERT INTO `sys_dict` VALUES ('zh_TW', 'label.common.lang.zh_TW', 'LANG', '9999', '2014-06-18 17:11:49', '2014-06-18 20:34:56');

-- ----------------------------
-- Table structure for `sys_job`
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
  `JOB_ID` int(11) NOT NULL,
  `JOB_NAME` varchar(32) NOT NULL,
  `CLAZZ_NAME` varchar(64) NOT NULL,
  `CRON` varchar(32) DEFAULT NULL,
  `ENABLED` tinyint(1) NOT NULL,
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`JOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_job
-- ----------------------------
INSERT INTO `sys_job` VALUES ('1', '测试作业', 'com.edgar.core.job.DayJob', '0 0/15 * * * ? *', '1', '2014-06-02 17:32:02', '2014-06-20 15:18:56');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `MENU_ID` int(11) NOT NULL,
  `PARENT_ID` int(11) NOT NULL DEFAULT '-1',
  `MENU_NAME` varchar(32) NOT NULL,
  `MENU_TYPE` varchar(32) DEFAULT NULL,
  `MENU_PATH` varchar(128) DEFAULT NULL,
  `SORTED` int(11) DEFAULT '9999',
  `ICON` varchar(32) DEFAULT NULL,
  `PERMISSION` varchar(64) DEFAULT NULL,
  `IS_ROOT` tinyint(1) DEFAULT NULL,
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单类型：菜单、按钮\r\n系统中把菜单看作是路由和资源的集合';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '-1', 'System Management', null, null, '9999', 'fa-cog', null, '0', '2014-04-28 22:17:57', '2014-06-18 13:59:50');
INSERT INTO `sys_menu` VALUES ('2', '1', 'Dictory Management', null, '/sys/dict/list', '9999', null, null, '1', '2014-04-28 22:18:31', '2014-06-18 14:04:24');
INSERT INTO `sys_menu` VALUES ('3', '1', 'Menu Management', null, '/sys/menu/list', '9998', null, null, '1', '2014-04-28 22:18:55', '2014-06-18 14:04:25');
INSERT INTO `sys_menu` VALUES ('4', '1', 'Route Management', null, '/sys/route/list', '9997', null, null, '1', '2014-04-28 22:19:17', '2014-06-18 14:04:26');
INSERT INTO `sys_menu` VALUES ('5', '1', 'Role Management', null, '/sys/role/list', '2', null, null, '0', '2014-04-28 22:19:47', '2014-06-18 14:04:28');
INSERT INTO `sys_menu` VALUES ('6', '1', 'User Management', null, '/sys/user/list', '1', null, null, '0', '2014-04-29 13:16:15', '2014-06-18 14:04:29');
INSERT INTO `sys_menu` VALUES ('7', '1', 'Resource Managemen', null, '/sys/resource/list', '9998', null, null, '1', '2014-05-04 10:08:16', '2014-06-18 14:04:30');
INSERT INTO `sys_menu` VALUES ('8', '1', 'Job Management', null, '/sys/job/list', '9999', null, null, '1', '2014-06-02 14:30:30', '2014-07-10 09:11:19');
INSERT INTO `sys_menu` VALUES ('9', '1', 'i18n Management', null, '/sys/i18n/list', '9990', null, null, '1', '2014-06-13 20:26:09', '2014-06-18 14:04:33');
INSERT INTO `sys_menu` VALUES ('255', '-1', 'Customer Management', null, '/customer/list', '3', 'fa fa-male', null, '0', '2014-06-27 16:08:11', '2014-07-11 11:27:29');
INSERT INTO `sys_menu` VALUES ('288', '-1', 'Basic Data', null, null, '8000', 'fa fa-thumb-tack', null, '0', '2014-06-30 09:17:55', '2014-06-30 09:17:55');
INSERT INTO `sys_menu` VALUES ('289', '1', 'Company Management', null, '/sys/company/list', '1', null, null, '1', '2014-06-30 09:18:24', '2014-08-07 09:46:06');
INSERT INTO `sys_menu` VALUES ('359', '-1', 'Task List', null, '/order/task/list', '1', 'fa fa-tasks', null, '0', '2014-07-02 11:54:23', '2014-07-02 13:21:49');
INSERT INTO `sys_menu` VALUES ('2189', '5', 'Query Role', null, null, '1', null, 'sys:role:query', '0', '2014-08-05 16:43:08', '2014-08-05 16:43:08');
INSERT INTO `sys_menu` VALUES ('2192', '5', 'Add Role', null, null, '2', null, 'sys:role:add', '0', '2014-08-05 16:44:36', '2014-08-05 16:44:36');
INSERT INTO `sys_menu` VALUES ('2195', '5', 'Edit Role', null, null, '3', null, 'sys:role:edit', '0', '2014-08-05 16:45:10', '2014-08-05 16:45:10');
INSERT INTO `sys_menu` VALUES ('2199', '5', 'Delete Role', null, null, '4', null, 'sys:role:delete', '0', '2014-08-05 16:45:31', '2014-08-05 16:45:31');
INSERT INTO `sys_menu` VALUES ('2201', '6', 'Query User', null, null, '1', null, 'sys:user:query', '0', '2014-08-05 16:46:00', '2014-08-05 16:46:00');
INSERT INTO `sys_menu` VALUES ('2204', '6', 'Add User', null, null, '2', null, 'sys:user:add', '0', '2014-08-05 16:46:30', '2014-08-05 16:46:30');
INSERT INTO `sys_menu` VALUES ('2207', '6', 'Edit User', null, null, '3', null, 'sys:user:edit', '0', '2014-08-05 16:47:03', '2014-08-05 16:47:03');
INSERT INTO `sys_menu` VALUES ('2223', '6', 'Delete User', null, null, '4', null, 'sys:user:delete', '0', '2014-08-05 16:58:21', '2014-08-05 16:58:21');
INSERT INTO `sys_menu` VALUES ('2267', '5', 'Set Permission', null, null, '5', null, 'sys:role:permission', '0', '2014-08-06 10:59:49', '2014-08-06 10:59:49');

-- ----------------------------
-- Table structure for `sys_menu_res`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_res`;
CREATE TABLE `sys_menu_res` (
  `MENU_RES_ID` int(11) NOT NULL,
  `RESOURCE_ID` int(11) DEFAULT NULL,
  `MENU_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`MENU_RES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单资源表，用户拥有菜单权限则拥有了相应的rest权限';

-- ----------------------------
-- Records of sys_menu_res
-- ----------------------------
INSERT INTO `sys_menu_res` VALUES ('2191', '2006', '2189');
INSERT INTO `sys_menu_res` VALUES ('2194', '2003', '2192');
INSERT INTO `sys_menu_res` VALUES ('2197', '2002', '2195');
INSERT INTO `sys_menu_res` VALUES ('2198', '2005', '2195');
INSERT INTO `sys_menu_res` VALUES ('2200', '2004', '2199');
INSERT INTO `sys_menu_res` VALUES ('2203', '2018', '2201');
INSERT INTO `sys_menu_res` VALUES ('2206', '2013', '2204');
INSERT INTO `sys_menu_res` VALUES ('2209', '2012', '2207');
INSERT INTO `sys_menu_res` VALUES ('2210', '2016', '2207');
INSERT INTO `sys_menu_res` VALUES ('2224', '2014', '2223');
INSERT INTO `sys_menu_res` VALUES ('2269', '2002', '2267');
INSERT INTO `sys_menu_res` VALUES ('2270', '1998', '2267');
INSERT INTO `sys_menu_res` VALUES ('2271', '2232', '2267');

-- ----------------------------
-- Table structure for `sys_menu_route`
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_route`;
CREATE TABLE `sys_menu_route` (
  `MENU_ROUTE_ID` int(11) NOT NULL,
  `ROUTE_ID` int(11) DEFAULT NULL,
  `MENU_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`MENU_ROUTE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色拥有菜单的权限，则代表拥有路由的权限';

-- ----------------------------
-- Records of sys_menu_route
-- ----------------------------
INSERT INTO `sys_menu_route` VALUES ('2190', '14', '2189');
INSERT INTO `sys_menu_route` VALUES ('2193', '15', '2192');
INSERT INTO `sys_menu_route` VALUES ('2196', '16', '2195');
INSERT INTO `sys_menu_route` VALUES ('2202', '10', '2201');
INSERT INTO `sys_menu_route` VALUES ('2205', '11', '2204');
INSERT INTO `sys_menu_route` VALUES ('2208', '12', '2207');
INSERT INTO `sys_menu_route` VALUES ('2268', '17', '2267');

-- ----------------------------
-- Table structure for `sys_resource`
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource` (
  `RESOURCE_ID` int(11) NOT NULL,
  `URL` varchar(128) NOT NULL,
  `METHOD` varchar(32) NOT NULL,
  `RESOURCE_NAME` varchar(64) DEFAULT NULL,
  `PERMISSION` varchar(64) DEFAULT NULL,
  `IS_ROOT` tinyint(1) NOT NULL,
  `AUTH_TYPE` varchar(32) NOT NULL,
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`RESOURCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('1970', '/index/user/**', 'GET', 'Query User Profile', 'index:user:read', '1', 'authc', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1972', '/sys/i18n/**', 'GET', 'View i18n', 'sys:i18n:read', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1973', '/sys/i18n/**', 'POST', 'Create i18n', 'sys:i18n:create', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1974', '/sys/i18n/**', 'DELETE', 'Delete i18n', 'sys:i18n:delete', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1975', '/sys/i18n/**', 'PUT', 'Update i18n', 'sys:i18n:update', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1976', '/sys/i18n/pagination/**', 'GET', 'Paging Query i18n', 'sys:i18n:pagination:read', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1977', '/sys/i18n/check/key/**', 'GET', 'Check Unique i18n', 'sys:i18n:check:key:read', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1978', '/sys/i18n/tojson/**', 'POST', 'Generate i18n JSON', 'sys:i18n:tojson:create', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1979', '/sys/dict/**', 'GET', 'View Dictory', 'sys:dict:read', '1', 'authc', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1980', '/sys/dict/**', 'POST', 'Create Dictory', 'sys:dict:create', '1', 'authc', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1981', '/sys/dict/**', 'DELETE', 'Delete Dictory', 'sys:dict:delete', '1', 'authc', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1982', '/sys/dict/**', 'PUT', 'Update Dictory', 'sys:dict:update', '1', 'authc', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1983', '/sys/dict/items/**', 'GET', 'Query the items Of Dictory ', 'sys:dict:items:read', '1', 'authc', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1984', '/sys/dict/pagination/**', 'GET', 'PagingQuery Dictory', 'sys:dict:pagination:read', '1', 'authc', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1985', '/sys/dict/check/dictCode/**', 'GET', 'Check Unique Dictory', 'sys:dict:check:dictCode:read', '1', 'authc', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1986', '/sys/job/**', 'GET', 'View Job', 'sys:job:read', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1987', '/sys/job/**', 'POST', 'Create Job', 'sys:job:create', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1988', '/sys/job/**', 'DELETE', 'Delete Job', 'sys:job:delete', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1989', '/sys/job/**', 'PUT', 'Update Job', 'sys:job:update', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1990', '/sys/job/pagination/**', 'GET', 'Paging Query Job', 'sys:job:pagination:read', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1991', '/sys/job/check/clazzname/**', 'GET', 'Check Unique Job', 'sys:job:check:clazzname:read', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1992', '/sys/menu/**', 'GET', 'View Menu', 'sys:menu:read', '0', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:29:28');
INSERT INTO `sys_resource` VALUES ('1993', '/sys/menu/resource/**', 'GET', 'Query relation of menu and resource', 'sys:menu:resource:read', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1994', '/sys/menu/**', 'POST', 'Create Menu', 'sys:menu:create', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1995', '/sys/menu/**', 'DELETE', 'Delete Menu', 'sys:menu:delete', '1', 'rest', '2014-08-05 16:23:49', '2014-08-05 16:23:49');
INSERT INTO `sys_resource` VALUES ('1996', '/sys/menu/**', 'PUT', 'Update Menu', 'sys:menu:update', '1', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('1997', '/sys/menu/route/**', 'GET', 'Query relation of menu and route', 'sys:menu:route:read', '1', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('1998', '/sys/permission/menu/**', 'GET', 'Query Permisison', 'sys:permission:menu:read', '0', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2000', '/sys/resource/**', 'GET', 'View Resource', 'sys:resource:read', '1', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2001', '/sys/resource/pagination/**', 'GET', 'Paging Query Resource', 'sys:resource:pagination:read', '1', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2002', '/sys/role/**', 'GET', 'View Role', 'sys:role:read', '0', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2003', '/sys/role/**', 'POST', 'Create Role', 'sys:role:create', '0', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2004', '/sys/role/**', 'DELETE', 'Delete Role', 'sys:role:delete', '0', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2005', '/sys/role/**', 'PUT', 'Update Role', 'sys:role:update', '0', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2006', '/sys/role/pagination/**', 'GET', 'Paging Query Role', 'sys:role:pagination:read', '0', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2007', '/sys/route/**', 'GET', 'View Route', 'sys:route:read', '1', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2008', '/sys/route/**', 'POST', 'Create Route', 'sys:route:create', '1', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2009', '/sys/route/**', 'DELETE', 'Delete Route', 'sys:route:delete', '1', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2010', '/sys/route/**', 'PUT', 'Update Route', 'sys:route:update', '1', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2011', '/sys/route/pagination/**', 'GET', 'Paging Query Route', 'sys:route:pagination:read', '1', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2012', '/sys/user/**', 'GET', 'View User', 'sys:user:read', '0', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2013', '/sys/user/**', 'POST', 'Create User', 'sys:user:create', '0', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2014', '/sys/user/**', 'DELETE', 'Delete User', 'sys:user:delete', '0', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2015', '/sys/user/roles/**', 'GET', 'Query Role', 'sys:user:roles:read', '0', 'authc', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2016', '/sys/user/**', 'PUT', 'Update User', 'sys:user:update', '0', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2017', '/sys/user/role/**', 'GET', 'Query the roles of user', 'sys:user:role:read', '0', 'authc', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2018', '/sys/user/pagination/**', 'GET', 'Paging Query User', 'sys:user:pagination:read', '0', 'rest', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2019', '/sys/user/check/username/**', 'GET', 'Check Unique Username', 'sys:user:check:username:read', '0', 'authc', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2020', '/sys/user/profile/**', 'GET', 'Query Profile', 'sys:user:profile:read', '0', 'authc', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2021', '/sys/user/profile/**', 'PUT', 'Update Profile', 'sys:user:profile:update', '0', 'authc', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2022', '/sys/user/password/**', 'PUT', 'Update Password', 'sys:user:password:update', '0', 'authc', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_resource` VALUES ('2232', '/sys/permission/**', 'POST', 'Save Permisison', 'sys:permission:create', '0', 'rest', '2014-08-05 17:28:29', '2014-08-05 17:28:29');
INSERT INTO `sys_resource` VALUES ('2296', '/sys/menu/check/permisson/**', 'GET', 'Check Unique Permission', 'sys:menu:check:permisson:read', '1', 'rest', '2014-08-06 11:16:14', '2014-08-06 11:16:14');
INSERT INTO `sys_resource` VALUES ('2306', '/sys/company/**', 'POST', 'Create Company', 'sys:company:create', '1', 'rest', '2014-08-06 15:53:51', '2014-08-06 15:53:51');
INSERT INTO `sys_resource` VALUES ('2307', '/sys/company/**', 'DELETE', 'Delete Company', 'sys:company:delete', '1', 'rest', '2014-08-06 15:53:51', '2014-08-06 15:53:51');
INSERT INTO `sys_resource` VALUES ('2308', '/sys/company/pagination/**', 'GET', 'Query Company', 'sys:company:pagination:read', '1', 'rest', '2014-08-06 15:53:51', '2014-08-06 15:53:51');
INSERT INTO `sys_resource` VALUES ('2309', '/sys/company/check/code/**', 'GET', 'Check Unique Job', 'sys:company:check:code:read', '1', 'rest', '2014-08-06 15:53:51', '2014-08-06 15:53:51');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `ROLE_ID` int(11) NOT NULL,
  `ROLE_NAME` varchar(32) NOT NULL,
  `ROLE_CODE` varchar(32) NOT NULL,
  `IS_ROOT` tinyint(1) DEFAULT NULL,
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('2023', 'root', 'root', '1', '2014-08-05 16:23:50', '2014-08-05 16:23:50');
INSERT INTO `sys_role` VALUES ('2225', 'User', 'ROLE_CODE_USER', '0', '2014-08-05 16:59:00', '2014-08-05 16:59:00');
INSERT INTO `sys_role` VALUES ('2456', 'Admin', 'ROLE_CODE_ADMIN', '0', '2014-08-06 17:08:41', '2014-08-06 17:08:41');

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `ROLE_MENU_ID` int(11) NOT NULL,
  `ROLE_ID` int(11) DEFAULT NULL,
  `MENU_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ROLE_MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('2272', '2225', '2201');
INSERT INTO `sys_role_menu` VALUES ('2274', '2225', '2223');
INSERT INTO `sys_role_menu` VALUES ('2275', '2225', '2204');
INSERT INTO `sys_role_menu` VALUES ('2276', '2225', '2189');
INSERT INTO `sys_role_menu` VALUES ('2277', '2225', '5');
INSERT INTO `sys_role_menu` VALUES ('2278', '2225', '6');
INSERT INTO `sys_role_menu` VALUES ('2279', '2225', '2207');
INSERT INTO `sys_role_menu` VALUES ('2280', '2225', '2192');
INSERT INTO `sys_role_menu` VALUES ('2281', '2225', '2195');
INSERT INTO `sys_role_menu` VALUES ('2282', '2225', '2199');
INSERT INTO `sys_role_menu` VALUES ('2283', '2225', '288');
INSERT INTO `sys_role_menu` VALUES ('2284', '2225', '1');
INSERT INTO `sys_role_menu` VALUES ('2285', '2225', '5');
INSERT INTO `sys_role_menu` VALUES ('2286', '2225', '6');
INSERT INTO `sys_role_menu` VALUES ('2372', '2023', '1');
INSERT INTO `sys_role_menu` VALUES ('2373', '2023', '2');
INSERT INTO `sys_role_menu` VALUES ('2374', '2023', '3');
INSERT INTO `sys_role_menu` VALUES ('2375', '2023', '4');
INSERT INTO `sys_role_menu` VALUES ('2376', '2023', '5');
INSERT INTO `sys_role_menu` VALUES ('2377', '2023', '6');
INSERT INTO `sys_role_menu` VALUES ('2378', '2023', '7');
INSERT INTO `sys_role_menu` VALUES ('2379', '2023', '8');
INSERT INTO `sys_role_menu` VALUES ('2380', '2023', '9');
INSERT INTO `sys_role_menu` VALUES ('2381', '2023', '255');
INSERT INTO `sys_role_menu` VALUES ('2382', '2023', '288');
INSERT INTO `sys_role_menu` VALUES ('2384', '2023', '359');
INSERT INTO `sys_role_menu` VALUES ('2385', '2023', '2189');
INSERT INTO `sys_role_menu` VALUES ('2386', '2023', '2192');
INSERT INTO `sys_role_menu` VALUES ('2387', '2023', '2195');
INSERT INTO `sys_role_menu` VALUES ('2388', '2023', '2199');
INSERT INTO `sys_role_menu` VALUES ('2389', '2023', '2201');
INSERT INTO `sys_role_menu` VALUES ('2390', '2023', '2204');
INSERT INTO `sys_role_menu` VALUES ('2391', '2023', '2207');
INSERT INTO `sys_role_menu` VALUES ('2392', '2023', '2223');
INSERT INTO `sys_role_menu` VALUES ('2393', '2023', '2267');
INSERT INTO `sys_role_menu` VALUES ('2440', '2023', '289');
INSERT INTO `sys_role_menu` VALUES ('2477', '2456', '2201');
INSERT INTO `sys_role_menu` VALUES ('2478', '2456', '2223');
INSERT INTO `sys_role_menu` VALUES ('2479', '2456', '2204');
INSERT INTO `sys_role_menu` VALUES ('2480', '2456', '2189');
INSERT INTO `sys_role_menu` VALUES ('2481', '2456', '5');
INSERT INTO `sys_role_menu` VALUES ('2482', '2456', '6');
INSERT INTO `sys_role_menu` VALUES ('2483', '2456', '2207');
INSERT INTO `sys_role_menu` VALUES ('2484', '2456', '2267');
INSERT INTO `sys_role_menu` VALUES ('2485', '2456', '2192');
INSERT INTO `sys_role_menu` VALUES ('2486', '2456', '2195');
INSERT INTO `sys_role_menu` VALUES ('2487', '2456', '2199');
INSERT INTO `sys_role_menu` VALUES ('2488', '2456', '1');
INSERT INTO `sys_role_menu` VALUES ('2489', '2456', '5');
INSERT INTO `sys_role_menu` VALUES ('2490', '2456', '6');

-- ----------------------------
-- Table structure for `sys_role_res`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res` (
  `ROLE_RES_ID` int(11) NOT NULL,
  `RESOURCE_ID` int(11) DEFAULT NULL,
  `ROLE_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ROLE_RES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_res
-- ----------------------------
INSERT INTO `sys_role_res` VALUES ('2315', '1970', '2023');
INSERT INTO `sys_role_res` VALUES ('2316', '1972', '2023');
INSERT INTO `sys_role_res` VALUES ('2317', '1973', '2023');
INSERT INTO `sys_role_res` VALUES ('2318', '1974', '2023');
INSERT INTO `sys_role_res` VALUES ('2319', '1975', '2023');
INSERT INTO `sys_role_res` VALUES ('2320', '1976', '2023');
INSERT INTO `sys_role_res` VALUES ('2321', '1977', '2023');
INSERT INTO `sys_role_res` VALUES ('2322', '1978', '2023');
INSERT INTO `sys_role_res` VALUES ('2323', '1979', '2023');
INSERT INTO `sys_role_res` VALUES ('2324', '1980', '2023');
INSERT INTO `sys_role_res` VALUES ('2325', '1981', '2023');
INSERT INTO `sys_role_res` VALUES ('2326', '1982', '2023');
INSERT INTO `sys_role_res` VALUES ('2327', '1983', '2023');
INSERT INTO `sys_role_res` VALUES ('2328', '1984', '2023');
INSERT INTO `sys_role_res` VALUES ('2329', '1985', '2023');
INSERT INTO `sys_role_res` VALUES ('2330', '1986', '2023');
INSERT INTO `sys_role_res` VALUES ('2331', '1987', '2023');
INSERT INTO `sys_role_res` VALUES ('2332', '1988', '2023');
INSERT INTO `sys_role_res` VALUES ('2333', '1989', '2023');
INSERT INTO `sys_role_res` VALUES ('2334', '1990', '2023');
INSERT INTO `sys_role_res` VALUES ('2335', '1991', '2023');
INSERT INTO `sys_role_res` VALUES ('2336', '1992', '2023');
INSERT INTO `sys_role_res` VALUES ('2337', '1993', '2023');
INSERT INTO `sys_role_res` VALUES ('2338', '1994', '2023');
INSERT INTO `sys_role_res` VALUES ('2339', '1995', '2023');
INSERT INTO `sys_role_res` VALUES ('2340', '1996', '2023');
INSERT INTO `sys_role_res` VALUES ('2341', '1997', '2023');
INSERT INTO `sys_role_res` VALUES ('2342', '1998', '2023');
INSERT INTO `sys_role_res` VALUES ('2343', '2000', '2023');
INSERT INTO `sys_role_res` VALUES ('2344', '2001', '2023');
INSERT INTO `sys_role_res` VALUES ('2345', '2002', '2023');
INSERT INTO `sys_role_res` VALUES ('2346', '2003', '2023');
INSERT INTO `sys_role_res` VALUES ('2347', '2004', '2023');
INSERT INTO `sys_role_res` VALUES ('2348', '2005', '2023');
INSERT INTO `sys_role_res` VALUES ('2349', '2006', '2023');
INSERT INTO `sys_role_res` VALUES ('2350', '2007', '2023');
INSERT INTO `sys_role_res` VALUES ('2351', '2008', '2023');
INSERT INTO `sys_role_res` VALUES ('2352', '2009', '2023');
INSERT INTO `sys_role_res` VALUES ('2353', '2010', '2023');
INSERT INTO `sys_role_res` VALUES ('2354', '2011', '2023');
INSERT INTO `sys_role_res` VALUES ('2355', '2012', '2023');
INSERT INTO `sys_role_res` VALUES ('2356', '2013', '2023');
INSERT INTO `sys_role_res` VALUES ('2357', '2014', '2023');
INSERT INTO `sys_role_res` VALUES ('2358', '2015', '2023');
INSERT INTO `sys_role_res` VALUES ('2359', '2016', '2023');
INSERT INTO `sys_role_res` VALUES ('2360', '2017', '2023');
INSERT INTO `sys_role_res` VALUES ('2361', '2018', '2023');
INSERT INTO `sys_role_res` VALUES ('2362', '2019', '2023');
INSERT INTO `sys_role_res` VALUES ('2363', '2020', '2023');
INSERT INTO `sys_role_res` VALUES ('2364', '2021', '2023');
INSERT INTO `sys_role_res` VALUES ('2365', '2022', '2023');
INSERT INTO `sys_role_res` VALUES ('2366', '2232', '2023');
INSERT INTO `sys_role_res` VALUES ('2367', '2296', '2023');
INSERT INTO `sys_role_res` VALUES ('2368', '2306', '2023');
INSERT INTO `sys_role_res` VALUES ('2369', '2307', '2023');
INSERT INTO `sys_role_res` VALUES ('2370', '2308', '2023');
INSERT INTO `sys_role_res` VALUES ('2371', '2309', '2023');

-- ----------------------------
-- Table structure for `sys_role_route`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_route`;
CREATE TABLE `sys_role_route` (
  `ROLE_ROUTE_ID` int(11) NOT NULL,
  `ROUTE_ID` int(11) DEFAULT NULL,
  `ROLE_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ROUTE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_route
-- ----------------------------
INSERT INTO `sys_role_route` VALUES ('2395', '1', '2023');
INSERT INTO `sys_role_route` VALUES ('2396', '2', '2023');
INSERT INTO `sys_role_route` VALUES ('2397', '3', '2023');
INSERT INTO `sys_role_route` VALUES ('2398', '4', '2023');
INSERT INTO `sys_role_route` VALUES ('2399', '5', '2023');
INSERT INTO `sys_role_route` VALUES ('2400', '6', '2023');
INSERT INTO `sys_role_route` VALUES ('2401', '7', '2023');
INSERT INTO `sys_role_route` VALUES ('2402', '8', '2023');
INSERT INTO `sys_role_route` VALUES ('2403', '9', '2023');
INSERT INTO `sys_role_route` VALUES ('2404', '10', '2023');
INSERT INTO `sys_role_route` VALUES ('2405', '11', '2023');
INSERT INTO `sys_role_route` VALUES ('2406', '12', '2023');
INSERT INTO `sys_role_route` VALUES ('2407', '13', '2023');
INSERT INTO `sys_role_route` VALUES ('2408', '14', '2023');
INSERT INTO `sys_role_route` VALUES ('2409', '15', '2023');
INSERT INTO `sys_role_route` VALUES ('2410', '16', '2023');
INSERT INTO `sys_role_route` VALUES ('2411', '17', '2023');
INSERT INTO `sys_role_route` VALUES ('2412', '18', '2023');
INSERT INTO `sys_role_route` VALUES ('2413', '19', '2023');
INSERT INTO `sys_role_route` VALUES ('2414', '20', '2023');
INSERT INTO `sys_role_route` VALUES ('2415', '21', '2023');
INSERT INTO `sys_role_route` VALUES ('2416', '22', '2023');
INSERT INTO `sys_role_route` VALUES ('2417', '23', '2023');
INSERT INTO `sys_role_route` VALUES ('2418', '24', '2023');
INSERT INTO `sys_role_route` VALUES ('2419', '25', '2023');
INSERT INTO `sys_role_route` VALUES ('2420', '26', '2023');
INSERT INTO `sys_role_route` VALUES ('2421', '27', '2023');
INSERT INTO `sys_role_route` VALUES ('2422', '28', '2023');
INSERT INTO `sys_role_route` VALUES ('2423', '29', '2023');
INSERT INTO `sys_role_route` VALUES ('2424', '30', '2023');
INSERT INTO `sys_role_route` VALUES ('2425', '31', '2023');
INSERT INTO `sys_role_route` VALUES ('2426', '361', '2023');
INSERT INTO `sys_role_route` VALUES ('2427', '371', '2023');
INSERT INTO `sys_role_route` VALUES ('2428', '391', '2023');
INSERT INTO `sys_role_route` VALUES ('2429', '392', '2023');
INSERT INTO `sys_role_route` VALUES ('2430', '394', '2023');
INSERT INTO `sys_role_route` VALUES ('2431', '2164', '2023');
INSERT INTO `sys_role_route` VALUES ('2432', '2165', '2023');
INSERT INTO `sys_role_route` VALUES ('2437', '2433', '2023');
INSERT INTO `sys_role_route` VALUES ('2438', '2434', '2023');

-- ----------------------------
-- Table structure for `sys_route`
-- ----------------------------
DROP TABLE IF EXISTS `sys_route`;
CREATE TABLE `sys_route` (
  `ROUTE_ID` int(11) NOT NULL,
  `NAME` varchar(32) NOT NULL,
  `URL` varchar(128) NOT NULL,
  `IS_ROOT` tinyint(1) DEFAULT NULL,
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ROUTE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_route
-- ----------------------------
INSERT INTO `sys_route` VALUES ('1', 'Route List', '/sys/route/list', '1', '2014-04-12 21:29:10', '2014-06-18 10:13:11');
INSERT INTO `sys_route` VALUES ('2', 'Add Route', '/sys/route/add', '1', '2014-04-12 21:29:10', '2014-06-18 10:13:22');
INSERT INTO `sys_route` VALUES ('3', 'Edit Route', '/sys/route/edit/:routeId', '1', '2014-04-12 21:29:10', '2014-06-18 10:13:33');
INSERT INTO `sys_route` VALUES ('4', 'Dictory List', '/sys/dict/list', '1', '2014-04-12 21:29:10', '2014-06-18 10:13:48');
INSERT INTO `sys_route` VALUES ('5', 'Add Dictory', '/sys/dict/add', '1', '2014-04-12 21:29:10', '2014-06-18 10:14:05');
INSERT INTO `sys_route` VALUES ('6', 'Edit Dictory', '/sys/dict/edit/:dictCode', '1', '2014-04-12 21:29:10', '2014-06-18 10:14:16');
INSERT INTO `sys_route` VALUES ('7', 'Dictory Item List', '/sys/dict/items/:dictCode', '1', '2014-04-12 21:29:10', '2014-06-18 10:14:23');
INSERT INTO `sys_route` VALUES ('8', 'Add Dictory Item', '/sys/dict/addItem/:dictCode', '1', '2014-04-12 21:29:10', '2014-06-18 10:14:31');
INSERT INTO `sys_route` VALUES ('9', 'Edit Dictory Item', '/sys/dict/editItem/:dictCode', '1', '2014-04-12 21:29:10', '2014-06-18 10:14:38');
INSERT INTO `sys_route` VALUES ('10', 'User List', '/sys/user/list', '0', '2014-04-12 21:29:10', '2014-06-18 10:17:05');
INSERT INTO `sys_route` VALUES ('11', 'Add User', '/sys/user/add', '0', '2014-04-12 21:29:10', '2014-06-18 10:15:00');
INSERT INTO `sys_route` VALUES ('12', 'Edit User', '/sys/user/edit/:userId', '0', '2014-04-12 21:29:10', '2014-06-18 10:15:08');
INSERT INTO `sys_route` VALUES ('13', 'View User', '/sys/route/view/:userId', '0', '2014-04-12 21:29:10', '2014-06-18 10:15:32');
INSERT INTO `sys_route` VALUES ('14', 'Role List', '/sys/role/list', '0', '2014-04-12 21:29:10', '2014-06-18 10:15:34');
INSERT INTO `sys_route` VALUES ('15', 'Add Role', '/sys/role/add', '0', '2014-04-12 21:29:10', '2014-06-18 10:17:11');
INSERT INTO `sys_route` VALUES ('16', 'Edit Role', '/sys/role/edit/:roleId', '0', '2014-04-12 21:29:10', '2014-06-18 10:17:48');
INSERT INTO `sys_route` VALUES ('17', 'Set Role Permission', '/sys/role/permission/:roleId', '0', '2014-04-12 21:29:10', '2014-06-18 10:17:50');
INSERT INTO `sys_route` VALUES ('18', 'Menu List', '/sys/menu/list', '1', '2014-04-22 16:27:40', '2014-06-18 14:04:58');
INSERT INTO `sys_route` VALUES ('19', 'Add Menu', '/sys/menu/add', '1', '2014-04-22 16:27:54', '2014-06-18 14:05:01');
INSERT INTO `sys_route` VALUES ('20', 'Edit Menu', '/sys/menu/edit/:menuId', '1', '2014-04-22 16:28:08', '2014-06-18 14:05:03');
INSERT INTO `sys_route` VALUES ('21', 'Add Menu Child', '/sys/menu/addChild/:menuId', '1', '2014-04-27 14:53:18', '2014-06-18 14:05:06');
INSERT INTO `sys_route` VALUES ('22', 'Reource List', '/sys/resource/list', '1', '2014-05-04 10:08:34', '2014-06-18 14:05:07');
INSERT INTO `sys_route` VALUES ('23', 'Edit Resource', '/sys/resource/edit/:resourceId', '1', '2014-05-04 10:09:01', '2014-06-18 14:05:09');
INSERT INTO `sys_route` VALUES ('24', 'Relate Resource', '/sys/route/resource/:routeId', '1', '2014-05-26 09:20:39', '2014-06-18 14:05:10');
INSERT INTO `sys_route` VALUES ('25', 'Ralate Route', '/sys/menu/route/:menuId', '1', '2014-05-26 10:41:47', '2014-06-18 14:05:12');
INSERT INTO `sys_route` VALUES ('26', 'Job List', '/sys/job/list', '1', '2014-06-02 14:29:06', '2014-07-29 09:25:47');
INSERT INTO `sys_route` VALUES ('27', 'Add Job', '/sys/job/add', '1', '2014-06-02 14:29:28', '2014-07-29 09:25:48');
INSERT INTO `sys_route` VALUES ('28', 'Edit Job', '/sys/job/edit/:jobId', '1', '2014-06-02 14:29:43', '2014-07-29 09:25:50');
INSERT INTO `sys_route` VALUES ('29', 'i18n List', '/sys/i18n/list', '1', '2014-06-13 20:19:21', '2014-07-29 09:25:51');
INSERT INTO `sys_route` VALUES ('30', 'Add i18n', '/sys/i18n/add', '1', '2014-06-13 20:19:39', '2014-07-29 09:25:52');
INSERT INTO `sys_route` VALUES ('31', 'Edit i18n', '/sys/i18n/edit/:i18nId', '1', '2014-06-13 20:19:53', '2014-07-29 09:25:55');
INSERT INTO `sys_route` VALUES ('361', 'Task List', '/order/task/list', '0', '2014-07-02 11:55:36', '2014-07-02 13:22:13');
INSERT INTO `sys_route` VALUES ('371', 'Task Handle', '/order/task/handle/:taskId', '0', '2014-07-02 15:01:16', '2014-07-02 15:01:16');
INSERT INTO `sys_route` VALUES ('391', 'Customer List', '/customer/list', '0', '2014-07-04 14:00:06', '2014-07-04 14:00:06');
INSERT INTO `sys_route` VALUES ('392', 'View Customer', '/customer/view/:customerId', '0', '2014-07-04 14:00:23', '2014-07-04 14:00:23');
INSERT INTO `sys_route` VALUES ('394', 'Edit Customer', '/customer/edit/:customerId', '0', '2014-07-04 14:00:48', '2014-07-04 14:00:48');
INSERT INTO `sys_route` VALUES ('2164', 'Edit Permission', '/sys/permission/edit/:menuId', '0', '2014-08-05 16:27:34', '2014-08-05 16:28:04');
INSERT INTO `sys_route` VALUES ('2165', 'Add Permission', '/sys/permission/add/:menuId', '0', '2014-08-05 16:27:46', '2014-08-05 16:27:58');
INSERT INTO `sys_route` VALUES ('2433', 'Company List', '/sys/company/list', '0', '2014-08-06 16:01:16', '2014-08-06 16:01:28');
INSERT INTO `sys_route` VALUES ('2434', 'Add Company', '/sys/company/add', '0', '2014-08-06 16:01:40', '2014-08-06 16:01:40');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `USER_ID` int(11) NOT NULL,
  `USERNAME` varchar(16) NOT NULL,
  `PASSWORD` varchar(64) NOT NULL,
  `FULL_NAME` varchar(32) NOT NULL,
  `EMAIL` varchar(64) DEFAULT NULL,
  `BIRTHDAY` date DEFAULT NULL,
  `SALT` varchar(64) DEFAULT NULL,
  `ENABLED` tinyint(1) DEFAULT NULL,
  `IS_ROOT` tinyint(1) DEFAULT NULL,
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('2161', 'root', '12cde7fd447dcb8561dad2d736f6763d', 'Adminiatrator', null, null, 'd6f4b7b827e87d4fcb3cf3ed343d2fde', null, '1', '2014-08-05 16:23:53', '2014-08-05 16:37:55');
INSERT INTO `sys_user` VALUES ('2253', 'zyz', '9d53d06a9a9a6de8dc9b232876648130', 'Edgar Zhang', null, null, '491ae093c155fc8bdafaf81ba638534a', null, '0', '2014-08-06 10:24:28', '2014-08-06 10:24:28');
INSERT INTO `sys_user` VALUES ('2454', 'yongan', '231852901d1147ba7302956a88e300a2', '永安保全', null, null, '1863718d151bb5616fef79058a374811', null, '0', '2014-08-06 17:08:21', '2014-08-06 17:08:21');
INSERT INTO `sys_user` VALUES ('2458', 'guardforce', '1957e062a339e6968048c02cda536195', 'guardforce', null, null, 'a4762459b4b6631641de373eab6ecc95', null, '0', '2014-08-06 17:09:13', '2014-08-06 17:09:13');

-- ----------------------------
-- Table structure for `sys_user_profile`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_profile`;
CREATE TABLE `sys_user_profile` (
  `PROFILE_ID` int(11) NOT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  `LANGUAGE` varchar(16) NOT NULL,
  PRIMARY KEY (`PROFILE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_profile
-- ----------------------------
INSERT INTO `sys_user_profile` VALUES ('2162', '2161', 'en');
INSERT INTO `sys_user_profile` VALUES ('2255', '2253', 'en');
INSERT INTO `sys_user_profile` VALUES ('2455', '2454', 'en');
INSERT INTO `sys_user_profile` VALUES ('2460', '2458', 'en');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `USER_ROLE_ID` int(11) NOT NULL,
  `USER_ID` int(11) DEFAULT NULL,
  `ROLE_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`USER_ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('2163', '2161', '2023');
INSERT INTO `sys_user_role` VALUES ('2254', '2253', '2225');
INSERT INTO `sys_user_role` VALUES ('2459', '2458', '2456');

-- ----------------------------
-- Table structure for `task`
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `TASK_ID` int(11) NOT NULL,
  `COMPANY_ID` int(11) DEFAULT NULL,
  `TASK_NAME` varchar(32) DEFAULT NULL,
  `ASSIGNEE` varchar(32) DEFAULT NULL,
  `ASSIGNEE_ID` int(11) DEFAULT NULL,
  `ASSIGNEE_TIME` decimal(14,0) DEFAULT NULL,
  `COMMENT` varchar(128) DEFAULT NULL,
  `TRACKER_ID` int(11) DEFAULT NULL,
  `TRACKER` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`TASK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='状态：未领取、进行中、完成\r\n类型：提交订单、分配任务、出库、领取保管箱、出库押运、客户签收、使用完毕、交还、';

-- ----------------------------
-- Records of task
-- ----------------------------

-- ----------------------------
-- Table structure for `test_table`
-- ----------------------------
DROP TABLE IF EXISTS `test_table`;
CREATE TABLE `test_table` (
  `TEST_CODE` varchar(32) NOT NULL,
  `DICT_NAME` varchar(32) NOT NULL,
  `PARENT_CODE` varchar(32) NOT NULL DEFAULT '-1',
  `SORTED` int(11) DEFAULT '9999',
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`TEST_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test_table
-- ----------------------------

-- ----------------------------
-- Table structure for `test2_table`
-- ----------------------------
DROP TABLE IF EXISTS `test2_table`;
CREATE TABLE `test2_table` (
  `TEST_ID` varchar(32) NOT NULL,
  `TEST_CODE2` varchar(32) NOT NULL,
  `DICT_NAME` varchar(32) NOT NULL,
  `PARENT_CODE` varchar(32) NOT NULL DEFAULT '-1',
  `SORTED` int(11) NOT NULL DEFAULT '9999',
  `CREATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATED_TIME` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`TEST_CODE2`,`TEST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of test2_table
-- ----------------------------
