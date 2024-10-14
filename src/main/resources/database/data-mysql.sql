

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for perm_permission
-- ----------------------------
DROP TABLE IF EXISTS `perm_permission`;
CREATE TABLE `perm_permission`  (
                                    `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                    `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `uris` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `parent_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    `symbol` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of perm_permission
-- ----------------------------
INSERT INTO `perm_permission` VALUES ('1', 'MENU', '', NULL, '系统设置', '/setting/father');
INSERT INTO `perm_permission` VALUES ('11', 'MENU', '/user/page,/department/tree,/role/forSelect', '1', '组织架构', '/setting/structure');
INSERT INTO `perm_permission` VALUES ('111', 'BUTTON', NULL, '11', '新增用户按钮', 'button-structure-add');
INSERT INTO `perm_permission` VALUES ('12', 'MENU', '/role/page,/permission/tree,/role/forSelect,/department/search,/department/deptUserTree', '1', '角色权限', '/setting/rolepermission');
INSERT INTO `perm_permission` VALUES ('2', 'MENU', '', NULL, '更多', '/more/father');
INSERT INTO `perm_permission` VALUES ('21', 'MENU', '', '2', '多语言设置', '/more/multilingual');
INSERT INTO `perm_permission` VALUES ('22', 'MENU', '', '2', '权限设置', '/more/permission');
INSERT INTO `perm_permission` VALUES ('23', 'MENU', '', '2', '操作日志', '/more/operationLog');

-- ----------------------------
-- Table structure for perm_role
-- ----------------------------
DROP TABLE IF EXISTS `perm_role`;
CREATE TABLE `perm_role`  (
                              `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                              `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                              `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                              `create_time` datetime NOT NULL,
                              `update_time` datetime NULL DEFAULT NULL,
                              `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of perm_role
-- ----------------------------
INSERT INTO `perm_role` VALUES ('1844264172260548609', '系统设置权限', '的点点滴滴', '2024-10-10 14:30:18', '2024-10-10 14:30:18', 'CUSTOM');
INSERT INTO `perm_role` VALUES ('1844264252602441730', '更多权限', '对对对对对对', '2024-10-10 14:30:37', '2024-10-14 15:50:55', 'CUSTOM');

-- ----------------------------
-- Table structure for perm_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `perm_role_permission`;
CREATE TABLE `perm_role_permission`  (
                                         `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                         `role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                         `permission_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of perm_role_permission
-- ----------------------------
INSERT INTO `perm_role_permission` VALUES ('1844264172327657473', '1844264172260548609', '1');
INSERT INTO `perm_role_permission` VALUES ('1844264172327657474', '1844264172260548609', '11');
INSERT INTO `perm_role_permission` VALUES ('1844264172327657475', '1844264172260548609', '12');
INSERT INTO `perm_role_permission` VALUES ('1845734011969814530', '1844264252602441730', '2');
INSERT INTO `perm_role_permission` VALUES ('1845734012003368962', '1844264252602441730', '21');
INSERT INTO `perm_role_permission` VALUES ('1845734012003368963', '1844264252602441730', '22');
INSERT INTO `perm_role_permission` VALUES ('1845734012070477825', '1844264252602441730', '23');

-- ----------------------------
-- Table structure for perm_user_role
-- ----------------------------
DROP TABLE IF EXISTS `perm_user_role`;
CREATE TABLE `perm_user_role`  (
                                   `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   `role_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of perm_user_role
-- ----------------------------
INSERT INTO `perm_user_role` VALUES ('1844287101161521154', '3', '1844264172260548609');
INSERT INTO `perm_user_role` VALUES ('1845734012070477826', '3', '1844264252602441730');
INSERT INTO `perm_user_role` VALUES ('1845734012137586690', '2', '1844264252602441730');

-- ----------------------------
-- Table structure for system_department
-- ----------------------------
DROP TABLE IF EXISTS `system_department`;
CREATE TABLE `system_department`  (
                                      `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                      `parent_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `manager_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                      `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                      `enable` tinyint NOT NULL,
                                      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

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
INSERT INTO `system_department` VALUES ('1843545251979018241', '1', NULL, '测试部门', 1);
INSERT INTO `system_department` VALUES ('1843545309755555842', '1843545251979018241', NULL, '测试子部门', 1);
INSERT INTO `system_department` VALUES ('2', NULL, NULL, '外围公司一', 0);
INSERT INTO `system_department` VALUES ('21', '2', NULL, '保安部', 0);

-- ----------------------------
-- Table structure for system_log
-- ----------------------------
DROP TABLE IF EXISTS `system_log`;
CREATE TABLE `system_log`  (
                               `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                               `user_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                               `source_ip` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                               `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                               `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                               `request` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                               `response` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL,
                               `cost_time` int NOT NULL,
                               `create_time` datetime NOT NULL,
                               `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                               PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;


-- ----------------------------
-- Table structure for system_multilingual
-- ----------------------------
DROP TABLE IF EXISTS `system_multilingual`;
CREATE TABLE `system_multilingual`  (
                                        `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                        `lang_symbol` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                        `lang_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                        `symbol` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                        `symbol_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                        `symbol_describe` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_multilingual
-- ----------------------------
INSERT INTO `system_multilingual` VALUES ('1833346293126213633', 'zh-TW', '繁體中文', 'success_code_msg', '處理成功', '后端接口请求成功描述');
INSERT INTO `system_multilingual` VALUES ('1833346293126213634', 'zh-TW', '繁體中文', 'login_expire', '登錄已過期', '后端登录过期接口描述');
INSERT INTO `system_multilingual` VALUES ('1833346293193322497', 'zh-TW', '繁體中文', 'username_password_error_code_msg', '用戶名或密碼錯誤', '后端登录接口用户名或者密码错误描述');
INSERT INTO `system_multilingual` VALUES ('1833346293193322498', 'zh-TW', '繁體中文', 'rate_limit_code_msg', '請求數據過快', '后端接口请求时触发限流描述');
INSERT INTO `system_multilingual` VALUES ('1833346293193322499', 'zh-TW', '繁體中文', 'no_permission_msg', '無權限訪問', '后端访问无权限提示描述');
INSERT INTO `system_multilingual` VALUES ('1833346293193322500', 'zh-TW', '繁體中文', 'common_error_code_msg', '處理失敗', '公共错误提示描述');
INSERT INTO `system_multilingual` VALUES ('1833346293193322501', 'zh-TW', '繁體中文', 'main_menu_system_setting', '系統設定', '前端主菜单系统设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293193322502', 'zh-TW', '繁體中文', 'main_menu_system_setting_structure', '組織架構設定', '前端菜单组织架构设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293193322503', 'zh-TW', '繁體中文', 'main_menu_system_setting_role_permission', '角色權限設定', '前端菜单角色权限设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293193322504', 'zh-TW', '繁體中文', 'main_menu_more', '更多設定', '前端菜单更多设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293260431361', 'zh-TW', '繁體中文', 'main_menu_more_multilingual', '多語言設定', '前端菜单多语言设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293260431362', 'zh-TW', '繁體中文', 'main_menu_more_permission', '權限設定', '前端菜单权限设置');
INSERT INTO `system_multilingual` VALUES ('1833346293260431363', 'zh-TW', '繁體中文', 'main_menu_more_operation_log', '操作日志', '前端菜单操作日志描述');
INSERT INTO `system_multilingual` VALUES ('1833346293260431364', 'en', 'English', 'success_code_msg', 'operation success', '后端接口请求成功描述');
INSERT INTO `system_multilingual` VALUES ('1833346293260431365', 'en', 'English', 'login_expire', 'login expire', '后端登录过期接口描述');
INSERT INTO `system_multilingual` VALUES ('1833346293260431366', 'en', 'English', 'username_password_error_code_msg', 'username or password error', '后端登录接口用户名或者密码错误描述');
INSERT INTO `system_multilingual` VALUES ('1833346293260431367', 'en', 'English', 'rate_limit_code_msg', 'request data too fast ', '后端接口请求时触发限流描述');
INSERT INTO `system_multilingual` VALUES ('1833346293327540226', 'en', 'English', 'no_permission_msg', 'no permission to request data', '后端访问无权限提示描述');
INSERT INTO `system_multilingual` VALUES ('1833346293394649090', 'en', 'English', 'common_error_code_msg', 'operation fail', '公共错误提示描述');
INSERT INTO `system_multilingual` VALUES ('1833346293520478210', 'en', 'English', 'main_menu_system_setting', 'System Setting', '前端主菜单系统设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293591781378', 'en', 'English', 'main_menu_system_setting_structure', 'Department Set', '前端菜单组织架构设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293591781379', 'en', 'English', 'main_menu_system_setting_role_permission', 'Role And Permission', '前端菜单角色权限设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293654695938', 'en', 'English', 'main_menu_more', 'More Setting', '前端菜单更多设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293654695939', 'en', 'English', 'main_menu_more_multilingual', 'Multilingual', '前端菜单多语言设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293721804802', 'en', 'English', 'main_menu_more_permission', 'Permission', '前端菜单权限设置');
INSERT INTO `system_multilingual` VALUES ('1833346293721804803', 'en', 'English', 'main_menu_more_operation_log', 'Operation Log', '前端菜单操作日志描述');
INSERT INTO `system_multilingual` VALUES ('1833346293788913666', 'zh-CN', '简体中文', 'success_code_msg', '操作成功', '后端接口请求成功描述');
INSERT INTO `system_multilingual` VALUES ('1833346293788913667', 'zh-CN', '简体中文', 'login_expire', '登录过期', '后端登录过期接口描述');
INSERT INTO `system_multilingual` VALUES ('1833346293856022529', 'zh-CN', '简体中文', 'username_password_error_code_msg', '用户名或密码错误', '后端登录接口用户名或者密码错误描述');
INSERT INTO `system_multilingual` VALUES ('1833346293856022530', 'zh-CN', '简体中文', 'rate_limit_code_msg', '数据请求过快', '后端接口请求时触发限流描述');
INSERT INTO `system_multilingual` VALUES ('1833346293856022531', 'zh-CN', '简体中文', 'no_permission_msg', '无权访问', '后端访问无权限提示描述');
INSERT INTO `system_multilingual` VALUES ('1833346293923131394', 'zh-CN', '简体中文', 'common_error_code_msg', '操作失败', '公共错误提示描述');
INSERT INTO `system_multilingual` VALUES ('1833346293923131395', 'zh-CN', '简体中文', 'main_menu_system_setting', '系统设置', '前端主菜单系统设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293923131396', 'zh-CN', '简体中文', 'main_menu_system_setting_structure', '组织架构设置', '前端菜单组织架构设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293990240257', 'zh-CN', '简体中文', 'main_menu_system_setting_role_permission', '角色权限设置', '前端菜单角色权限设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346293990240258', 'zh-CN', '简体中文', 'main_menu_more', '更多', '前端菜单更多设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346294048960513', 'zh-CN', '简体中文', 'main_menu_more_multilingual', '多语言设置', '前端菜单多语言设置描述');
INSERT INTO `system_multilingual` VALUES ('1833346294048960514', 'zh-CN', '简体中文', 'main_menu_more_permission', '权限设置', '前端菜单权限设置');
INSERT INTO `system_multilingual` VALUES ('1833346294048960515', 'zh-CN', '简体中文', 'main_menu_more_operation_log', '操作日志', '前端菜单操作日志描述');

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
                                `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                                `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                `pass` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                `enable` tinyint(1) NULL DEFAULT NULL,
                                `department_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                `create_time` datetime NULL DEFAULT NULL,
                                `update_time` datetime NULL DEFAULT NULL,
                                `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
                                PRIMARY KEY (`id`) USING BTREE,
                                INDEX `department_id`(`department_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('1', 'Bruce123456789', 'abc@abc.com', '$2a$10$V20kQbN/uJxqttCqNVB6F.FQSpv7T/h.Me7arh76uKa2TNyZ7sYLm', 1, '1', '2024-08-21 16:30:32', '2024-08-21 16:30:35', NULL);
INSERT INTO `system_user` VALUES ('123', '辅导辅导', '222@2.com', NULL, 1, '1111', '2024-10-08 14:47:05', '2024-10-08 14:47:05', NULL);
INSERT INTO `system_user` VALUES ('2', 'kk', 'abb@abb.com', 'k', 1, '1112', '2024-10-08 14:47:05', '2024-10-14 10:22:26', NULL);
INSERT INTO `system_user` VALUES ('3', 'll', 'll@ll.com', '$2a$10$V20kQbN/uJxqttCqNVB6F.FQSpv7T/h.Me7arh76uKa2TNyZ7sYLm', 1, '11', '2024-10-08 14:47:05', '2024-10-10 16:01:25', NULL);

SET FOREIGN_KEY_CHECKS = 1;
