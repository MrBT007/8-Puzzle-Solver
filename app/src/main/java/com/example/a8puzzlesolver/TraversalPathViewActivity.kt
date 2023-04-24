package com.example.a8puzzlesolver

import Adapters.PuzzleAdapter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.a8puzzlesolver.Azure.ApiClient
import com.example.a8puzzlesolver.Azure.AzureFunctionApi
import com.example.a8puzzlesolver.databinding.ActivityTraversalPathViewBinding
import retrofit2.Response

class TraversalPathViewActivity : BaseActivity() {

    private lateinit var binding: ActivityTraversalPathViewBinding
    lateinit var initialState: ArrayList<String>
    lateinit var goalState: ArrayList<String>
    lateinit var traversalPath: List<PuzzleState>
    var algoNumber = 0
    /*
        1 -> dfs
        2 -> bfs
        3 -> astar
        4 -> hill climbing
     */
    val algosWithIndex = listOf("DFS","BFS","ASTAR","HILL CLIMBING")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTraversalPathViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        traversalPath = listOf()
        supportActionBar?.title = "Traversal Path"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.title_bar)))

        initialState = intent.getStringArrayListExtra("initState")!!
        goalState = intent.getStringArrayListExtra("goalState")!!
        algoNumber = intent.getIntExtra("algo",3)

        val dataToSend = AzureFunctionApi.RequestBody(initialState, goalState,algoNumber)
        val apiService = ApiClient.instance
        val functionCode = "ZIzl3WnP-hZD0_iJCoYB4s1oY_5WlAeBFSwb7OSOwAEvAzFu5T_vvA=="

        showProgressDialog("Please Wait...")
        apiService.compute(dataToSend, functionCode)
            .enqueue(object : retrofit2.Callback<AzureFunctionApi.ResponseBody> {
                override fun onResponse(
                    call: retrofit2.Call<AzureFunctionApi.ResponseBody>,
                    response: Response<AzureFunctionApi.ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null && responseBody.success) {
                            val path = responseBody.path
                            showSnackBar("Solved with ${algosWithIndex[algoNumber-1]}",R.color.colorSnackBarSuccess)
                            traversalPath = convertToPuzzleState(path)

                            showPath(traversalPath)

                        } else {
                            showSnackBar("Error : ${responseBody?.message}",R.color.colorSnackBarError)

                            hideProgressDialog()
                        }
                    } else {
                        // if not get response
                        showSnackBar("No response from cloud : ${response.message()}",R.color.colorSnackBarError)
                        hideProgressDialog()
                    }
                }


                override fun onFailure(
                    call: retrofit2.Call<AzureFunctionApi.ResponseBody>,
                    t: Throwable
                ) {
                    // HTTP error
                    showSnackBar("Error Unsuccessful : ${t.message}",R.color.colorSnackBarError)

                    hideProgressDialog()
                    Log.e("On Failure", "$t" )
                }
            })
    }

    private fun showPath(traversalPath: List<PuzzleState>) {
        hideProgressDialog()
        val layoutManager = GridLayoutManager(this@TraversalPathViewActivity, 1)
        binding.puzzleRecyclerView.layoutManager = layoutManager
        binding.puzzleRecyclerView.adapter = PuzzleAdapter(traversalPath)
    }

    private fun convertToPuzzleState(path: List<List<List<Int>>>?): List<PuzzleState> {
        var convertedTraversalPath = listOf<PuzzleState>()
        for (state in path!!) {
            val board = Array(state.size) { Array(state[0].size) { 0 } }

            for (i in state.indices) {
                for (j in state[i].indices) {
                    board[i][j] = state[i][j]
                }
            }
            convertedTraversalPath += (PuzzleState(board))
        }
        return convertedTraversalPath
    }
}
