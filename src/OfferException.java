/** @author Inci Keleher 
 *  s3646416
 *  23/05/2017
 *  
 *  This is a custom Exception class for the makeOffer method
 *  in the Sale class
*/
public class OfferException extends Exception{

    private static final long serialVersionUID = -3979576570175054903L;

    public OfferException(String message){
        super(message);   
    }
}