package com.example.otpnewfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {


    private lateinit var number: EditText
    private lateinit var otp: EditText
    private lateinit var sendotp: Button
    private lateinit var verifyotp: Button

    private lateinit var auth: FirebaseAuth
    var verificationID=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        number=findViewById(R.id.enterNuber_EditNumber)
        otp=findViewById(R.id.enterOtp_EditNumber)
        sendotp=findViewById(R.id.sendOtp)
        verifyotp=findViewById(R.id.verifyOtp)


        auth=FirebaseAuth.getInstance()


        sendotp.setOnClickListener {
            otpsend()
        }
        verifyotp.setOnClickListener {
            otpverify()
        }
    }

    private fun otpsend() {
        val phoneAuthOptions = PhoneAuthOptions.newBuilder()
            .setPhoneNumber("+91${number.text}")
            .setActivity(this)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks( object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    Toast.makeText(this@MainActivity,"Verification successful", Toast.LENGTH_SHORT).show()
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    Toast.makeText(this@MainActivity,"Verification fail", Toast.LENGTH_SHORT).show()

                }

                override fun onCodeSent(verificationId: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationId, p1)
                    verificationID = verificationId
                    Toast.makeText(this@MainActivity, "OTP sent", Toast.LENGTH_SHORT).show()
                }
            })
            .build()
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)

    }

    private fun otpverify(){
        val otptext=otp.text.toString()
        val phoneAuthCredential= PhoneAuthProvider.getCredential(verificationID,otptext)
        auth.signInWithCredential(phoneAuthCredential)
            .addOnSuccessListener {
                Toast.makeText(this,"login Sucess", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,HomeActivity
                ::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(this,"login fail", Toast.LENGTH_SHORT).show()
            }

    }

}