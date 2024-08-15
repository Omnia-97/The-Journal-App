package com.example.journalkotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.journalkotlin.databinding.ActivityJournalListBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference

class JournalListActivity : AppCompatActivity() {
    lateinit var binding: ActivityJournalListBinding
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    var db = FirebaseFirestore.getInstance()
    lateinit var storageReference: StorageReference
    lateinit var journalList: MutableList<Journal>
    lateinit var adapter: JournalRecyclerAdapter
    var collectionReference: CollectionReference = db.collection("Journal")
    lateinit var noPostTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_journal_list)
        firebaseAuth = Firebase.auth
        firebaseUser = firebaseAuth.currentUser!!

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        journalList = arrayListOf<Journal>()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> if (firebaseUser != null && firebaseAuth != null) {
                val intent = Intent(this, AddJournalActivity::class.java)
                startActivity(intent)
            }

            R.id.action_signout -> if (firebaseUser != null && firebaseAuth != null) {
                firebaseAuth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        collectionReference.
        whereEqualTo("userId", JournalUser.instance?.userId).
        get().addOnSuccessListener {
            if (!it.isEmpty){
                it.forEach{
                    val journal = it.toObject(Journal::class.java)
                    journalList.add(journal)
                }
                adapter = JournalRecyclerAdapter(this, journalList)
                binding.recyclerView.setAdapter(adapter)
                adapter.notifyDataSetChanged()
            }else{
                binding.textView2.visibility = View.VISIBLE
            }
        }.addOnFailureListener {
            Toast.makeText(this , "oops something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

}