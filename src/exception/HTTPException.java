package exception;

public class HTTPException extends Exception {
    public HTTPException() {
        super("Method Not Allowed");
    }
}
