package com.example.a8puzzlesolver

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.a8puzzlesolver.databinding.ActivityStateInitBinding

class stateInitActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStateInitBinding
    private lateinit var initialStateArray: ArrayList<Int>
    private lateinit var goalStateArray: ArrayList<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStateInitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSolve.setOnClickListener{
            if(validateTable()){
//                Toast.makeText(this, "Valid", Toast.LENGTH_SHORT).show()
            }
            else
            {
//                Toast.makeText(this, "Not Valid", Toast.LENGTH_SHORT).show()
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Invalid state")
                builder.setMessage("Invalid State!! Please Check it out.")
                builder.setPositiveButton("OK") { dialog, which ->
                    // Do nothing, just close the dialog
                }
                builder.show()
            }
        }
    }

    private fun validateTable():Boolean{

        if(binding.init00.text.toString()!="")
        {
            initialStateArray.add(binding.init00.text.toString().toInt())
        }
        else return false

        if(binding.init01.text.toString()!="")
        {
            initialStateArray.add(binding.init01.text.toString().toInt())
        }
        else return false

        if(binding.init02.text.toString()!="")
        {
            initialStateArray.add(binding.init02.text.toString().toInt())
        }
        else return false

        if(binding.init10.text.toString()!="")
        {
            initialStateArray.add(binding.init10.text.toString().toInt())
        }
        else return false

        if(binding.init11.text.toString()!="")
        {
            initialStateArray.add(binding.init11.text.toString().toInt())
        }
        else return false

        if(binding.init12.text.toString()!="")
        {
            initialStateArray.add(binding.init12.text.toString().toInt())
        }
        else return false

        if(binding.init20.text.toString()!="")
        {
            initialStateArray.add(binding.init20.text.toString().toInt())
        }
        else return false

        if(binding.init21.text.toString()!="")
        {
            initialStateArray.add(binding.init21.text.toString().toInt())
        }
        else return false

        if(binding.init22.text.toString()!="")
        {
            initialStateArray.add(binding.init22.text.toString().toInt())
        }
        else return false

        //goal
        if(binding.goal00.text.toString()!="")
        {
            goalStateArray.add(binding.init00.text.toString().toInt())
        }
        else return false

        if(binding.goal01.text.toString()!="")
        {
            goalStateArray.add(binding.init01.text.toString().toInt())
        }
        else return false

        if(binding.goal02.text.toString()!="")
        {
            goalStateArray.add(binding.init02.text.toString().toInt())
        }
        else return false

        if(binding.goal10.text.toString()!="")
        {
            goalStateArray.add(binding.init10.text.toString().toInt())
        }
        else return false

        if(binding.goal11.text.toString()!="")
        {
            goalStateArray.add(binding.init11.text.toString().toInt())
        }
        else return false

        if(binding.goal12.text.toString()!="")
        {
            goalStateArray.add(binding.init12.text.toString().toInt())
        }
        else return false

        if(binding.goal20.text.toString()!="")
        {
            goalStateArray.add(binding.init20.text.toString().toInt())
        }
        else return false

        if(binding.goal21.text.toString()!="")
        {
            goalStateArray.add(binding.init21.text.toString().toInt())
        }
        else return false

        if(binding.goal22.text.toString()!="")
        {
            goalStateArray.add(binding.init22.text.toString().toInt())
        }
        else return false

        if((initialStateArray.size != initialStateArray.distinct().size) or (goalStateArray.size != goalStateArray.distinct().size))return false

        //todo : validate the state such that it won't go in infinite loop.
        return true
    }

}