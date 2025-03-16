package com.example.wannahelp.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.wannahelp.R

abstract class ToolbarFragment : Fragment() {

    protected lateinit var toolbar: Toolbar
    private lateinit var frameLayout: ViewGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_toolbar, container, false)

        toolbar = view.findViewById<Toolbar>(R.id.main_toolbar)
        frameLayout = view.findViewById<ViewGroup>(R.id.main_fragment_container)
        setupToolbar()

        val fragmentContent =
            inflater.inflate(getFragmentContent(), container, false)
        frameLayout.addView(fragmentContent)

        return view
    }

    abstract fun getFragmentContent(): Int
    abstract fun setupToolbar()
}
