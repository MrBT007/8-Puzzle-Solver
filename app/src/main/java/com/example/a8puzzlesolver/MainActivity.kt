package com.example.a8puzzlesolver

import android.animation.ObjectAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import java.util.*
import java.util.Collections.swap
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    lateinit var buttons: List<List<Button>>
    var emptyButtonIndex_i: Int = 2
    var emptyButtonIndex_j: Int = 2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        buttons = listOf(
            listOf(findViewById(R.id.button1),
            findViewById(R.id.button2),
            findViewById(R.id.button3)),
            listOf(findViewById(R.id.button4),
            findViewById(R.id.button5),
            findViewById(R.id.button6)),
            listOf(findViewById(R.id.button7),
            findViewById(R.id.button8),
            findViewById(R.id.button9))
        )

        buttons.forEachIndexed { i, row ->
            row.forEachIndexed { j, button ->
                button.setOnClickListener {
//                    Toast.makeText(this, "Button at ($i, $j) clicked", Toast.LENGTH_SHORT).show()
                    swapButtons(i, j)
                }
            }
        }
    }

//    private fun swapButtons(i: Int, j: Int) {
//        val buttonToMove = buttons[i][j]
//        val emptyButton = buttons[emptyButtonIndex_i][emptyButtonIndex_j]
//
//        // Check if the clicked button is adjacent to the empty button (not diagonally)
//        var isAdjacent = false
//        if (i - 1 >= 0 && buttons[i - 1][j].text.toString() == "") {
//            isAdjacent = true
//            emptyButtonIndex_i = i - 1
//            emptyButtonIndex_j = j
//        } else if (i + 1 < 3 && buttons[i + 1][j].text.toString() == "") {
//            isAdjacent = true
//            emptyButtonIndex_i = i + 1
//            emptyButtonIndex_j = j
//        } else if (j - 1 >= 0 && buttons[i][j - 1].text.toString() == "") {
//            isAdjacent = true
//            emptyButtonIndex_i = i
//            emptyButtonIndex_j = j - 1
//        } else if (j + 1 < 3 && buttons[i][j + 1].text.toString() == "") {
//            isAdjacent = true
//            emptyButtonIndex_i = i
//            emptyButtonIndex_j = j + 1
//        }
//
//        if (isAdjacent) {
//            // Animate the movement of the buttons using ObjectAnimator
//            val xDiff = emptyButton.x - buttonToMove.x
//            val yDiff = emptyButton.y - buttonToMove.y
//
//            ObjectAnimator.ofFloat(buttonToMove, View.TRANSLATION_X, xDiff)
//                .apply { duration = 200 }
//                .start()
//            ObjectAnimator.ofFloat(buttonToMove, View.TRANSLATION_Y, yDiff)
//                .apply { duration = 200 }
//                .start()
//            ObjectAnimator.ofFloat(emptyButton, View.TRANSLATION_X, -xDiff)
//                .apply { duration = 200 }
//                .start()
//            ObjectAnimator.ofFloat(emptyButton, View.TRANSLATION_Y, -yDiff)
//                .apply { duration = 200 }
//                .start()
//
//            // Swap the buttons in the 2D list
////            val temp = buttons[i][j]
////            buttons[i][j] = buttons[emptyButtonIndex_i][emptyButtonIndex_j]
////            buttons[emptyButtonIndex_i][emptyButtonIndex_j] = temp
//            buttons[emptyButtonIndex_i][emptyButtonIndex_j].text = buttons[i][j].text
//            buttons[i][j].text = ""
//            for(i in buttons.indices){
//                for(j in buttons[i].indices){
//                    Log.e("Button $i,$j", "${buttons[i][j].text}")
//                }
//            }
//        }
//    }
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