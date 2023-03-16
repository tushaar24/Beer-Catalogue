package com.example.beercatalogue.presenter.beerDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.beercatalogue.R
import com.example.beercatalogue.databinding.FragmentBeerDetailsBinding

class BeerDetailsFragment : Fragment() {

    private var _binding: FragmentBeerDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<BeerDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBeerDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext())
            .load(args.beerDetails.imageURL)
            .into(binding.ivBeerImage)

        binding.apply {
            tvBeerName.text = args.beerDetails.name
            tvDescription.text = args.beerDetails.brewerTips
            tvTagline.text = args.beerDetails.tagline
            tvAbv.text = requireContext().getString(R.string.beer_details_abv, args.beerDetails.abv)
            tvBrewerTips.text = args.beerDetails.description

            ivBackButton.setOnClickListener {
                val action =
                    BeerDetailsFragmentDirections.actionBeerDetailsFragmentToBeerCatalogueListFragment()
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}