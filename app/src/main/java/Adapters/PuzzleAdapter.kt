package Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a8puzzlesolver.PuzzleState
import com.example.a8puzzlesolver.R

class PuzzleAdapter(private val states: List<PuzzleState>) : RecyclerView.Adapter<PuzzleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_puzzle_state, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val state = states[position]
        holder.bind(state)
    }

    override fun getItemCount() = states.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(state: PuzzleState) {
            val puzzleLayout = itemView.findViewById<GridLayout>(R.id.puzzle_layout)
            for (i in 0 until puzzleLayout.childCount) {
                val tile = puzzleLayout.getChildAt(i) as TextView
                val row = i / 3
                val col = i % 3
                val value = state.state[row][col]
                tile.text = value.toString()

                if (value == 0) {
                    tile.setBackgroundColor(itemView.context.getColor(R.color.colorSnackBarSuccess))
                }
                else
                {
                    tile.setBackgroundColor(itemView.context.getColor(R.color.offWhite))
                }
            }
        }
    }
}
