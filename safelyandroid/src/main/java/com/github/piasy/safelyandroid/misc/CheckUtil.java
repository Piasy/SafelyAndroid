package com.github.piasy.safelyandroid.misc;

/**
 * Created by Piasy{github.com/Piasy} on 8/8/16.
 */

public final class CheckUtil {
    private CheckUtil() {
        // no instance
    }

    public static boolean nonNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }
}
