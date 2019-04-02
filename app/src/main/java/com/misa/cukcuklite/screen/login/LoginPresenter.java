package com.misa.cukcuklite.screen.login;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.misa.cukcuklite.data.db.DataCallback;

import androidx.annotation.NonNull;

public class LoginPresenter implements ILoginContract.IPresenter {
    private static final String TAG = LoginPresenter.class.getName();
    private ILoginContract.IView mView;

    private DataCallback<FirebaseUser> mCallback;

    public LoginPresenter(ILoginContract.IView view) {
        mView = view;
        initSignInCallback();
    }

    @Override
    public void loginWithFacebook(AccessToken accessToken) {
        try {
            AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    try {
                        if (task.isSuccessful()) {
                            mCallback.onGetDataSuccess(task.getResult().getUser());
                            mView.navigateHomeScreen();
                        } else {
                            mCallback.onGetDataFailed(task.getException().getMessage());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mCallback.onGetDataFailed(e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSignInCallback() {
        try {
            mCallback = new DataCallback<FirebaseUser>() {
                @Override
                public void onGetDataSuccess(FirebaseUser data) {
                    if (data == null) {
                        return;
                    }
                }

                @Override
                public void onGetDataFailed(String msg) {
                }
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
