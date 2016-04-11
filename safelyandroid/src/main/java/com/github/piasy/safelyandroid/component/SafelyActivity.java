package com.github.piasy.safelyandroid.component;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.github.piasy.safelyandroid.fragment.FragmentTransactionDelegate;
import com.github.piasy.safelyandroid.fragment.TransactionCommitter;

/**
 * Created by Piasy{github.com/Piasy} on 4/3/16.
 */
public class SafelyActivity extends Activity implements TransactionCommitter {

    private volatile boolean mIsResumed = false;
    private final FragmentTransactionDelegate mFragmentTransactionDelegate =
            new FragmentTransactionDelegate();

    protected boolean safeCommit(@NonNull FragmentTransaction transaction) {
        return mFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsResumed = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsResumed = true;
        mFragmentTransactionDelegate.onResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsResumed = false;
    }

    @Override
    public boolean isCommitterResumed() {
        return mIsResumed;
    }
}
