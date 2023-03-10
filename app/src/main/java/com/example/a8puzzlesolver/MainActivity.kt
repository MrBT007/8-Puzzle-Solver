package com.example.a8puzzlesolver

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.a8puzzlesolver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var buttons: List<List<Button>>
    private lateinit var binding: ActivityMainBinding
    var emptyButtonIndex_i: Int = 2
    var emptyButtonIndex_j: Int = 2
    private lateinit var popupWindow: PopupWindow
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Solve 8 Puzzle"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.title_bar)))

        textView = findViewById(R.id.i_goal_state_text)

        //showing text for viewing goal state
        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                // Show the textview with fade-in animation
                textView.visibility = View.VISIBLE
                textView.startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.i_button_fade_in))

                Handler().postDelayed({
                    textView.visibility = View.GONE
                }, 5000)

                // Repeat the animation after 15 seconds
                handler.postDelayed(this, 15000)
            }
        }
        handler.post(runnable)


        //listing all buttons
        buttons = listOf(
            listOf(
                findViewById(R.id.button1),
                findViewById(R.id.button2),
                findViewById(R.id.button3)
            ),
            listOf(
                findViewById(R.id.button4),
                findViewById(R.id.button5),
                findViewById(R.id.button6)
            ),
            listOf(
                findViewById(R.id.button7),
                findViewById(R.id.button8),
                findViewById(R.id.button9)
            )
        )

        val initState = intent.getStringArrayListExtra("initState")
        val goalState = intent.getStringArrayListExtra("goalState")

        // getting index of empty tile
        var index = 0
        for (i in 0..2) {
            for (j in 0..2) {
               //Log.e("Value at $i,$j", "${initState?.get(index)}", )
                buttons[i][j].text = initState!![index].toString()
                if (buttons[i][j].text.toString() == "0") {
                    emptyButtonIndex_i = i
                    emptyButtonIndex_j = j
                }
                index++
            }
        }

        //initialize pop up view
        val popupView = LayoutInflater.from(this).inflate(R.layout.activity_goal_state_popup, null)
        popupView.findViewById<TextView>(R.id.popup_goal00).text = goalState?.get(0).toString()
        popupView.findViewById<TextView>(R.id.popup_goal01).text = goalState?.get(1).toString()
        popupView.findViewById<TextView>(R.id.popup_goal02).text = goalState?.get(2).toString()
        popupView.findViewById<TextView>(R.id.popup_goal10).text = goalState?.get(3).toString()
        popupView.findViewById<TextView>(R.id.popup_goal11).text = goalState?.get(4).toString()
        popupView.findViewById<TextView>(R.id.popup_goal12).text = goalState?.get(5).toString()
        popupView.findViewById<TextView>(R.id.popup_goal20).text = goalState?.get(6).toString()
        popupView.findViewById<TextView>(R.id.popup_goal21).text = goalState?.get(7).toString()
        popupView.findViewById<TextView>(R.id.popup_goal22).text = goalState?.get(8).toString()
        popupWindow = PopupWindow(popupView, 800, 800)
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindow.animationStyle = R.style.PopupAnimation

        val closeButton = popupView.findViewById<Button>(R.id.close_button)
        closeButton.setOnClickListener {
            popupWindow.dismiss()
        }

        popupView.findViewById<Button>(R.id.close_button).setOnClickListener {
            popupWindow.dismiss()
        }
        binding.iButton.setOnClickListener{
            popupWindow.showAtLocation(binding.iButton, Gravity.CENTER, 0, 0)
        }

        buttons[emptyButtonIndex_i][emptyButtonIndex_j].setBackgroundColor(Color.parseColor("#5a5a5a"))
        buttons.forEachIndexed { i, row ->
            row.forEachIndexed { j, button ->
                button.setOnClickListener {
//                    Toast.makeText(this, "Button at ($i, $j) clicked", Toast.LENGTH_SHORT).show()
                    swapButtons(i, j)
                }
            }
        }
    }

    private fun swapButtons(i: Int, j: Int) {
        val clickedButton = buttons[i][j]
        val emptyButton = buttons[emptyButtonIndex_i][emptyButtonIndex_j]

        // Check if the clicked button is adjacent to the empty button (not diagonally)
        if ((i == emptyButtonIndex_i && j == emptyButtonIndex_j - 1) // Left
            || (i == emptyButtonIndex_i && j == emptyButtonIndex_j + 1) // Right
            || (j == emptyButtonIndex_j && i == emptyButtonIndex_i - 1) // Up
            || (j == emptyButtonIndex_j && i == emptyButtonIndex_i + 1) // Down
        ) {
            // Swap the text of the clicked button and the empty button
            val temp = clickedButton.text.toString()
            clickedButton.text = emptyButton.text.toString()
            emptyButton.text = temp

            // Update the indices of the empty button
            emptyButtonIndex_i = i
            emptyButtonIndex_j = j

            // Update the background color of the buttons to indicate the swap
            clickedButton.setBackgroundColor(Color.parseColor("#5a5a5a"))
            emptyButton.setBackgroundColor(Color.parseColor("#f0f0f0"))
        }
    }
}