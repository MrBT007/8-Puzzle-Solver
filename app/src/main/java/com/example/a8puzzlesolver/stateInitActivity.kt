package com.example.a8puzzlesolver

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.set
import com.example.a8puzzlesolver.databinding.ActivityStateInitBinding
import java.util.*
import kotlin.collections.ArrayList

class stateInitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStateInitBinding
    private lateinit var initialStateArray: ArrayList<String>
    private lateinit var goalStateArray: ArrayList<String>

    //    private lateinit var randomStatesArray: ArrayList<ArrayList<Int>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStateInitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Initialize States"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.title_bar)))
        initialStateArray = ArrayList()
        goalStateArray = ArrayList()

        val randomStatesArray = listOf(
            listOf(2, 8, 3, 1, 6, 4, 7, 5, 0),
            listOf(4, 6, 1, 5, 8, 2, 7, 3, 0),
            listOf(1, 4, 2, 3, 0, 5, 6, 7, 8),
            listOf(7, 2, 4, 1, 5, 0, 3, 8, 6),
            listOf(8, 2, 3, 7, 1, 6, 0, 5, 4),
            listOf(1, 2, 3, 0, 4, 6, 7, 5, 8)
        )

        binding.buttonRandom.setOnClickListener {
            val randomState = randomStatesArray.random()
//            Toast.makeText(this, randomState[0].toString(), Toast.LENGTH_SHORT).show()
            binding.init00.setText(randomState[0].toString())
            binding.init01.setText(randomState[1].toString())
            binding.init02.setText(randomState[2].toString())
            binding.init10.setText(randomState[3].toString())
            binding.init11.setText(randomState[4].toString())
            binding.init12.setText(randomState[5].toString())
            binding.init20.setText(randomState[6].toString())
            binding.init21.setText(randomState[7].toString())
            binding.init22.setText(randomState[8].toString())

            binding.goal00.setText("1")
            binding.goal01.setText("2")
            binding.goal02.setText("3")
            binding.goal10.setText("4")
            binding.goal11.setText("5")
            binding.goal12.setText("6")
            binding.goal20.setText("7")
            binding.goal21.setText("8")
            binding.goal22.setText("0")
        }

        binding.buttonClearInitState.setOnClickListener {
            binding.init00.setText("")
            binding.init01.setText("")
            binding.init02.setText("")
            binding.init10.setText("")
            binding.init11.setText("")
            binding.init12.setText("")
            binding.init20.setText("")
            binding.init21.setText("")
            binding.init22.setText("")
        }
        binding.buttonClearGoalState.setOnClickListener {
            binding.goal00.setText("")
            binding.goal01.setText("")
            binding.goal02.setText("")
            binding.goal10.setText("")
            binding.goal11.setText("")
            binding.goal12.setText("")
            binding.goal20.setText("")
            binding.goal21.setText("")
            binding.goal22.setText("")
        }
        binding.buttonSolve.setOnClickListener {

            // stores element. need to delete previous elements before adding new elements.
            initialStateArray.clear()
            goalStateArray.clear()
            if (!checkIfTilesAreEmpty()) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Tiles are empty")
                builder.setMessage("Some of tiles are empty!! Please Check it out.")
                builder.setPositiveButton("OK") { dialog, which ->
                    // Do nothing, just close the dialog
                }
                builder.show()
            } else if (hasDuplicate()) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Contains Duplicate")
                builder.setMessage("Some of tiles are duplicate!! Please Check it out.")
                builder.setPositiveButton("OK") { dialog, which ->
                    // Do nothing, just close the dialog
                }
                builder.show()
            } else if (!isSolvable(initialStateArray, goalStateArray)) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("State is Invalid")
                builder.setMessage("Given states are unsolvable!!")
                builder.setPositiveButton("OK") { dialog, which ->
                    // Do nothing, just close the dialog
                }
                builder.show()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                intent.putStringArrayListExtra("initState", initialStateArray)
                intent.putStringArrayListExtra("goalState", goalStateArray)

                startActivity(intent)
            }
        }
    }

    private fun checkIfTilesAreEmpty(): Boolean {

        if (binding.init00.text.toString() != "") {
            initialStateArray.add(binding.init00.text.toString())
        } else return false

        if (binding.init01.text.toString() != "") {
            initialStateArray.add(binding.init01.text.toString())
        } else return false

        if (binding.init02.text.toString() != "") {
            initialStateArray.add(binding.init02.text.toString())
        } else return false

        if (binding.init10.text.toString() != "") {
            initialStateArray.add(binding.init10.text.toString())
        } else return false

        if (binding.init11.text.toString() != "") {
            initialStateArray.add(binding.init11.text.toString())
        } else return false

        if (binding.init12.text.toString() != "") {
            initialStateArray.add(binding.init12.text.toString())
        } else return false

        if (binding.init20.text.toString() != "") {
            initialStateArray.add(binding.init20.text.toString())
        } else return false

        if (binding.init21.text.toString() != "") {
            initialStateArray.add(binding.init21.text.toString())
        } else return false

        if (binding.init22.text.toString() != "") {
            initialStateArray.add(binding.init22.text.toString())
        } else return false

        //goal
        if (binding.goal00.text.toString() != "") {
            goalStateArray.add(binding.goal00.text.toString())
        } else return false

        if (binding.goal01.text.toString() != "") {
            goalStateArray.add(binding.goal01.text.toString())
        } else return false

        if (binding.goal02.text.toString() != "") {
            goalStateArray.add(binding.goal02.text.toString())
        } else return false

        if (binding.goal10.text.toString() != "") {
            goalStateArray.add(binding.goal10.text.toString())
        } else return false

        if (binding.goal11.text.toString() != "") {
            goalStateArray.add(binding.goal11.text.toString())
        } else return false

        if (binding.goal12.text.toString() != "") {
            goalStateArray.add(binding.goal12.text.toString())
        } else return false

        if (binding.goal20.text.toString() != "") {
            goalStateArray.add(binding.goal20.text.toString())
        } else return false

        if (binding.goal21.text.toString() != "") {
            goalStateArray.add(binding.goal21.text.toString())
        } else return false

        if (binding.goal22.text.toString() != "") {
            goalStateArray.add(binding.goal22.text.toString())
        } else return false
        return true
    }

    private fun hasDuplicate(): Boolean {
        if ((initialStateArray.size != initialStateArray.distinct().size) or (goalStateArray.size != goalStateArray.distinct().size)) return true
        return false
    }

    private fun isSolvable(
        initialStateArray: ArrayList<String>,
        goalStateArray: ArrayList<String>
    ): Boolean {
        val initialState = stringArrayToIntArray(initialStateArray)
        val goalState = stringArrayToIntArray(goalStateArray)
        var initialStateInversionCount = countInversions(initialState)
        var goalStateInversionCount = countInversions(goalState)

        Log.i("StateInit", "Init State Inversion: $initialStateInversionCount")
        Log.i("StateInit", "Goal State Inversion: $goalStateInversionCount")

        // Check if the parity of the inversion counts is the same
        return (initialStateInversionCount % 2 == 0 && goalStateInversionCount % 2 == 0) ||
                (initialStateInversionCount % 2 == 1 && goalStateInversionCount % 2 == 1)
    }


    private fun countInversions(state: IntArray): Int {
        var count = 0
        for (i in 0 until state.size - 1) {
            for (j in i + 1 until state.size) {
                if (state[i] != 0 && state[j] != 0 && state[i] > state[j]) {
                    count++
                }
            }
        }
        return count
    }

    private fun stringArrayToIntArray(stringArray: ArrayList<String>): IntArray {
        return stringArray.flatMap { it.split(" ") }.filter { it.isNotEmpty() }.map { it.toInt() }
            .toIntArray()
    }
}