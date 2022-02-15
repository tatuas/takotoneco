package com.tatuas.takotoneco.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tatuas.takotoneco.R
import com.tatuas.takotoneco.databinding.ItemUserListDataBinding
import com.tatuas.takotoneco.databinding.ItemUserListProgressBinding
import com.tatuas.takotoneco.databinding.ItemUserListRetryBinding
import com.tatuas.takotoneco.model.UserItem

class UserListAdapter : ListAdapter<UserItem, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<UserItem>() {
        override fun areItemsTheSame(
            oldItem: UserItem,
            newItem: UserItem,
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: UserItem,
            newItem: UserItem,
        ): Boolean = oldItem == newItem
    }
) {

    var onDataClick: ((UserItem.Data) -> Unit)? = null

    var onRetryClick: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.item_user_list_data -> {
                UserListViewHolder.Data(
                    ItemUserListDataBinding.inflate(inflater, parent, false)
                )
            }
            R.layout.item_user_list_retry -> {
                UserListViewHolder.Retry(
                    ItemUserListRetryBinding.inflate(inflater, parent, false)
                )
            }
            R.layout.item_user_list_progress -> {
                UserListViewHolder.Progress(
                    ItemUserListProgressBinding.inflate(inflater, parent, false)
                )
            }
            else -> {
                throw RuntimeException("Invalid view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is UserListViewHolder.Data -> {
                val item = getItem(position) as? UserItem.Data ?: return

                holder.bind(item, onDataClick)
            }
            is UserListViewHolder.Retry -> {
                holder.bind(onRetryClick)
            }
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        when (holder) {
            is UserListViewHolder.Data -> holder.unbind()
            is UserListViewHolder.Retry -> holder.unbind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UserItem.Data -> R.layout.item_user_list_data
            is UserItem.Retry -> R.layout.item_user_list_retry
            is UserItem.Progress -> R.layout.item_user_list_progress
        }
    }
}
