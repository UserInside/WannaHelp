package com.example.wannahelp.authScreen

import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import com.example.wannahelp.R
import com.example.wannahelp.common.ToolbarFragment

class AuthFragment : ToolbarFragment(R.layout.fragment_auth, showBackButton = true) {

    override fun setupToolbar(toolbar: Toolbar, actionButton: ImageButton) {
        toolbar.title = getString(R.string.authorization)
    }


}