package com.github.piasy.safelyandroid.component.support;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import com.github.piasy.safelyandroid.activity.StartActivityDelegate;
import com.github.piasy.safelyandroid.dialogfragment.SupportDialogFragmentDismissDelegate;
import com.github.piasy.safelyandroid.fragment.SupportFragmentTransactionDelegate;
import com.github.piasy.safelyandroid.fragment.TransactionCommitter;

/**
 * Created by Piasy{github.com/Piasy} on 4/3/16.
 */
public class SafelySupportDialogFragment extends DialogFragment implements TransactionCommitter {

    private final SupportDialogFragmentDismissDelegate mSupportDialogFragmentDismissDelegate =
            new SupportDialogFragmentDismissDelegate();

    private final SupportFragmentTransactionDelegate mSupportFragmentTransactionDelegate =
            new SupportFragmentTransactionDelegate();

    protected boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    protected boolean safeCommit(@NonNull FragmentTransaction transaction) {
        return mSupportFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    public boolean safeDismiss() {
        return mSupportDialogFragmentDismissDelegate.safeDismiss(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSupportDialogFragmentDismissDelegate.onResumed(this);
        mSupportFragmentTransactionDelegate.onResumed();
    }

    @Override
    public boolean isCommitterResumed() {
        return isResumed();
    }
}
