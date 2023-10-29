package com.example.semana09dpafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var auth = FirebaseAuth.getInstance()

        val txtEmail: EditText = findViewById(R.id.txtEmail)
        val txtPassword: EditText = findViewById(R.id.txtPassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnLogin.setOnClickListener {

            val correo = txtEmail.text.toString()
            val clave = txtPassword.text.toString()

            auth.signInWithEmailAndPassword(correo, clave)

                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {


                        Snackbar
                            .make(
                                findViewById(android.R.id.content),
                                "Ingreso exitoso...",
                                Snackbar.LENGTH_LONG
                            ).show()

                        //Con la siguiente linea se le dice que en caso de login exitoso
                        // se debe iniciar el "Mainactivity"
                        startActivity(Intent(this, PrincipalActivity::class.java))


                    } else {
                        Snackbar
                            .make(
                                findViewById(android.R.id.content),
                                "Credenciales inválidas",
                                Snackbar.LENGTH_LONG
                            ).show()

                    }
                }

        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))


        }
    }

}