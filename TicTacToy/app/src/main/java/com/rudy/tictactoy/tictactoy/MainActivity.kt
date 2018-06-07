package com.rudy.tictactoy.tictactoy

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    var myEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            // Obtain the FirebaseAnalytics instance.
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

            var b: Bundle = intent.extras
            myEmail = b.getString("Email")

            IncommingCalls()

    }


    var Player1 = ArrayList<Int>()
    var Player2 = ArrayList<Int>()
    var ActivePlayer = 1
    var setPlayer = 1
    var multi = 0
    var checksend = 0



    fun restartGame(view: View) {
        button1.setBackgroundResource(android.R.drawable.btn_default)
        button2.setBackgroundResource(android.R.drawable.btn_default)
        button3.setBackgroundResource(android.R.drawable.btn_default)
        button4.setBackgroundResource(android.R.drawable.btn_default)
        button5.setBackgroundResource(android.R.drawable.btn_default)
        button6.setBackgroundResource(android.R.drawable.btn_default)
        button7.setBackgroundResource(android.R.drawable.btn_default)
        button8.setBackgroundResource(android.R.drawable.btn_default)
        button9.setBackgroundResource(android.R.drawable.btn_default)

        button1.text = ""
        button2.text = ""
        button3.text = ""
        button4.text = ""
        button5.text = ""
        button6.text = ""
        button7.text = ""
        button8.text = ""
        button9.text = ""

        Player1.clear()
        Player2.clear()
        ActivePlayer = 1

        button1.isEnabled = true
        button2.isEnabled = true
        button3.isEnabled = true
        button4.isEnabled = true
        button5.isEnabled = true
        button6.isEnabled = true
        button7.isEnabled = true
        button8.isEnabled = true
        button9.isEnabled = true

        setPlayer = 1
        PVP.setBackgroundColor(Color.CYAN)
        PVC.setBackgroundColor(android.R.drawable.btn_default)

        multi = 0
        PVC.alpha = 0.9F
        PVP.alpha = 0.9F
        PVC.isEnabled = true
        PVP.isEnabled = true

        if (checksend == 1)
        {
            etEmail.setText("")
        }
        checksend = 0

    }


    fun buttonClick(view: View) {
        val buSelected: Button = view as Button
        var cellId = 0
        when (buSelected.id) {
            R.id.button1 -> cellId = 1
            R.id.button2 -> cellId = 2
            R.id.button3 -> cellId = 3

            R.id.button4 -> cellId = 4
            R.id.button5 -> cellId = 5
            R.id.button6 -> cellId = 6

            R.id.button7 -> cellId = 7
            R.id.button8 -> cellId = 8
            R.id.button9 -> cellId = 9
        }

        if (multi == 1)
        {
            //////////////s
            myRef.child("PlayerOnline").child(sessionID).child(cellId.toString()).setValue(myEmail)
            //////////////e
        }
        else {
            PlayGame(cellId, buSelected)
        }


    }

    fun PlayerChoose(view: View) {
        val ps: Button = view as Button
        when (ps.id) {
            R.id.PVP -> {
                setPlayer = 1
                ps.setBackgroundColor(Color.CYAN)
                PVC.setBackgroundColor(android.R.drawable.btn_default)
            }
            R.id.PVC -> {
                setPlayer = 2
                ps.setBackgroundColor(Color.CYAN)
                PVP.setBackgroundColor(android.R.drawable.btn_default)
            }
        }
    }


    fun PlayGame(cellId: Int, buSelected: Button) {
        if (ActivePlayer == 1) {
            buSelected.text = "X"
            buSelected.setBackgroundColor(Color.GREEN)
            Player1.add(cellId)
            ActivePlayer = 2


            if (multi != 1) {
///////////////////////////////////////////////////////////////////////////////
            if (setPlayer == 1) {
            } else {
                try {
                    AutoPlay()
                } catch (ex: Exception) {
                    Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show()
                }

            }
///////////////////////////////////////////////////////////////////////////////
            }

        } else {
            buSelected.text = "O"
            buSelected.setBackgroundColor(Color.CYAN)
            Player2.add(cellId)
            ActivePlayer = 1
        }
        buSelected.isEnabled = false
        if (multi == 1) {
            CheckOnlineWinner()
        }
        else {
            CheckWinner()
        }
    }

    fun CheckOnlineWinner() {
        var winner = -1

        //row1
        if (button1.text=="X" && button2.text=="X" && button3.text=="X")
        {
            winner = 1
        }
        if (button1.text=="O" && button2.text=="O" && button3.text=="O")
        {
            winner = 2
        }

        //row2
        if (button4.text=="X" && button5.text=="X" && button6.text=="X")
        {
            winner = 1
        }
        if (button4.text=="O" && button5.text=="O" && button6.text=="O")
        {
            winner = 2
        }

        //row3
        if (button7.text=="X" && button8.text=="X" && button9.text=="X")
        {
            winner = 1
        }
        if (button7.text=="O" && button8.text=="O" && button9.text=="O")
        {
            winner = 2
        }

        //col1
        if (button1.text=="X" && button4.text=="X" && button7.text=="X")
        {
            winner = 1
        }
        if (button1.text=="O" && button4.text=="O" && button7.text=="O")
        {
            winner = 2
        }

        //col2
        if (button2.text=="X" && button5.text=="X" && button8.text=="X")
        {
            winner = 1
        }
        if (button2.text=="O" && button5.text=="O" && button8.text=="O")
        {
            winner = 2
        }

        //col3
        if (button3.text=="X" && button6.text=="X" && button9.text=="X")
        {
            winner = 1
        }
        if (button3.text=="O" && button6.text=="O" && button9.text=="O")
        {
            winner = 2
        }

        //cross1
        if (button1.text=="X" && button5.text=="X" && button9.text=="X")
        {
            winner = 1
        }
        if (button1.text=="O" && button5.text=="O" && button9.text=="O")
        {
            winner = 2
        }

        //cross2
        if (button3.text=="X" && button5.text=="X" && button7.text=="X")
        {
            winner = 1
        }
        if (button3.text=="O" && button5.text=="O" && button7.text=="O")
        {
            winner = 2
        }

        if (winner != -1) {
            if (winner == 1) {
                Toast.makeText(this, "X Wins!!", Toast.LENGTH_SHORT).show()
                stopTouch()

            } else {
                Toast.makeText(this, "O Wins!!", Toast.LENGTH_SHORT).show()
                stopTouch()

            }
        }

    }

    fun CheckWinner() {
        var winner = -1

        //row1
        if (Player1.contains(1) && Player1.contains(2) && Player1.contains(3)) {
            winner = 1
        }
        if (Player2.contains(1) && Player2.contains(2) && Player2.contains(3)) {
            winner = 2
        }

        //row2
        if (Player1.contains(4) && Player1.contains(5) && Player1.contains(6)) {
            winner = 1
        }
        if (Player2.contains(4) && Player2.contains(5) && Player2.contains(6)) {
            winner = 2
        }

        //row3
        if (Player1.contains(7) && Player1.contains(8) && Player1.contains(9)) {
            winner = 1
        }
        if (Player2.contains(7) && Player2.contains(8) && Player2.contains(9)) {
            winner = 2
        }

        //col1
        if (Player1.contains(1) && Player1.contains(4) && Player1.contains(7)) {
            winner = 1
        }
        if (Player2.contains(1) && Player2.contains(4) && Player2.contains(7)) {
            winner = 2
        }

        //col2
        if (Player1.contains(2) && Player1.contains(5) && Player1.contains(8)) {
            winner = 1
        }
        if (Player2.contains(2) && Player2.contains(5) && Player2.contains(8)) {
            winner = 2
        }

        //col3
        if (Player1.contains(3) && Player1.contains(6) && Player1.contains(9)) {
            winner = 1
        }
        if (Player2.contains(3) && Player2.contains(6) && Player2.contains(9)) {
            winner = 2
        }

        //cross1
        if (Player1.contains(1) && Player1.contains(5) && Player1.contains(9)) {
            winner = 1
        }
        if (Player2.contains(1) && Player2.contains(5) && Player2.contains(9)) {
            winner = 2
        }

        //cross2
        if (Player1.contains(3) && Player1.contains(5) && Player1.contains(7)) {
            winner = 1
        }
        if (Player2.contains(3) && Player2.contains(5) && Player2.contains(7)) {
            winner = 2
        }

        if (winner != -1) {
            if (winner == 1) {
                if (setPlayer == 1) {
                    Toast.makeText(this, "Player 1 Wins!!", Toast.LENGTH_SHORT).show()
                    stopTouch()
                } else {
                    Toast.makeText(this, "You Won!!", Toast.LENGTH_SHORT).show()
                    stopTouch()
                }
            } else {
                if (setPlayer == 1) {
                    Toast.makeText(this, "Player 2 Wins!!", Toast.LENGTH_SHORT).show()
                    stopTouch()
                } else {
                    Toast.makeText(this, "CPU Wins!!", Toast.LENGTH_SHORT).show()
                    stopTouch()
                }
            }
        }
    }

    fun stopTouch() {
        button1.isEnabled = false
        button2.isEnabled = false
        button3.isEnabled = false
        button4.isEnabled = false
        button5.isEnabled = false
        button6.isEnabled = false
        button7.isEnabled = false
        button8.isEnabled = false
        button9.isEnabled = false
    }

    //////////////s
    fun AutoPlayOnline(cellId:Int) {

        val buSelect: Button?
        when (cellId) {
            1 -> buSelect = button1
            2 -> buSelect = button2
            3 -> buSelect = button3
            4 -> buSelect = button4
            5 -> buSelect = button5
            6 -> buSelect = button6
            7 -> buSelect = button7
            8 -> buSelect = button8
            9 -> buSelect = button9
            else -> buSelect = null
        }

        if (buSelect != null) {
            PlayGame(cellId, buSelect)

        }
    }
    //////////////e


    fun AutoPlay() {
        val emptyCells = ArrayList<Int>()
        for (cellId in 1..9) {
            if (Player1.contains(cellId) || Player2.contains(cellId)) {
            } else {
                emptyCells.add(cellId)
            }
        }

        val r = Random()
        val randomIndex = r.nextInt(emptyCells.size - 0) + 0
        val cellId = emptyCells[randomIndex]

        val buSelect: Button?
        when (cellId) {
            1 -> buSelect = button1
            2 -> buSelect = button2
            3 -> buSelect = button3
            4 -> buSelect = button4
            5 -> buSelect = button5
            6 -> buSelect = button6
            7 -> buSelect = button7
            8 -> buSelect = button8
            9 -> buSelect = button9
            else -> buSelect = button1
        }

        PlayGame(cellId, buSelect)
    }

    fun buRequestEvent(view: View) {
        var userEmail = etEmail.text.toString()
        myRef.child("Users").child(splitString(userEmail)).child("Request").push().setValue(myEmail)
        PlayerSymbol = "X"
        multi = 1
        PVC.alpha = 0.2F
        PVP.alpha = 0.2F
        PVC.isEnabled = false
        PVP.isEnabled = false
        PlayerOnline(splitString(myEmail!!) + splitString(userEmail))

    }

    fun buAcceptEvent(view: View) {
        var userEmail = etEmail.text.toString()
        myRef.child("Users").child(splitString(userEmail)).child("Request").push().setValue(myEmail)
        PlayerSymbol = "O"
        multi = 1
        PVC.alpha = 0.2F
        PVP.alpha = 0.2F
        PVC.isEnabled = false
        PVP.isEnabled = false
        checksend = 1
        PlayerOnline(splitString(userEmail) + splitString(myEmail!!))

    }



    var sessionID:String? = null
    var PlayerSymbol:String? = null



    fun PlayerOnline(sessionID:String)
    {
        this.sessionID = sessionID
        myRef.child("PlayerOnline").removeValue()
        myRef.child("PlayerOnline").child(sessionID)
                .addValueEventListener(object:ValueEventListener{
                    override fun onDataChange(p0: DataSnapshot?) {
                        try {
                            Player1.clear()
                            Player2.clear()
                            val td = p0!!.value as HashMap<String,Any>
                            if (td != null)
                            {
                                var value:String?
                                for(key in td.keys)
                                {
                                        myRef.child("PlayerOnline").child(sessionID).removeValue()
                                        value = td[key] as String
                                        if (value != myEmail) {
                                            Enablebutton()
                                            ActivePlayer = if (PlayerSymbol == "X") 1 else 2
                                        } else {
                                            Disablebutton()
                                            ActivePlayer = if (PlayerSymbol == "X") 2 else 1
                                        }

                                        AutoPlayOnline(key.toInt())
                                }
                            }
                        }catch (ex:Exception)
                        {

                        }
                    }

                    override fun onCancelled(p0: DatabaseError?) {

                    }
                })
    }

    var number = 1
    fun IncommingCalls()
    {
        myRef.child("Users").child(splitString(myEmail!!)).child("Request")
                .addValueEventListener(object:ValueEventListener{

                    override fun onDataChange(p0: DataSnapshot?) {
                        try {
                            val td = p0!!.value as HashMap<String,Any>
                            if (td != null)
                            {
                                var value:String
                                for(key in td.keys)
                                {
                                    value = td[key] as String
                                    etEmail.setText(value)

//                                    var notifyMe = Notifications()
//                                    notifyMe.Notify(applicationContext,value + " wants to play Tic Tac Toe", number)
//                                    number++

                                    myRef.child("Users").child(splitString(myEmail!!)).child("Request").setValue(true)
                                    break
                                }
                            }
                        }catch (ex:Exception)
                        {

                        }
                    }

                    override fun onCancelled(p0: DatabaseError?) {

                    }
                })
    }

    fun Enablebutton()
    {
        if(button1.text=="") {
            button1.isEnabled = true
        }
        if(button2.text=="") {
            button2.isEnabled = true
        }
        if(button3.text=="") {
            button3.isEnabled = true
        }
        if(button4.text=="") {
            button4.isEnabled = true
        }
        if(button5.text=="") {
            button5.isEnabled = true
        }
        if(button6.text=="") {
            button6.isEnabled = true
        }
        if(button7.text=="") {
            button7.isEnabled = true
        }
        if(button8.text=="") {
            button8.isEnabled = true
        }
        if(button9.text=="") {
            button9.isEnabled = true
        }

    }

    fun Disablebutton()
    {
        if(button1.text=="") {
            button1.isEnabled = false
        }
        if(button2.text=="") {
            button2.isEnabled = false
        }
        if(button3.text=="") {
            button3.isEnabled = false
        }
        if(button4.text=="") {
            button4.isEnabled = false
        }
        if(button5.text=="") {
            button5.isEnabled = false
        }
        if(button6.text=="") {
            button6.isEnabled = false
        }
        if(button7.text=="") {
            button7.isEnabled = false
        }
        if(button8.text=="") {
            button8.isEnabled = false
        }
        if(button9.text=="") {
            button9.isEnabled = false
        }

    }

    fun splitString(str:String):String
    {
        var split = str.split("@")
        return split[0]
    }

}

