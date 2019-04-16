package com.misa.cukcuklite.screen.login;

import android.app.Activity;
import androidx.annotation.NonNull;
import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.misa.cukcuklite.data.db.DataCallback;

public class LoginPresenter implements ILoginContract.IPresenter {

  private static final String TAG = LoginPresenter.class.getName();
  private ILoginContract.IView mView;

  private DataCallback<FirebaseUser> mCallback;

  public LoginPresenter(ILoginContract.IView view) {
    mView = view;
    initSignInCallback();
  }

  /**
   * Mục đích method: Đăng nhập facebook
   *
   * @created_by Hoàng Hiệp on 4/12/2019
   */
  @Override
  public void loginWithFacebook(AccessToken accessToken) {
    try {
      mView.showLoading();
      AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
      FirebaseAuth.getInstance().signInWithCredential(credential)
          .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              try {
                mView.hideLoading();
                if (task.isSuccessful()) {
                  mCallback.onGetDataSuccess(task.getResult().getUser());
                  mView.navigateHomeScreen();
                } else {
                  mCallback.onGetDataFailed(task.getException().getMessage());
                }
              } catch (Exception e) {
                mView.hideLoading();
                e.printStackTrace();
              }
            }
          }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
          mCallback.onGetDataFailed(e.getMessage());
          mView.hideLoading();
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
      mView.hideLoading();
    }
  }
  /**
   * Mục đích method: Đăng nhập Google
   *
   * @created_by Hoàng Hiệp on 4/12/2019
   */
  @Override
  public void handleSignInResult(Activity activity, Task<GoogleSignInAccount> task) {
    try {
      GoogleSignInAccount account = task.getResult(ApiException.class);
      firebaseAuthWithGoogle(activity, account);
    } catch (ApiException e) {
    }
  }

  private void firebaseAuthWithGoogle(Activity activity, GoogleSignInAccount account) {
    try {
      mView.showLoading();
      AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
      FirebaseAuth.getInstance().signInWithCredential(credential)
          .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              try {
                mView.hideLoading();
                if (task.isSuccessful()) {
                  mView.navigateHomeScreen();
                } else {

                }
              } catch (Exception e) {
                mView.hideLoading();
                e.printStackTrace();
              }
            }
          });
    } catch (Exception e) {
      e.printStackTrace();
      mView.hideLoading();
    }
  }

  /**
   * Mục đích method: Lắng nghe trạng thái đăng nhập
   *
   * @created_by Hoàng Hiệp on 4/12/2019
   */
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
