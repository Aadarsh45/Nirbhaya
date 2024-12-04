package com.example.nirbhaya

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import android.content.Context

import android.util.Log
import android.widget.ImageView

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {
    private val listContact: ArrayList<ContactModel> = ArrayList()

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
        val listMember1: List<ContactModel> = listOf(
            ContactModel("John", "9734567890"),
            ContactModel("RAM", "5634567890"),
            ContactModel("RAHUL", "1234567890")
        )


        // Set up the RecyclerView for members
        val memberAdapter = MemberAdapter(listMember)
        val memberRecyclerView = requireView().findViewById<RecyclerView>(R.id.rv)
        memberRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        memberRecyclerView.adapter = memberAdapter



        val inviteAdapter = InviteAdapter(listContact)

        // Fetch contacts and set up the RecyclerView for contacts

        CoroutineScope(Dispatchers.IO).launch {

            listContact.addAll(fetchContacts())

            withContext(Dispatchers.Main) {
                inviteAdapter.notifyDataSetChanged()
            }
        }
        val contactList = fetchContacts()

        val contactRecyclerView = requireView().findViewById<RecyclerView>(R.id.rv1)
        contactRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        contactRecyclerView.adapter = inviteAdapter
    }
    private fun fetchContacts(): List<ContactModel> {
        val contactList = mutableListOf<ContactModel>()
        val cr = requireContext().contentResolver
        val cursor = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val name = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber = it.getInt(it.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if (hasPhoneNumber > 0) {
                    val phoneCursor = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} =?",
                        arrayOf(id),
                        null
                    )

                    phoneCursor?.use { pCur ->
                        while (pCur.moveToNext()) {
                            val rawPhoneNum = pCur.getString(
                                pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                            val sanitizedPhoneNum = rawPhoneNum.replace("[^+\\d]".toRegex(), "")
                            contactList.add(ContactModel(name, sanitizedPhoneNum))
                        }
                    }
                }
            }
        }

        return contactList
    }






    private fun fetchContacts1(): List<ContactModel> {
        val contactList = mutableListOf<ContactModel>()

        val cr = requireContext().contentResolver
        val cursor = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                val name =
                    it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                val hasPhoneNumber =
                    it.getInt(it.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))

                if (hasPhoneNumber > 0) {
                    val phoneCursor = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} =?",
                        arrayOf(id),
                        null
                    )

                    phoneCursor?.use { pCur ->
                        while (pCur.moveToNext()) {
                            val phoneNum =
                                pCur.getString(
                                    pCur.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                                )
                            contactList.add(ContactModel(name, phoneNum))
                        }
                    }
                }
            }
        }

        return contactList
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
