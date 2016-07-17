package com.github.piasy.safelyandroid.fragment;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

/**
 * Created by Piasy{github.com/Piasy} on 7/15/16.
 */

public class SupportFragmentTransactionBuilder {

    private final FragmentManager mFragmentManager;
    private final FragmentTransaction mTransaction;

    private SupportFragmentTransactionBuilder(FragmentManager fragmentManager,
            FragmentTransaction transaction) {
        mFragmentManager = fragmentManager;
        mTransaction = transaction;
    }

    public static SupportFragmentTransactionBuilder transaction(FragmentManager fragmentManager) {
        return new SupportFragmentTransactionBuilder(fragmentManager,
                fragmentManager.beginTransaction());
    }

    public SupportFragmentTransactionBuilder add(@IdRes int id, Fragment fragment, String tag)
            throws IllegalArgumentException, IllegalStateException {
        checkId(id);
        checkTag(tag);
        checkIdNotExist(mFragmentManager, id);
        checkTagNotExist(mFragmentManager, tag);
        mTransaction.add(id, fragment, tag);
        return this;
    }

    public SupportFragmentTransactionBuilder remove(@IdRes int id)
            throws IllegalArgumentException, IllegalStateException {
        checkId(id);
        checkIdExist(mFragmentManager, id);
        mTransaction.remove(mFragmentManager.findFragmentById(id));
        return this;
    }

    public SupportFragmentTransactionBuilder remove(String tag)
            throws IllegalArgumentException, IllegalStateException {
        checkTag(tag);
        checkTagExist(mFragmentManager, tag);
        mTransaction.remove(mFragmentManager.findFragmentByTag(tag));
        return this;
    }

    public SupportFragmentTransactionBuilder replace(@IdRes int id, Fragment fragment, String tag)
            throws IllegalArgumentException, IllegalStateException {
        checkId(id);
        checkTag(tag);
        checkIdExist(mFragmentManager, id);
        checkTagNotExist(mFragmentManager, tag);
        mTransaction.replace(id, fragment, tag);
        return this;
    }

    public FragmentTransaction build() {
        return mTransaction;
    }

    private void checkTagNotExist(FragmentManager fragmentManager, String tag)
            throws IllegalStateException {
        if (fragmentManager.findFragmentByTag(tag) != null) {
            throw new IllegalStateException("fragment with tag "
                    + tag
                    + " already exist: "
                    + fragmentManager.findFragmentByTag(tag));
        }
    }

    private void checkIdNotExist(FragmentManager fragmentManager, @IdRes int id)
            throws IllegalStateException {
        if (fragmentManager.findFragmentById(id) != null) {
            throw new IllegalStateException("fragment with id "
                    + id
                    + " already exist: "
                    + fragmentManager.findFragmentById(id));
        }
    }

    private void checkTagExist(FragmentManager fragmentManager, String tag)
            throws IllegalStateException {
        if (fragmentManager.findFragmentByTag(tag) == null) {
            throw new IllegalStateException(
                    "fragment with tag " + tag + " not exist: " + fragmentManager.findFragmentByTag(
                            tag));
        }
    }

    private void checkIdExist(FragmentManager fragmentManager, @IdRes int id)
            throws IllegalStateException {
        if (fragmentManager.findFragmentById(id) == null) {
            throw new IllegalStateException(
                    "fragment with id " + id + " not exist: " + fragmentManager.findFragmentById(
                            id));
        }
    }

    private void checkTag(String tag) throws IllegalArgumentException {
        if (TextUtils.isEmpty(tag)) {
            throw new IllegalArgumentException("tag is empty");
        }
    }

    private void checkId(@IdRes int id) throws IllegalArgumentException {
        if (id <= 0) {
            throw new IllegalArgumentException("id: " + id + " <= 0");
        }
    }
}
