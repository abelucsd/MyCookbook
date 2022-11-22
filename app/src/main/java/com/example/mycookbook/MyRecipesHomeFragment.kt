package com.example.mycookbook

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycookbook.databinding.MyRecipesHomeFragmentBinding

class MyRecipesHomeFragment : Fragment() {

    companion object{
        fun newInstance(): MyRecipesHomeFragment {
            return MyRecipesHomeFragment()
        }
    }
    //

    private var _binding: MyRecipesHomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = MyRecipesHomeFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity() as MyRecipesActivity

        val rv = binding.myRecipesRecyclerView
        val adapter = MyRecipesRowAdapter(activity.recipesDb)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}