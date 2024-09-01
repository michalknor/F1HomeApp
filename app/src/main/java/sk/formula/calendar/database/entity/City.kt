package sk.formula.calendar.database.entity

object City {
    const val TABLE_NAME = "City"

    object Column {
        const val ID = "id"
        const val COUNTRY_ID = "country_id"
        const val NAME = "name"
    }

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            ${Column.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.COUNTRY_ID} INTEGER NOT NULL,
            ${Column.NAME} STRING NOT NULL,
            FOREIGN KEY(${Column.COUNTRY_ID}) REFERENCES ${Country.TABLE_NAME}(${Country.Column.ID})
        )
    """

    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}