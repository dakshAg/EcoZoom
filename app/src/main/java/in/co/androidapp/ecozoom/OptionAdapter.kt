package `in`.co.androidapp.ecozoom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import coil.load

class OptionAdapter(private val listt: List<Option>, val navController: NavController) :
    RecyclerView.Adapter<OptionAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img = itemView.findViewById<ImageView>(R.id.img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.row_option, parent, false)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = listt[position]
        holder.img.load(current.image)
        holder.img.setOnClickListener {
            navController.navigate(
                OptionsFragmentDirections.actionOptionsFragmentToReportFragment(
                    current.product
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return listt.size
    }

}