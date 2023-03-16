package com.example.beercatalogue.presenter.beerCatalogue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.beercatalogue.data.common.entity.BeerEntity
import com.example.beercatalogue.databinding.FragmentBeerCatalogueListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BeerCatalogueListFragment : Fragment() {

    private var _binding: FragmentBeerCatalogueListBinding? = null
    private val binding get() = _binding!!
    private lateinit var mViewModel: BeerCatalogueListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBeerCatalogueListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel =
            ViewModelProvider(requireActivity()).get(BeerCatalogueListViewModel::class.java)

        val onClick: (beerDetails: BeerEntity) -> Unit = {
            val action =
                BeerCatalogueListFragmentDirections.actionBeerCatalogueListFragmentToBeerDetailsFragment(
                    it
                )
            findNavController().navigate(action)
        }

        val mAdapter = BeerCataloguePagingAdapter(onClick, requireContext())
        binding.rvBeerCatalogue.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvBeerCatalogue.adapter = mAdapter
        binding.progressBar.visibility = View.VISIBLE

        mAdapter.isDataSetChanged.observe(requireActivity()) { isDataChanged ->
            if (isDataChanged) {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            mViewModel.getCachedBeerCatalogue().collectLatest {
                mAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}