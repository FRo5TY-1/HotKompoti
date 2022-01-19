package com.example.hotkompoti.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.hotkompoti.R
import com.example.hotkompoti.databinding.FragmentPostsBinding

class PostsFragment : Fragment(R.layout.fragment_posts) {
    private var fragmentPostsBinding: FragmentPostsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentPostsBinding.bind(view)
        fragmentPostsBinding = binding
    }
}