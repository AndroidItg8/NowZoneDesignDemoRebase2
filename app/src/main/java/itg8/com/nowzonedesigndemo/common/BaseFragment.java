package itg8.com.nowzonedesigndemo.common;

import android.support.v4.app.Fragment;

/**
 * Created by itg_Android on 2/27/2017.
 */

public abstract class BaseFragment<T> extends Fragment {


    @Override
    public void onResume() {
        super.onResume();
    }

    public String getClassName(Class<T> tClass){
        return tClass.getSimpleName();
    }
}
