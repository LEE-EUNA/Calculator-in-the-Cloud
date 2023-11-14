// The ManyArgumentException class represents a custom exception for cases where too many arguments are provided.
// It extends the standard Exception class and provides constructors for creating exceptions with and without a custom message.
public class ManyArgumentException extends Exception {

    // Constructs a ManyArgumentException with a default message indicating too many arguments.
    public ManyArgumentException() {
        super("Too many arguments");
    }

    // Constructs a ManyArgumentException with a custom error message.
    public ManyArgumentException(String message) {
        super(message);
    }
}
