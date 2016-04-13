package com.github.piasy.safelyandroid.component;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.github.piasy.safelyandroid.activity.StartActivityDelegate;
import com.github.piasy.safelyandroid.dialogfragment.DialogFragmentDismissDelegate;
import com.github.piasy.safelyandroid.fragment.FragmentTransactionDelegate;
import com.github.piasy.safelyandroid.fragment.TransactionCommitter;

/**
 * Created by Piasy{github.com/Piasy} on 4/3/16.
 */
public class SafelyDialogFragment extends DialogFragment implements TransactionCommitter {

    private final DialogFragmentDismissDelegate mDialogFragmentDismissDelegate =
            new DialogFragmentDismissDelegate();

    private final FragmentTransactionDelegate mFragmentTransactionDelegate =
            new FragmentTransactionDelegate();

    protected boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    protected boolean safeCommit(@NonNull FragmentTransaction transaction) {
        return mFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    public boolean safeDismiss() {
        return mDialogFragmentDismissDelegate.safeDismiss(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDialogFragmentDismissDelegate.onResumed(this);
        mFragmentTransactionDelegate.onResumed();
    }

    @Override
    public boolean isCommitterResumed() {
        return isResumed();
    }
}
