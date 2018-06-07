package com.rudy.tictactoy.tictactoy

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var mAuth:FirebaseAuth? = null
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
    }

    fun buLoginEvent(view:View)
    {
        LoginToFirebase(etEmail.text.toString(),etPassword.text.toString())
    }

    fun LoginToFirebase(email:String,password:String)
    {
        mAuth!!.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){ task ->
                    if (task.isSuccessful)
                    {
                        var currentUser = mAuth!!.currentUser
                        Toast.makeText(applicationContext,"Successful Login",Toast.LENGTH_SHORT).show()
                        if (currentUser != null) {
                            myRef.child("Users").child(splitString(currentUser.email.toString())).child("Request").setValue(currentUser.uid)
                        }
                        LoadMain()
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"Login Failed. Check Email and Password Again!",Toast.LENGTH_SHORT).show()
                    }

                }
    }

    override fun onStart() {
        super.onStart()
        LoadMain()
    }

    fun LoadMain()
    {
        var currentUser = mAuth!!.currentUser
        if (currentUser != null) {

            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Email", currentUser.email)
            intent.putExtra("uid", currentUser.uid)

            startActivity(intent)
        }
    }

    fun splitString(str:String):String
    {
        var split = str.split("@")
        return split[0]
    }
}
