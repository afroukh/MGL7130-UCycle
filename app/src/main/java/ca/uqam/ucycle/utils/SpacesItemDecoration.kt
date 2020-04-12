package ca.uqam.ucycle.utils


import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpacesItemDecoration(private val space: Int) : ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = space
        outRect.right = space

//        val position = parent.getChildAdapterPosition(view)

//        if (position == parent.adapter!!.itemCount - 1) {
//            outRect.bottom = 200
//        }

        if (parent.getChildAdapterPosition(view) == 0)
            outRect.top = space
    }


}