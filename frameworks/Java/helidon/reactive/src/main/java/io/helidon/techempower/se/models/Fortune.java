package io.helidon.techempower.se.models;

/**
 * Fortune model used for template processing.
 */
public final class Fortune {
    public int id;
    public String message;

    public Fortune(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
