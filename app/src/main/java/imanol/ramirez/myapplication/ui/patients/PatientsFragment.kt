package imanol.ramirez.myapplication.ui.patients

import Model.Connection
import Model.Patients
import RecyclerViewHelper.Adapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import imanol.ramirez.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class PatientsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_patient, container, false)

        val txtName = root.findViewById<EditText>(R.id.txtName)
        val txtLastName = root.findViewById<EditText>(R.id.txtLastName)
        val txtAge = root.findViewById<EditText>(R.id.txtAge)
        val txtDisease = root.findViewById<EditText>(R.id.txtDisease)
        val txtMedication = root.findViewById<EditText>(R.id.txtMedication)
        val txtAdmmissionDate = root.findViewById<EditText>(R.id.txtAddmissionDate)
        val txtMedicationTime = root.findViewById<EditText>(R.id.txtMedicationTime)
        val txtRoomNumber = root.findViewById<EditText>(R.id.txtRoomNumber)
        val txtBedNumber = root.findViewById<EditText>(R.id.txtBedNumber)
        val btnAddPatient = root.findViewById<Button>(R.id.btnAddPatient)


        fun clear()
        {
            txtName.setText("")
            txtLastName.setText("")
            txtAge.setText("")
            txtDisease.setText("")
            txtMedication.setText("")
            txtAdmmissionDate.setText("")
            txtMedicationTime.setText("")
            txtRoomNumber.setText("")
            txtBedNumber.setText("")
        }

        fun getPatients(): List<Patients> {
            val objCon = Connection().StringConection()
            val statement = objCon?.createStatement()
            val resulSet = statement?.executeQuery("SELECT * FROM TBPatients")!!

            val listPatients = mutableListOf<Patients>()

            while (resulSet.next()) {
                val uuid = resulSet.getString("UUID_Patients")
                val name = resulSet.getString("Name ")
                val lastName = resulSet.getString("LastName")
                val Age = resulSet.getInt("Age")
                val Disease = resulSet.getString("Disease")
                val Medication = resulSet.getString("Medication")
                val AdmmissioDate = resulSet.getString("AdmmissionDate")
                val MedicationtTime = resulSet.getString("MedicationTime")
                val RoomNumber = resulSet.getInt("RoomNumber")
                val BedNumber = resulSet.getInt("BedNumber")

                val values = Patients(uuid, name, lastName, Age, Disease, RoomNumber,BedNumber,Medication,AdmmissioDate,MedicationtTime)
                listPatients.add(values)
            }

            return listPatients
        }

        btnAddPatient.setOnClickListener{
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    var validation = true

                    if (txtName.text.isEmpty() || txtLastName.text.isEmpty() || txtAge.text.isEmpty() || txtDisease.text.isEmpty() || txtMedication.text.isEmpty() || txtAdmmissionDate.text.isEmpty() || txtMedicationTime.text.isEmpty()) {

                        withContext(Dispatchers.Main)
                        {
                            Toast.makeText(this@PatientsFragment.context, "Complete los campos", Toast.LENGTH_SHORT).show()
                            validation = false
                        }

                    } else {

                        if (txtName.text.contains("[0-9]".toRegex())) {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PatientsFragment.context, "El título no puede contener números", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                        if (txtLastName.text.contains("[0-9]".toRegex())) {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PatientsFragment.context, "El nombre del autor no puede contener números", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                        if (txtAdmmissionDate.text.matches(Regex("[0-9]{1,2}/[0-9]{1,2}/[0-9]{4}"))) {}
                        else
                        {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PatientsFragment.context, "La fecha de admisión es inválida", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                        if (txtAge.text.contains("[a-zA-Z]".toRegex())) {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PatientsFragment.context, "La edad no puede contener letras", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                        if (txtBedNumber.text.contains("[a-zA-Z]".toRegex())) {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PatientsFragment.context, "El número de cama no puede contener letras", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                        if (txtRoomNumber.text.contains("[a-zA-Z]".toRegex())) {
                            withContext(Dispatchers.Main)
                            {
                                Toast.makeText(this@PatientsFragment.context, "El número de cuarto no puede contener letras", Toast.LENGTH_SHORT).show()
                                validation = false
                            }
                        }

                    }
                    if (validation) {
                        GlobalScope.launch(Dispatchers.IO)
                        {
                            val objCon = Connection().StringConection()

                            val addPatient =
                                objCon?.prepareStatement("INSERT INTO TBPatients (UUID_Patients, Name, LastName, Age, Disease, RoomNumber, BedNumber, Medication, AddmissionDate, MedicationTime) VALUES (?, ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?)")!!

                            addPatient.setString(1, UUID.randomUUID().toString())
                            addPatient.setString(2, txtName.text.toString())
                            addPatient.setString(3, txtLastName.text.toString())
                            addPatient.setString(4, txtAge.text.toString())
                            addPatient.setString(5,  txtDisease.text.toString())
                            addPatient.setString(6, txtRoomNumber.text.toString())
                            addPatient.setString(7, txtBedNumber.text.toString())
                            addPatient.setString(8, txtMedication.text.toString())
                            addPatient.setString(9, txtAdmmissionDate.text.toString())
                            addPatient.setString(10, txtMedicationTime.text.toString())

                            addPatient.executeUpdate()

                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@PatientsFragment.context,
                                    "Se agregó el paciente correctamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            clear()
                        }
                    }

                }catch(e:Exception)
                {
                    println("Error: $e")
                }



            }
        }

        return root
    }
}