package cz.RSS.archive.springbootcrudjpaPostgre.exeption;

public class DuplicateRssException extends RuntimeException {
    public DuplicateRssException(String message) {
        super(message);
    }
}
