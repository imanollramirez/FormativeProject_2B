    package imanol.ramirez.myapplication

import Model.Connection
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay
import java.util.UUID

    class ActivityRegister : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val linkLogin = findViewById<TextView>(R.id.lkBackToLogin)
        val txtName = findViewById<EditText>(R.id.txtname)
        val txtUsername = findViewById<EditText>(R.id.txtRegisterUser)
        val txtPassword = findViewById<EditText>(R.id.txtRegisterPassword)
        val txtConfirmPass = findViewById<EditText>(R.id.txtRegisterConfirmPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        fun clear()
        {
            txtUsername.setText("")
            txtPassword.setText("")
            txtConfirmPass.setText("")
        }

        linkLogin.setOnClickListener{
            GlobalScope.launch (Dispatchers.Main) {
                val login = Intent(this@ActivityRegister, LoginActivity::class.java)
                startActivity(login)
                clear()
            }
        }

        btnRegister.setOnClickListener{
            GlobalScope.launch (Dispatchers.IO ) {
                try
                {
                    if(txtUsername.text.isEmpty() || txtPassword.text.isEmpty() || txtConfirmPass.text.isEmpty())
                    {
                        GlobalScope.launch (Dispatchers.IO) {

                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@ActivityRegister, "Complete los campos", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    else if(txtPassword.text.toString() != txtConfirmPass.text.toString())
                    {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ActivityRegister, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else if(txtPassword.text.toString().length < 8){
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ActivityRegister, "Las contraseña debe de contener mínimo 8 carácteres", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else if(txtName.text.contains("[0-9]"))
                    {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@ActivityRegister, "El nombre no puede contener numeros", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        GlobalScope.launch (Dispatchers.IO) {
                            val objCon = Connection().StringConection()

                            val userData = objCon?.prepareStatement("INSERT INTO Users VALUES(?, ? , ?, ?)")!!
                            userData.setString(1, UUID.randomUUID().toString())
                            userData.setString(2, txtName.text.toString())
                            userData.setString(3, txtUsername.text.toString())
                            userData.setString(4, txtPassword.text.toString())
                            userData.executeUpdate()

                            withContext(Dispatchers.Main) {
                                Toast.makeText(this@ActivityRegister, "¡Éxito!, Ahora inicie sesión con sus credenciales.", Toast.LENGTH_SHORT).show()

                                delay(999)
                                val intent = Intent(this@ActivityRegister, LoginActivity::class.java)
                                startActivity(intent)
                                clear()
                            }
                        }
                    }


                }
                catch(e: Exception)
                {
                    println("Error: $e")
                }

            }
        }
    }
}