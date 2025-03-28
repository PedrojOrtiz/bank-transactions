package ec.tcs.banktransactions.application.exception;

public class InsufficientBalance extends RuntimeException{

    public InsufficientBalance(String message){
        super(message);
    }

}
