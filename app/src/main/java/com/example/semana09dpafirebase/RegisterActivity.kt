package com.example.semana09dpafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.semana09dpafirebase.model.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //declarar el id de los campos de layout register

        val etFullName: EditText = findViewById(R.id.etFullName)
        val etCountry: EditText = findViewById(R.id.etCountry)
        val etEmailRegister: EditText = findViewById(R.id.etEmailRegister)
        val etPasswordRegister: EditText = findViewById(R.id.etPasswordRegister)
        val btnSaveRegister: Button = findViewById(R.id.btnSaveRegister)

        //guardar los datos en firebase en el colector user
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("users")


        // las instrucciones que pasará cuando usuario le da click en registrar
        btnSaveRegister.setOnClickListener {


            val fullname = etFullName.text.toString()
            val country = etCountry.text.toString()
            val email = etEmailRegister.text.toString()
            val password = etPasswordRegister.text.toString()

            auth.createUserWithEmailAndPassword(email, password)

                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //se registró en firebase authentication y ahora registramos en firebase firestore

                        var user: FirebaseUser? = auth.currentUser
                        var uid = user?.uid
                        var userModel =
                            UserModel(email, password, fullname, country, uid.toString())
                        collectionRef.add(userModel)
                            .addOnCompleteListener {
                                Snackbar
                                    .make(
                                        findViewById(android.R.id.content),
                                        "Registro exitoso",
                                        Snackbar.ANIMATION_MODE_FADE).show()

                                etFullName.setText("")
                                etCountry.setText("")
                                etEmailRegister.setText("")
                                etPasswordRegister.setText("")


                            }.addOnFailureListener {

                                Snackbar
                                    .make(
                                        findViewById(android.R.id.content),
                                        "No se pudo completar la transacción",
                                        Snackbar.LENGTH_LONG).show()
                            }


                    } else {

                        Snackbar
                            .make(
                                findViewById(android.R.id.content),
                                "Ocurrió un error al registrarse",
                                Snackbar.LENGTH_LONG).show()

                    }

                }

            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}