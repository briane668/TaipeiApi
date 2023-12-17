package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // 初始化 ViewModel
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // 在 Activity 中使用 ViewModel 中的 fetchData 函數
        viewModel.fetchData("en")

//        val recyclerView: RecyclerView = binding.recyclerView
//        val adapter = AttractionAdapter(getDataList()) { selectedItem ->
//            // 在這裡處理 RecyclerView 中的項目點擊事件
//            // 例如，進行導航到另一個 Fragment
//            val action = YourFragmentDirections.actionYourFragmentToAnotherFragment(selectedItem.itemId)
//            findNavController().navigate(action)
//        }
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}