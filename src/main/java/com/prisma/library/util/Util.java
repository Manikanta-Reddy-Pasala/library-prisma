package com.prisma.library.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static com.prisma.library.util.Constants.DATE_FORMAT;

@UtilityClass
public class Util {

    /**
     *
     * @return today's date in yyyy-MM-dd format
     */
    public String getTodayDate() {

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        return date.format(formatter);
    }

    /**
     *
     * @param collection : takes collection as input
     * @return true of given collection is null or empty
     */
    public boolean isEmptyOrNull(Collection< ? > collection) {
        return (collection == null || collection.isEmpty());
    }

    /**
     *
     * @param object : takes any object as input
     * @return true if given object is null
     */
    public boolean isNull( Object object ){
        return object == null;
    }
}
