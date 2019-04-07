package com.misa.cukcuklite.screen.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.home.HomeActivity;
import com.misa.cukcuklite.screen.loginphoneemail.LoginPhoneEmailActivity;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity implements ILoginContract.IView, View.OnClickListener {
    private static final String TAG = LoginActivity.class.getName();
    private ILoginContract.IPresenter mPresenter;
    private LinearLayout lnFacebook, lnGoogle, lnPhoneEmail;
    private CallbackManager mCallbackManager;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponent();
        initFacebookSDK();
    }

    private void initComponent() {
        mDialog = new ProgressDialog(this);
        mPresenter = new LoginPresenter(this);
        lnFacebook = findViewById(R.id.lnLoginFacebook);
        lnGoogle = findViewById(R.id.lnLoginGoogle);
        lnPhoneEmail = findViewById(R.id.lnLoginPhoneEmail);
        lnGoogle.setOnClickListener(this);
        lnFacebook.setOnClickListener(this);
        lnPhoneEmail.setOnClickListener(this);
    }

    private void initFacebookSDK() {
        try {
            mCallbackManager = CallbackManager.Factory.create();
            LoginManager.getInstance().registerCallback(mCallbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            if (loginResult == null) {
                                return;
                            }
                            AccessToken accessToken = loginResult.getAccessToken();
                            loginWithFacebook(accessToken);
                        }

                        @Override
                        public void onCancel() {
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            Toast.makeText(LoginActivity.this, exception.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loginWithFacebook(AccessToken accessToken) {
        mPresenter.loginWithFacebook(accessToken);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnLoginFacebook:
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "user_friends"));
                break;
            case R.id.lnLoginGoogle:
                break;
            case R.id.lnLoginPhoneEmail:
                startActivity(LoginPhoneEmailActivity.getIntent(this));
                break;
        }
    }

    @Override
    public void navigateHomeScreen() {
        startActivity(HomeActivity.getIntent(this));
    }

    @Override
    public void showLoading() {
        mDialog.setMessage(getString(R.string.loading));
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
    }

    @Override
    public void hideLoading() {
        mDialog.dismiss();
    }
}
