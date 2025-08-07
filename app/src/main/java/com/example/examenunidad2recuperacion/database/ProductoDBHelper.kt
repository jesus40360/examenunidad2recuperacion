package com.example.examenunidad2recuperacion.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductosDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "productos.db"
        const val DATABASE_VERSION = 1

        const val TABLA = "Productos"
        const val COL_ID = "codigo"
        const val COL_NOMBRE = "nombre"
        const val COL_MARCA = "marca"
        const val COL_PRECIO = "precio"
        const val COL_TIPO = "tipo"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val sql = "CREATE TABLE $TABLA (" +
                "$COL_ID INTEGER PRIMARY KEY, " +   // <-- quitar AUTOINCREMENT
                "$COL_NOMBRE TEXT, " +
                "$COL_MARCA TEXT, " +
                "$COL_PRECIO REAL, " +
                "$COL_TIPO TEXT)"
        db.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA")
        onCreate(db)
    }
}
