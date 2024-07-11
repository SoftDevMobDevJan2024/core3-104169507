package au.edu.swin.sdmd.myapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import au.edu.swin.sdmd.myapp.model.Group

class RecyclerListAdapter(private var data: List<Group>) : RecyclerView.Adapter<RecyclerListAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.name)
        val datetime: TextView = itemView.findViewById(R.id.datetime)
        val location: TextView = itemView.findViewById(R.id.location)
        val icon: ImageView = itemView.findViewById(R.id.icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = data[position]
        holder.name.text = currentItem.name
        holder.datetime.text = currentItem.dateTime.toString()
        holder.location.text = currentItem.location
        if (currentItem.location == "Online") {
            holder.icon.setImageResource(R.drawable.online)
        } else {
            holder.icon.setImageResource(R.drawable.offline)
        }
        when (currentItem.type) {
            "Cultural" -> holder.name.setTextColor(Color.RED)
            "Tech" -> holder.name.setTextColor(Color.GREEN)
            "Politics" -> holder.name.setTextColor(Color.BLUE)
            "Sport" -> holder.name.setTextColor(Color.YELLOW)
        }
    }

    fun updateData(newData: List<Group>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return data.size
            }

            override fun getNewListSize(): Int {
                return newData.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return data[oldItemPosition] == newData[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return data[oldItemPosition] == newData[newItemPosition]
            }
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }
}