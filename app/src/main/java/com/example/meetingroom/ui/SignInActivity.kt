package com.example.meetingroom.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.meetingroom.R
import com.example.meetingroom.viewModel.LoginAndSignInViewModel

class SignInActivity : AppCompatActivity() {
    private val LoginAndSignInViewModel: LoginAndSignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)

        val signUpButton: Button = findViewById(R.id.signUpButton)
        val textField: TextView = findViewById(R.id.fullNameEditText)
        val passwordField: TextView = findViewById(R.id.passwordEditText)
        val emailField: TextView = findViewById(R.id.emailEditText)

        LoginAndSignInViewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessage?.let {showErrorDialog(it) }
            }
        )

        signUpButton.setOnClickListener {
            val name = textField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val email = emailField.text.toString().trim()
            if (LoginAndSignInViewModel.validateForm(name, password, email)) {
                Toast.makeText(this, "kayıt Olundu", Toast.LENGTH_SHORT).show()
                val loginIntent = Intent(this, LoginActivity::class.java)
                startActivity(loginIntent)
            }
        }
    }
    fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }
}