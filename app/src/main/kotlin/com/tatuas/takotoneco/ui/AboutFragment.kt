package com.tatuas.takotoneco.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tatuas.takotoneco.R
import com.tatuas.takotoneco.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {

    private var _binding: FragmentAboutBinding? = null
    private val binding: FragmentAboutBinding get() = requireNotNull(_binding)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAboutBinding.bind(view)

        binding.launchGitHub.setOnClickListener {
            startActivity(
                Intent.createChooser(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/tatuas/takotoneco")
                    ),
                    null,
                    null
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
