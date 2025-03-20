package com.example.wannahelp.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.wannahelp.R

abstract class ToolbarFragment(
    private val fragmentContent: Int,
    private val showBackButton: Boolean = false,
) : Fragment() {
    protected var content: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_toolbar, container, false)

        val frameLayout = view.findViewById<ViewGroup>(R.id.main_fragment_container)
        val toolbar: Toolbar = view.findViewById(R.id.main_toolbar)
        val actionButton: ImageButton = view.findViewById(R.id.btn_action)
        if (showBackButton) toolbar.setNavigationIcon(R.drawable.icon_arrow_back_24)
        setupToolbar(toolbar, actionButton)

        content =
            try {
                inflater.inflate(fragmentContent, frameLayout, true)
            } catch (ex: Exception) {
                println(ex.message)
            } as View?
        return view
    }

    abstract fun setupToolbar(
        toolbar: Toolbar,
        actionButton: ImageButton,
    )

    override fun onDestroyView() {
        super.onDestroyView()
        content = null
    }
}
