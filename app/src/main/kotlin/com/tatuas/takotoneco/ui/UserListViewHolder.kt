package com.tatuas.takotoneco.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import coil.load
import coil.util.CoilUtils
import com.tatuas.takotoneco.databinding.ItemUserListDataBinding
import com.tatuas.takotoneco.databinding.ItemUserListProgressBinding
import com.tatuas.takotoneco.databinding.ItemUserListRetryBinding
import com.tatuas.takotoneco.ext.getImagePlaceholderColor
import com.tatuas.takotoneco.model.UserItem


sealed class UserListViewHolder<T : ViewBinding>(binding: T) :
    RecyclerView.ViewHolder(binding.root) {

    class Progress(
        binding: ItemUserListProgressBinding,
    ) : UserListViewHolder<ItemUserListProgressBinding>(binding)

    class Retry(
        private val binding: ItemUserListRetryBinding,
    ) : UserListViewHolder<ItemUserListRetryBinding>(binding) {

        fun bind(onClick: (() -> Unit)?) {
            binding.retry.isEnabled = true
            binding.retry.setOnClickListener {
                binding.retry.isEnabled = false
                onClick?.invoke()
            }
        }

        fun unbind() {
            binding.retry.setOnClickListener(null)
        }
    }

    class Data(
        private val binding: ItemUserListDataBinding,
    ) : UserListViewHolder<ItemUserListDataBinding>(binding) {

        private val color = binding.root.context.getImagePlaceholderColor()

        fun bind(data: UserItem.Data, onClick: ((UserItem.Data) -> Unit)?) {
            binding.root.setOnClickListener {
                onClick?.invoke(data)
            }

            binding.name.text = data.login
            binding.icon.load(data.iconUrl) {
                placeholder(color)
                error(color)
            }
        }

        fun unbind() {
            binding.root.setOnClickListener(null)

            CoilUtils.clear(binding.icon)
        }
    }
}
