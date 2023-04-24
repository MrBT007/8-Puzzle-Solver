package com.example.a8puzzlesolver

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import com.example.a8puzzlesolver.databinding.ActivityMainBinding


class MainActivity : BaseActivity() {
    lateinit var buttons: List<List<Button>>
    lateinit var initialState:ArrayList<String>
    lateinit var goalState:ArrayList<String>
    private lateinit var binding: ActivityMainBinding
    var emptyButtonIndex_i: Int = 2
    var emptyButtonIndex_j: Int = 2
    private lateinit var popupWindowGoalState: PopupWindow
    private lateinit var popupWindowAlgos: PopupWindow
    private lateinit var popupWindowCongratulation: PopupWindow
    private lateinit var textView: TextView
    private lateinit var mediaPlayer:MediaPlayer
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
                textView.startAnimation(
                    AnimationUtils.loadAnimation(
                        this@MainActivity,
                        R.anim.i_button_fade_in
                    )
                )

                Handler().postDelayed({
                    textView.visibility = View.GONE
                }, 5000)

                // Repeat the animation after 15 seconds
                handler.postDelayed(this, 15000)
            }
        }
        handler.post(runnable)

        mediaPlayer = MediaPlayer.create(this,R.raw.success1)

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

        initialState = intent.getStringArrayListExtra("initState")!!
        goalState = intent.getStringArrayListExtra("goalState")!!

        // getting index of empty tile
        var index = 0
        for (i in 0..2) {
            for (j in 0..2) {
                //Log.e("Value at $i,$j", "${initState?.get(index)}", )
                buttons[i][j].text = initialState!![index].toString()
                if (buttons[i][j].text.toString() == "0") {
                    emptyButtonIndex_i = i
                    emptyButtonIndex_j = j
                }
                index++
            }
        }

        //initialize pop up view
        val popupViewGoalState = LayoutInflater.from(this).inflate(R.layout.activity_goal_state_popup, null)
        val popupViewAlgos = LayoutInflater.from(this).inflate(R.layout.searching_algo_popup, null)
        val popupViewCongratulation = LayoutInflater.from(this).inflate(R.layout.congratulations_popup,null)

        popupViewGoalState.findViewById<TextView>(R.id.popup_goal00).text = goalState?.get(0).toString()
        popupViewGoalState.findViewById<TextView>(R.id.popup_goal01).text = goalState?.get(1).toString()
        popupViewGoalState.findViewById<TextView>(R.id.popup_goal02).text = goalState?.get(2).toString()
        popupViewGoalState.findViewById<TextView>(R.id.popup_goal10).text = goalState?.get(3).toString()
        popupViewGoalState.findViewById<TextView>(R.id.popup_goal11).text = goalState?.get(4).toString()
        popupViewGoalState.findViewById<TextView>(R.id.popup_goal12).text = goalState?.get(5).toString()
        popupViewGoalState.findViewById<TextView>(R.id.popup_goal20).text = goalState?.get(6).toString()
        popupViewGoalState.findViewById<TextView>(R.id.popup_goal21).text = goalState?.get(7).toString()
        popupViewGoalState.findViewById<TextView>(R.id.popup_goal22).text = goalState?.get(8).toString()

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = (displayMetrics.heightPixels * 0.4).toInt() // Set the height to 80% of the screen height

        val width = (displayMetrics.widthPixels * 0.8).toInt() // Set the width to 80% of the screen width

//        var popupWindow = PopupWindow(popupView, width, height)


        popupWindowGoalState = PopupWindow(popupViewGoalState, width, ViewGroup.LayoutParams.WRAP_CONTENT)
        popupWindowAlgos = PopupWindow(popupViewAlgos,width,ViewGroup.LayoutParams.WRAP_CONTENT)
//        popupWindowGoalState.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        popupWindowAlgos.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        popupWindowGoalState.animationStyle = R.style.PopupAnimation
        popupWindowAlgos.animationStyle = R.style.PopupAnimation
        popupWindowCongratulation = PopupWindow(popupViewCongratulation,width,ViewGroup.LayoutParams.WRAP_CONTENT)
