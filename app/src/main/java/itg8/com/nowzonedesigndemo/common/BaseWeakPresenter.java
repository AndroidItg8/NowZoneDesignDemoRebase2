package itg8.com.nowzonedesigndemo.common;

import java.lang.ref.WeakReference;


public class BaseWeakPresenter<T> {
    private WeakReference<T> weakReference;

    public BaseWeakPresenter(T t) {
        this.weakReference=new WeakReference<T>(t);
    }

    public boolean hasView(){
        return weakReference.get() != null;
    }

    public T getView(){
        return weakReference.get();
    }


    protected void detactView(){
        weakReference=null;
    }

}
