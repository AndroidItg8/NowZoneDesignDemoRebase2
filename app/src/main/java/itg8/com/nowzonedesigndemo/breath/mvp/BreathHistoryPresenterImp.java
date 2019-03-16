package itg8.com.nowzonedesigndemo.breath.mvp;

import android.content.Context;

import java.util.List;

import itg8.com.nowzonedesigndemo.common.BasePresenter;
import itg8.com.nowzonedesigndemo.db.tbl.TblState;

/**
 * Created by itg_Android on 6/10/2017.
 */

public class BreathHistoryPresenterImp extends BasePresenter implements BreathHistoryMVP.BreathHistoryPresenter, BreathHistoryMVP.BreathHistoryListener {


    private BreathHistoryMVP.BreathHistoryView view;
    private BreathHistoryMVP.BreathHostoryModule module;
    public BreathHistoryPresenterImp(BreathHistoryMVP.BreathHistoryView view) {
        super(view);
        this.view = view;
        module=new BreathHistoryModuleImp((Context) view);
    }

    @Override
    public void initListOfState() {
            module.onBreathHistoryListener(this);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        if(isNotNull())
            view=null;
    }

    @Override
    public void onListAvailable(List<TblState> list) {
        if(isNotNull())
            view.onListAvailable(list);
    }

    @Override
    public void onErrorLoadingDb(String error) {
        if(isNotNull())
            view.onErrorLoading(error);
    }

    @Override
    public void onConnectionFailed(String issue) {
        if(isNotNull())
            view.onErrorLoading(issue);
    }
}
