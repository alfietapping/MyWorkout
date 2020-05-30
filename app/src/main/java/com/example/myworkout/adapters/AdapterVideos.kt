package com.example.myworkout.adapters

import android.support.v7.view.menu.MenuView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myworkout.R
import com.example.myworkout.VideoObject
import kotlinx.android.synthetic.main.card_view_video.view.*

class AdapterVideos : RecyclerView.Adapter<AdapterVideos.VideoViewHolder>() {

    private var items: List<VideoObject> = ArrayList()


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): VideoViewHolder {
        return VideoViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.card_view_video, p0, false))
    }


    override fun onBindViewHolder(p0: VideoViewHolder, p1: Int) {
        p0.bind(items[p1])
    }

    fun setData(list: List<VideoObject>){
        items = list
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class VideoViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView){

        val videoThumbnail: ImageView = itemView.findViewById(R.id.videoNameTV)
        val videoName: TextView = itemView.findViewById(R.id.videoNameTV)
        val videoDescription: TextView = itemView.findViewById(R.id.videoDescriptionTV)

        fun bind (videoObject: VideoObject){
            videoName.text = videoObject.videoName
            videoDescription.text = videoObject.videoDescription
            Glide.with(itemView.context).load(videoObject.videoThumbnail).into(videoThumbnail)
        }
    }
}