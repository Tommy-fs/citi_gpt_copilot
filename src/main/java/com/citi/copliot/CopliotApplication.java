package com.citi.copliot;

import com.citi.copliot.util.CsvUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.citi.copliot"})
public class CopliotApplication {

    public static void main(String[] args)		{

        CsvUtil.initialHolidays();
        SpringApplication.run(CopliotApplication.class, args);
    }



}
