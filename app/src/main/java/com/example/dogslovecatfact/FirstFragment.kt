package com.example.dogslovecatfact

//import com.example.dogslovecatfact.databinding.FragmentFirstBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.dogslovecatfact.databinding.FragmentFirstBinding
import com.example.dogslovecatfact.viewmodels.MainViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView (
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFirstBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.dogBreed.observeForever{
            binding.dogIcon.visibility = if (it.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }

        viewModel.catFactText.observeForever{
            binding.catIcon.visibility = if (it.isNotEmpty()) {
                View.VISIBLE
            } else {
                View.INVISIBLE
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}