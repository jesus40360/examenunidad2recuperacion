package com.example.examenunidad2recuperacion

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.examenunidad2recuperacion.database.ProductosDBHelper

class ProductosDB(context: Context) {

    private val helper = ProductosDBHelper(context)

    fun insert(producto: Producto): Boolean {
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(ProductosDBHelper.COL_ID, producto.codigo)
            put(ProductosDBHelper.COL_NOMBRE, producto.nombre)
            put(ProductosDBHelper.COL_MARCA, producto.marca)
            put(ProductosDBHelper.COL_PRECIO, producto.precio)
            put(ProductosDBHelper.COL_TIPO, producto.tipo)
        }
        val result = db.insertWithOnConflict(
            ProductosDBHelper.TABLA,
            null,
            values,
            android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE
        )
        db.close()
        return result != -1L
    }

    fun findByCodigo(codigo: Int): Producto? {
        val db = helper.readableDatabase
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM ${ProductosDBHelper.TABLA} WHERE ${ProductosDBHelper.COL_ID} = ?",
            arrayOf(codigo.toString())
        )
        var producto: Producto? = null
        if (cursor.moveToFirst()) {
            producto = Producto(
                cursor.getInt(cursor.getColumnIndexOrThrow(ProductosDBHelper.COL_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(ProductosDBHelper.COL_NOMBRE)),
                cursor.getString(cursor.getColumnIndexOrThrow(ProductosDBHelper.COL_MARCA)),
                cursor.getFloat(cursor.getColumnIndexOrThrow(ProductosDBHelper.COL_PRECIO)),
                cursor.getString(cursor.getColumnIndexOrThrow(ProductosDBHelper.COL_TIPO))
            )
        }
        cursor.close()
        db.close()
        return producto
    }

    fun update(producto: Producto): Boolean {
        val db = helper.writableDatabase
        val values = ContentValues().apply {
            put(ProductosDBHelper.COL_NOMBRE, producto.nombre)
            put(ProductosDBHelper.COL_MARCA, producto.marca)
            put(ProductosDBHelper.COL_PRECIO, producto.precio)
            put(ProductosDBHelper.COL_TIPO, producto.tipo)
        }
        val result = db.update(
            ProductosDBHelper.TABLA,
            values,
            "${ProductosDBHelper.COL_ID} = ?",
            arrayOf(producto.codigo.toString())
        )
        db.close()
        return result > 0
    }

    fun deleteByCodigo(codigo: Int): Boolean {
        val db = helper.writableDatabase
        val result = db.delete(
            ProductosDBHelper.TABLA,
            "${ProductosDBHelper.COL_ID} = ?",
            arrayOf(codigo.toString())
        )
        db.close()
        return result > 0
    }
}
