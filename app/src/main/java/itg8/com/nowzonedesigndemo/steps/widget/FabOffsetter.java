package itg8.com.nowzonedesigndemo.steps.widget;

import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.Log;

import itg8.com.nowzonedesigndemo.R;

/**
 * Created by Android itg 8 on 5/25/2017.
 */

public class FabOffsetter implements AppBarLayout.OnOffsetChangedListener {
    private static final String TAG = FabOffsetter.class.getSimpleName();
    private final CoordinatorLayout parent;
    private final TabLayout fab;

    public FabOffsetter(@NonNull CoordinatorLayout parent, @NonNull TabLayout child) {
        this.parent = parent;
        this.fab = child;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        // fab should scroll out down in sync with the appBarLayout scrolling out up.
        // let's see how far along the way the appBarLayout is
        // (if displacementFraction == 0.0f then no displacement, appBar is fully expanded;
        //  if displacementFraction == 1.0f then full displacement, appBar is totally collapsed)
        float displacementFraction = -verticalOffset / (float) appBarLayout.getHeight();
        Log.d(TAG,"displacementFraction:"+displacementFraction);

        // need to separate translationY on the fab that comes from this behavior
        // and one that comes from other sources
        // translationY from this behavior is stored in a tag on the fab
       float translationYFromThis = coalesce((float) fab.getTag(R.id.fab_translationY_from_AppBarBoundFabBehavior), 0f);
        Log.d(TAG,"translationYFromThis:"+translationYFromThis);

        // float translationYFromThis = horizontalOffset((Float) fab.getTag(R.id.fab_translationY_from_AppBarBoundFabBehavior), 0f);

        // top position, accounting for translation not coming from this behavior
        float topUntranslatedFromThis = fab.getTop() + fab.getTranslationY() - translationYFromThis;
        Log.d(TAG,"topUntranslatedFromThis:"+topUntranslatedFromThis);

        // total length to displace by (from position uninfluenced by this behavior) for a full appBar collapse
        float fullDisplacement = parent.getBottom() - topUntranslatedFromThis;
        Log.d(TAG,"fullDisplacement:"+fullDisplacement);




        // calculate and store new value for displacement coming from this behavior
        float newTranslationYFromThis = fullDisplacement * displacementFraction;
        Log.d(TAG,"newTranslationYFromThis:"+newTranslationYFromThis);


        fab.setTag(R.id.fab_translationY_from_AppBarBoundFabBehavior, newTranslationYFromThis);


        // update translation value by difference found in this step
        fab.setTranslationY(newTranslationYFromThis - translationYFromThis + fab.getTranslationY());
    }

    private float coalesce(float... tag) {

        for(float i : tag)
            if(i != 0f)
                return i;

        return 0f;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

     AppBarLayout.OnOffsetChangedListener that = (AppBarLayout.OnOffsetChangedListener) o;

        //return parent.equals(that.parent) && fab.equals(that.fab);
        return parent.equals(this.parent) && fab.equals(this.fab);

    }

    @Override
    public int hashCode() {
        int result = parent.hashCode();
        result = 31 * result + fab.hashCode();
        Log.d(TAG,"result:"+result);

        return result;
    }
}