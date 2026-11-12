package com.example.healthmate.data

import android.content.Context
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.healthmate.model.HealthRecord

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "healthmate.db"
        private const val DATABASE_VERSION = 2

        const val TABLE_RECORDS = "health_records"
        const val COL_ID = "id"
        const val COL_STEPS = "steps"
        const val COL_CALORIES = "calories"
        const val COL_WATER = "water"
        const val COL_DATE = "record_date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE health_records (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                steps INTEGER NOT NULL,
                calories INTEGER NOT NULL,
                water INTEGER NOT NULL,
                record_date TEXT NOT NULL
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (oldVersion < 2) {
            db.execSQL(
                """
                CREATE TABLE health_records_new (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    steps INTEGER NOT NULL,
                    calories INTEGER NOT NULL,
                    water INTEGER NOT NULL,
                    record_date TEXT NOT NULL
                )
                """.trimIndent()
            )

            db.execSQL(
                """
                INSERT INTO health_records_new
                    (id, steps, calories, water, record_date)
                SELECT
                    id,
                    steps,
                    CAST(ROUND(calories) AS INTEGER),
                    water,
                    record_date
                FROM health_records
                """.trimIndent()
            )

            db.execSQL("DROP TABLE health_records")

            db.execSQL(
                "ALTER TABLE health_records_new RENAME TO health_records"
            )
        }
    }

    fun insertRecord(
        steps: Int,
        calories: Int,
        water: Int,
        date: String
    ): Boolean {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COL_STEPS, steps)
            put(COL_CALORIES, calories)
            put(COL_WATER, water)
            put(COL_DATE, date)
        }

        val result = db.insert(TABLE_RECORDS, null, values)
        return result != -1L
    }

    fun getAllRecords(): List<HealthRecord> {
        val records = mutableListOf<HealthRecord>()
        val db = readableDatabase

        val cursor = db.query(
            TABLE_RECORDS,
            null,
            null,
            null,
            null,
            null,
            "$COL_DATE DESC, $COL_ID DESC"
        )

        cursor.use {
            while (it.moveToNext()) {
                val record = HealthRecord(
                    id = it.getInt(it.getColumnIndexOrThrow(COL_ID)),
                    steps = it.getInt(it.getColumnIndexOrThrow(COL_STEPS)),
                    calories = it.getInt(
                        it.getColumnIndexOrThrow(COL_CALORIES)
                    ),
                    water = it.getInt(it.getColumnIndexOrThrow(COL_WATER)),
                    date = it.getString(it.getColumnIndexOrThrow(COL_DATE))
                )

                records.add(record)
            }
        }

        return records
    }

    // Delete a health record using its ID
    fun deleteRecord(recordId: Int): Boolean {
        val db = writableDatabase

        val rowsDeleted = db.delete(
            TABLE_RECORDS,
            "$COL_ID = ?",
            arrayOf(recordId.toString())
        )

        return rowsDeleted > 0
    }

    fun updateRecord(
        recordId: Int,
        steps: Int,
        calories: Int,
        water: Int
    ): Boolean {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COL_STEPS, steps)
            put(COL_CALORIES, calories)
            put(COL_WATER, water)
        }

        val rowsUpdated = db.update(
            TABLE_RECORDS,
            values,
            "$COL_ID = ?",
            arrayOf(recordId.toString())
        )

        return rowsUpdated > 0
    }
}
