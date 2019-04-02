package com.misa.cukcuklite.screen.loginphoneemail;

import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;

public class LoginPhoneEmailPresenter implements ILoginPhoneEmailContract.IPresenter {
    private static final String TAG = LoginPhoneEmailPresenter.class.getName();

    private ILoginPhoneEmailContract.IView mView;

    public LoginPhoneEmailPresenter(ILoginPhoneEmailContract.IView view) {
        mView = view;
    }

    @Override
    public void login(String username, String password) {
        if (isValidInput(username, password)) {
            mView.showLoading();
            FirebaseAuth.getInstance().signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mView.hideLoading();
                            if (!task.isSuccessful()) {
                                mView.onLoginFail();
                                return;
                            }
                            mView.navigateHomeScreen();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }

    private boolean isValidInput(String username, String password) {
        try {
            if (TextUtils.isEmpty(username)) {
                mView.hideLoading();
                mView.onEmptyUsername();
                return false;
            }
            if (TextUtils.isEmpty(password)) {

                mView.hideLoading();
                mView.onEmptyPassword();
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
