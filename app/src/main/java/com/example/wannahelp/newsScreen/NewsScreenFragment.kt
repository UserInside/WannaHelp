package com.example.wannahelp.newsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wannahelp.databinding.FragmentNewsScreenBinding


class NewsScreenFragment : Fragment() {
    private lateinit var binding: FragmentNewsScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsList = listOf(

        )


    }

    companion object {
        fun newInstance() = NewsScreenFragment()
    }
}
