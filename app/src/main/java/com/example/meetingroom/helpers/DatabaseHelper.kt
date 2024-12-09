package com.example.meetingroom.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSİON) {
    companion object {
        private const val DATABASE_NAME = "meeting_room.db"
        private const val DATABASE_VERSİON = 2

        const val TABLE_NAME = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_MAİL = "mail"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE ${TABLE_NAME} (
                ${COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${COLUMN_NAME} TEXT NOT NULL,
                ${COLUMN_PASSWORD} TEXT NOT NULL,
                ${COLUMN_MAİL} TEXT NOT NULL UNIQUE
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(name: String, password: String, email: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_MAİL, email)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteUser(userId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(userId.toString()))
        db.close()
    }

    fun checkUserCredentials(email: String, password: String) : Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_MAİL = ? AND $COLUMN_PASSWORD = ?"
        val cursor = db.rawQuery(query, arrayOf(email, password))

        val isValid = cursor.count > 0
        cursor.close()
        db.close()

        return isValid
    }
    fun checkUserCredentials(email: String) : Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_MAİL = ?"
        val cursor = db.rawQuery(query, arrayOf(email))

        val isValid = cursor.count > 0
        cursor.close()
        db.close()

        return isValid
    }
}