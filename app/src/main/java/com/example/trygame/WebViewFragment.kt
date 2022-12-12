package com.example.trygame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.trygame.databinding.FragmentWebViewBinding


class WebViewFragment : Fragment() {
    private var _binding: FragmentWebViewBinding? = null
    private val binding: FragmentWebViewBinding
        get() = _binding ?: throw RuntimeException("WebViewFragment == null")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebViewBinding.inflate(inflater,container,false )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.web.webViewClient = MyWebViewClient()
        binding.web.settings.javaScriptEnabled = true
        binding.web.loadUrl("http://football.ua")
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                if (binding.web.canGoBack()) {
                    binding.web.goBack()
                }else{
                    activity?.finish()
                }
            }

        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, callback)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}