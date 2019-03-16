package itg8.com.nowzonedesigndemo.common;


/**
 * Created by itg_Android on 6/10/2017.
 */

public class BasePresenter extends BaseModuleOrm{

    private Object object;

    public BasePresenter(Object object) {
        this.object = object;
    }

    public BasePresenter() {
    }

    public boolean isNotNull(){
        return object!=null;
    }

    public void setNull(){
        object=null;
    }

    public void setObject(Object object) {
        this.object = object;
    }


}
