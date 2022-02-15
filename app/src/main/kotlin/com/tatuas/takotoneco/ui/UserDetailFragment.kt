package com.tatuas.takotoneco.ui

import android.os.Bundle
import android.text.format.DateUtils.*
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.tatuas.takotoneco.R
import com.tatuas.takotoneco.databinding.FragmentUserDetailBinding
import com.tatuas.takotoneco.ext.getImagePlaceholderColor
import com.tatuas.takotoneco.ext.observeOnStarted
import com.tatuas.takotoneco.model.LoadState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {

    private val viewModel by viewModels<UserDetailViewModel>()

    private val args by navArgs<UserDetailFragmentArgs>()

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUserDetailBinding.bind(view)

        viewModel.init(args.user)

        viewModel.loadStateFlow.observeOnStarted(viewLifecycleOwner) {
            binding.scrollView.isVisible = it is LoadState.Success
            binding.progress.isVisible = it is LoadState.Loading
            binding.errorContainer.isVisible = it is LoadState.Failure

            when (it) {
                is LoadState.Success -> {
                    val item = it.data

                    binding.name.text = item.name
                    binding.login.text = item.login
                    binding.icon.load(item.iconUrl) {
                        val color = requireContext().getImagePlaceholderColor()
                        placeholder(color)
                        error(color)
                    }
                    binding.followers.text = item.followers.toString()
                    binding.follows.text = item.follows.toString()
                    binding.createdAt.text = getString(
                        R.string.value_created_at,
                        formatDateTime(
                            requireContext(),
                            item.createdAt.time,
                            FORMAT_SHOW_YEAR or
                                    FORMAT_SHOW_DATE or
                                    FORMAT_SHOW_WEEKDAY
                        )
                    )
                }
                is LoadState.Failure -> {
                    binding.errorMessage.text = it.message.getString(requireContext())
                }
                is LoadState.Loading -> {
                    Timber.d("Loading")
                }
                else -> {
                    // nop
                }
            }
        }

        binding.screenRetry.setOnClickListener {
            viewModel.reload(args.user)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
