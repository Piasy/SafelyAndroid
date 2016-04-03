package com.github.piasy.safelyandroid.component;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import com.github.piasy.safelyandroid.fragment.FragmentTransactionDelegate;
import com.github.piasy.safelyandroid.fragment.TransactionCommitter;

/**
 * Created by Piasy{github.com/Piasy} on 4/3/16.
 */
public class SafelyFragment extends Fragment implements TransactionCommitter {

    private final FragmentTransactionDelegate mFragmentTransactionDelegate =
            new FragmentTransactionDelegate();

    protected boolean safeCommit(@NonNull FragmentTransaction transaction) {
        return mFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentTransactionDelegate.onResumed();
    }

    @Override
    public boolean isCommitterResumed() {
        return isResumed();
    }
}
