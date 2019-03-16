package itg8.com.nowzonedesigndemo.steps.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Android itg 8 on 5/25/2017.
 */

public class AppBarBoundTabBehavior extends CoordinatorLayout.Behavior<TabLayout> {



    public AppBarBoundTabBehavior(Context context, AttributeSet attrs) {
        super();
    }
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TabLayout child, View dependency) {
        if (dependency instanceof AppBarLayout) {
            ((AppBarLayout) dependency).addOnOffsetChangedListener(new FabOffsetter(parent, child));
        }
        return dependency instanceof AppBarLayout || super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TabLayout fab, View dependency) {
        //noinspection SimplifiableIfStatement
        if (dependency instanceof AppBarLayout) {
            // if the dependency is an AppBarLayout, do not allow super to react on that
            // we don't want that behavior
            return true;
        }
        return super.onDependentViewChanged(parent, fab, dependency);
    }

}
