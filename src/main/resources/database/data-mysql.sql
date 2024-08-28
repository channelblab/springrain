-- ----------------------------
-- Records of perm_permission
-- ----------------------------
INSERT INTO `perm_permission` VALUES ('1', 'MENU', '/log/page,/user/currentOnlineUsers', NULL, '多语言的一级菜单', NULL);
INSERT INTO `perm_permission` VALUES ('11', 'MENU', NULL, '1', '二级菜单1', NULL);
INSERT INTO `perm_permission` VALUES ('111', 'MENU', 'kkk', '11', '三级菜单', NULL);
INSERT INTO `perm_permission` VALUES ('1111', 'MENU', NULL, '111', '四级菜单', NULL);
INSERT INTO `perm_permission` VALUES ('12', 'MENU', 'bbb', '1', '二级菜单2', NULL);
INSERT INTO `perm_permission` VALUES ('2', 'API', 'aaa', NULL, '一级api', NULL);
INSERT INTO `perm_permission` VALUES ('3', 'BUTTON', 'ababab/fafdsa,/fdsfsadfs/fff/f,rr/rrdfd/d', NULL, '一级按钮', NULL);
-- ----------------------------
-- Records of perm_role
-- ----------------------------
INSERT INTO `perm_role` VALUES ('1826865938558935042', '测试1111', '范德萨范德萨发的湿哒哒多撒发大水打撒法大使士大夫啊', '2024-08-23 14:15:56', '2024-08-23 14:18:03', 'CUSTOM');
-- ----------------------------
-- Records of perm_role_permission
-- ----------------------------
INSERT INTO `perm_role_permission` VALUES ('1826866472560943105', '1826865938558935042', '2');
-- ----------------------------
-- Records of perm_user_role
-- ----------------------------
INSERT INTO `perm_user_role` VALUES ('1826866472623857665', '1', '1826865938558935042');
-- ----------------------------
-- Records of system_department
-- ----------------------------
INSERT INTO `system_department` VALUES ('1', NULL, NULL, '奇迹公司', 1);
INSERT INTO `system_department` VALUES ('11', '1', NULL, '技术开发', 1);
INSERT INTO `system_department` VALUES ('111', '11', NULL, '技术开发部', 1);
INSERT INTO `system_department` VALUES ('1111', '111', NULL, '技术开发1部', 1);
INSERT INTO `system_department` VALUES ('1112', '111', NULL, '技术开发2部', 1);
INSERT INTO `system_department` VALUES ('112', '11', NULL, '系统运维部', 1);
INSERT INTO `system_department` VALUES ('113', '11', NULL, '售后支持部', 0);
INSERT INTO `system_department` VALUES ('1131', '113', NULL, '售后1部', 0);
INSERT INTO `system_department` VALUES ('1132', '113', NULL, '售后2部', 0);
INSERT INTO `system_department` VALUES ('12', '1', NULL, '人事行政', 1);
INSERT INTO `system_department` VALUES ('121', '12', NULL, '人事部', 1);
INSERT INTO `system_department` VALUES ('122', '12', NULL, '行政部', 1);
INSERT INTO `system_department` VALUES ('1828671062029631490', '1828669329446535169', NULL, '通天塔吞吞吐吐2', 0);
INSERT INTO `system_department` VALUES ('1828672036890738690', '1828669329446535169', NULL, '给个广告费', 0);
INSERT INTO `system_department` VALUES ('1828672178058428418', '1828669329446535169', NULL, '大幅度发的', 1);
INSERT INTO `system_department` VALUES ('1828672883238371330', '1828669329446535169', NULL, '二热热热', 1);
INSERT INTO `system_department` VALUES ('1828673320519729154', '1828669329446535169', NULL, '大萨达撒大撒', 1);
INSERT INTO `system_department` VALUES ('1828678583431725057', '1828669329446535169', NULL, '大萨达撒大撒5', 1);
INSERT INTO `system_department` VALUES ('1828678871005790209', '1828669329446535169', '对对对', '对对对', 1);
INSERT INTO `system_department` VALUES ('1828680986767917057', '1', NULL, '测试部门', 0);
INSERT INTO `system_department` VALUES ('1828681360136470530', '1828680986767917057', NULL, '对对对', 0);
INSERT INTO `system_department` VALUES ('1828681529510854658', '1828680986767917057', NULL, '嘻嘻嘻', 0);
INSERT INTO `system_department` VALUES ('1828681696704200705', '1828680986767917057', NULL, '休息休息吧', 0);
INSERT INTO `system_department` VALUES ('1828682444351471618', '1828681360136470530', NULL, '用英语2', 0);
INSERT INTO `system_department` VALUES ('1828682536567439361', '1828682444351471618', NULL, '热热热', 0);
INSERT INTO `system_department` VALUES ('2', NULL, NULL, '外围公司一', 0);
INSERT INTO `system_department` VALUES ('21', '2', NULL, '保安部', 0);
-- ----------------------------
-- Records of system_multilingual
-- ----------------------------
INSERT INTO `system_multilingual` VALUES ('1826156780381634561', 'zh_CN', '简体中文', 'login_expire', '登录已过期', '登录过期提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780381634562', 'zh_CN', '简体中文', 'success_code_msg', '操作成功', '操作成功提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780381634563', 'zh_CN', '简体中文', 'username_password_error_code_msg', '用户名或密码错误', '用户名或密码错误提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780381634564', 'zh_CN', '简体中文', 'rate_limit_code_msg', '请求太过频繁', '限流提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780381634565', 'zh_CN', '简体中文', 'no_permission_msg', '无权访问', '无权访问提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780381634566', 'zh_hans', '繁體中文', 'login_expire', '登錄過期', '登录过期提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780381634567', 'zh_hans', '繁體中文', 'success_code_msg', '操作成功', '操作成功提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780452937730', 'zh_hans', '繁體中文', 'username_password_error_code_msg', '用戶名或密碼錯誤', '用户名或密码错误提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780452937731', 'zh_hans', '繁體中文', 'rate_limit_code_msg', '請求太過頻繁', '限流提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780452937732', 'zh_hans', '繁體中文', 'no_permission_msg', '無權訪問', '无权访问提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780452937733', 'en', '英语', 'login_expire', 'login expire', '登录过期提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780452937734', 'en', '英语', 'success_code_msg', 'operation success', '操作成功提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780452937735', 'en', '英语', 'username_password_error_code_msg', 'user name or password error', '用户名或密码错误提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780452937736', 'en', '英语', 'rate_limit_code_msg', 'too many request', '限流提示', 'backend');
INSERT INTO `system_multilingual` VALUES ('1826156780452937737', 'en', '英语', 'no_permission_msg', 'no permission to access', '无权访问提示', 'backend');

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('1', 'Bruce', 'abc@abc.com', '$2a$10$V20kQbN/uJxqttCqNVB6F.FQSpv7T/h.Me7arh76uKa2TNyZ7sYLm', 1, '1', '2024-08-21 16:30:32', '2024-08-21 16:30:35');
INSERT INTO `system_user` VALUES ('2', 'kk', 'abb@abb.com', 'k', 1, NULL, NULL, NULL);
INSERT INTO `system_user` VALUES ('3', 'll', 'll@ll.com', 'll', 1, NULL, NULL, NULL);