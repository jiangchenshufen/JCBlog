package com.jiangchen.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PathUtils {

    public static String generateFilePath(String fileName) {
        //根据日期生成路径
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = format.format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        return new StringBuffer().append(datePath).append(uuid).append(fileType).toString();

    }
}
