package com.github.piasy.safelyandroid.fragment;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 4/3/16.
 *
 * Delegate to commit {@link android.support.v4.app.FragmentTransaction} from
 * {@link TransactionCommitter} safely, if it's resumed, then commit it right now,
 * otherwise, it will be committed when {@link #onResumed()} is called
 */
public class SupportFragmentTransactionDelegate {

    private final List<android.support.v4.app.FragmentTransaction> mPendingTransactions =
            new ArrayList<>();

    /**
     * commit a {@link android.support.v4.app.FragmentTransaction} from
     * {@link TransactionCommitter} safely
     *
     * @param committer committer we commit from
     * @param transaction transaction to commit
     * @return {@code true} if it's unsafe to commit now, it will be committed when
     * {@link #onResumed()} is called
     */
    public synchronized boolean safeCommit(@NonNull TransactionCommitter committer,
            @NonNull android.support.v4.app.FragmentTransaction transaction) {
        if (committer.isCommitterResumed()) {
            transaction.commit();
            return false;
        } else {
            mPendingTransactions.add(transaction);
            return true;
        }
    }

    /**
     * called at the {@code onResume()} life cycle method, e.g.
     * {@link android.app.Fragment#onResume()},
     * {@link android.support.v4.app.Fragment#onResume()},
     * {@link android.app.Activity#onResume()},
     * {@link android.support.v7.app.AppCompatActivity#onResumeFragments()},
     * it will commit pending transactions.
     *
     * @return {@code true} if pending transactions are committed.
     */
    public synchronized boolean onResumed() {
        if (!mPendingTransactions.isEmpty()) {
            for (int i = 0, size = mPendingTransactions.size(); i < size; i++) {
                mPendingTransactions.get(i).commit();
            }
            mPendingTransactions.clear();
            return true;
        }
        return false;
    }
}
