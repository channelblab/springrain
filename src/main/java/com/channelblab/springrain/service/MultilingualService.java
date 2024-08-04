package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.channelblab.springrain.dao.MultilingualDao;
import com.channelblab.springrain.model.Multilingual;
import com.channelblab.springrain.model.MultilingualDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author     ：dengyi(A.K.A Bear)
 * @date       ：Created in 2024-07-23 8:33
 * @description：
 * @modified By：
 */
@Service
public class MultilingualService {
    @Autowired
    private MultilingualDao multilingualDao;

    public MultilingualDto allLang() {
        List<Multilingual> multilinguals = multilingualDao.selectList(Wrappers.lambdaQuery(Multilingual.class).orderByAsc(Multilingual::getSymbol));
        MultilingualDto multilingualDto = new MultilingualDto();
        //zh_CN as the default standard language
        List<Multilingual> standardLang = multilinguals.stream().filter(item -> item.getLangSymbol().equals("zh_CN")).collect(Collectors.toList());
        multilingualDto.setStandardLang(standardLang);
        Map<String, List<Multilingual>> totalLangData = multilinguals.stream().collect(Collectors.groupingBy(Multilingual::getLangSymbol));
        multilingualDto.setTotalLangData(totalLangData);
        return multilingualDto;
    }

    @Transactional
    public void addOrUpdate(Multilingual multilingual) {
        if (ObjectUtils.isEmpty(multilingual.getId())) {
            //add
            multilingualDao.insert(multilingual);
        } else {
            multilingualDao.updateById(multilingual);
        }
    }
}
