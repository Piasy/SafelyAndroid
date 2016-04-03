package com.github.piasy.safelyandroid.fragment;


/**
 * Created by Piasy{github.com/Piasy} on 4/3/16.
 *
 * The place we commit a {@link android.app.FragmentTransaction} or
 * {@link android.support.v4.app.FragmentTransaction} from, we should commit only when it's resumed,
 * avoiding the Activity state loss error.
 */
public interface TransactionCommitter {
    /**
     * whether the host is resumed
     *
     * @return {@code true} if it's resumed.
     */
    boolean isCommitterResumed();
}
