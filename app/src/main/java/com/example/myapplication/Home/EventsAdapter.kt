package com.example.myapplication.Home
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.EventsData
import com.example.myapplication.databinding.EventItemBinding


class EventsAdapter(private val context: Context, listener : OnEventItemClickListener) : RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {
    var mListener = listener;
    var events: List<EventsData> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = EventItemBinding.inflate(inflater, parent, false)
        return EventsViewHolder(binding,context,mListener)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(events[position])
    }



    class EventsViewHolder(private val binding: EventItemBinding, private val context: Context,private val listener : OnEventItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(event: EventsData) {

            binding.event = event

            binding.root.setOnClickListener {
                listener.onEventItemClick(event)
            }
            binding.executePendingBindings()

        }

    }

    interface OnEventItemClickListener {
        fun onEventItemClick(event: EventsData)
    }
}
