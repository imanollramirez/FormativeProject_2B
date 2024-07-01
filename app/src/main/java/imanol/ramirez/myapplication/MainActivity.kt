package imanol.ramirez.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //R.id.navigation_home,R.id.navigation_profile,R.id.navigation_patients
    }

}