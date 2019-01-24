/** @author Inci Keleher 
 *  s3646416
 *  23/05/2017
 *  
 *  This Sale class contains everything needed for creating a sale!
 *  
 **/

public class Sale {

	private String saleID;
	private String propertyAddress;
	private int currentOffer;
	private int reservePrice;
	private boolean acceptingOffers = true;

	public Sale(String saleID, String propertyAddress, int reservePrice) {
		this.saleID = saleID;
		this.propertyAddress = propertyAddress;
		this.reservePrice = reservePrice;

	}

	public String getSaleID() {
		return saleID;
	}

	public String getPropertyAddress() {
		return propertyAddress;
	}

	public int getCurrentOffer() {
		return currentOffer;
	}

	public int getReservePrice() {
		return reservePrice;
	}

	public boolean isAcceptingOffers() {
		return acceptingOffers;
	}

	public void setAcceptingOffers(boolean acceptingOffers) {
		this.acceptingOffers = acceptingOffers;
	}

	public void setCurrentOffer(int currentOffer) {
		this.currentOffer = currentOffer;
	}
	//Return if offer was successful
    public void makeOffer(int offerPrice) throws OfferException{
	    try{
	        if(acceptingOffers == false){
	            throw new OfferException("The sale is not open for offers!");
	        }
	        else if(offerPrice <= currentOffer){
	            throw new OfferException("New offer price is less than the current offer price!");
	        }
	        else{
	            currentOffer = offerPrice; 
	            throw new OfferException("New offer accepted but less than reserve!");
	        }
	    }finally{     
	        if(currentOffer >= reservePrice){
                acceptingOffers = false;
                throw new OfferException("New offer accepted! Sale closed!");
            }
	            
	    }
    }
		

	public String getPropertyStatus() {
		if (isAcceptingOffers() == true)
			return "ON SALE";
		else
			return "SOLD";

	}
	//just returns information on sale object
	public String getSaleDetails() {
		String firstLine = String.format("%-20s %s\n", "Sale ID:", saleID);
		String secondLine = String.format("%-20s %s\n", "Property Address:", propertyAddress);
		String thirdLine = String.format("%-20s %s\n", "Current Offer:", currentOffer);
		String fourthLine = String.format("%-20s %s\n", "Reserve Price:", reservePrice);
		String fifthLine = String.format("%-20s %s\n", "Accepting Offers:", acceptingOffers);
		String sixthLine = String.format("%-20s %s\n", "Sale Status:", getPropertyStatus());

		return firstLine + secondLine + thirdLine + fourthLine + fifthLine + sixthLine;

	}
	//simple hack to make readFile() work properly
    public String getHighestBidder() {
        // TODO Auto-generated method stub
        return null;
    }
}
