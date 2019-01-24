/** @author Inci Keleher 
 *  s3646416
 *  23/05/2017
 *  
 *  Auction subclass adds features to a sale, like bidding!
 *  
 **/

import java.util.Scanner;

public class Auction extends Sale{

	private String highestBidder;
	private static Scanner console = new Scanner(System.in);

	
	public Auction(String saleID, String propertyAddress, int reservePrice) {
		super(saleID, propertyAddress, reservePrice);
		this.highestBidder = "NO BIDS PLACED";
	}
	
	//override getPropertyStatus for auction sales
	public String getPropertyStatus(){
		if(super.isAcceptingOffers() == false &&
		        super.getCurrentOffer() > super.getReservePrice())
				return "SOLD";
		else if(super.isAcceptingOffers() == true)
				return "ACCEPTING BIDS";
		else
				return "PASSED IN";
	}
	
	public boolean closeAuction(){
		if(super.isAcceptingOffers() == false)
			return false;
		else
			super.setAcceptingOffers(false);
			return true;
	}
	
	public void setHighestBidder(String highestBidder){       
	    this.highestBidder = highestBidder;         
	}
	
	public String getHighestBidder(){
	    return highestBidder;
	}
	
    public void makeOffer(int offerPrice) throws OfferException{
        try{
            if(super.isAcceptingOffers()== false){
                throw new OfferException("The sale is not open for offers!");
            }
            else if(offerPrice <= super.getCurrentOffer()
                    || offerPrice < super.getReservePrice()){
                throw new OfferException("New offer price is "
                        + "less than the current asking price!");
            }
            else{
                super.setCurrentOffer(offerPrice); 
                System.out.println("Enter name of bidder!");
                setHighestBidder(console.nextLine());
                throw new OfferException("New offer accepted from " 
                + highestBidder);
            }
        }finally{
            //some stuff
                
        }
    }
	
	public String getSaleDetails() {
		String highestBidderDetails = String.format("%-20s %s\n", "Highest Bidder:", highestBidder);
		return super.getSaleDetails() + highestBidderDetails;
		
	}
}
