package com.example.youtubemotionlayout.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.youtubemotionlayout.data.remote.response.Item
import com.example.youtubemotionlayout.data.remote.response.VideoModel
import com.example.youtubemotionlayout.databinding.RvItemVideoBinding

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.VideoVH>(){

    private val videos: MutableList<Item> = mutableListOf()

    private var listener: ((Item) -> Unit)? = null

    fun addItems(items: List<Item>) {
        videos.addAll(items)
        notifyDataSetChanged()
    }

    fun setListener(listener: ((Item) -> Unit)) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoVH {
        val viewBinding = RvItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return VideoVH(viewBinding)
    }

    override fun onBindViewHolder(holder: VideoVH, position: Int) {
        holder.init(videos[position])
        holder.itemView.setOnClickListener {
            listener?.invoke(videos[position])
        }
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    class VideoVH(private val viewBinding: RvItemVideoBinding): RecyclerView.ViewHolder(viewBinding.root) {
        fun init(video: Item) {
            viewBinding.apply {
                Glide.with(viewBinding.root.context).load(video.snippet?.thumbnails?.high?.url).into(imgCover)
                tvTitle.text = video.snippet?.title
            }
        }
    }
}