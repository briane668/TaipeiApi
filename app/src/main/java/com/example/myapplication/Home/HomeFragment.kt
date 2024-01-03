package com.example.myapplication.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.AttractionData
import com.example.myapplication.EventsData
import com.example.myapplication.R
import com.example.myapplication.databinding.EventItemBinding
import com.example.myapplication.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import me.relex.circleindicator.CircleIndicator

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
        val temporaryAdapter = CustomPagerAdapter.TemporaryAdapter()

//        adapter.registerDataSetObserver(indicator.dataSetObserver)
        val attractionRecyclerView: RecyclerView = _binding.attractionRecycleView
        attractionRecyclerView.adapter = temporaryAdapter
        val viewPager: ViewPager = _binding.viewPager
        viewPager.offscreenPageLimit = 2


        val eventAdapter = CustomPagerAdapter(this)
        viewPager.setPageTransformer(true, CustomPagerAdapter.GasOfferPageTransformer())


        viewPager.adapter = eventAdapter


        val attractionAdapter = AttractionAdapter(context!!, this)
        attractionRecyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.attractionsList.observe(this, Observer {
            attractionAdapter.attractions = it
            attractionRecyclerView.adapter = attractionAdapter
        })

        attractionRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                // 判斷是否到達 RecyclerView 底部
                if (!attractionAdapter.isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                ) {
                    // 載入更多數據
                    // 在這裡觸發載入更多數據的操作，並在載入的同時設置 isLoading 為 true
                    customAdapter.setLoading(true)

                    // 模擬載入數據的過程，實際中你需要根據具體情況更改
                    Handler(Looper.getMainLooper()).postDelayed({
                        // 添加新的數據到 Adapter 中
                        // 在數據載入完成後，設置 isLoading 為 false
                        customAdapter.addMoreData(/* 新的數據 */)
                        customAdapter.setLoading(false)
                    }, 2000) // 模擬載入數據的時間，實際中需要根據具體情況調整
                }
            }
        })









        viewModel.eventsList.observe(this, Observer {
            _binding.shimmerLayout.hideShimmer()
            eventAdapter.events = it
            viewPager.adapter = eventAdapter

            _binding.indicator.setViewPager(viewPager)
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

    private class CustomPagerAdapter(listener : EventsAdapter.OnEventItemClickListener) : PagerAdapter() {
        var mListener = listener;
        var events: List<EventsData> = emptyList()
            set(value) {
                field = value
                notifyDataSetChanged()
            }
        override fun getCount(): Int {
            return events.size // Number of pages
        }

        override fun isViewFromObject(view: View, o: Any): Boolean {
            return view == o
        }

        override fun getPageWidth(position: Int): Float {
            return super.getPageWidth(position)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {


            val inflater = LayoutInflater.from(container.context)
            val binding: EventItemBinding = DataBindingUtil.inflate(inflater, R.layout.event_item, container, false)
            container.addView(binding.root)



            binding.event = events[position]
            binding.root.setOnClickListener {
                mListener.onEventItemClick(events[position])
            }


//            binding.root.setScaleX(0.75f)
//            binding.root.setScaleY(0.75f)



            return binding.root


        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            // Remove the view from the ViewPager
            container.removeView(`object` as View)
        }

        class GasOfferPageTransformer : ViewPager.PageTransformer {
            override fun transformPage(view: View, position: Float) {
                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.scaleX = MIN_SCALE
                    view.scaleY = MIN_SCALE
                } else if (position <= 0) { // [-1,0]
                    val scaleFactor = (MIN_SCALE
                            + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                    view.scaleX = scaleFactor
                    view.scaleY = scaleFactor
                } else if (position <= 1) { // (0,1]
                    val scaleFactor = (MIN_SCALE
                            + (1 - MIN_SCALE) * (1 - Math.abs(position)))
                    view.scaleX = scaleFactor
                    view.scaleY = scaleFactor
                } else { // (1,+Infinity]
                    // This page is way off-screen to the right
                    view.scaleX = MIN_SCALE
                    view.scaleY = MIN_SCALE
                }
            }

            companion object {
                private const val MIN_SCALE = 0.75f
            }
        }

        class TemporaryAdapter : RecyclerView.Adapter<TemporaryAdapter.ViewHolder>() {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_preview, parent, false)
                return ViewHolder(view)
            }

            override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                // Set preview data to the views
                // You can customize this based on your UI
            }

            override fun getItemCount(): Int {
                return 10 // Number of preview items
            }

            class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
        }





    }
}