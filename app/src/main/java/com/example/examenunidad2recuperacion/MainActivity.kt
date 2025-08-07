package com.example.examenunidad2recuperacion

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txtCodigo: EditText
    private lateinit var txtNombre: EditText
    private lateinit var txtMarca: EditText
    private lateinit var txtPrecio: EditText
    private lateinit var rgTipo: RadioGroup
    private lateinit var rbPerecedero: RadioButton
    private lateinit var rbNoPerecedero: RadioButton

    private lateinit var btnGuardar: Button
    private lateinit var btnLimpiar: Button
    private lateinit var btnNuevo: Button
    private lateinit var btnIrEditar: Button

    private lateinit var db: ProductosDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias UI
        txtCodigo = findViewById(R.id.txtCodigo)
        txtNombre = findViewById(R.id.txtNombre)
        txtMarca = findViewById(R.id.txtMarca)
        txtPrecio = findViewById(R.id.txtPrecio)
        rgTipo = findViewById(R.id.rgTipo)
        rbPerecedero = findViewById(R.id.rbPerecedero)
        rbNoPerecedero = findViewById(R.id.rbNoPerecedero)

        btnGuardar = findViewById(R.id.btnGuardar)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        btnNuevo = findViewById(R.id.btnNuevo)
        btnIrEditar = findViewById(R.id.btnIrEditar)

        db = ProductosDB(this)

        btnGuardar.setOnClickListener {
            val codigo = txtCodigo.text.toString().toIntOrNull()
            val nombre = txtNombre.text.toString()
            val marca = txtMarca.text.toString()
            val precio = txtPrecio.text.toString().toFloatOrNull()

            val tipo = when {
                rbPerecedero.isChecked -> "Perecedero"
                rbNoPerecedero.isChecked -> "No Perecedero"
                else -> ""
            }

            if (codigo == null || nombre.isBlank()) {
                Toast.makeText(this, "Código y nombre son requeridos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val producto = Producto(
                codigo,
                nombre,
                marca.ifBlank { "Sin marca" },
                precio ?: 0f,
                tipo.ifBlank { "No especificado" }
            )

            val existente = db.findByCodigo(codigo)

            val resultado = if (existente != null) {
                db.update(producto)
            } else {
                db.insert(producto)
            }

            if (resultado) {
                Toast.makeText(this, if (existente != null) "Producto actualizado" else "Producto insertado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
            }
        }


        // Botón Limpiar
        btnLimpiar.setOnClickListener {
            limpiarCampos()
        }

        // Botón Nuevo
        btnNuevo.setOnClickListener {
            limpiarCampos()
            Toast.makeText(this, "Listo para nuevo registro", Toast.LENGTH_SHORT).show()
        }

        // Botón Ir Editar
        btnIrEditar.setOnClickListener {
            startActivity(Intent(this, EditarActivity::class.java))
        }
    }

    private fun limpiarCampos() {
        txtCodigo.text.clear()
        txtNombre.text.clear()
        txtMarca.text.clear()
        txtPrecio.text.clear()
        rgTipo.clearCheck()
    }
}
