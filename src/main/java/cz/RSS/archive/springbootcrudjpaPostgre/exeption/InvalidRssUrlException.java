package cz.RSS.archive.springbootcrudjpaPostgre.exeption;

public class InvalidRssUrlException extends RuntimeException {
    public InvalidRssUrlException(String message) {
        super(message);
    }
}
