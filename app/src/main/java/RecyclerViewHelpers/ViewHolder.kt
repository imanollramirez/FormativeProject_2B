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
    val btnVerInformacion = view.findViewById<ImageView>(R.id.btnPatientInformation)
}
