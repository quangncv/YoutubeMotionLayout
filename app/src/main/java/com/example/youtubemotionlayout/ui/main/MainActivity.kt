package com.example.youtubemotionlayout.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelLazy
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.youtubemotionlayout.ui.detail.VideoPlayerFragment
import com.example.youtubemotionlayout.databinding.ActivityMainBinding
import com.example.youtubemotionlayout.widgets.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    private val viewModel by ViewModelLazy(MainViewModel::class, {viewModelStore}, {defaultViewModelProviderFactory})
    private val videoAdapter by lazy { VideoAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // addFragment()
        initRecyclerView()

        viewModel.getVideoList()
        viewModel.videoListResponse.observe(this, {
            it.items?.let { items ->
                videoAdapter.addItems(items)
            }
        })

        viewModel.loading.observe(this, {
            Log.e("loading", it.toString())
            runOnUiThread {
                if (it) {
                    viewBinding.loading.visibility = View.VISIBLE
                    viewBinding.loading.alpha = 1f
                } else {
                    viewBinding.loading.visibility = View.GONE
                    viewBinding.loading.alpha = 0f
                }
            }
        })
    }

    private fun initRecyclerView() {
        videoAdapter.setListener { video ->
            supportFragmentManager.fragments.find { it is VideoPlayerFragment }?.let {
                (it as VideoPlayerFragment).setVideoModel(video)
            } ?: kotlin.run {
                addFragment(VideoPlayerFragment.newInstance(video))
            }
        }

        viewBinding.rvPlaylist.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = videoAdapter
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(viewBinding.fragmentContainer.id, fragment)
            .commit()
    }

    fun transitionChange(progress: Float) {
        viewBinding.rootLayout.progress = abs(progress)
    }
}