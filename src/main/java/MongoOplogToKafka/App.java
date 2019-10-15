package MongoOplogToKafka;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class App {

    private static final Logger LOGGER = Logger.getLogger(App.class);

    public static void main(final String... aArguments) {
        try {
        	
            InputParameters theArgs = new InputParameters(aArguments);
            LOGGER.info(theArgs);
            switch (MigrationType.get(theArgs.getMigrationType())) {
            case MONGO_CHANGE_STREAM:
                MongoChangeStreams mongoChangeStreams = new MongoChangeStreams();
                mongoChangeStreams.changeStream();
                break;
            default:
                LOGGER.info("Call Service with PopulateAttributeCache | UpdateAttributeCache ...");
            }

        } catch (final Exception theEx) {
            LOGGER.info(ExceptionUtils.getStackTrace(theEx));
        }
    }
}
