package itg8.com.nowzonedesigndemo.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.common.Prefs;
import itg8.com.nowzonedesigndemo.home.HomeActivity;
import itg8.com.nowzonedesigndemo.login.mvp.LoginMVP;
import itg8.com.nowzonedesigndemo.login.mvp.LoginPresenterImp;
import itg8.com.nowzonedesigndemo.sanning.ScanDeviceActivity;

public class LoginActivity extends AppCompatActivity implements LoginMVP.LoginView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.card2)
    CardView mCard2;
    @BindView(R.id.edt_password)
    EditText mEdtPassword;
    @BindView(R.id.edt_username)
    EditText mEdtUsername;
    @BindView(R.id.card1)
    CardView mCard1;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    LoginMVP.LoginPresenter presenter;
    @BindView(R.id.txtProgressbar)
    ProgressBar mTxtProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String pref=Prefs.getString(CommonMethod.ISLOGIN,null);
        String token=Prefs.getString(CommonMethod.TOKEN,null);
        if(pref!=null
                && token!=null)
        {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
        presenter = new LoginPresenterImp(this);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public String getUsername() {
        return mEdtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEdtPassword.getText().toString();
    }

    @Override
    public void onSuccess() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Prefs.putString(CommonMethod.ISLOGIN,"yet");
                Intent i = new Intent(LoginActivity.this, ScanDeviceActivity.class);
                startActivity(i);
                LoginActivity.this.finish();
            }
        });
    }

    @Override
    public void onFail(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Object t) {
        ((Throwable) t).printStackTrace();
        Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUsernameInvalid(String err) {
        mEdtUsername.requestFocus();
        mEdtUsername.setError(err);
    }

    @Override
    public void onPasswordInvalid(String err) {
        mEdtPassword.requestFocus();
        mEdtPassword.setError(err);
    }

    @Override
    public void onClick(View view) {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        presenter.onLoginClicked(view);
    }

    @Override
    public void showProgress() {
        mBtnLogin.setVisibility(View.GONE);
        mTxtProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mBtnLogin.setVisibility(View.VISIBLE);
        mTxtProgressbar.setVisibility(View.GONE);
    }
}
