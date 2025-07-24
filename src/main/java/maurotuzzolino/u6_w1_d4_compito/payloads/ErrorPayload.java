package maurotuzzolino.u6_w1_d4_compito.payloads;


import java.time.LocalDateTime;

public class ErrorPayload {
    private String message;
    private LocalDateTime timestamp;
    private int status;

    public ErrorPayload(String message, LocalDateTime timestamp, int status) {
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ErrorPayload{" +
                "message='" + message + '\'' +
                ", timestamp=" + timestamp +
                ", status=" + status +
                '}';
    }
}