//        popupViewCongratulation.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val closeButtonGoalSate = popupViewGoalState.findViewById<Button>(R.id.close_button_goal_state)
        val closeButtonSearchingAlgo = popupViewAlgos.findViewById<ImageButton>(R.id.closeButtonSearchingAlgosPopup)
        val closeButtonCongratulationPopup = popupViewCongratulation.findViewById<Button>(R.id.btn_close_congratulation_popup)
        val buttonNewPuzzleCongratulationPopup = popupViewCongratulation.findViewById<Button>(R.id.btn_new_puzzle_congratulation_popup)
        val dfsButton = popupViewAlgos.findViewById<Button>(R.id.dfs_button)
        val bfsButton = popupViewAlgos.findViewById<Button>(R.id.bfs_button)
        val aStarButton = popupViewAlgos.findViewById<Button>(R.id.a_star_button)
        val hillClimbingButton = popupViewAlgos.findViewById<Button>(R.id.hill_climbing_button)

        //sample to show the traversal path
        dfsButton.setOnClickListener{
            val intent = Intent(this, TraversalPathViewActivity::class.java)
            intent.putStringArrayListExtra("initState", initialState)
            intent.putStringArrayListExtra("goalState", goalState)
            intent.putExtra("algo",1)
            startActivity(intent)
        }
        bfsButton.setOnClickListener{
            val intent = Intent(this, TraversalPathViewActivity::class.java)
            intent.putStringArrayListExtra("initState", initialState)
            intent.putStringArrayListExtra("goalState", goalState)
            intent.putExtra("algo",2)
            startActivity(intent)
        }
        aStarButton.setOnClickListener{
            val intent = Intent(this, TraversalPathViewActivity::class.java)
            intent.putStringArrayListExtra("initState", initialState)
            intent.putStringArrayListExtra("goalState", goalState)
            intent.putExtra("algo",3)
            startActivity(intent)
        }
        hillClimbingButton.setOnClickListener{
            val intent = Intent(this, TraversalPathViewActivity::class.java)
            intent.putStringArrayListExtra("initState", initialState)
            intent.putStringArrayListExtra("goalState", goalState)
            intent.putExtra("algo",4)
            startActivity(intent)
        }

        closeButtonGoalSate.setOnClickListener {
            popupWindowGoalState.dismiss()
        }

        closeButtonSearchingAlgo.setOnClickListener {
            popupWindowAlgos.dismiss()
        }

        closeButtonCongratulationPopup.setOnClickListener{
            popupWindowCongratulation.dismiss()
        }

        //create new puzzle from pop up
        buttonNewPuzzleCongratulationPopup.setOnClickListener {
            val intent = Intent(this, stateInitActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        binding.iButton.setOnClickListener {
            popupWindowGoalState.showAtLocation(binding.iButton, Gravity.CENTER, 0, 0)
        }

        binding.buttonSolveAI.setOnClickListener{
            popupWindowAlgos.showAtLocation(binding.buttonSolveAI,Gravity.CENTER,0,0)
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

    private fun isSolved(): Boolean {
        for (i in 0 until buttons.size) {
            for (j in 0 until buttons[i].size) {
                val buttonText = buttons[i][j].text.toString()
                if (buttonText != goalState!![i * buttons.size + j]) {
                    return false
                }
            }
        }
        return true
    }

    private fun solvedSuccess() {
        val anim = binding.congratulationAnimationView

        Handler(Looper.getMainLooper()).postDelayed(Runnable{
            anim.visibility = View.VISIBLE
            anim.playAnimation()
        },200)
        popupWindowCongratulation.showAtLocation(binding.root, Gravity.CENTER,0,0)
        mediaPlayer.start()
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

            if(isSolved())
            {
                // if solved pop up the congratulations animation
                solvedSuccess()
            }
        }
    }
}