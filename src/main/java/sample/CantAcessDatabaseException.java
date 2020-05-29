package sample;

public class CantAcessDatabaseException extends RuntimeException {

    private static final long serialVersionUID = 2360456407878003009L;

    public CantAcessDatabaseException(Exception e) {
        super(e);
    }

    public CantAcessDatabaseException(String message) {
        super(message);
    }

    public CantAcessDatabaseException(String message, Exception e) {
        super(message, e);
    }
}
