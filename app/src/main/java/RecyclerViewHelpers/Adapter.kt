package RecyclerViewHelper

import Model.Connection
import Model.Patients
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import imanol.ramirez.myapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Adapter(private var Data: List<Patients>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_itemcard_patient, parent, false)

        return ViewHolder(view)
    }

    fun ActualizarPantalla(Nombre: String,
                     LastNombre: String,
                     Edad: Int,
                     Enfermedad: String,
                     NumCuarto: Int,
                     NumCama: Int,
                     Medicamentos: String,
                     AddmissionDate: String,
                     TiempoDeMedicamentos: String,
                     UUID: String){
        val index = Data.indexOfFirst { it.uuid == UUID }
        Data[index].name = Nombre
        Data[index].lastName = LastNombre
        Data[index].age = Edad
        Data[index].disease = Enfermedad
        Data[index].roomNumber = NumCuarto
        Data[index].bedNumber = NumCama
        Data[index].medication = Medicamentos
        Data[index].addmissionDate = AddmissionDate
        Data[index].medicationTime = TiempoDeMedicamentos
        notifyDataSetChanged()
    }

    fun UpdateList(newList: List<Patients>)
    {
        Data = newList
        notifyDataSetChanged()
    }

    fun EditData(
        Nombre: String,
        Apellido: String,
        Edad: Int,
        Enfermedad: String,
        NumCuarto: Int,
        NumCama: Int,
        Medicamentos: String,
        AddmissionDate: String,
        TiempoDeMedicamentos: String,
        UUID: String
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val objCon = Connection().StringConection()

            val updateTicket =
                objCon?.prepareStatement("UPDATE TBPatients SET Name= ?,  LastName = ?, Age = ?, Disease = ?, RoomNumber = ?, BedNumber = ?, Medication = ?, AddmissionDate = ?, MedicationDate = ?   WHERE UUID_Patients = ?")!!
            updateTicket.setString(1, Nombre)
            updateTicket.setString(2, Apellido)
            updateTicket.setString(3, Edad.toString())
            updateTicket.setString(4, Enfermedad)
            updateTicket.setString(5, NumCuarto.toString())
            updateTicket.setString(6, NumCama.toString())
            updateTicket.setString(7, Medicamentos)
            updateTicket.setString(8, AddmissionDate)
            updateTicket.setString(9, TiempoDeMedicamentos)
            updateTicket.setString(10, UUID)

            updateTicket.executeUpdate()

            withContext(Dispatchers.Main){
                ActualizarPantalla(
                    Nombre,
                    Apellido,
                    Edad,
                    Enfermedad,
                    NumCuarto,
                    NumCama,
                    Medicamentos,
                    AddmissionDate,
                    TiempoDeMedicamentos,
                    UUID)
            }
        }
    }

        fun DeleteData(Nombre: String, posicion: Int) {
            val dataList = Data.toMutableList()
            dataList.removeAt(posicion)

            GlobalScope.launch(Dispatchers.IO) {
                val objCon = Connection().StringConection()

                val borrarPaciente = objCon?.prepareStatement("DELETE FROM TBpatient WHERE Nombre = ?")!!
                borrarPaciente.setString(1, Nombre)
                borrarPaciente.executeUpdate()

                val commit = objCon.prepareStatement("commit")
                commit.executeUpdate()
            }

            Data = dataList.toList()

            notifyItemRemoved(posicion)
            notifyDataSetChanged()
        }

        override fun getItemCount() = Data.size

        override fun onBindViewHolder(holder: ViewHolder, posicion: Int) {

            val patient = Data[posicion]

            holder.lblNombre.text = patient.name

            holder.btnEliminar.setOnClickListener {

                val context = holder.itemView.context

                val builder = AlertDialog.Builder(context)
                builder.setMessage("¿Desea eliminar el paciente?")

                builder.setPositiveButton("Aceptar") { dialog, which ->
                    DeleteData(patient.name, posicion)
                    Toast.makeText(context, "Paciente eliminado correctamente", Toast.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }

            holder.btnEditar.setOnClickListener {
                val context = holder.itemView.context

                val layout = LinearLayout(context)
                layout.orientation = LinearLayout.VERTICAL

                val txt1 = EditText(context)
                layout.addView(txt1)
                txt1.setHint(patient.name)
                val txt2 = EditText(context)
                layout.addView(txt2)
                txt2.setHint(patient.lastName)
                val txt3 = EditText(context)
                layout.addView(txt3)
                txt3.setHint(patient.age)
                val txt4 = EditText(context)
                layout.addView(txt4)
                txt4.setHint(patient.disease)
                val txt5 = EditText(context)
                txt5.setHint(patient.roomNumber)
                layout.addView(txt5)
                val txt6 = EditText(context)
                txt6.setHint(patient.bedNumber)
                layout.addView(txt6)
                val txt7 = EditText(context)
                txt7.setHint(patient.medication)
                layout.addView(txt7)
                val txt8 = EditText(context)
                txt8.setHint(patient.addmissionDate)
                layout.addView(txt8)
                val txt9 = EditText(context)
                txt9.setHint(patient.medicationTime)
                layout.addView(txt9)

                val uuid = patient.uuid

                val builder = AlertDialog.Builder(context)
                builder.setView(layout)
                builder.setTitle("Editar paciente")


                builder.setPositiveButton("Aceptar") { dialog, which ->

                    EditData(txt1.text.toString(),txt2.text.toString(),txt3.text.toString().toInt(),txt4.text.toString(),txt5.text.toString().toInt(),txt6.text.toString().toInt(),txt7.text.toString(),txt8.text.toString(),txt9.text.toString(),uuid)
                    Toast.makeText(context, "Paciente editado correctamente", Toast.LENGTH_SHORT).show()

                }

                builder.setNegativeButton("Cancelar") { dialog, which ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        }
}
