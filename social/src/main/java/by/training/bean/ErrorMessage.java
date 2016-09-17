package by.training.bean;

public class ErrorMessage {

    private String message;

    public ErrorMessage(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorMessage [message=" + message + "]";
    }

}
