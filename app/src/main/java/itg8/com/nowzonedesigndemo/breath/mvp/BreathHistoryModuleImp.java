package itg8.com.nowzonedesigndemo.breath.mvp;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import itg8.com.nowzonedesigndemo.common.DBModule;
import itg8.com.nowzonedesigndemo.db.tbl.TblState;
import timber.log.Timber;

/**
 * Created by itg_Android on 6/10/2017.
 */

public class BreathHistoryModuleImp extends DBModule implements BreathHistoryMVP.BreathHostoryModule {

    public boolean dbInit;
    private boolean stateDaoInit=false;

    public BreathHistoryModuleImp(Context context) {
        super(context);
        onRequestStateDao();
    }

    @Override
    public void onBreathHistoryListener(BreathHistoryMVP.BreathHistoryListener listener) {
        this.listener=listener;
        if(stateDaoInit)
            createObserver();
    }

    private List<TblState> listAllDataFrom() {
        if(stateDaoInit) {
            try {
                QueryBuilder<TblState,Integer> queryBuilder=getStateDao().queryBuilder();
                queryBuilder.orderBy(TblState.FIELD_ID,false);
                return getStateDao().query(queryBuilder.prepare());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void initStateDao() {
        stateDaoInit=true;
    }

    @Override
    public void initStateDao(boolean b, String issue) {
        listener.onConnectionFailed(issue);
    }

    private void createObserver() {
        Observable.create((ObservableOnSubscribe<List<TblState>>) e -> {
            e.onNext(listAllDataFrom());
        })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(o -> listener.onListAvailable(o), Throwable::printStackTrace,() -> Timber.d("CREATED"));
    }
}
