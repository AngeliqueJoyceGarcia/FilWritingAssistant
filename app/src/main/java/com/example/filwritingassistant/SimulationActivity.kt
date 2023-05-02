package com.example.filwritingassistant

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation
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

                //make previous button visible and next button invisible
                previous.setVisibility(View.VISIBLE)
                next.setVisibility(View.INVISIBLE)
            }

            //set onclick listener for the previous button
            previous.setOnClickListener {
                //play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_aa1)
                video.start()

                //make previous button invisible and next button visible
                previous.setVisibility(View.INVISIBLE)
                next.setVisibility(View.VISIBLE)
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                //restart the video from the beginning
                video.seekTo(0)
                video.start()
            }

            //make previous button invisible and next button visible initially
            previous.setVisibility(View.INVISIBLE)
            next.setVisibility(View.VISIBLE)
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

                //make previous button visible and next button invisible
                previous.setVisibility(View.VISIBLE)
                next.setVisibility(View.INVISIBLE)
            }

            //set onclick listener for the previous button
            previous.setOnClickListener {
                //play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_a1)
                video.start()

                //make previous button invisible and next button visible
                previous.setVisibility(View.INVISIBLE)
                next.setVisibility(View.VISIBLE)
            }

            //listener to the video view to continuously play the video
            video.setOnCompletionListener {
                //restart the video from the beginning
                video.seekTo(0)
                video.start()
            }

            //make previous button invisible and next button visible initially
            previous.setVisibility(View.INVISIBLE)
            next.setVisibility(View.VISIBLE)
        }

        //check if the letter is 'b' and capitalization is 'SMALL' and index is 1
        if (letterCapitalization == "SMALL" && index == "1") {
            //play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_bb1)
            video.start()

            //make previous button invisible and next button visible initially
            previous.setVisibility(View.INVISIBLE)
            next.setVisibility(View.VISIBLE)

            //set onclick listener for the next button
            next.setOnClickListener {
                //play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_bb2)
                video.start()

                //make previous button visible and next button invisible
                previous.setVisibility(View.VISIBLE)
                next.setVisibility(View.INVISIBLE)
            }

            //set onclick listener for the previous button
            previous.setOnClickListener {
                //play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_bb1)
                video.start()

                //make previous button invisible and next button visible
                previous.setVisibility(View.INVISIBLE)
                next.setVisibility(View.VISIBLE)
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

        //check if the letter is 'c' and capitalization is 'SMALL' and index is 2
        if (letterCapitalization == "SMALL" && index == "2") {
            //play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_cc1)
            video.start()

            previous.visibility = View.INVISIBLE
            next.visibility = View.INVISIBLE

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                //restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'c' and capitalization is 'BIG' and index is 2
        if (letterCapitalization == "BIG" && index == "2") {
            //play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_c1)
            video.start()

            previous.visibility = View.INVISIBLE
            next.visibility = View.INVISIBLE

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                //restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'd' and capitalization is 'SMALL' and index is 3
        if (letterCapitalization == "SMALL" && index == "3") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_dd1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_dd2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_dd1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'd' and capitalization is 'BIG' and index is 3
        if (letterCapitalization == "BIG" && index == "3") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_d1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_d2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_d1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'e' and capitalization is 'SMALL' and index is 4
        if (letterCapitalization == "SMALL" && index == "4") {
            //play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_ee1)
            video.start()

            previous.visibility = View.INVISIBLE
            next.visibility = View.INVISIBLE

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                //restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'e' and capitalization is 'BIG' and index is 4
        if (letterCapitalization == "BIG" && index == "4") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_e1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_e2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_e1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'f' and capitalization is 'SMALL' and index is 5
        if (letterCapitalization == "SMALL" && index == "5") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_ff1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_ff2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_ff1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'F' and capitalization is 'BIG' and index is 5
        if (letterCapitalization == "BIG" && index == "5") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_f1, R.raw.s_f2, R.raw.s_f3)

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

        // check if the letter is 'g' and capitalization is 'SMALL' and index is 6
        if (letterCapitalization == "SMALL" && index == "6") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_gg1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_gg2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_gg1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'g' and capitalization is 'BIG' and index is 6
        if (letterCapitalization == "BIG" && index == "6") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_g1, R.raw.s_g2, R.raw.s_g3)

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

        // check if the letter is 'h' and capitalization is 'SMALL' and index is 7
        if (letterCapitalization == "SMALL" && index == "7") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_hh1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_hh2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_hh1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'h' and capitalization is 'BIG' and index is 7
        if (letterCapitalization == "BIG" && index == "7") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_h1, R.raw.s_h2, R.raw.s_h3)

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

        // check if the letter is 'i' and capitalization is 'SMALL' and index is 8
        if (letterCapitalization == "SMALL" && index == "8") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_ii1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_ii2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_ii1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'i' and capitalization is 'BIG' and index is 8
        if (letterCapitalization == "BIG" && index == "8") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_i1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_i2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_i1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'j' and capitalization is 'SMALL' and index is 9
        if (letterCapitalization == "SMALL" && index == "9") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_jj1, R.raw.s_jj2, R.raw.s_jj3)

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

        // check if the letter is 'j' and capitalization is 'BIG' and index is 9
        if (letterCapitalization == "BIG" && index == "9") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_j1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_j2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_j1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'k' and capitalization is 'SMALL' and index is 10
        if (letterCapitalization == "SMALL" && index == "10") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_kk1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_kk2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_kk1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'k' and capitalization is 'BIG' and index is 10
        if (letterCapitalization == "BIG" && index == "10") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_k1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_k2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_k1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'l' and capitalization is 'SMALL' and index is 11
        if (letterCapitalization == "SMALL" && index == "11") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_ll1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_ll2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_ll1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'l' and capitalization is 'BIG' and index is 11
        if (letterCapitalization == "BIG" && index == "11") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_l1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_l2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_l1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'm' and capitalization is 'SMALL' and index is 12
        if (letterCapitalization == "SMALL" && index == "12") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_mm1, R.raw.s_mm2, R.raw.s_mm3)

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

        //check if the letter is 'm' and capitalization is 'BIG' and index is 12
        if (letterCapitalization == "BIG" && index == "12") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_m1, R.raw.s_m2, R.raw.s_m3)

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

        // check if the letter is 'n' and capitalization is 'SMALL' and index is 13
        if (letterCapitalization == "SMALL" && index == "13") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_nn1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_nn2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_nn1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'n' and capitalization is 'BIG' and index is 13
        if (letterCapitalization == "BIG" && index == "13") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_n1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_n2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_n1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'ñ' and capitalization is 'SMALL' and index is 14
        if (letterCapitalization == "SMALL" && index == "14") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_enyeenye1, R.raw.s_enyeenye2, R.raw.s_enyeenye3)

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

        //check if the letter is 'ñ' and capitalization is 'BIG' and index is 14
        if (letterCapitalization == "BIG" && index == "14") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_enye1, R.raw.s_enye2, R.raw.s_enye3)

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

        //check if the letter is 'ng' and capitalization is 'SMALL' and index is 15
        if (letterCapitalization == "SMALL" && index == "15") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_ngng1, R.raw.s_ngng2, R.raw.s_ngng3, R.raw.s_ngng4)

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

        //check if the letter is 'ng' and capitalization is 'BIG' and index is 15
        if (letterCapitalization == "BIG" && index == "15") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_ngng1, R.raw.s_ng2, R.raw.s_ng3, R.raw.s_ng4)

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

        // check if the letter is 'o' and capitalization is 'SMALL' and index is 16
        if (letterCapitalization == "SMALL" && index == "16") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_oo1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_oo2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_oo1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'o' and capitalization is 'BIG' and index is 16
        if (letterCapitalization == "BIG" && index == "16") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_o1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_o2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_o1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'p' and capitalization is 'SMALL' and index is 17
        if (letterCapitalization == "SMALL" && index == "17") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_pp1, R.raw.s_pp2, R.raw.s_pp3)

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

        // check if the letter is 'p' and capitalization is 'BIG' and index is 17
        if (letterCapitalization == "BIG" && index == "17") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_p1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_p2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_p1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'q' and capitalization is 'SMALL' and index is 18
        if (letterCapitalization == "SMALL" && index == "18") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_qq1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_qq2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_qq1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'q' and capitalization is 'BIG' and index is 18
        if (letterCapitalization == "BIG" && index == "18") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_q1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_q2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_q1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'r' and capitalization is 'SMALL' and index is 19
        if (letterCapitalization == "SMALL" && index == "19") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_rr1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_rr2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_rr1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'r' and capitalization is 'BIG' and index is 19
        if (letterCapitalization == "BIG" && index == "19") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_r1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_r2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_r1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 's' and capitalization is 'SMALL' and index is 20
        if (letterCapitalization == "SMALL" && index == "20") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_ss1, R.raw.s_ss2, R.raw.s_ss3)

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

        // check if the letter is 's' and capitalization is 'BIG' and index is 20
        if (letterCapitalization == "BIG" && index == "20") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_s1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_s2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_s1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 't' and capitalization is 'SMALL' and index is 21
        if (letterCapitalization == "SMALL" && index == "21") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_tt1, R.raw.s_tt2, R.raw.s_tt3)

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

        //check if the letter is 't' and capitalization is 'BIG' and index is 21
        if (letterCapitalization == "BIG" && index == "21") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_t1, R.raw.s_t2, R.raw.s_t3)

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

        //check if the letter is 'u' and capitalization is 'SMALL' and index is 22
        if (letterCapitalization == "SMALL" && index == "22") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_uu1, R.raw.s_uu2, R.raw.s_uu3)

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

        // check if the letter is 'u' and capitalization is 'BIG' and index is 22
        if (letterCapitalization == "SMALL" && index == "22") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_u1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_u2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_u1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'v' and capitalization is 'SMALL' and index is 23
        if (letterCapitalization == "SMALL" && index == "23") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_vv1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_vv2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_vv1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'v' and capitalization is 'BIG' and index is 23
        if (letterCapitalization == "BIG" && index == "23") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_v1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_v2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_v1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        //check if the letter is 'w' and capitalization is 'SMALL' and index is 24
        if (letterCapitalization == "SMALL" && index == "24") {
            var currentVideoIndex = 0
            val videos = arrayOf(R.raw.s_ww1, R.raw.s_ww2, R.raw.s_ww3)

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

        // check if the letter is 'w' and capitalization is 'BIG' and index is 23
        if (letterCapitalization == "BIG" && index == "24") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_w1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_w2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_w1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'x' and capitalization is 'SMALL' and index is 25
        if (letterCapitalization == "SMALL" && index == "25") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_xx1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_xx2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_xx1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'x' and capitalization is 'BIG' and index is 25
        if (letterCapitalization == "BIG" && index == "25") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_x1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_x2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_x1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'y' and capitalization is 'SMALL' and index is 26
        if (letterCapitalization == "SMALL" && index == "26") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_yy1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_yy2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_yy1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'y' and capitalization is 'BIG' and index is 26
        if (letterCapitalization == "BIG" && index == "26") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_y1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_y2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_y1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'z and capitalization is 'SMALL' and index is 27
        if (letterCapitalization == "SMALL" && index == "27") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_zz1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_zz2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_zz1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
                video.seekTo(0)
                video.start()
            }
        }

        // check if the letter is 'z and capitalization is 'BIG' and index is 27
        if (letterCapitalization == "BIG" && index == "27") {
            // play the first video
            video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_z1)
            video.start()

            // make previous button invisible and next button visible initially
            previous.visibility = View.INVISIBLE
            next.visibility = View.VISIBLE

            // set onclick listener for the next button
            next.setOnClickListener {
                // play the second video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_z2)
                video.start()

                // make previous button visible and next button invisible
                previous.visibility = View.VISIBLE
                next.visibility = View.INVISIBLE
            }

            // set onclick listener for the previous button
            previous.setOnClickListener {
                // play the first video
                video.setVideoPath("android.resource://" + packageName + "/" + R.raw.s_zz1)
                video.start()

                // make previous button invisible and next button visible
                previous.visibility = View.INVISIBLE
                next.visibility = View.VISIBLE
            }

            // listener to the video view to continuously play the video
            video.setOnCompletionListener {
                // restart the video from the beginning
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







