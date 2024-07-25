package adrielmoreno.jaimeperla.hospitalbloom

import Modelo.ClaseConexion
import Modelo.dataClassMedicina
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.app.DatePickerDialog
import android.text.InputType
import android.widget.Toast
import androidx.core.view.isEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.UUID

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Pacientes : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    fun getMedicina(): List<dataClassMedicina>{
        val conexion = ClaseConexion().cadenaConexion()
        val statement = conexion?.createStatement()
        val resultSet = statement?.executeQuery("SELECT * FROM Medicamentoo")!!

        val listaMedicina = mutableListOf<dataClassMedicina>()

        while (resultSet.next())
        {
         val uuid_Medicina =resultSet.getString("UUID_Medicamento")
            val nombreMedicina = resultSet.getString("Nombre_medicamento")

            val medicinaCompleta = dataClassMedicina(uuid_Medicina, nombreMedicina)

            listaMedicina.add(medicinaCompleta)
        }
        return listaMedicina
    }


    fun showYearPickerDialog(textView: EditText) {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)

        // Configurar el rango de fechas permitidas
        val sixteenYearsAgo = Calendar.getInstance().apply {
            set(year - 16, Calendar.JANUARY, 1) // 16 años atrás desde el primer día del año
        }
        val today = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, _, _ ->
                textView.setText(selectedYear.toString())
            },
            year,
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )

        // Establecer el rango de fechas
        datePickerDialog.datePicker.minDate = sixteenYearsAgo.timeInMillis
        datePickerDialog.datePicker.maxDate = today.timeInMillis

        // Ocultar el selector de mes y día
        val dayPickerId = resources.getIdentifier("android:id/day", null, null)
        val monthPickerId = resources.getIdentifier("android:id/month", null, null)

        if (dayPickerId != 0 && monthPickerId != 0) {
            val dayPicker = datePickerDialog.datePicker.findViewById<View>(dayPickerId)
            val monthPicker = datePickerDialog.datePicker.findViewById<View>(monthPickerId)

            dayPicker?.visibility = View.GONE
            monthPicker?.visibility = View.GONE
        }

        datePickerDialog.show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_addpaciente, container, false)

        val txtNombresPaciente = root.findViewById<EditText>(R.id.txtNombresPaciente)
        val txtApellidos = root.findViewById<EditText>(R.id.txtApellidos)
        val txtedad = root.findViewById<EditText>(R.id.txtEdad)
        val txtFechaNacimiento = root.findViewById<EditText>(R.id.txtNacimiento)
        val txtEnfermedad = root.findViewById<EditText>(R.id.txtEnfermedad)
        val txtNumerohabitacion = root.findViewById<EditText>(R.id.txtHabitacion)
        val txtnumeroCama = root.findViewById<EditText>(R.id.txtCama)
        val spMedicamento = root.findViewById<Spinner>(R.id.spnMedicamento1)
        val txtAplicaion = root.findViewById<EditText>(R.id.txtHoraMed1)
        val txtMedicamentosExtas = root.findViewById<EditText>(R.id.txtMedicamentosExtras)
        val icRegresar = root.findViewById<ImageView>(R.id.icRegresar)
        val btnAgregarPacientes = root.findViewById<Button>(R.id.btnAgregarPaciente)
        val btnAgregarMedicamentos = root.findViewById<Button>(R.id.btnMedeicamento)


        txtFechaNacimiento.inputType = InputType.TYPE_NULL
    txtFechaNacimiento.setOnClickListener {
    showYearPickerDialog(txtFechaNacimiento)
    }
        btnAgregarPacientes.setOnClickListener {
            if (txtNombresPaciente.text.toString().isEmpty() || txtApellidos.text.toString().isEmpty() || txtedad.text.toString().isEmpty() || txtFechaNacimiento.text.toString().isEmpty()|| txtEnfermedad.text.toString().isEmpty()||spMedicamento.isEmpty())
            {
                Toast.makeText(
                    requireContext(),
                    "Por favor, complete todos los campos.",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                GlobalScope.launch(Dispatchers.IO)
                {
                    try {
                        val objConexion = ClaseConexion().cadenaConexion()
                        val medicina = getMedicina()

                        val addPaciente = objConexion?.prepareStatement("INSERT INTO pacientee (UUID_Paciente, Nombres, Apellidos, Edad, Efermedad, Fecha_Nacimiento, numero_habitacion, numero_cama, UUID_Medicamento, hora_aplicacio, medicamento_adiccional)VALUES (?,?,?,?,?,?,?,?,?,?,?)")!!
                        addPaciente.setString(1,UUID.randomUUID().toString())
                        addPaciente.setString(2,txtNombresPaciente.text.toString())
                        addPaciente.setString(3,txtApellidos.text.toString())
                        addPaciente.setInt(4,txtedad.text.toString().toInt())
                        addPaciente.setString(5,txtEnfermedad.text.toString())
                        addPaciente.setString(6,txtFechaNacimiento.text.toString())
                        addPaciente.setString(7,txtNumerohabitacion.text.toString())
                        addPaciente.setInt(8,txtnumeroCama.text.toString().toInt())
                        addPaciente.setString(9,medicina[spMedicamento.selectedItemPosition].UUID_Medicamento)
                        addPaciente.setString(10,txtAplicaion.text.toString())
                        addPaciente.setString(11,txtMedicamentosExtas.text.toString())

                        addPaciente.executeUpdate()
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                "Cita agendada exitosamente.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                requireContext(),
                                "Error al agendar la cita: ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

            }

        }
        return root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            Pacientes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}