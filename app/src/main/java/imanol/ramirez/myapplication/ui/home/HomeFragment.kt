package imanol.ramirez.myapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import imanol.ramirez.myapplication.LoginActivity
import imanol.ramirez.myapplication.R


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val UserNamesession = root.findViewById<TextView>(R.id.lblUserName)
        UserNamesession.text = LoginActivity.nameSession

        return root
    }
}