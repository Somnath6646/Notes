package com.example.notes.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R

abstract class SwipeToShareCallback internal constructor(var context: Context):
    ItemTouchHelper.Callback(){

    private val  background: ColorDrawable
    private val backgroundColor: Int
    private val mClearPaint: Paint
    private val shareIcon: Drawable?
    private val intrinsicWidth: Int
    private val intrinsicHeight: Int

    init {
        background = ColorDrawable()
        mClearPaint = Paint()
        backgroundColor = Color.parseColor("#2196F3")
        shareIcon = ContextCompat.getDrawable(context, R.drawable.ic_share)
        intrinsicHeight = shareIcon!!.intrinsicHeight
        intrinsicWidth = shareIcon!!.intrinsicWidth
    }


    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val itemHeight = itemView.height

        val isCancelled = dX == 0f && !isCurrentlyActive

        if(isCancelled){
            clearCanvas(
                canvas = c,
                left = itemView.left.toFloat(),
                top = itemView.top.toFloat(),
                right = itemView.left + dX,
                bottom = itemView.bottom.toFloat()
            )
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }
        background.color = backgroundColor
        background.setBounds(
            itemView.left,
            itemView.top,
            (itemView.left + dX).toInt(),
            itemView.bottom.toFloat().toInt()
        )

        background.draw(c)

        val shareIconTop = itemView.top + (itemHeight - intrinsicHeight)/2
        val shareIconMargin = (itemHeight - intrinsicHeight)/2
        val shareIconLeft =itemView.left + shareIconMargin
        val shareIconRight = itemView.left + shareIconMargin + intrinsicWidth
        val shareIconBottom = shareIconTop + intrinsicHeight

        shareIcon!!.setBounds(shareIconLeft, shareIconTop, shareIconRight, shareIconBottom)
        shareIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

    }

    override fun getMoveThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7f
    }

    private fun clearCanvas(canvas: Canvas, left: Float, top: Float, bottom: Float, right: Float){
        canvas.drawRect(left, top, right, bottom, mClearPaint)
    }

}