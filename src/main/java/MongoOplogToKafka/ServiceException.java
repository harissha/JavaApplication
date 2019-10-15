package MongoOplogToKafka;

public class ServiceException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String message = null; // NOSONAR

    private Exception exception = null; // NOSONAR

    private Throwable throwable = null; // NOSONAR

    public ServiceException() {
        super();
    }

    /*
     * Constructor that accepts a message
     */
    public ServiceException(final String message) {

        super(message);
        this.message = message;
    }

    public ServiceException(final Throwable cause) {
        super(cause);
        this.message = cause.getMessage();
        this.throwable = cause;
    }

    public ServiceException(final Exception exception) {
        super(exception);
        this.message = exception.getMessage();
        this.exception = exception;
    }

    public ServiceException(final String message, final Exception exception) {
        super(exception);
        this.message = message;
        this.exception = exception;

    }

    public ServiceException(final String message, final Throwable cause) {
        super(message, cause);
        this.message = message;
        this.throwable = cause;
    }

    public String getDetailedMessage() {
        if (this.getCause() != null) {
            final StringBuilder sb = new StringBuilder();
            sb.append(this.toString()).append("; ");
            if (this.getCause() instanceof ServiceException) {
                sb.append(((ServiceException) this.getCause()).getDetailedMessage());
            } else {
                sb.append(this.getCause());
            }
            return sb.toString();

        } else {

            return super.toString();

        }
    }

    public Throwable getRootCause() {

        Throwable rootCause = this;
        Throwable cause = this.getCause();
        while ((cause != null) && (cause != rootCause)) {
            rootCause = cause;
            cause = cause.getCause();
        }

        return rootCause;
    }

    public Throwable getMostSpecificCause() {
        return this.getRootCause();
        // return (rootCause != null ? rootCause : this); // NOSONAR
        // return rootCause;// Sonar fix - redundant null check
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ServiceException [message=" + this.message + ", exception=" + this.exception + ", throwable=" + this.throwable + "]";
    }

}
