package com.channelblab.springrain.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.channelblab.springrain.common.utils.ExcelUtil;
import com.channelblab.springrain.common.utils.MultilingualUtil;
import com.channelblab.springrain.dao.MultilingualDao;
import com.channelblab.springrain.model.Multilingual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    public List<Map<String, Object>> allLang(String symbol, String symbolDescribe) {
        List<Multilingual> multilinguals = multilingualDao.selectList(Wrappers.lambdaQuery(Multilingual.class).eq(!ObjectUtils.isEmpty(symbol), Multilingual::getSymbol, symbol)
                .like(!ObjectUtils.isEmpty(symbolDescribe), Multilingual::getSymbolDescribe, symbolDescribe).orderByAsc(Multilingual::getSymbol));
        List<Map<String, Object>> listRes = new ArrayList<>();
        //zh_CN as the default standard language
        List<Multilingual> standardLang = multilinguals.stream().filter(item -> item.getLangSymbol().equals("zh_CN")).collect(Collectors.toList());
        Map<String, List<Multilingual>> totalLangData = multilinguals.stream().collect(Collectors.groupingBy(Multilingual::getLangSymbol));
        standardLang.forEach(standard -> {
            Map<String, Object> mapRes = new HashMap<>();
            mapRes.put("id", standard.getId());
            mapRes.put("symbol", standard.getSymbol());
            mapRes.put("symbolValue", standard.getSymbolValue());
            mapRes.put("symbolDescribe", standard.getSymbolDescribe());
            for (String key : totalLangData.keySet()) {
                List<Multilingual> multilingualsWithLang = totalLangData.get(key);
                mapRes.put(key, getSymbolValue(standard, multilingualsWithLang));
            }
            listRes.add(mapRes);
        });
        return listRes;
    }

    private String getSymbolValue(Multilingual standard, List<Multilingual> multilingualsWithLang) {
        for (Multilingual item : multilingualsWithLang) {
            if (item.getSymbol().equals(standard.getSymbol())) {
                return item.getSymbolValue();
            }
        }
        return null;
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

    public List<Multilingual> langList() {
        return multilingualDao.selectList(
                Wrappers.lambdaQuery(Multilingual.class).select(Multilingual::getLangSymbol, Multilingual::getLangDescribe).groupBy(Multilingual::getLangSymbol, Multilingual::getLangDescribe));
    }

    @Transactional
    public void importExcel(MultipartFile file) {
        try {
            List<Multilingual> multilinguals = ExcelUtil.importMultilingualExcel(file.getInputStream());
            multilingualDao.delete(null);
            for (Multilingual multilingual : multilinguals) {
                multilingualDao.insert(multilingual);
            }
            //update cache
            List<Multilingual> multilingualList = multilingualDao.selectList(null);
            MultilingualUtil.updateData(multilingualList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Map<String, Object> dataForExport() {
        Map<String, Object> resData = new HashMap<>();
        List<Multilingual> multilinguals = multilingualDao.selectList(Wrappers.lambdaQuery(Multilingual.class).orderByAsc(Multilingual::getSymbol));
        List<Map<String, Object>> listRes = new ArrayList<>();
        //zh_CN as the default standard language
        List<Multilingual> standardLang = multilinguals.stream().filter(item -> item.getLangSymbol().equals("zh_CN")).collect(Collectors.toList());
        Map<String, List<Multilingual>> totalLangData = multilinguals.stream().collect(Collectors.groupingBy(Multilingual::getLangSymbol));
        standardLang.forEach(standard -> {
            Map<String, Object> mapRes = new HashMap<>();
            mapRes.put("id", standard.getId());
            mapRes.put("symbol", standard.getSymbol());
            mapRes.put("symbolValue", standard.getSymbolValue());
            mapRes.put("symbolDescribe", standard.getSymbolDescribe());
            for (String key : totalLangData.keySet()) {
                List<Multilingual> multilingualsWithLang = totalLangData.get(key);
                mapRes.put(key, getSymbolValue(standard, multilingualsWithLang));
            }
            listRes.add(mapRes);
        });
        resData.put("realData", listRes);
        Map<String, String> frameData = new HashMap<>();
        for (String key : totalLangData.keySet()) {
            frameData.put(key, totalLangData.get(key).get(0).getLangDescribe());
        }
        resData.put("frameData", frameData);
        return resData;
    }

    //todo need cache
    public List<Multilingual> frontedMultilingual() {
        return multilingualDao.selectList(Wrappers.lambdaQuery(Multilingual.class).eq(Multilingual::getType, "frontend"));
    }
}
