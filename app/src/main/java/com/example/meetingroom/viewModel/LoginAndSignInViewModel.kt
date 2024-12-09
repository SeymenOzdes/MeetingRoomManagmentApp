package com.example.meetingroom.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.meetingroom.helpers.DatabaseHelper
import com.example.meetingroom.model.User

class LoginAndSignInViewModel(application: Application) : AndroidViewModel(application) {
    private val _errorMessage = MutableLiveData<String?>()
    private val _userList = MutableLiveData<List<User>>()
    val errorMessage: LiveData<String?> = _errorMessage
    val userList: LiveData<List<User>> get() = _userList
    private val databaseHelper = DatabaseHelper(application)

    fun validateForm(name: String, password: String, email: String) : Boolean {
        if (databaseHelper.checkUserCredentials(email)) {
            _errorMessage.value = "Email zaten kayıtlı"
            return false
        }
        if (name.isEmpty()) {
            _errorMessage.value = "Lütfen isim alanını boş bırakmayın."
            return false
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = "Lütfen Geçerli bir email adresi girin."
            return false
        }
        if (password.length < 8) {
            _errorMessage.value = "Lütfen en az 8 karakterli bir şifre kullanın."
            return false
        }
        _errorMessage.value = null
        return true
    }
    fun validateForm(email: String, password: String) : Boolean {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = "Lütfen Geçerli bir e-posta adresi girin."
            return false
        }
        if (password.length < 8) {
            _errorMessage.value = "Lütfen en az 8 karakter kullanın."
            return false
        }
        if(!databaseHelper.checkUserCredentials(email, password)) {
            _errorMessage.value = "E-posta veya şifre yanlış."
            return false
        }
        _errorMessage.value = null
        return true
    }

    fun addUser(name: String, password: String, mail: String) {
        databaseHelper.insertUser(name, password, mail)
    }

    fun deleteUser(userId: Int) {
        databaseHelper.deleteUser(userId)
    }
}