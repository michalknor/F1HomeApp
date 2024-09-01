package sk.formula.calendar.database.entity

object Event {
    const val TABLE_NAME = "Event"

    object Column {
        const val ID = "id"
        const val GRAND_PRIX_ID = "grand_prix_id"
        const val EVENT_TYPE_ID = "event_type_id"
        const val ROUND = "round"
        const val TIME_FROM = "time_from"
        const val TIME_TO = "time_to"
    }

    const val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            ${Column.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Column.GRAND_PRIX_ID} INTEGER NOT NULL,
            ${Column.EVENT_TYPE_ID} INTEGER NOT NULL,
            ${Column.ROUND} INTEGER NOT NULL,
            ${Column.TIME_FROM} INTEGER NOT NULL,
            ${Column.TIME_TO} INTEGER NOT NULL,
            FOREIGN KEY(${Column.GRAND_PRIX_ID}) REFERENCES ${GrandPrix.TABLE_NAME}(${GrandPrix.Column.ID}),
            FOREIGN KEY(${Column.EVENT_TYPE_ID}) REFERENCES ${EventType.TABLE_NAME}(${EventType.Column.ID})
        )
    """

    const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}