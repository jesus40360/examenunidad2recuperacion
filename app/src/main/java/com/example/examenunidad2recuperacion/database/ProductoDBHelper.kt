package com.example.examenunidad2recuperacion.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductoDBHelper(context: Context) :
    SQLiteOpenHelper(context, "sistema.db", null, 1) {

    companion object {
        const val TABLA = "productos"
        const val COL_CODIGO = "codigo"
        const val COL_NOMBRE = "nombre"
        const val COL_MARCA = "marca"
        const val COL_PRECIO = "precio"
        const val COL_TIPO = "tipo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE $TABLA (" +
                "$COL_CODIGO INTEGER PRIMARY KEY, " +
                "$COL_NOMBRE TEXT, " +
                "$COL_MARCA TEXT, " +
                "$COL_PRECIO REAL, " +
                "$COL_TIPO TEXT)"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // En este examen no se requiere actualización de versión.
    }
}
