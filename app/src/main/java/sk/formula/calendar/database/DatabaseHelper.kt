import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import sk.formula.calendar.database.entity.Circuit
import sk.formula.calendar.database.entity.City
import sk.formula.calendar.database.entity.Country
import sk.formula.calendar.database.entity.Event
import sk.formula.calendar.database.entity.EventType
import sk.formula.calendar.database.entity.GrandPrix
import sk.formula.calendar.database.entity.Season
import sk.formula.calendar.database.entity.Version

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "f1.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(GrandPrix.CREATE_TABLE)
        db.execSQL(Event.CREATE_TABLE)
        db.execSQL(EventType.CREATE_TABLE)
        db.execSQL(Circuit.CREATE_TABLE)
        db.execSQL(City.CREATE_TABLE)
        db.execSQL(Country.CREATE_TABLE)
        db.execSQL(Version.CREATE_TABLE)
        db.execSQL(Season.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(GrandPrix.DROP_TABLE)
        db.execSQL(Event.DROP_TABLE)
        db.execSQL(EventType.DROP_TABLE)
        db.execSQL(Circuit.DROP_TABLE)
        db.execSQL(City.DROP_TABLE)
        db.execSQL(Country.DROP_TABLE)
        db.execSQL(Version.DROP_TABLE)
        db.execSQL(Season.DROP_TABLE)

        onCreate(db)
    }
}