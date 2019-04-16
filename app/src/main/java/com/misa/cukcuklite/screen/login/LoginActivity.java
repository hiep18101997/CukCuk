package com.misa.cukcuklite.screen.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.misa.cukcuklite.R;
import com.misa.cukcuklite.screen.home.HomeActivity;
import com.misa.cukcuklite.screen.loginphoneemail.LoginPhoneEmailActivity;
import com.misa.cukcuklite.screen.register.RegisterActivity;
import com.misa.cukcuklite.utils.DateUtil;
import java.util.Arrays;

/**
 * - Mục đích Class :Màn hình Login - @created_by Hoàng Hiệp on 4/15/2019
 */
public class LoginActivity extends AppCompatActivity implements ILoginContract.IView,
    View.OnClickListener {

  private static final String TAG = LoginActivity.class.getName();
  private static final int RC_SIGN_IN = 1997;
  private ILoginContract.IPresenter mPresenter;
  private LinearLayout lnFacebook, lnGoogle, lnPhoneEmail;
  private CallbackManager mCallbackManager;
  private GoogleSignInClient mGoogleSignInClient;
  private ProgressDialog mDialog;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    initComponent();
    initFacebookSDK();
    initGoogleSDK();
    Log.d(TAG, "Start: " + DateUtil.getLastMonth()[0].toString());
    Log.d(TAG, "End: " + DateUtil.getLastMonth()[1].toString());
  }
  /**
   * Mục đích method: Khởi tạo Google SDK
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private void initGoogleSDK() {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();
    mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
  }

  /**
   * Mục đích method: Ánh xạ khởi tạo đối tượng
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  private void initComponent() {
    mDialog = new ProgressDialog(this);
    mPresenter = new LoginPresenter(this);
    lnFacebook = findViewById(R.id.lnLoginFacebook);
    lnGoogle = findViewById(R.id.lnLoginGoogle);
    lnPhoneEmail = findViewById(R.id.lnLoginPhoneEmail);
    findViewById(R.id.tvRegister).setOnClickListener(this);
    lnGoogle.setOnClickListener(this);
    lnFacebook.setOnClickListener(this);
    lnPhoneEmail.setOnClickListener(this);
  }

  /**
   * Mục đích method: Khởi tao Facebook SDK
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
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

  /**
   * Mục đích method: Đăng nhập bằng Facebook
   *
   * @created_by Hoàng Hiệp on 4/15/2019
   */
  private void loginWithFacebook(AccessToken accessToken) {
    mPresenter.loginWithFacebook(accessToken);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {
      Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
      mPresenter.handleSignInResult(this, task);
    } else {
      mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
  }

  /**
   * Mục đích method: Xử lý sự kiện
   *
   * @created_by Hoàng Hiệp on 3/27/2019
   */
  @Override
  public void onClick(View v) {
    try {
      switch (v.getId()) {
        case R.id.lnLoginFacebook:
          LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
              Arrays.asList("public_profile", "user_friends"));
          break;
        case R.id.lnLoginGoogle:
          Intent signInIntent = mGoogleSignInClient.getSignInIntent();
          startActivityForResult(signInIntent, RC_SIGN_IN);
          break;
        case R.id.lnLoginPhoneEmail:
          startActivity(LoginPhoneEmailActivity.getIntent(this));
          break;
        case R.id.tvRegister:
          startActivity(RegisterActivity.getIntent(this));
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Chuyển màn hình
   *
   * @created_by Hoàng Hiệp on 4/12/2019
   */
  @Override
  public void navigateHomeScreen() {
    startActivity(HomeActivity.getIntent(this));
  }

  /**
   * Mục đích method: Hiển thị loading
   *
   * @created_by Hoàng Hiệp on 4/12/2019
   */
  @Override
  public void showLoading() {
    try {
      mDialog.setMessage(getString(R.string.loading));
      mDialog.setCancelable(false);
      mDialog.setCanceledOnTouchOutside(false);
      mDialog.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Mục đích method: Ẩn loading
   *
   * @created_by Hoàng Hiệp on 4/12/2019
   */
  @Override
  public void hideLoading() {
    try {
      mDialog.dismiss();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onLoginFail() {

  }
}
