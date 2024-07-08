package imanol.ramirez.myapplication.ui.home

import Model.Connection
import Model.Patients
import RecyclerViewHelper.Adapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import imanol.ramirez.myapplication.LoginActivity
import imanol.ramirez.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val UserNamesession = root.findViewById<TextView>(R.id.lblUserName)
        UserNamesession.text = LoginActivity.nameSession

        fun getPatients(): List<Patients> {
            val objCon = Connection().StringConection()
            val statement = objCon?.createStatement()
            val resulSet = statement?.executeQuery("SELECT * FROM TBPatients")!!

            val listPatients = mutableListOf<Patients>()

            while (resulSet.next()) {
                val uuid = resulSet.getString("UUID_Patients")
                val name = resulSet.getString("Name")
                val lastName = resulSet.getString("LastName")
                val Age = resulSet.getInt("Age")
                val Disease = resulSet.getString("Disease")
                val RoomNumber = resulSet.getInt("RoomNumber")
                val BedNumber = resulSet.getInt("BedNumber")
                val Medication = resulSet.getString("Medication")
                val AdmmissioDate = resulSet.getString("AddmissionDate")
                val MedicationtTime = resulSet.getString("MedicationTime")

                val values = Patients(uuid, name, lastName, Age, Disease, RoomNumber,BedNumber,Medication,AdmmissioDate,MedicationtTime)
                listPatients.add(values)
            }

            return listPatients
        }

        val rcvPatients = root.findViewById<RecyclerView>(R.id.rcvPatients)
        rcvPatients.layoutManager = LinearLayoutManager(this.context)

        GlobalScope.launch(Dispatchers.IO)
        {
            val patients = getPatients()
            withContext(Dispatchers.Main)
            {
                val adapter = Adapter(patients)
                rcvPatients.adapter = adapter
            }
        }

        return root
    }
}