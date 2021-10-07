package com.example.youtubemotionlayout.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import com.example.youtubemotionlayout.R
import com.example.youtubemotionlayout.widgets.PlayerScreenMotionLayout
import com.example.youtubemotionlayout.data.remote.response.Item
import com.example.youtubemotionlayout.databinding.FragmentVideoPlayerBinding
import com.example.youtubemotionlayout.ui.main.MainActivity
import com.example.youtubemotionlayout.utils.DatetimeUtils
import com.google.android.exoplayer2.SimpleExoPlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo

class VideoPlayerFragment: Fragment() {
    private lateinit var viewBinding: FragmentVideoPlayerBinding

    private var videoModel: Item? = null
    private var duration:Float = 0f
    private var state: PlayerConstants.PlayerState = PlayerConstants.PlayerState.UNSTARTED

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPlayer()

        viewBinding.rootLayout.setListener(object : PlayerScreenMotionLayout.ITransitionListener {
            override fun onTransitionStarted() {
                viewBinding.playerView.getPlayerUiController().showUi(false)
            }

            override fun onTransitionChange(progress: Float) {
                (activity as MainActivity).also { mainActivity ->
                    mainActivity.transitionChange(progress)
                }
            }

            override fun onTransitionCompleted() {
                if (viewBinding.rootLayout.progress == 0f) {
                    viewBinding.playerView.getPlayerUiController().showUi(true)
                } else {
                    viewBinding.playerView.getPlayerUiController().showUi(false)
                }
            }
        })

        play()
    }

    private fun initPlayer() {
        viewBinding.playerView.apply {
            lifecycle.addObserver(this)
            addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    viewBinding.imgPlayPause.setOnClickListener {
                        if (state == PlayerConstants.PlayerState.PLAYING) {
                            youTubePlayer.pause()
                        } else {
                            youTubePlayer.play()
                        }
                    }
                }

                override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                    viewBinding.seekBar.progress = ((second / duration) * 100).toInt()
                }

                override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {
                    this@VideoPlayerFragment.duration = duration
                }

                override fun onStateChange(
                    youTubePlayer: YouTubePlayer,
                    state: PlayerConstants.PlayerState
                ) {
                    this@VideoPlayerFragment.state = state
                    if (state == PlayerConstants.PlayerState.PLAYING) {
                        viewBinding.imgPlayPause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_pause))
                    } else {
                        viewBinding.imgPlayPause.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_play))
                    }
                }
            })
        }
    }

    fun setVideoModel(videoModel: Item) {
        this.videoModel = videoModel
        play()
    }

    private fun play() {
        viewBinding.rootLayout.transitionToStart()
        viewBinding.tvTitle.text = videoModel?.snippet?.title
        viewBinding.tvTitleMini.text = videoModel?.snippet?.title
        viewBinding.tvDescription.text = videoModel?.snippet?.description
        viewBinding.tvPublishedAt.text = "Published on ${DatetimeUtils.parseLocalTimeToString(videoModel?.snippet?.publishedAt ?: "")}"

        viewBinding.playerView.getYouTubePlayerWhenReady(object : YouTubePlayerCallback {
            override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadOrCueVideo(lifecycle, videoModel?.snippet?.resourceId?.videoId.toString(), 0f)
            }
        })
    }

    companion object {
        fun newInstance(videoModel: Item): VideoPlayerFragment {
            return VideoPlayerFragment().apply {
                this.videoModel = videoModel
            }
        }
    }
}