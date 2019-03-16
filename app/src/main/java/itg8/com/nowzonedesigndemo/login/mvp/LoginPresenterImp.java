package itg8.com.nowzonedesigndemo.login.mvp;

import android.text.TextUtils;
import android.view.View;

import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.BasePresenter;
import itg8.com.nowzonedesigndemo.common.BaseWeakPresenter;

/**
 * Created by itg_Android on 9/6/2017.
 */

public class LoginPresenterImp extends BaseWeakPresenter implements LoginMVP.LoginListener,LoginMVP.LoginPresenter {

    LoginMVP.LoginModule module;
    public LoginPresenterImp(LoginMVP.LoginView view) {
        super(view);
        module=new LoginModuleImp();
    }

    @Override
    public void onDestroy() {
        module.onDestroy();
    }

    @Override
    public void onLoginClicked(View view) {
        if(hasView()){
            boolean isValid=true;
            String password=getLoginView().getPassword();
            String username=getLoginView().getUsername();
            if(TextUtils.isEmpty(username)){
                isValid=false;
                getLoginView().onUsernameInvalid(view.getContext().getString(R.string.empty));
            }
            if(TextUtils.isEmpty(password)){
                isValid=false;
                getLoginView().onPasswordInvalid(view.getContext().getString(R.string.empty));
            }
            if(!isValid)
                return;
            if(!android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                isValid=false;
                getLoginView().onUsernameInvalid(view.getContext().getString(R.string.invalid_email));
            }
            if(password.length()<6){
                isValid=false;
                getLoginView().onPasswordInvalid(view.getContext().getString(R.string.invalid_pass));
            }
            if(isValid){
                getLoginView().showProgress();
                module.onStartCall(view.getContext().getString(R.string.url_login),username,password,this);
            }
        }
    }


    @Override
    public void onSuccess() {
        if(hasView()){
            getLoginView().hideProgress();
            getLoginView().onSuccess();
        }
    }

    @Override
    public void onFail(String message) {
        if(hasView()) {
            getLoginView().hideProgress();
            getLoginView().onFail(message);
        }
    }

    @Override
    public void onError(Object t) {
        if(hasView()) {
            getLoginView().hideProgress();
            getLoginView().onError(t);
        }
    }

    private LoginMVP.LoginView getLoginView(){
        return (LoginMVP.LoginView) getView();
    }
}
