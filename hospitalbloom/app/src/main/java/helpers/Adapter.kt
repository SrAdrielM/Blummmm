package helpers

import Modelo.dataClassPacientes
import adrielmoreno.jaimeperla.hospitalbloom.R
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class Adapter {
    class PacienteAdapter(private val pacientes: List<dataClassPacientes>, private val context: Context) :
        RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacienteViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_card_pacientes, parent, false)
            return PacienteViewHolder(view)
        }

        override fun onBindViewHolder(holder: PacienteViewHolder, position: Int) {
            val paciente = pacientes[position]
            holder.bind(paciente)
            holder.itemView.setOnClickListener {
                showPatientInfoDialog(paciente)
            }
        }

        override fun getItemCount(): Int = pacientes.size

        private fun showPatientInfoDialog(paciente: dataClassPacientes) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.info_paciente, null)

            val textViewName = dialogView.findViewById<TextView>(R.id.txtNombresShow)
            val textViewSurname = dialogView.findViewById<TextView>(R.id.txtApellidosShow)
            val textViewAge = dialogView.findViewById<TextView>(R.id.txtEdadShow)
            val textViewBirthDate = dialogView.findViewById<TextView>(R.id.txtNacimientoShow)
            val textViewDisease = dialogView.findViewById<TextView>(R.id.txtEnfermedadShow)
            val textViewRoomNumber = dialogView.findViewById<TextView>(R.id.txtHabitacionShow)
            val textViewBedNumber = dialogView.findViewById<TextView>(R.id.txtCamaShow)
            val textViewMedicament = dialogView.findViewById<TextView>(R.id.txtMedicamento1Show)
            val textViewMedHour = dialogView.findViewById<TextView>(R.id.txtHoraMed1Show)
            val textViewMedExtra = dialogView.findViewById<TextView>(R.id.txtInfoExtraShow)

            textViewName.text = "Nombre: ${paciente.name}"
            textViewSurname.text = "Apellidos: ${paciente.surname}"
            textViewAge.text = "Edad: ${paciente.age}"
            textViewBirthDate.text = "Fecha de Nacimiento: ${paciente.birthDate}"
            textViewDisease.text = "Enfermedad: ${paciente.disease}"
            textViewRoomNumber.text = "Número de Habitación: ${paciente.roomNumber}"
            textViewBedNumber.text = "Número de Cama: ${paciente.bedNumber}"
            textViewMedicament.text = "${}"

            AlertDialog.Builder(context)
                .setTitle("Información del Paciente")
                .setView(dialogView)
                .setPositiveButton("OK", null)
                .show()
        }

        class PacienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textViewName: TextView = itemView.findViewById(R.id.txtNombre)

            fun bind(paciente: dataClassPacientes) {
                textViewName.text = paciente.name
            }
        }
    }
}
