package imanol.ramirez.myapplication.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import imanol.ramirez.myapplication.LoginActivity
import imanol.ramirez.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root =  inflater.inflate(R.layout.fragment_profile, container, false)
        val btnLogOut = root.findViewById<Button>(R.id.btnLogOut)

        GlobalScope.launch(Dispatchers.IO)
        {
            btnLogOut.setOnClickListener {  startActivity(Intent(requireContext(), LoginActivity::class.java))  }
        }

        return root
    }
}