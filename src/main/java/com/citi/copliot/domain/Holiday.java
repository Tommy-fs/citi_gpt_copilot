package com.citi.copliot.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Holiday {
    private String countryCode;
    private String countryDesc;
    private String holidayCode;
    private Date holidayDate;

}
