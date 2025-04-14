package com.example.polimedapplication.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BazaDateAjutor(context: Context) : SQLiteOpenHelper(context, NUME_BD, null, VERSIUNE_BD) {

    companion object {
        const val NUME_BD = "PoliMed.db"
        const val VERSIUNE_BD = 5
    }

    override fun onCreate(db: SQLiteDatabase) {
        // ======================== TABEL UTILIZATORI ========================
        db.execSQL("""
        CREATE TABLE IF NOT EXISTS Utilizatori (
            idUtilizator TEXT PRIMARY KEY,
            cnp TEXT UNIQUE NOT NULL,
            parola TEXT NOT NULL,
            rol TEXT NOT NULL,
            aprobat INTEGER DEFAULT 0
        )
    """)

        // ======================== TABEL PACIENTI ========================
        db.execSQL("""
        CREATE TABLE IF NOT EXISTS Pacienti (
            idPacient INTEGER PRIMARY KEY AUTOINCREMENT,
            nume TEXT,
            prenume TEXT,
            telefon TEXT,
            adresa TEXT,
            idUtilizator TEXT,
            FOREIGN KEY(idUtilizator) REFERENCES Utilizatori(idUtilizator) ON DELETE CASCADE
        )
    """)

        // ======================== TABEL MEDICI ========================
        db.execSQL("""
        CREATE TABLE IF NOT EXISTS Medici (
            idMedic INTEGER PRIMARY KEY AUTOINCREMENT,
            nume TEXT,
            prenume TEXT,
            telefon TEXT,
            specializare TEXT,
            idUtilizator TEXT,
            FOREIGN KEY(idUtilizator) REFERENCES Utilizatori(idUtilizator) ON DELETE CASCADE
        )
    """)

        // ======================== TABEL MEDICAMENTE ========================
        db.execSQL("""
        CREATE TABLE IF NOT EXISTS Medicamente (
            idMedicament INTEGER PRIMARY KEY AUTOINCREMENT,
            cnpPacient TEXT,
            idMedic INTEGER,
            numeMedicament TEXT,
            doza REAL,
            unitate TEXT,
            frecventa TEXT,
            alias TEXT,
            instructiuni TEXT,
            dataIncepere TEXT,
            dataIncheiere TEXT,
            FOREIGN KEY(cnpPacient) REFERENCES Pacienti(cnp),
            FOREIGN KEY(idMedic) REFERENCES Medici(idMedic)
        )
    """)

        // ======================== TABEL ADMINISTRARI PROGRAMATE ========================
        db.execSQL("""
    CREATE TABLE IF NOT EXISTS AdministrariMedicamente (
        idAdministrare INTEGER PRIMARY KEY AUTOINCREMENT,
        idMedicament INTEGER NOT NULL,
        dataAdministrare TEXT NOT NULL,
        oraAdministrare TEXT NOT NULL,
        ziSaptamana TEXT,
        FOREIGN KEY(idMedicament) REFERENCES Medicamente(idMedicament) ON DELETE CASCADE
    )
""")

    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS AdministrariProgramate")
        db.execSQL("DROP TABLE IF EXISTS Medicamente")
        db.execSQL("DROP TABLE IF EXISTS Medici")
        db.execSQL("DROP TABLE IF EXISTS Pacienti")
        db.execSQL("DROP TABLE IF EXISTS Utilizatori")
        onCreate(db)
    }
}
