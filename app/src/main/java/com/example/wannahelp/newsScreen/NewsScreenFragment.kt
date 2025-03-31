package com.example.wannahelp.newsScreen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment
import com.example.wannahelp.databinding.FragmentNewsScreenBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class NewsScreenFragment : ToolbarFragment(R.layout.fragment_news_screen) {
    private lateinit var viewModel: NewsViewModel
    private lateinit var rvAdapter: NewsRecyclerViewAdapter
    private val disposables = CompositeDisposable()

    override fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton,
    ) {
        toolbar.title = getString(R.string.news)
        actionButton.apply {
            visibility = View.VISIBLE
            setImageResource(R.drawable.icon_filter)
            setOnClickListener {
                NavHostFragment.findNavController(this@NewsScreenFragment)
                    .navigate(R.id.navigateToNewsFilterScreen)
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentNewsScreenBinding.bind(content ?: view)
        viewModel = ViewModelProvider(requireActivity())[NewsViewModel::class]

        rvAdapter =
            NewsRecyclerViewAdapter { item ->
                if (!item.isRead) viewModel.decreaseCount()
                val action = NewsScreenFragmentDirections.navigateToEventDetailsScreen(item)
                NavHostFragment.findNavController(this@NewsScreenFragment).navigate(action)
            }

        binding.recyclerViewNews.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val newsSubscription =
            viewModel.listToShow.observeOn(AndroidSchedulers.mainThread()).subscribe {
                rvAdapter.submitList(it)
            }
        disposables.add(newsSubscription)

        val stateSubscription =
            viewModel.screenStateObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribe { state ->
                    when (state) {
                        is NewsState.Progress -> {
                            binding.newsProgressBar.visibility = View.VISIBLE
                            binding.newsProgressBar.progress = state.progress
                        }

                        is NewsState.Done -> {
                            binding.newsProgressBar.visibility = View.GONE
                        }
                    }
                }
        disposables.add(stateSubscription)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}
