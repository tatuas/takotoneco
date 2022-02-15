package com.tatuas.takotoneco.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tatuas.takotoneco.R
import com.tatuas.takotoneco.data.logger.AnalyticsLogger
import com.tatuas.takotoneco.databinding.FragmentUserListBinding
import com.tatuas.takotoneco.ext.observeOnStarted
import com.tatuas.takotoneco.model.LoadState
import com.tatuas.takotoneco.model.UserItemList
import com.tatuas.takotoneco.view.RecyclerViewVerticalScrollListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment : Fragment(R.layout.fragment_user_list) {

    @Inject
    lateinit var analyticsLogger: AnalyticsLogger

    private val mainViewModel by activityViewModels<MainViewModel>()

    private var _binding: FragmentUserListBinding? = null
    private val binding: FragmentUserListBinding get() = requireNotNull(_binding)

    private var _listAdapter: UserListAdapter? = null
    private val listAdapter: UserListAdapter get() = requireNotNull(_listAdapter)

    private var _scrollListener: RecyclerView.OnScrollListener? = null
    private val scrollListener: RecyclerView.OnScrollListener get() = requireNotNull(_scrollListener)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserListBinding.bind(view)
        _listAdapter = UserListAdapter().apply {
            onRetryClick = {
                analyticsLogger.logEvent("user_list.list_retry.clicked")

                val current = mainViewModel.loadStateFlow.value

                if (current is LoadState.Success &&
                    current.data.pagingState == UserItemList.PagingState.RETRY
                ) {
                    mainViewModel.loadPagingUserList(current.data)
                }
            }

            onDataClick = {
                analyticsLogger.logEvent(
                    "user_list.list_data.clicked",
                    "user_id" to it.id.toString(),
                )

                findNavController().navigate(
                    UserListFragmentDirections.actionListToDetail(it.login, it)
                )
            }
        }
        _scrollListener = RecyclerViewVerticalScrollListener {
            val current = mainViewModel.loadStateFlow.value

            if (current is LoadState.Success &&
                current.data.pagingState == UserItemList.PagingState.NONE
            ) {
                mainViewModel.loadPagingUserList(current.data)
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
            addOnScrollListener(scrollListener)
        }

        binding.screenRetry.setOnClickListener {
            it.isEnabled = false
            mainViewModel.loadFirstUserList()
        }

        mainViewModel.loadStateFlow.observeOnStarted(viewLifecycleOwner) {
            binding.progress.isVisible = it is LoadState.Loading
            binding.recyclerView.isVisible = it is LoadState.Success
            binding.errorContainer.isVisible = it is LoadState.Failure

            when (it) {
                is LoadState.Success -> {
                    if (it.data.pagingState == UserItemList.PagingState.NONE) {
                        analyticsLogger.logEvent(
                            name = "user_list.list_load.succeed",
                            "action_by" to "user_scroll",
                        )
                    }

                    Timber.d("${it.data.pagingState}")

                    listAdapter.submitList(it.data.list)
                }
                is LoadState.Failure -> {
                    binding.screenRetry.isEnabled = true
                    binding.errorMessage.text = it.message.getString(requireContext())

                    analyticsLogger.logEvent(
                        "user_list.list_load.failed",
                    )
                }
                is LoadState.Loading -> {
                    Timber.d("Loading")
                }
                else -> {
                    // nop
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.recyclerView.removeOnScrollListener(scrollListener)
        _binding = null
        _listAdapter = null
        _scrollListener = null
    }
}
