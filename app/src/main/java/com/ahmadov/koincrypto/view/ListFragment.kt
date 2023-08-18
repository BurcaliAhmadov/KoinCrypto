package com.ahmadov.koincrypto.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadov.koincrypto.ViewModel.CryptoViewModel
import com.ahmadov.koincrypto.databinding.FragmentListBinding
import com.ahmadov.koincrypto.model.Crypto
import com.ahmadov.koincrypto.service.CryptoApi
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ListFragment : Fragment(),RecyclerViewAdapter.Listener {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var adapter=RecyclerViewAdapter(arrayListOf(),this)
    private val viewModel by viewModel<CryptoViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentListBinding.inflate(inflater,container,false)
        val view=binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager=layoutManager
        //viewModel= ViewModelProvider(this)[CryptoViewModel::class.java]
        viewModel.getDataFromApi()
        observeLiveData()

    }
    private fun observeLiveData(){
        viewModel.cryptoList.observe(viewLifecycleOwner, Observer {cryptos->
            cryptos?.let{
                binding.recyclerView.visibility=View.VISIBLE
                adapter=RecyclerViewAdapter(ArrayList(it.data?: arrayListOf()),this@ListFragment)
                binding.recyclerView.adapter=adapter
            }
        })
        viewModel.cryptoError.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it.data==true){
                    binding.cryptoError.visibility=View.VISIBLE
                    binding.cryptoProgressBar.visibility=View.GONE
                    binding.recyclerView.visibility=View.GONE
                }else{
                    binding.cryptoError.visibility=View.GONE
                }
            }
        })
        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it.data==true){
                    binding.cryptoProgressBar.visibility=View.VISIBLE
                    binding.cryptoError.visibility=View.GONE
                    binding.recyclerView.visibility=View.GONE
                }else{
                    binding.cryptoProgressBar.visibility=View.GONE
                }
            }
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null

    }

    override fun onItemClick(cryptoModel: Crypto) {
        Toast.makeText(requireContext(),"Clicked on: ${cryptoModel.currency}", Toast.LENGTH_SHORT).show()
    }


}