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

package com.github.piasy.safelyandroid.dialogfragment;

import android.annotation.SuppressLint;

/**
 * Created by Piasy{github.com/Piasy} on 4/1/16.
 *
 * Delegate to dismiss {@link android.app.DialogFragment} safely, if it's resumed, then dismiss
 * it right now, otherwise, it will be dismissed when
 * {@link #onResumed(android.app.DialogFragment)} is called
 */
public class DialogFragmentDismissDelegate {

    private volatile boolean mPendingDismiss = false;

    /**
     * safe dismiss the {@link android.app.DialogFragment}, when the
     * {@link android.app.DialogFragment} is resumed, dismiss it right now, other wise, dismiss it
     * when {@link #onResumed(android.app.DialogFragment)} is called.
     *
     * @param dialogFragment the {@link android.app.DialogFragment} want to be dismissed safely
     * @return {@code true} if the {@link android.app.DialogFragment} will be dismissed when
     * {@link #onResumed(android.app.DialogFragment)} is called
     */
    @SuppressLint("UnsafeDismiss")
    public boolean safeDismiss(android.app.DialogFragment dialogFragment) {
        if (dialogFragment.isResumed()) {
            dialogFragment.dismiss();
            return false;
        } else {
            mPendingDismiss = true;
            return true;
        }
    }

    /**
     * called at the {@link android.app.DialogFragment#onResume()}, it will be dismissed if it has
     * a pending dismiss job.
     *
     * @param dialogFragment the {@link android.app.DialogFragment} resumed
     * @return {@code true} if it is dismissed.
     */
    @SuppressLint("UnsafeDismiss")
    public boolean onResumed(android.app.DialogFragment dialogFragment) {
        if (mPendingDismiss) {
            dialogFragment.dismiss();
            mPendingDismiss = false;
            return true;
        }
        return false;
    }
}
