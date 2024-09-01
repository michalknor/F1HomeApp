package sk.formula.calendar.database.entity

object Country {
    const val TABLE_NAME = "Country"

    object Column {
        const val ID = "id"
        const val ABBREVIATION = "abbreviation"
        const val NAME = "name"
    }

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            ${Column.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.ABBREVIATION} STRING NOT NULL,
            ${Column.NAME} STRING NOT NULL
        )
    """

    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}