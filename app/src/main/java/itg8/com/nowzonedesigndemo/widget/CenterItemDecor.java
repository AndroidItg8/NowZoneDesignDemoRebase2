package itg8.com.nowzonedesigndemo.widget;

import android.graphics.Rect;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by itg_Android on 9/13/2017.
 */

public class CenterItemDecor extends RecyclerView.ItemDecoration {

    private int paddingStartEnd;

    public CenterItemDecor(int paddingStartEnd) {
        this.paddingStartEnd = paddingStartEnd;
    }

//    @Override
//    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
//        super.getItemOffsets(outRect, itemPosition, parent);
//    }

    @Override
    public void getItemOffsets(Rect outRect, View child, RecyclerView parent, RecyclerView.State state) {
        int lastItemIndex = parent.getLayoutManager().getItemCount() - 1;
        int childIndex = parent.getChildAdapterPosition(child);

        int startMargin = childIndex == 0 ? paddingStartEnd:0;
        int endMargin = childIndex == lastItemIndex ? paddingStartEnd : 0;

        //RTL works for API 17+
        if (ViewCompat.getLayoutDirection(child) == ViewCompat.LAYOUT_DIRECTION_RTL) {
            // The view has RTL layout
            ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).setMargins(endMargin, 0, startMargin, 0);
        } else {
            // The view has LTR layout
            ((ViewGroup.MarginLayoutParams) child.getLayoutParams()).setMargins(startMargin, 0, endMargin, 0);
        }

        child.requestLayout();
    }
}
