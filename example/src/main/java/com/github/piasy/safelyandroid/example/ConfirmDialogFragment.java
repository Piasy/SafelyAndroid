package com.github.piasy.safelyandroid.example;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.piasy.safelyandroid.component.support.SafelySupportDialogFragment;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Piasy{github.com/Piasy} on 4/3/16.
 */
public class ConfirmDialogFragment extends SafelySupportDialogFragment {

    @BindView(R.id.mTv)
    TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_confirm, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        Observable.interval(1, TimeUnit.SECONDS)
                .take(4)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        safeDismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // unsafe dismiss, lint will give error in `Inspection` window
                        dismiss();
                    }

                    @Override
                    public void onNext(Long time) {
                        mTv.setText(String.format(getString(R.string.confirm_hint_formatter),
                                3 - time));
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        // without title and title divider

        // Less dimmed background; see http://stackoverflow.com/q/13822842/56285
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.4F; // dim only a little bit
        window.setAttributes(params);

        window.setLayout(dip2px(getContext(), 200), dip2px(getContext(), 100));
        window.setGravity(Gravity.CENTER);

        // Transparent background; see http://stackoverflow.com/q/15007272/56285
        // (Needed to make dialog's alpha shadow look good)
        window.setBackgroundDrawableResource(android.R.color.transparent);

        final Resources res = getResources();
        final int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
        if (titleDividerId > 0) {
            final View titleDivider = getDialog().findViewById(titleDividerId);
            if (titleDivider != null) {
                titleDivider.setBackgroundColor(res.getColor(android.R.color.transparent));
            }
        }
    }

    private static int dip2px(Context context, int dip) {
        return (int) (context.getResources().getDisplayMetrics().density * dip);
    }
}
