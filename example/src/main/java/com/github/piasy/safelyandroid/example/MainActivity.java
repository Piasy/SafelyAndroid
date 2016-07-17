package com.github.piasy.safelyandroid.example;

import android.os.Bundle;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.piasy.safelyandroid.component.support.SafelyAppCompatActivity;
import com.github.piasy.safelyandroid.fragment.SupportFragmentTransactionBuilder;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends SafelyAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ConfirmDialogFragment().show(getSupportFragmentManager(), "ConfirmDialogFragment");
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        safeCommit(SupportFragmentTransactionBuilder.transaction(
                                getSupportFragmentManager())
                                .add(R.id.mHeader, new HeaderFragment(), "HeaderFragment")
                                .add(R.id.mBody, new BodyFragment(), "BodyFragment")
                                .add(R.id.mFooter, new FooterFragment(), "FooterFragment")
                                .build());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });

        try {
            safeCommit(SupportFragmentTransactionBuilder.transaction(getSupportFragmentManager())
                    .remove("HeaderFragment")
                    .build());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        MaterialDialog dialog = new MaterialDialog.Builder(this).content("Test for dismiss").show();
        dialog.dismiss();
    }
}
