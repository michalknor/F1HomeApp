package sk.formula.calendar.database.entity

object Version {
    const val TABLE_NAME = "season"
    object Column {
        const val ID = "id"
        const val SEASON_ID = "season_id"
        const val CREATED = "created"
    }

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            ${Column.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.SEASON_ID} INTEGER NOT NULL,
            ${Column.CREATED} INTEGER NOT NULL,
            FOREIGN KEY(${Column.SEASON_ID}) REFERENCES ${Season.TABLE_NAME}(${Season.Column.ID})
        )
    """

    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}