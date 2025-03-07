package com.example.productshopping.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.productshopping.apiuses.ApiService
import com.example.productshopping.apiuses.ServiceBuilder
import com.example.productshopping.dataclasses.RegisterData
import com.example.productshopping.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var password2EditText: EditText
    private lateinit var errorTV: TextView
    private lateinit var textLogin: TextView
    private lateinit var registerButton: Button
    private lateinit var progressBar: ProgressBar

    private val _registrationStatus = MutableLiveData<Boolean>()
    private val _error = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()

        _registrationStatus.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

        _error.observe(this) { errorMessage ->
            errorTV.text = errorMessage
            errorTV.visibility = View.VISIBLE
        }

        onClickListener()
    }

    private fun initView() {
        fullNameEditText = findViewById(R.id.editTextFullName)
        emailEditText = findViewById(R.id.editTextEmail)
        phoneEditText = findViewById(R.id.editTextPhone)
        passwordEditText = findViewById(R.id.editTextPassword)
        password2EditText = findViewById(R.id.editTextPassword2)
        errorTV = findViewById(R.id.errorText)
        textLogin = findViewById(R.id.textLogin)
        registerButton = findViewById(R.id.buttonRegister)
        progressBar = findViewById(R.id.progressBarRegistration)
    }

    private fun onClickListener() {
        textLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        registerButton.setOnClickListener {
            errorTV.visibility = View.GONE
            val fullName = fullNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            val password2 = password2EditText.text.toString()

            if (validateInput(fullName, email, phone, password, password2)) {
                progressBar.visibility = View.VISIBLE
                val apiService = ServiceBuilder.createService(ApiService::class.java)
                apiService.registerUser(fullName, email, phone, password, password2)
                    .enqueue(object : Callback<RegisterData> {
                        override fun onResponse(call: Call<RegisterData>, response: Response<RegisterData>) {
                            progressBar.visibility = View.GONE
                            if (response.isSuccessful) {
                                _registrationStatus.postValue(true)
                            } else {
                                _error.postValue("Error: ${response.code()} ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<RegisterData>, t: Throwable) {
                            progressBar.visibility = View.GONE
                            _error.postValue("Network Error: ${t.localizedMessage}")
                        }
                    })
            }
        }
    }

    private fun validateInput(fullName: String, email: String, phone: String, password: String, password2: String): Boolean {
        if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != password2) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}

