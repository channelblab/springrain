package com.channelblab.springrain.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-10-31 10:27
 * @description：
 * @modified By：
 */
public class FileServiceLocal extends FileService {
    @Override
    public String[] upload(MultipartFile[] files) {
        return new String[0];
    }
}
