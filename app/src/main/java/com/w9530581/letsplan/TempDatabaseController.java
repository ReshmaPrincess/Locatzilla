package com.w9530581.letsplan;

import io.paperdb.Paper;

public class TempDatabaseController {
    public static final String USERNAME = "USERNAME";
    public static final String PERSONALITY = "PERSONALITY";

    public static boolean isKeyExist(String key) {
        return Paper.book().contains(key);
    }

    public static void setValue(String key, Object value) {
        Paper.book().write(key, value);
    }

    public static Object getValue(String key) {
        if (isKeyExist(key)) {
            return Paper.book().read(key);
        }
        return null;
    }


}
