package RecyclerViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import imanol.ramirez.myapplication.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val lblNombre = view.findViewById<TextView>(R.id.lblPatientName)
    val btnEliminar = view.findViewById<ImageView>(R.id.btnDelete)
    val btnEditar = view.findViewById<ImageView>(R.id.btnEdit)
    /**
    val Apellido = view.findViewById<TextView>(R.id.lblTicketID)
    val Edad = view.findViewById<TextView>(R.id.lblTicketsAutor)
    val Enfermedad = view.findViewById<TextView>(R.id.lblAuthorsEmail)
    val NumCuarto = view.findViewById<TextView>(R.id.lblTicketsCreationDate)
    val NumCama = view.findViewById<TextView>(R.id.lblTicketsFinishDate)
    val Medicamentos = view.findViewById<TextView>(R.id.lblTicketsStatus)
    val FechaDeAdmision = view.findViewById<TextView>(R.id.lblDescriptionTicket)
    val HoraDeMedicamentos = view.findViewById<ImageView>(R.id.btnEdit)
    val uuid = view.findViewById<ImageView>(R.id.btnDelete)**/
}
