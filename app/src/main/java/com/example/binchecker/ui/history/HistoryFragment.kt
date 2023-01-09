package com.example.binchecker.ui.history

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.binchecker.databinding.FragmentHistoryBinding
import com.example.binchecker.databinding.ItemDescriptionBinding
import com.example.binchecker.model.BinModel
import com.example.binchecker.ui.BinViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.String
import java.util.*

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    lateinit var viewModel: BinViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.listBins
        historyAdapter = HistoryAdapter(object : HistoryAdapter.Listener{
            override fun onItemClick(item: BinModel) {
                val builder = MaterialAlertDialogBuilder(activity!!)
                val dialogBinding = ItemDescriptionBinding.inflate(layoutInflater)
                builder.setView(dialogBinding.root)
                builder.setPositiveButton("OK",null)
                dialogBinding.apply {
                    textScheme.text = item.scheme
                    textType.text = item.type
                    textBrand.text = item.brand
                    textPrepaid.text = if (item.prepaid) "Yes" else "No"
                    textLength.text = item.number.length.toString()
                    textLunh.text = if (item.number.luhn) "Yes" else "No"
                    textBank.text = StringBuilder(item.bank.city + ", " + item.bank.name)
                    textUrl.text = item.bank.url
                    textPhone.text = item.bank.phone
                    textCountry.text = StringBuilder(item.country.alpha2 + ", " + item.country.name)
                    textLocation.text = StringBuilder("latitude: " + item.country.latitude
                            + " , longitude: " + item.country.longitude)

                    val latitude = item.country.latitude.toFloat()
                    val longitude = item.country.longitude.toFloat()
                    textLocation.setOnClickListener{
                        if(textLocation.text != "null"){
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(String.format(Locale.US, "geo:%.8f,%.8f", latitude, longitude))
                            )
                            startActivity(Intent.createChooser(intent, "Select an application"))
                        }
                    }
                }
                builder.create().show()
            }
        })
        (recyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = historyAdapter
        viewModel = ViewModelProvider(this)[BinViewModel::class.java]
        viewModel.getList().observe(viewLifecycleOwner) { bin ->
            historyAdapter.submitList(bin.asReversed()) }
    }
}