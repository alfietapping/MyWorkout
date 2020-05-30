package com.example.myworkout

import android.os.Bundle
import androidx,LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myworkout.adapters.AdapterVideos
import kotlinx.android.synthetic.main.video_recycler.*

class VideoPlayerFragment : Fragment() {

    private lateinit var videoAdapter: AdapterVideos

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_workout_builder, container, false)

        initRecycler()

        return v
    }

    private fun initRecycler(){
        recycler_video.layoutManager = LinearLayoutManager(context)
        videoAdapter = AdapterVideos()
        recycler_video.adapter = videoAdapter
    }

    private fun data(){
        val list = mutableListOf<VideoObject>()
        list.add(VideoObject("Deadlifts", "https://youtu.be/-4qRntuXBSc",
                "https://img.youtube.com/vi/-4qRntuXBSc/0.jpg", "How to perform Deadlifts"))
    }
}