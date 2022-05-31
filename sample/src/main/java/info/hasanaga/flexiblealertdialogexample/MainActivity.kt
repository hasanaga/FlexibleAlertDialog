package info.hasanaga.flexiblealertdialogexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {


    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {

            return if (position == 0) {
                Fragment1()
            } else if (position == 1) {
                Fragment2();
            } else {
                Fragment1()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewPager2 = findViewById(R.id.pager)
        tabLayout = findViewById(R.id.tab_layout)

        viewPagerAdapter = ViewPagerAdapter(this)

        viewPager2.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = "Fragment ${(position + 1)}"
        }.attach()
    }


}


