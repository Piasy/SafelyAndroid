/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.safelyandroid.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by Piasy{github.com/Piasy} on 4/2/16.
 *
 * Delegate to start activity safely, check intent safety before start it.
 */
public final class StartActivityDelegate {

    private StartActivityDelegate() {
        // no instance
    }

    /**
     * start activity from {@link android.support.v4.app.Fragment}
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivitySafely(@NonNull android.support.v4.app.Fragment fragment,
            @NonNull Intent intent) {
        return startActivitySafely(fragment, intent, null);
    }

    /**
     * start activity from {@link android.support.v4.app.Fragment}
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @param options options used to start activity
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivitySafely(@NonNull android.support.v4.app.Fragment fragment,
            @NonNull Intent intent, Bundle options) {
        if (isIntentSafe(fragment.getActivity().getPackageManager(), intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fragment.startActivity(intent, options);
            } else {
                fragment.startActivity(intent);
            }
            return true;
        }
        return false;
    }

    /**
     * start activity for result from {@link android.support.v4.app.Fragment}
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @param requestCode request code
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivityForResultSafely(
            @NonNull android.support.v4.app.Fragment fragment, @NonNull Intent intent,
            int requestCode) {
        return startActivityForResultSafely(fragment, intent, requestCode, null);
    }

    /**
     * start activity for result from {@link android.support.v4.app.Fragment}
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @param requestCode request code
     * @param options options used to start activity
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivityForResultSafely(
            @NonNull android.support.v4.app.Fragment fragment, @NonNull Intent intent,
            int requestCode, Bundle options) {
        if (isIntentSafe(fragment.getActivity().getPackageManager(), intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fragment.startActivityForResult(intent, requestCode, options);
            } else {
                fragment.startActivityForResult(intent, requestCode);
            }
            return true;
        }
        return false;
    }

    /**
     * start activity from {@link android.app.Fragment}
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivitySafely(@NonNull android.app.Fragment fragment,
            @NonNull Intent intent) {
        return startActivitySafely(fragment, intent, null);
    }

    /**
     * start activity from {@link android.app.Fragment}
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @param options options used to start activity
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivitySafely(@NonNull android.app.Fragment fragment,
            @NonNull Intent intent, Bundle options) {
        if (isIntentSafe(fragment.getActivity().getPackageManager(), intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fragment.startActivity(intent, options);
            } else {
                fragment.startActivity(intent);
            }
            return true;
        }
        return false;
    }

    /**
     * start activity for result from {@link android.app.Fragment}
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @param requestCode request code
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivityForResultSafely(@NonNull android.app.Fragment fragment,
            @NonNull Intent intent, int requestCode) {
        return startActivityForResultSafely(fragment, intent, requestCode, null);
    }

    /**
     * start activity for result from {@link android.app.Fragment}
     *
     * @param fragment fragment we start from
     * @param intent intent to start
     * @param requestCode request code
     * @param options options used to start activity
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivityForResultSafely(@NonNull android.app.Fragment fragment,
            @NonNull Intent intent, int requestCode, Bundle options) {
        if (isIntentSafe(fragment.getActivity().getPackageManager(), intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                fragment.startActivityForResult(intent, requestCode, options);
            } else {
                fragment.startActivityForResult(intent, requestCode);
            }
            return true;
        }
        return false;
    }

    /**
     * start activity from {@link Context}
     *
     * @param context context we start from
     * @param intent intent to start
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivitySafely(@NonNull Context context, @NonNull Intent intent) {
        return startActivitySafely(context, intent, null);
    }

    /**
     * start activity from {@link Context}
     *
     * @param context context we start from
     * @param intent intent to start
     * @param options options used to start activity
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivitySafely(@NonNull Context context, @NonNull Intent intent,
            Bundle options) {
        if (isIntentSafe(context.getPackageManager(), intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                context.startActivity(intent, options);
            } else {
                context.startActivity(intent);
            }
            return true;
        }
        return false;
    }

    /**
     * start activity for result from {@link Activity}
     *
     * @param activity activity we start from
     * @param intent intent to start
     * @param requestCode request code
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivityForResultSafely(@NonNull Activity activity,
            @NonNull Intent intent, int requestCode) {
        return startActivityForResultSafely(activity, intent, requestCode, null);
    }

    /**
     * start activity for result from {@link Activity}
     *
     * @param activity activity we start from
     * @param intent intent to start
     * @param requestCode request code
     * @param options options used to start activity
     * @return {@code true} if we start it safely, {@code false} if it's unsafe so we didn't start
     * it
     */
    public static boolean startActivityForResultSafely(@NonNull Activity activity,
            @NonNull Intent intent, int requestCode, Bundle options) {
        if (isIntentSafe(activity.getPackageManager(), intent)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                activity.startActivityForResult(intent, requestCode, options);
            } else {
                activity.startActivityForResult(intent, requestCode);
            }
            return true;
        }
        return false;
    }

    /**
     * this method is from the official guide:
     * http://developer.android.com/training/basics/intents/sending.html#StartActivity
     * */
    private static boolean isIntentSafe(PackageManager packageManager, Intent intent) {
        return !packageManager.queryIntentActivities(intent, 0).isEmpty();
    }
}
