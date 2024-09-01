package sk.formula.calendar.database.entity

object Circuit {
    const val TABLE_NAME = "Circuit"

    object Column {
        const val ID = "id"
        const val CITY_ID = "city_id"
        const val NAME = "name"
    }

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            ${Column.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.CITY_ID} INTEGER NOT NULL,
            ${Column.NAME} STRING NOT NULL,
            FOREIGN KEY(${Column.CITY_ID}) REFERENCES ${City.TABLE_NAME}(${City.Column.ID})
        )
    """

    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}