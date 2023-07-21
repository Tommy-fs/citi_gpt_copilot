package com.citi.copliot.util;

import com.citi.copliot.domain.Holiday;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CsvUtil {
    public static void write(ArrayList<Holiday> data, String path) {
        int getPhoneNum = 19;

        try {
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
            for (int i = 0; i < data.size(); i++) {
                Holiday onerow = data.get(i);
                //write onerow 所有的属性 用逗号隔开
                out.write(DelQuota(onerow.getCountryCode()));
                out.write(",");
                out.write(DelQuota(onerow.getCountryDesc()));
                out.write(",");
                out.write(DelQuota(onerow.getHolidayCode()));
                out.write(",");
                out.write(DelQuota(String.valueOf(onerow.getHolidayDate())));
                out.write(",");
                out.newLine();
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String DelQuota(String str) {
        String result = str;
        String[] strQuota = {"~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "`", ";", "'", ",", ".", "/", ":", "/,", "<", ">", "?"};
        for (int i = 0; i < strQuota.length; i++) {
            if (result.indexOf(strQuota[i]) > -1)
                result = result.replace(strQuota[i], "");
        }
        return result;
    }

    public static ArrayList<Holiday> read(String path) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            ArrayList<Holiday> alldata = new ArrayList<Holiday>();
            String line;
            String[] onerow;
            while ((line = in.readLine()) != null) {
                onerow = line.split(",");
                List<String> onerowlist = Arrays.asList(onerow);
                //将onerowlist转换为Holiday对象
                Holiday holiday = new Holiday();
                holiday.setCountryCode(onerowlist.get(0));
                holiday.setCountryDesc(onerowlist.get(1));
                holiday.setHolidayCode(onerowlist.get(2));
                Date date = new Date();
                date = new SimpleDateFormat("yyyy-MM-dd").parse(onerowlist.get(3));
                holiday.setHolidayDate(date);
                alldata.add(holiday);
            }
            in.close();
            return alldata;
        } catch (Exception e) {
            return null;
        }

    }

    public static void initialHolidays() {
        String str[] = new String[10000];
        for (int i = 0; i < str.length; i++) {
            str[i] = i + "";
        }

        ArrayList<Holiday> alldata = new ArrayList<Holiday>();
        //随机生成holiday30个对象 用于测试
        for (int i = 0; i < 30; i++) {
            Holiday holiday = new Holiday();
            holiday.setCountryCode("countryCode" + i);
            holiday.setCountryDesc("countryDesc" + i);
            holiday.setHolidayCode("holidayCode" + i);
            holiday.setHolidayDate(new Date());
            alldata.add(holiday);
        }

        CsvUtil.write(alldata, "test.csv");

    }

}
