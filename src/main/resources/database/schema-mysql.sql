CREATE TABLE `system_multilingual` (
                                       `id` int NOT NULL AUTO_INCREMENT,
                                       `lang` varchar(100) NOT NULL,
                                       `symbol` varchar(200) NOT NULL,
                                       `value` varchar(255) NOT NULL,
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `system_log` (
                              `id` varchar(100) NOT NULL,
                              `user_id` varchar(100) DEFAULT NULL,
                              `source_ip` varchar(200) NOT NULL,
                              `request_uri` varchar(255) NOT NULL,
                              `status` varchar(10) NOT NULL,
                              `request` varchar(255) NOT NULL,
                              `response` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
                              `cost_time` int NOT NULL,
                              `create_time` datetime NOT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `perm_permission` (
                                   `id` varchar(100) NOT NULL,
                                   `type` varchar(100) DEFAULT NULL,
                                   `uris` varchar(255) DEFAULT NULL,
                                   `parent_id` varchar(100) DEFAULT NULL,
                                   `name` varchar(255) DEFAULT NULL,
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;