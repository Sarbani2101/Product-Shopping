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
import com.example.productshopping.Constant
import com.example.productshopping.dataclasses.LoginData
import com.example.productshopping.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    private lateinit var textRegister: TextView
    private lateinit var errorText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnLogin: Button

    private val _userDetails = MutableLiveData<LoginData>()
    private val _errorMessage = MutableLiveData<String?>()
    private val _loginSuccess = MutableLiveData<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        if (Constant.isUserLoggedIn(this)) {
            navigateToMainScreen()
        } else {
            onClickListener()
        }

        _loginSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                Constant.saveUserLoginStatus(this, true, edtEmail.text.toString())
                navigateToMainScreen()
            }
        }

        _errorMessage.observe(this) { errorMessage ->
            errorText.text = errorMessage
            errorText.visibility = View.VISIBLE
        }
    }

    private fun initView() {
        edtEmail = findViewById(R.id.editTextEmail)
        edtPass = findViewById(R.id.editTextPassword)
        textRegister = findViewById(R.id.textRegister)
        errorText = findViewById(R.id.errorText)
        progressBar = findViewById(R.id.progressBar)
        btnLogin = findViewById(R.id.buttonLogin)
    }

    private fun onClickListener() {
        btnLogin.setOnClickListener {
            errorText.visibility = View.GONE
            val email = edtEmail.text.toString().trim()
            val password = edtPass.text.toString().trim()
            if (validateInput(email, password)) {
                progressBar.visibility = View.VISIBLE
                val apiService = ServiceBuilder.createService(ApiService::class.java)
                apiService.loginUser(email, password).enqueue(object : Callback<LoginData> {
                    override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                        progressBar.visibility = View.GONE
                        if (response.isSuccessful) {
                            _loginSuccess.postValue(true)
                            _userDetails.postValue(response.body())
                        } else {
                            _errorMessage.postValue("Login failed. Incorrect email or password.")
                        }
                    }

                    override fun onFailure(call: Call<LoginData>, t: Throwable) {
                        progressBar.visibility = View.GONE
                        _errorMessage.postValue("Network Error: ${t.message}")
                    }
                })
            }
        }

        textRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun navigateToMainScreen() {
        startActivity(Intent(this, ProductActivity::class.java))
        finish()
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
