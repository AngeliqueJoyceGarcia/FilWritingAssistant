package com.example.filwritingassistant

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView

class SimulationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation)

        //from letter picker activity, m1 will return BIG or SMALl, m2 will return 0-27
        val letterCapitalization = intent.getStringExtra("m1")
        val index = intent.getStringExtra("m2")

        //initializing elements of the simulation activity
        val video = findViewById<VideoView>(R.id.videosimulator)
        val previous = findViewById<ImageView>(R.id.btnprevious)
        val next = findViewById<ImageView>(R.id.btnNext)


        //check if the letter is 'a' and capitalization is 'SMALL' and index is 0
        if (letterCapitalization == "SMALL" && index == "0") {
            //play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_aa1)
            video.start()

            //set onclick listener for the next button
            next.setOnClickListener {

                //play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_aa2)
                video.start()
            }

            //set onclick listener for the previous button
            previous.setOnClickListener {
                //play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_aa1)
                video.start()
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                //restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'a' and capitalization is 'BIG' and index is 0
        if (letterCapitalization == "BIG" && index == "0") {
            //play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_a1)
            video.start()

            //set onclick listener for the next button
            next.setOnClickListener {
                //play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_a2)
                video.start()
            }

            //set onclick listener for the previous button
            previous.setOnClickListener {
                //play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_a1)
                video.start()
            }

            //listener to the video view to continuously play the video
            video.setOnCompletionListener {
                //restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'b' and capitalization is 'SMALL' and index is 1
        if (letterCapitalization == "SMALL" && index == "1") {
            //play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_bb1)
            video.start()

            //set onclick listener for the next button
            next.setOnClickListener {

                //play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_bb2)
                video.start()
            }

            //set onclick listener for the previous button
            previous.setOnClickListener {
                //play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_bb1)
                video.start()
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                //restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'b' and capitalization is 'BIG' and index is 1
        if (letterCapitalization == "BIG" && index == "1") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_b1, R.raw.s_b2, R.raw.s_b3)

            //play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + videos[currentVideoIndex])
            video.start()

            // set initial visibility for previous and next buttons
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            //set onclick listener for the next button
            next.setOnClickListener {
                //increment the current video index and play the next video
                currentVideoIndex = (currentVideoIndex + 1) % videos.size
                video.setVideoPath("android.resource://" + packageName + "/" + videos[currentVideoIndex])
                video.start()

                // show or hide the previous and next buttons based on current video index
                if (currentVideoIndex == 1) {
                    previous.visibility = View.VISIBLE
                    next.visibility = View.VISIBLE
                } else if (currentVideoIndex == 2) {
                    previous.visibility = View.VISIBLE
                    next.visibility = View.INVISIBLE
                }
            }

            //set onclick listener for the previous button
            previous.setOnClickListener {
                //decrement the current video index and play the previous video
                currentVideoIndex = (currentVideoIndex - 1 + videos.size) % videos.size
                video.setVideoPath("android.resource://" + packageName + "/" + videos[currentVideoIndex])
                video.start()

                // show or hide the previous and next buttons based on current video index
                if (currentVideoIndex == 0) {
                    previous.visibility = View.INVISIBLE
                    next.visibility = View.VISIBLE
                } else if (currentVideoIndex == 1) {
                    previous.visibility = View.VISIBLE
                    next.visibility = View.VISIBLE
                }
            }

            //listener to the video view to continuously play the video
            video.setOnCompletionListener {
                //restart the current video from the beginning
                video.seekTo(0)
                video.start()
            }
        }









    }
}