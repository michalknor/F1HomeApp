package sk.formula.calendar.database.entity

object Season {
    const val TABLE_NAME = "season"

    object Column {
        const val ID = "id"
        const val YEAR = "year"
    }

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            ${Column.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.YEAR} INTEGER NOT NULL
        )
    """

    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}