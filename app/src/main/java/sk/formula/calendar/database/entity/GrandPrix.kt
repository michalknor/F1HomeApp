package sk.formula.calendar.database.entity

object GrandPrix {
    const val TABLE_NAME = "season"

    object Column {
        const val ID = "id"
        const val VERSION_ID = "version_id"
        const val CIRCUIT_ID = "circuit_id"
        const val ROUND = "model"
        const val NAME = "name"
        const val CANCELLED = "cancelled"
    }

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            ${Column.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.VERSION_ID} INTEGER NOT NULL,
            ${Column.CIRCUIT_ID} INTEGER NOT NULL,
            ${Column.ROUND} INTEGER NOT NULL,
            ${Column.NAME} STRING NOT NULL,
            ${Column.CANCELLED} INTEGER NOT NULL,
            FOREIGN KEY(${Column.VERSION_ID}) REFERENCES ${Version.TABLE_NAME}(${Version.Column.ID}),
            FOREIGN KEY(${Column.CIRCUIT_ID}) REFERENCES ${Circuit.TABLE_NAME}(${Circuit.Column.ID})
        )
    """

    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}