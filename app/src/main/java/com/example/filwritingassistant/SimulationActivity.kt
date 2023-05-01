package com.example.filwritingassistant

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import java.io.File
import java.io.FileOutputStream


class SimulationActivity : AppCompatActivity() {



    @SuppressLint("ClickableViewAccessibility")
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

        //PaintView
        val paintView = findViewById<PaintView>(R.id.paintView)
        paintView.setOnTouchListener { _, event ->
            paintView.onTouchEvent(event)
        }

        // once clicked, it will clear the drawing in the paintView layout
        val clearButton = findViewById<ImageView>(R.id.btn_clear)
        clearButton.setOnClickListener {
            paintView.reset()
        }

        //once clicked, it will go back to the Letter Picker of simulation
        val backButton = findViewById<ImageView>(R.id.btn_backpicker)
        backButton.setOnClickListener {
            val intent = Intent(this, LetterPickerSimuActivity::class.java)
            startActivity(intent)
        }

        //once clicked, it will save the drawing to the hidden folder in the phone
        val saveButton = findViewById<ImageView>(R.id.btn_save)
        saveButton.setOnClickListener{
            saveImage()
        }



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

    private fun saveImage() {
        val paintView = findViewById<PaintView>(R.id.paintView)
        val bitmap = paintView.getBitmapFromView()


        //path: /storage/emulated/0/Android/data/filwritingassistant/files/.images/simulation.jpg
        val imagesFolder = File(getExternalFilesDir(null), ".images")
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs()
        }

        val file = File(imagesFolder, "simulation.jpg")

        try {
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }


}







