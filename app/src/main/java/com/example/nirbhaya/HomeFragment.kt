package com.example.nirbhaya

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Define the list of members
        val listMember: List<MemberModel> = listOf(
            MemberModel("John", "RKPURAM", "12", "90%", "223M", "WiFi"),
            MemberModel("RAM", "RKPURAM", "12", "90%", "223M", "WiFi"),
            MemberModel("RAHUL", "RKPURAM", "12", "90%", "223M", "WiFi")
        )

        // Set up the RecyclerView
        val adapter = MemberAdapter(listMember)

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment() // Returning an instance of HomeFragment
        }
    }
}
