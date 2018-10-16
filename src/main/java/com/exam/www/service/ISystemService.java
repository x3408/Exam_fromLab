package com.exam.www.service;


import com.exam.www.entity.DataDictionary;
import com.exam.www.utils.DataRequest;
import com.exam.www.utils.DataTableReturnObject;

import java.util.List;

/**
 * Created by admin on 2017/2/7.
 */
public interface ISystemService {

    public List<DataDictionary> getDataDictionaryByType(String type);

    /*public CheckCode getCheckCode(String mobilePhone);

    public void saveCheckCode(CheckCode checkCode);*/

    public List<DataDictionary> getDictionaryTree();

    public DataTableReturnObject getDataDictionaryPageMode(DataRequest dr, final String type);

    public void saveDataDictionary(DataDictionary dataDictionary);

    public boolean checkDataDictionaryTypeCodeExist(String type, String code, String id);

    public void deleteDataDictionary(String[] ids);

    public DataDictionary getDataDictionary(String id);

    public void updateDataDictionary(DataDictionary dataDictionary);

}
