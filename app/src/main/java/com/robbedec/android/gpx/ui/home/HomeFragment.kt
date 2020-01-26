package com.robbedec.android.gpx.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.robbedec.android.gpx.PusherApplication
import com.robbedec.android.gpx.R
import javax.inject.Inject

class HomeFragment : Fragment() {


    @Inject lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity!!.applicationContext as PusherApplication).appComponent.inject(this)
    }
}