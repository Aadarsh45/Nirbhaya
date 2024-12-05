package com.example.nirbhaya

import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {
    private val listContact: ArrayList<ContactModel> = ArrayList()
    private lateinit var contactViewModel: ContactViewModel

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

        // Initialize Database, Repository, and ViewModel
        val database = ContactDatabase.getInstance(requireContext())
        val repository = ContactRepository(database.contactDao())
        val viewModelFactory = ContactViewModelFactory(repository)
        contactViewModel = ViewModelProvider(this, viewModelFactory)[ContactViewModel::class.java]

        // Observe contacts using Flow
        contactViewModel.allContactsFlow.observe(viewLifecycleOwner) { contacts ->
            // Update UI with the list of contacts
            println("Contacts: $contacts")
        }

        // Insert a list of sample contacts
//        val contacts = listOf(
//            ContactModel(name = "John Doe", number = "1234567890"),
//            ContactModel(name = "Jane Smith", number = "9876543210"),
//            ContactModel(name = "Alice Johnson", number = "4567891230")
//        )
//        lifecycleScope.launch {
//
//        }

        // Define the list of members
        val listMember: List<MemberModel> = listOf(
            MemberModel("John", "RKPURAM", "12", "90%", "223M", "WiFi"),
            MemberModel("RAM", "RKPURAM", "12", "90%", "223M", "WiFi"),
            MemberModel("RAHUL", "RKPURAM", "12", "90%", "223M", "WiFi")
        )

        // Set up RecyclerView for members
        val memberAdapter = MemberAdapter(listMember)
        val memberRecyclerView = requireView().findViewById<RecyclerView>(R.id.rv)
        memberRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        memberRecyclerView.adapter = memberAdapter

        // Set up RecyclerView for contacts
        val inviteAdapter = InviteAdapter(listContact)
        CoroutineScope(Dispatchers.IO).launch {

            listContact.addAll(fetchContacts())
            contactViewModel.insertContacts(listContact)
            withContext(Dispatchers.Main) {
                inviteAdapter.notifyDataSetChanged()
            }
        }
        val contactRecyclerView = requireView().findViewById<RecyclerView>(R.id.rv1)
        contactRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        contactRecyclerView.adapter = inviteAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
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
                            val phoneNum = pCur.getString(
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
}