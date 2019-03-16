package itg8.com.nowzonedesigndemo.breath;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import itg8.com.nowzonedesigndemo.R;
import itg8.com.nowzonedesigndemo.common.CommonMethod;
import itg8.com.nowzonedesigndemo.db.tbl.TblState;

public class BreathHistoryListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath_history_list);
        ButterKnife.bind(this);
        init();



    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRecyclerView();

    }

    private void setRecyclerView() {
         if(getIntent().hasExtra(CommonMethod.BREATH))
         {
             List<TblState> list = getIntent().getParcelableExtra(CommonMethod.BREATH);
             if(list!= null && list.size()>0) {
                 BreathHistoryAdapter adapter = new BreathHistoryAdapter(list);
                 LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                 recyclerView.setLayoutManager(layoutManager);
                 DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                         layoutManager.getOrientation());
                 recyclerView.addItemDecoration(dividerItemDecoration);
                 recyclerView.setAdapter(adapter);
             }
         }

    }

}
