package com.example.trygame

import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.trygame.databinding.FragmentGameBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameFragment : Fragment() {
   private var _binding: FragmentGameBinding? = null
   private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("GameFragment == null")
    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.view1)
            add(binding.view2)
            add(binding.view3)
            add(binding.view4)
            add(binding.view5)
            add(binding.view6)
            add(binding.view7)
            add(binding.view8)
            add(binding.view9)
            add(binding.view10)
            add(binding.view11)
            add(binding.view12)
            add(binding.view13)
            add(binding.view14)
            add(binding.view15)
            add(binding.view16)
            add(binding.view17)
            add(binding.view18)
            add(binding.view19)
            add(binding.view20)
        }
    }
    private var listOk = listOf<String>()
    private var count = 0
    private var stepCount = 1
    private var levelCount = 7
    private var score = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater,container,false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickView(false)
        showButtons(false)
        with(binding){
            textView.text = getString(R.string.start)
            imageView.setOnClickListener {
                lifecycleScope.launch {

                    when (textView.text) {
                        getString(R.string.start) -> listOk = loadNum(tvOptions, levelCount)
                        getString(R.string.win) -> listOk = loadNum(tvOptions, ++levelCount)
                        getString(R.string.lost) -> {
                            levelCount = 7
                            score = 0
                            textViewLevel.text = score.toString()
                            listOk = loadNum(tvOptions, levelCount)
                            textView.text = ""
                        }

                    }


                }

            }

            imageView2.setOnClickListener {
                setText(listOk, tvOptions, levelCount)

            }

                refresh.setOnClickListener {
                    lifecycleScope.launch {
                        listOk = loadNum(tvOptions, levelCount)
                        showButtons(false)
                    }

                }
            }
        }



    private fun setText(textList: List<String>, viewList: List<TextView>, levelCount: Int) {
        for (i in 0 until levelCount - 1) {
            viewList[textList[i].toInt()].text = (i + 1).toString()
            viewList[textList[i].toInt()].setTextColor(Color.BLUE)
        }
    }

    private suspend fun loadNum(
        viewList: MutableList<TextView>,
        levelCount: Int
    ): MutableList<String> {
        binding.textView.text = ""
       showButtons(false)
        val listOfIndex =
            mutableListOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 18, 19, 20)
        listOfIndex.shuffle()
        count = 0
        stepCount = 1
        val list = mutableListOf<String>()
        for (view in viewList) {
            view.text = ""
        }

        for (i in 0 until levelCount - 1) {
            delay(1000)
            viewList[listOfIndex[i] - 1].text = (i + 1).toString()
            viewList[listOfIndex[i] - 1].setTextColor(Color.BLUE)
            list.add((listOfIndex[i] - 1).toString())


        }
        delay(3000)
        for (i in viewList.indices) {
            viewList[i].setTextColor(Color.TRANSPARENT)

        }

        touch(tvOptions)
        clickView(true)
        return list
    }

    private fun touch(list: List<TextView>) {

        for (view in list) {

            view.setOnClickListener {
                if (view.text == "" || view.text != stepCount.toString()) {
                    count++
                    mPlayer(R.raw.error)
                    view.setBackgroundColor(resources.getColor(R.color.red))
                    when (count) {

                        1 -> binding.textView.text = getString(R.string.attempt2)
                        2 -> binding.textView.text = getString(R.string.attempt1)
                        3 -> {
                            clickView(false)
                            showButtons(true)
                            binding.textView.text = getString(R.string.lost)
                            Log.d("STEPCOUNT", "$stepCount, $levelCount")
                        }
                    }
                } else {
                    view.setTextColor(Color.BLUE)
                    view.setBackgroundColor(resources.getColor(R.color.white))
                    score += 10
                    mPlayer(R.raw.truer)
                    binding.textViewLevel.text = score.toString()
                    stepCount++
                    checkGame(stepCount, levelCount)

                }

            }
        }

    }

    private fun checkGame(stepCount: Int, levelCount: Int) {
        if (stepCount == levelCount) {
            binding.textView.text = getString(R.string.win)
            clickView(false)

        }
    }

    private fun mPlayer(source: Int) {
        val media = MediaPlayer.create(activity, source).apply {
            start()
        }
        media.setOnCompletionListener {
            media.release()
        }
    }

    private fun clickView(state: Boolean) {

        for (view in tvOptions) {
            view.setBackgroundColor(resources.getColor(R.color.white))
            view.isClickable = state
        }

    }
    private fun showButtons(state: Boolean) {
        binding.refresh.isVisible = state
        binding.imageView2.isVisible = state


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}