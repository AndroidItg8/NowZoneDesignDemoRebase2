package itg8.com.nowzonedesigndemo.breath.mvp;

import java.util.List;

import itg8.com.nowzonedesigndemo.db.tbl.TblState;

/**
 * Created by itg_Android on 6/10/2017.
 */

public class BreathHistoryMVP {
    public interface BreathHistoryView{
        void onListAvailable(List<TblState> list);
        void onErrorLoading(String error);
        void onConnectionFailed(String issue);
    }

    public interface BreathHistoryPresenter{
        void initListOfState();
        void onStart();
        void onDestroy();
    }

    public interface BreathHistoryListener{
        void onListAvailable(List<TblState> list);
        void onErrorLoadingDb(String error);
        void onConnectionFailed(String issue);
    }

    public interface BreathHostoryModule{
        void onBreathHistoryListener(BreathHistoryListener listener);
        void onDestroy();
    }
}
