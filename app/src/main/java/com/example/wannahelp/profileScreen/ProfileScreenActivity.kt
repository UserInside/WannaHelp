package com.example.wannahelp.profileScreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.example.wannahelp.R
import com.example.wannahelp.databinding.ActivityProfileScreenBinding

class ProfileScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavView.bottomNavigationView.selectedItemId = R.id.action_profile

        val recycler = binding.profileInformation.recyclerViewYourFriends

        val friendsList = listOf(
            Friend(R.drawable.avatar_3, "Дмитрий Валериевич"),
            Friend(R.drawable.avatar_2, "Евгений Александров"),
            Friend(R.drawable.avatar_1, "Виктор Кузнецов"),
        )

        val adapter = FriendsAdapter(friendsList)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

    }
}

data class Friend(
    val avatar: Int,
    val name: String,
)
