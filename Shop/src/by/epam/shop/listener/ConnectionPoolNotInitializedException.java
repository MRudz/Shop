package by.epam.shop.listener;

public class ConnectionPoolNotInitializedException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConnectionPoolNotInitializedException() {
        super();
    }

    public ConnectionPoolNotInitializedException(String message) {
        super(message);
    }

    public ConnectionPoolNotInitializedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionPoolNotInitializedException(Throwable cause) {
        super(cause);
    }
}
