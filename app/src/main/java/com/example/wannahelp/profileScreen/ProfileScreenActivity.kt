package com.example.wannahelp.profileScreen

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wannahelp.R
import com.example.wannahelp.databinding.ActivityProfileScreenBinding
import com.example.wannahelp.profileEditScreen.ProfileEditScreenActivity

class ProfileScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.bottomNavigationView.selectedItemId = R.id.action_profile

        val recycler = binding.profileInformation.recyclerViewYourFriends

        val friendsList =
            listOf(
                FriendCard(R.drawable.avatar_3, "Дмитрий Валериевич"),
                FriendCard(R.drawable.avatar_2, "Евгений Александров"),
                FriendCard(R.drawable.avatar_1, "Виктор Кузнецов"),
            )

        val adapter = FriendsRecyclerViewAdapter(friendsList)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter


        val toolbar = binding.appBar.toolbar
        setSupportActionBar(toolbar)

        onBackPressedDispatcher.addCallback(this) {
            finishAffinity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit_profile) {
            startActivity(Intent(this@ProfileScreenActivity, ProfileEditScreenActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}
