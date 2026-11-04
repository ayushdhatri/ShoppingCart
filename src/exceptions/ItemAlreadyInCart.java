package exceptions;

public class ItemAlreadyInCart extends RuntimeException{

    public ItemAlreadyInCart(String message){
        super(message);
    }


}
