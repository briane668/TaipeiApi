package com.example.myapplication.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.*
import com.example.myapplication.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment(), EventsAdapter.OnEventItemClickListener,
    AttractionAdapter.OnAttractionItemClickListener {

    private lateinit var _binding: FragmentHomeBinding

    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        // 初始化 ViewModel
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        if (!viewModel.hasApiBeenCalled) {
            lifecycleScope.launch {
                _binding.shimmerLayout.startShimmer()
                viewModel.fetchData("zh-tw")
            }
            viewModel.hasApiBeenCalled = true
        }

        val eventsRecyclerView: RecyclerView = _binding.eventsRecycleView
        val attractionRecyclerView: RecyclerView = _binding.attractionRecycleView
        val eventAdapter = EventsAdapter(context!!, this)
        val attractionAdapter = AttractionAdapter(context!!, this)
        attractionRecyclerView.layoutManager = LinearLayoutManager(context)
        eventsRecyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.attractionsList.observe(this, Observer {
            _binding.shimmerLayout.hideShimmer()
            val activity: MainActivity? = activity as MainActivity?
            activity?.endAnimation()
            attractionAdapter.attractions = it
            attractionRecyclerView.adapter = attractionAdapter
        })
        viewModel.eventsList.observe(this, Observer {
            val activity: MainActivity? = activity as MainActivity?
            activity?.endAnimation()
            _binding.shimmerLayout.hideShimmer()
            eventAdapter.events = it
            eventsRecyclerView.adapter = eventAdapter
        })

        return _binding.root

    }

    override fun onEventItemClick(event: EventsData) {
        val bundle = Bundle()
        bundle.putString("url", event.url)
        findNavController().navigate(R.id.action_global_webFragment, bundle)
    }

    override fun onAttractionItemClick(attraction: AttractionData) {
        val bundle = Bundle()
        bundle.putSerializable("attraction", attraction)
        findNavController().navigate(R.id.action_global_DetailFragment, bundle)
    }


    fun callData(lang: String) {

        lifecycleScope.launch {
            _binding.shimmerLayout.startShimmer()
            viewModel.fetchData(lang)
        }
    }
}