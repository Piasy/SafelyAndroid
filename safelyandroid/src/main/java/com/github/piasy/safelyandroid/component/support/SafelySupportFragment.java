package com.github.piasy.safelyandroid.component.support;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.github.piasy.safelyandroid.fragment.SupportFragmentTransactionDelegate;
import com.github.piasy.safelyandroid.fragment.TransactionCommitter;

/**
 * Created by Piasy{github.com/Piasy} on 4/3/16.
 */
public class SafelySupportFragment extends Fragment implements TransactionCommitter {

    private final SupportFragmentTransactionDelegate mSupportFragmentTransactionDelegate =
            new SupportFragmentTransactionDelegate();

    protected boolean safeCommit(@NonNull FragmentTransaction transaction) {
        return mSupportFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSupportFragmentTransactionDelegate.onResumed();
    }

    @Override
    public boolean isCommitterResumed() {
        return isResumed();
    }
}
