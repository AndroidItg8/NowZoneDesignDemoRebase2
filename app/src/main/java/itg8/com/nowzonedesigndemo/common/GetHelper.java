package itg8.com.nowzonedesigndemo.common;

import android.content.Context;

import itg8.com.nowzonedesigndemo.db.DbHelper;


public class GetHelper {
    private static final GetHelper ourInstance = new GetHelper();

    private DbHelper mHelper;

    private GetHelper() {
    }

    private GetHelper(Context context) {
        if (mHelper == null)
            mHelper = new DbHelper(context);
    }

    public static GetHelper getInstance() {
        return ourInstance;
    }


}
