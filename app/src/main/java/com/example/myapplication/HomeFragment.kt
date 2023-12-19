package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() , EventsAdapter.OnEventItemClickListener,AttractionAdapter.OnAttractionItemClickListener {

    private lateinit var _binding: FragmentHomeBinding

    // This property is only valid between onCreateView and
    // onDestroyView.

    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // 初始化 ViewModel
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // 在 Activity 中使用 ViewModel 中的 fetchData 函數


        if (!viewModel.hasApiBeenCalled) {
            lifecycleScope.launch {
                _binding.shimmerLayout.startShimmer()
                viewModel.fetchData("zh-tw")
            }
            viewModel.hasApiBeenCalled = true
        }

        val eventsRecyclerView: RecyclerView = _binding.eventsRecycleView
        val attractionRecyclerView :RecyclerView = _binding.attractionRecycleView
        val eventAdapter = EventsAdapter(context!!,this)
        val attractionAdapter = AttractionAdapter(context!!,this)
        attractionRecyclerView.layoutManager = LinearLayoutManager(context)
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.attractionsList.observe(this, Observer {
            _binding.shimmerLayout.hideShimmer()
            attractionAdapter.attractions = it
            attractionRecyclerView.adapter = attractionAdapter
            attractionRecyclerView.adapter!!.notifyDataSetChanged()


        })

        viewModel.eventsList.observe(this, Observer {
            _binding.shimmerLayout.hideShimmer()
            eventAdapter.events = it
            eventsRecyclerView.adapter = eventAdapter
            eventsRecyclerView.adapter!!.notifyDataSetChanged()
        })




        return _binding.root

    }

    override fun onEventItemClick(event: EventsData) {

        val bundle = Bundle()
        bundle.putString("url", event.url)
        findNavController().navigate(R.id.action_global_webFragment,bundle)

    }

    override fun onAttractionItemClick(attraction: AttractionData) {
        val bundle = Bundle()
        bundle.putSerializable("attraction", attraction)
        findNavController().navigate(R.id.action_global_DetailFragment,bundle)

    }


    public fun callData (lang :String){
        println("wade wade"+lang)


        lifecycleScope.launch {
            _binding.shimmerLayout.startShimmer()
            viewModel.fetchData(lang)
        }


    }
}