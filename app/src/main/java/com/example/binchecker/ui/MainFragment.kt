package com.example.binchecker.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.binchecker.R
import com.example.binchecker.databinding.FragmentMainBinding
import java.lang.String
import java.util.*


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: BinViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.buttonHistory -> {
                        Navigation.findNavController(view)
                            .navigate(R.id.action_mainFragment_to_historyFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel = ViewModelProvider(this)[BinViewModel::class.java]
        var latitude = 1f
        var longitude = 1f

        binding.buttonEnter.setOnClickListener{
            viewModel.getDescription(binding.editBin.text.toString())
        }

        viewModel.description.observe(viewLifecycleOwner) {
            binding.apply {
                textScheme.text = it.scheme
                textType.text = it.type
                textBrand.text = it.brand
                textPrepaid.text = if (it.prepaid) "Yes" else "No"
                textLength.text = it.number.length.toString()
                textLunh.text = if (it.number.luhn) "Yes" else "No"
                textBank.text = StringBuilder(it.bank.city + ", " + it.bank.name)
                textUrl.text = it.bank.url
                textPhone.text = it.bank.phone
                textCountry.text = StringBuilder(it.country.alpha2 + ", " + it.country.name)
                textLocation.text = StringBuilder("latitude: " + it.country.latitude
                        + " , longitude: " + it.country.longitude)

                latitude = it.country.latitude.toFloat()
                longitude = it.country.longitude.toFloat()
            }
        }

        binding.textLocation.setOnClickListener{
            if(binding.textLocation.text != "null"){
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(String.format(Locale.US, "geo:%.8f,%.8f", latitude, longitude))
                )
                startActivity(Intent.createChooser(intent, "Select an application"))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}