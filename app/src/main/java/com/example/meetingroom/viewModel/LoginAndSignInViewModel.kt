package com.example.meetingroom.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginAndSignInViewModel : ViewModel() {
        private val _errorMessage = MutableLiveData<String?>()
        val errorMessage: LiveData<String?> = _errorMessage

    fun validateForm(name: String, password: String, email: String): Boolean {
        if (name.isEmpty()) {
            _errorMessage.value = "Lütfen İsim alanını boş bırakmayın"
            return false
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _errorMessage.value = "Geçersiz email adresi."
            return false
        }
        if (password.length < 8) {
            _errorMessage.value = "Lütfen en az 8 karakter kullanın"
            return false
        }
        _errorMessage.value = null
        return true
    }


}