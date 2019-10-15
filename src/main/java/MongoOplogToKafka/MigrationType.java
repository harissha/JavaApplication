package MongoOplogToKafka;

public enum MigrationType {
    MONGO_CHANGE_STREAM("mongoChangeStreams"),
    UNKNOWN("");


    private final String gValue;

    MigrationType(final String aValue) {
        gValue = aValue;
    }

    public static MigrationType get(final String aType) {
        MigrationType theReturnValue = UNKNOWN;
        switch (aType) {
            case "mongoChangeStreams":
                theReturnValue = MONGO_CHANGE_STREAM;
                break;
            default:
                break;
        }
        return theReturnValue;
    }
}

