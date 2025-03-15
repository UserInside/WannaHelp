package com.example.wannahelp.wannaHelpScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.wannahelp.R
import com.example.wannahelp.common.extentions.readFile
import com.example.wannahelp.databinding.FragmentWannaHelpScreenBinding
import kotlinx.serialization.json.Json

class WannaHelpScreenFragment : Fragment() {
    private lateinit var binding: FragmentWannaHelpScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentWannaHelpScreenBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().title = getString(R.string.wanna_help)

        val jsonString = requireContext().assets.readFile("categories.json")
        val categoriesItemList = Json.decodeFromString<List<CategoryItem>>(jsonString)

        val recyclerView = binding.recyclerViewCategories

        val adapter = CategoriesRecyclerViewAdapter(categoriesItemList)
        val spanCount = 2
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)

        val spacing = resources.getDimensionPixelSize(R.dimen.spacing_xs)
        val includeEdge = true
        recyclerView.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing, includeEdge))

        recyclerView.adapter = adapter
    }
}
