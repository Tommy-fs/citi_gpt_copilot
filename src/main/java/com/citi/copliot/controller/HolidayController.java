package com.citi.copliot.controller;

import com.citi.copliot.domain.Holiday;
import com.citi.copliot.domain.HolidayJudge;
import com.citi.copliot.util.CsvUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping(path = "/holiday")
public class HolidayController {

    //generate a restful api to add a holiday

    @PostMapping("/addHolidays")
    public String AddHolidays(ArrayList<Holiday> data) {
        //use CsvUtil to write data to csv file
        ArrayList<Holiday> holidayArrayList = CsvUtil.read("holiday.csv");
        //循环遍历holidayArrayList，如果有相同的holidayCode和holidayDate，就不添加

        //for循环遍历data
        for (Holiday holiday : data) {
            if (holiday.getHolidayCode().equals("") || holiday.getHolidayDate().equals("")) {
                return "holidayCode or holidayDate is null";
            }
            int i;
            for (i = 0; i < holidayArrayList.size(); i++) {
                Holiday holiday1 = holidayArrayList.get(i);
                if (holiday1.getHolidayCode().equals(holiday.getHolidayCode()) && holiday1.getHolidayDate().equals(holiday.getHolidayDate())) {
                    break;
                }
            }
            if (i == holidayArrayList.size())
                holidayArrayList.add(holiday);
        }

        CsvUtil.write(holidayArrayList, "holiday.csv");

        return "addHolidays";
    }

    @PostMapping("/updateHolidays") //update holiday by id
    public String UpdateHolidays(Holiday holiday) {
        // update holiday to csv file

        ArrayList<Holiday> holidayArrayList = CsvUtil.read("holiday.csv");
        for (int i = 0; i < holidayArrayList.size(); i++) {
            Holiday holiday1 = holidayArrayList.get(i);
            if (holiday1.getHolidayCode().equals(holiday.getHolidayCode()) && holiday1.getHolidayDate().equals(holiday.getHolidayDate())) {
                holidayArrayList.set(i, holiday);
                break;
            }
        }

        CsvUtil.write(holidayArrayList, "holiday.csv");

        return "updateHolidays";
    }


    @PostMapping("/removeHolidays") //remove holiday by holidayCode and holidayDate
    public String RemoveHolidays(Holiday holiday) {
        //remove holiday by holidayCode and holidayDate

        ArrayList<Holiday> holidayArrayList = CsvUtil.read("holiday.csv");
        for (int i = 0; i < holidayArrayList.size(); i++) {
            Holiday holiday1 = holidayArrayList.get(i);
            if (holiday1.getHolidayCode().equals(holiday.getHolidayCode()) && holiday1.getHolidayDate().equals(holiday.getHolidayDate())) {
                holidayArrayList.remove(i);
                break;
            }
        }

        CsvUtil.write(holidayArrayList, "holiday.csv");

        return "RemoveHolidays";
    }

    @PostMapping("/getNextYearHolidays") //get next year holidays by countryCode
    public ArrayList<Holiday> GetNextYearHolidays(String countryCode) {
        ArrayList<Holiday> res = null;
        //get next year holidays by countryCode
        ArrayList<Holiday> holidayArrayList = CsvUtil.read("holiday.csv");
        for (int i = 0; i < holidayArrayList.size(); i++) {
            Holiday holiday1 = holidayArrayList.get(i);
            if (holiday1.getCountryCode().equals(countryCode)) {
                res.add(holiday1);
            }
        }

        for (int i = 0; i < res.size(); i++) {
            Holiday holiday = res.get(i);
            if (holiday.getHolidayDate().getYear() != new java.util.Date().getYear() + 1)
                res.remove(i);
        }
        return res;
    }

    @PostMapping("/getNextHolidays") //get next holidays by countryCode
    public Holiday getNextHolidays(String countryCode) {
        //get next holidays by countryCode
        ArrayList<Holiday> holidayArrayList = CsvUtil.read("holiday.csv");

        ArrayList<Holiday> res = null;

        for (int i = 0; i < holidayArrayList.size(); i++) {
            Holiday holiday1 = holidayArrayList.get(i);
            if (holiday1.getCountryCode().equals(countryCode)) {
                res.add(holiday1);
            }
        }

        Date date = new Date();
        Holiday holiday = null;
        //在res中找出离今天最近的一个未来的Holiday
        for (int i = 0; i < res.size(); i++) {
            Holiday holiday1 = res.get(i);
            if (holiday1.getHolidayDate().getTime()>=date.getTime())
                holiday = holiday1;
        }
        for (int i = 1; i < res.size(); i++) {
            Holiday holiday1 = res.get(i);
            if (holiday1.getHolidayDate().getTime()>=date.getTime() && holiday1.getHolidayDate().getTime()< holiday.getHolidayDate().getTime())
                holiday = holiday1;
        }
        return holiday;
    }

    @PostMapping("/checkDateIsOrNotHoliday") //check date is or not holiday by date
    public ArrayList<HolidayJudge> checkDateIsOrNotHoliday(Date date){
        ArrayList<HolidayJudge> res=null;
        //check date is or not holiday by date
        ArrayList<Holiday> holidayArrayList = CsvUtil.read("holiday.csv");
        for (int i = 0; i < holidayArrayList.size(); i++) {
            Holiday holiday1 = holidayArrayList.get(i);
            if (holiday1.getHolidayDate().equals(date)) {
                HolidayJudge holidayJudge = new HolidayJudge();
                holidayJudge.setCountryCode(holiday1.getCountryCode());
                holidayJudge.setHoliday(true);
                res.add(holidayJudge);
            }else {
                HolidayJudge holidayJudge = new HolidayJudge();
                holidayJudge.setCountryCode(holiday1.getCountryCode());
                holidayJudge.setHoliday(false);
                res.add(holidayJudge);
            }
        }
        return res;
    }
    @PostMapping("/InitalHolidays") //Inital Holidays
    public String InitalHolidays(){
        //Inital Holidays
        CsvUtil.initialHolidays();
        return "InitalHolidays";
    }
}
