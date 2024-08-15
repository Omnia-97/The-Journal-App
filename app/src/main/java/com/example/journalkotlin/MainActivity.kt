package com.example.journalkotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.journalkotlin.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this , R.layout.activity_main)
        binding.creatAccount.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.emailSignin.setOnClickListener {
           loginWithEmailAndPassword(
               binding.email.text.toString().trim(),
               binding.password.text.toString().trim()
           )
        }
        auth = Firebase.auth

    }

    private fun loginWithEmailAndPassword(email : String , password : String) {
        auth.signInWithEmailAndPassword(
            email,
            password).addOnCompleteListener (this) { task ->
                if (task.isSuccessful){
                    val user = auth.currentUser
                    val intent = Intent(this, JournalListActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }

        }


    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this, JournalListActivity::class.java)
            startActivity(intent)
        }
    }

}