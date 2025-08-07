package com.example.examenunidad2recuperacion

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class EditarActivity : AppCompatActivity() {

    private lateinit var txtCodigo: EditText
    private lateinit var txtNombre: EditText
    private lateinit var txtMarca: EditText
    private lateinit var txtPrecio: EditText
    private lateinit var rgTipo: RadioGroup
    private lateinit var rbPerecedero: RadioButton
    private lateinit var rbNoPerecedero: RadioButton
    private lateinit var btnBuscar: Button
    private lateinit var btnActualizar: Button
    private lateinit var btnBorrar: Button
    private lateinit var btnCerrar: Button

    private lateinit var db: ProductosDB
    private var productoEncontrado: Producto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        // Asignar vistas
        txtCodigo = findViewById(R.id.txtCodigoEdit)
        txtNombre = findViewById(R.id.txtNombreEdit)
        txtMarca = findViewById(R.id.txtMarcaEdit)
        txtPrecio = findViewById(R.id.txtPrecioEdit)
        rgTipo = findViewById(R.id.rgTipoEdit)
        rbPerecedero = findViewById(R.id.rbPerecederoEdit)
        rbNoPerecedero = findViewById(R.id.rbNoPerecederoEdit)
        btnBuscar = findViewById(R.id.btnBuscarEdit)
        btnActualizar = findViewById(R.id.btnActualizar)
        btnBorrar = findViewById(R.id.btnBorrar)
        btnCerrar = findViewById(R.id.btnCerrar)

        db = ProductosDB(this)

        // Botón Buscar
        btnBuscar.setOnClickListener {
            val codigo = txtCodigo.text.toString().toIntOrNull()
            if (codigo == null) {
                Toast.makeText(this, "Código inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val producto = db.findByCodigo(codigo)
            if (producto != null) {
                productoEncontrado = producto
                txtNombre.setText(producto.nombre)
                txtMarca.setText(producto.marca)
                txtPrecio.setText(producto.precio.toString())
                if (producto.tipo.equals("perecedero", ignoreCase = true)) {
                    rbPerecedero.isChecked = true
                } else {
                    rbNoPerecedero.isChecked = true
                }
            } else {
                Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            }
        }

        // Botón Actualizar
        btnActualizar.setOnClickListener {
            if (productoEncontrado == null) {
                Toast.makeText(this, "Primero busque un producto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nombre = txtNombre.text.toString()
            val marca = txtMarca.text.toString()
            val precio = txtPrecio.text.toString().toFloatOrNull()
            val tipo = when (rgTipo.checkedRadioButtonId) {
                R.id.rbPerecederoEdit -> "perecedero"
                R.id.rbNoPerecederoEdit -> "No perecedero"
                else -> ""
            }

            if (nombre.isBlank() || marca.isBlank() || precio == null || tipo.isBlank()) {
                Toast.makeText(this, "Completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val productoEditado = Producto(
                productoEncontrado!!.codigo,
                nombre,
                marca,
                precio,
                tipo
            )

            val actualizado = db.update(productoEditado)
            if (actualizado) {
                Toast.makeText(this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Error al actualizar el producto", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón Borrar
        btnBorrar.setOnClickListener {
            val codigo = productoEncontrado?.codigo
            if (codigo != null) {
                val eliminado = db.deleteByCodigo(codigo)
                if (eliminado) {
                    Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show()
                    limpiarCampos()
                } else {
                    Toast.makeText(this, "No se pudo eliminar", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Primero busque un producto", Toast.LENGTH_SHORT).show()
            }
        }

        // Botón Cerrar
        btnCerrar.setOnClickListener {
            finish()
        }
    }

    private fun limpiarCampos() {
        txtCodigo.text.clear()
        txtNombre.text.clear()
        txtMarca.text.clear()
        txtPrecio.text.clear()
        rgTipo.clearCheck()
        productoEncontrado = null
    }
}
