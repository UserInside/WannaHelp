package com.example.wannahelp.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.wannahelp.R

abstract class ToolbarFragment(
    private val fragmentContent: Int,
) : Fragment() {
    protected var content: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_toolbar, container, false)

        val frameLayout = view.findViewById<ViewGroup>(R.id.main_fragment_container)
        setupToolbar(view.findViewById(R.id.main_toolbar))

        content = inflater.inflate(fragmentContent, frameLayout, true)

        return view
    }

    abstract fun setupToolbar(toolbar: Toolbar)

    override fun onDestroyView() {
        super.onDestroyView()
        content = null
    }
}
