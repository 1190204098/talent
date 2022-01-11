package com.talent.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jmj
 * @date 2021/8/2 16:54
 */
@Component
public class StrToDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        if (source.trim().length()!=0) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
