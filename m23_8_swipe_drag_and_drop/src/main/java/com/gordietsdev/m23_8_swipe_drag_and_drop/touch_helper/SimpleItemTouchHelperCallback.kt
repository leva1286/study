package com.gordietsdev.m23_8_swipe_drag_and_drop.touch_helper

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gordietsdev.m23_8_swipe_drag_and_drop.adapter.MyAdapter
import java.util.*

class SimpleItemTouchHelperCallback(val adapter: MyAdapter) : ItemTouchHelper.Callback() {

    override fun isLongPressDragEnabled(): Boolean {
        //Drag & drop поддерживается
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        //Swipe поддерживается
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        //Настраиваем флаги для drag & drop и swipe жестов
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val items = adapter.data
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        //Меняем элементы местами с помощью метода swap
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        //Сообщаем об изменениях адаптеру
        //Or DiffUtil
        adapter.notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //Удаляем элемент из списка после жеста swipe
        adapter.data.removeAt(viewHolder.adapterPosition)
        //Or DiffUtil
        adapter.notifyItemRemoved(viewHolder.adapterPosition);
    }
}