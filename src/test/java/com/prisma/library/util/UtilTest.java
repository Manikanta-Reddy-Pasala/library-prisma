package com.prisma.library.util;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {
    @Test
    void testGetTodayDate() {
        assertNotNull(Util.getTodayDate());
    }

    @Test
    void testIsEmptyOrNullCheckNull() {
        assertTrue(Util.isEmptyOrNull(new ArrayList<>()));
        assertTrue(Util.isEmptyOrNull(null));
    }

    @Test
    void testIsEmptyOrNull() {
        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add("42");
        assertFalse(Util.isEmptyOrNull(objectList));
    }

    @Test
    void testIsNull() {
        assertFalse(Util.isNull("Object"));
        assertTrue(Util.isNull(null));
    }
}

