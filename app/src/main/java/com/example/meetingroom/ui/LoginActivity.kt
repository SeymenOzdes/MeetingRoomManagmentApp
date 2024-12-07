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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.meetingroom.R
import com.example.meetingroom.viewModel.LoginAndSignInViewModel

class LoginActivity : AppCompatActivity() {
   private val LoginAndSignInViewModel: LoginAndSignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        val emailField: TextView = findViewById(R.id.emailEditTextLoginScreen)
        val passwordField: TextView = findViewById(R.id.passwordEditTextLoginScreen)
        val logInButton: Button = findViewById(R.id.loginButton)

        LoginAndSignInViewModel.errorMessage.observe(this, { errorMessage ->
            errorMessage?.let { showErrorDialog(it) }
        })

        logInButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            if (LoginAndSignInViewModel.validateForm(password, email)) {
                Toast.makeText(this, "Giriş yapıldı", Toast.LENGTH_SHORT).show()
                val homePageIntent = Intent(this, HomePageActivity::class.java)
                startActivity(homePageIntent)
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

