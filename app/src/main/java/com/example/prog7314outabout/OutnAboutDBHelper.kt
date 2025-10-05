package com.example.prog7314outabout

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OutnAboutDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "OutnAbout.db"
        private const val DATABASE_VERSION = 1
        private const val USER_TABLE_NAME = "users"

        // Columns (match your Register page fields)
        private const val COL_ID = "id"
        private const val COL_NAME = "name"
        private const val COL_SURNAME = "surname"
        private const val COL_NUMBER = "mobile_number"
        private const val COL_EMAIL = "email"
        private const val COL_COUNTRY = "country"
        private const val COL_USERNAME = "username"
        private const val COL_PASSWORD = "password"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $USER_TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NAME TEXT,
                $COL_SURNAME TEXT,
                $COL_NUMBER TEXT,
                $COL_EMAIL TEXT,
                $COL_COUNTRY TEXT,
                $COL_USERNAME TEXT UNIQUE,
                $COL_PASSWORD TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(
        name: String,
        surname: String,
        mobile: String,
        email: String,
        country: String,
        username: String,
        password: String
    ): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, name)
            put(COL_SURNAME, surname)
            put(COL_NUMBER, mobile)
            put(COL_EMAIL, email)
            put(COL_COUNTRY, country)
            put(COL_USERNAME, username)
            put(COL_PASSWORD, password)
        }
        val result = db.insert(USER_TABLE_NAME, null, values)
        db.close()
        return result != -1L
    }

    fun userExists(username: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT 1 FROM $USER_TABLE_NAME WHERE $COL_USERNAME = ?", arrayOf(username))
        val exists = cursor.moveToFirst()
        cursor.close()
        db.close()
        return exists
    }

    // ✅ NEW METHOD: Validate username + password
    fun validateUser(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT 1 FROM $USER_TABLE_NAME WHERE $COL_USERNAME = ? AND $COL_PASSWORD = ?",
            arrayOf(username, password)
        )
        val isValid = cursor.moveToFirst()
        cursor.close()
        db.close()
        return isValid
    }
    fun emailExists(email: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    // Get user by username
    fun getUser(username: String): User? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $USER_TABLE_NAME WHERE $COL_USERNAME = ?", arrayOf(username))
        val user = if (cursor.moveToFirst()) {
            User(
                cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_SURNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_USERNAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD))
            )
        } else null
        cursor.close()
        db.close()
        return user
    }

    // Update user info
    fun updateUser(oldUsername: String, newFirst: String, newLast: String, newUsername: String, newPassword: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", newFirst)
            put("surname", newLast)
            put("username", newUsername)
            put("password", newPassword)
        }

        val result = db.update("users", values, "username = ?", arrayOf(oldUsername))
        db.close()
        return result > 0
    }


    // User data class
    data class User(val name: String, val surname: String, val username: String, val password: String)

}



//package com.example.prog7314outabout
//
//
//import android.content.ContentValues
//import android.content.Context
//import android.database.sqlite.SQLiteDatabase
//import android.database.sqlite.SQLiteOpenHelper
//
//class OutnAboutDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
//
//    companion object {
//        private const val DATABASE_NAME = "OutnAbout.db"
//        private const val DATABASE_VERSION = 1
//        private const val USER_TABLE_NAME = "users"
//
//        // Columns (match your Register page fields)
//        private const val COL_ID = "id"
//        private const val COL_NAME = "name"
//        private const val COL_SURNAME = "surname"
//        private const val COL_NUMBER = "mobile_number"
//        private const val COL_EMAIL = "email"
//        private const val COL_COUNTRY = "country"
//        private const val COL_USERNAME = "username"
//        private const val COL_PASSWORD = "password"
//    }
//
//    override fun onCreate(db: SQLiteDatabase) {
//        val createTable = """
//            CREATE TABLE $USER_TABLE_NAME (
//                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
//                $COL_NAME TEXT,
//                $COL_SURNAME TEXT,
//                $COL_NUMBER TEXT,
//                $COL_EMAIL TEXT,
//                $COL_COUNTRY TEXT,
//                $COL_USERNAME TEXT UNIQUE,
//                $COL_PASSWORD TEXT
//            )
//        """.trimIndent()
//        db.execSQL(createTable)
//    }
//
//    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
//        onCreate(db)
//    }
//
//    fun insertUser(
//        name: String,
//        surname: String,
//        mobile: String,
//        email: String,
//        country: String,
//        username: String,
//        password: String
//    ): Boolean {
//        val db = writableDatabase
//        val values = ContentValues().apply {
//            put(COL_NAME, name)
//            put(COL_SURNAME, surname)
//            put(COL_NUMBER, mobile)
//            put(COL_EMAIL, email)
//            put(COL_COUNTRY, country)
//            put(COL_USERNAME, username)
//            put(COL_PASSWORD, password)
//        }
//        val result = db.insert(USER_TABLE_NAME, null, values)
//        return result != -1L
//    }
//
//    fun userExists(username: String): Boolean {
//        val db = readableDatabase
//        val cursor = db.rawQuery("SELECT 1 FROM $USER_TABLE_NAME WHERE $COL_USERNAME = ?", arrayOf(username))
//        val exists = cursor.moveToFirst()
//        cursor.close()
//        return exists
//    }
//}
