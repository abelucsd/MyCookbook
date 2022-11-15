package com.example.mycookbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycookbook.databinding.RecipeSearchHomeFragmentBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RecipeSearchHomeFragment : Fragment() {

    companion object{
        fun newInstance(): RecipeSearchHomeFragment {
            return RecipeSearchHomeFragment()
        }
    }
    private val viewModel: SearchViewModel by activityViewModels()

    private var _binding: RecipeSearchHomeFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = RecipeSearchHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as RecipeSearchActivity

        val rv = binding.searchRecyclerView
        val adapter = SearchRowAdapter(viewModel, activity.recipesDb)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)

        /*
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_First2Fragment_to_Second3Fragment)
        }*/

        viewModel.observeRecipes().observe(viewLifecycleOwner) {
            adapter.addRecipes(it)
            adapter.notifyDataSetChanged()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}