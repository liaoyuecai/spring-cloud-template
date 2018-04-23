package org.template.cloud.tcc.client;

public class TransactionException extends Exception {
    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionException(String message) {
        super(message);
    }
}
