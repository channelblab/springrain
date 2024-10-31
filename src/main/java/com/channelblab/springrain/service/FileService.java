package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.channelblab.springrain.dao.FileDao;
import com.channelblab.springrain.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-10-31 9:39
 * @description：
 * @modified By：
 */
@Service
public abstract class FileService {
    @Autowired
    private FileDao fileDao;

    public abstract String[] upload(MultipartFile[] files);

    public IPage<File> page(Integer page, Integer size, String name) {
        IPage<File> pageParam = new Page<>(page, size);
        return fileDao.selectPage(pageParam, Wrappers.lambdaQuery(File.class));
    }
}
