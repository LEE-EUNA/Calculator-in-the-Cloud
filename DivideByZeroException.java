// The DivideByZeroException class represents a custom exception for division by zero.
// It extends the standard Exception class and provides constructors for creating exceptions with and without a custom message.
public class DivideByZeroException extends Exception {

    // Constructs a DivideByZeroException with a default message indicating division by zero.
    public DivideByZeroException() {
        super("Divided by zero");
    }

    // Constructs a DivideByZeroException with a custom error message.
    public DivideByZeroException(String message) {
        super(message);
    }
}
