package com.channelblab.springrain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-10-31 10:27
 * @description：
 * @modified By：
 */
@Service
public class FileServiceMinio extends FileService {
    @Override
    public String[] upload(MultipartFile[] files) {
        return new String[0];
    }
}
