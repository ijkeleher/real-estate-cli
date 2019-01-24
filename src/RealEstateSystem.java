/** @author Inci Keleher 
 *  s3646416
 *  23/05/2017
 *  
 *  This is the main class with most of the methods for actually
 *  using the program: adding sales and auctions, making offers,
 *  see sales summary and close auctions.
 *  
 *  File I/O functionality also here -> fileRead() and fileWrite()
 **/

import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class RealEstateSystem {
    // create storage for Sales objects
    private static HashMap<String, Sale> saleMap = new HashMap<String, Sale>();
    
    // enable keyboard input from console
    private static Scanner console = new Scanner(System.in);

    public static void main(String[] args) {
        //load data from backup if possible
        fileRead();
        
        // endless loop to display menu
        while (true)
            displayMenu();
    }

    public static void displayMenu() {
        //print menu
        System.out.println("*** Real Estate System Menu ***");
        System.out.println("\nAdd New Sale\t\tA" + "\nSubmit Offer\t\tB" + "\nDisplay Sales Summary\tC"
                + "\nAdd New Auction\t\tD" + "\nClose Auction\t\tE" + "\nExit Program\t\tX" + "\n\nEnter Selection: ");
        
        // take the user input, capitalize it and use input to select option
        String selection = console.nextLine().toUpperCase();
        switch (selection) {
        case "A":
            addNewSale();
            break;
        case "B":
            submitOffer();
            break;
        case "C":
            displaySales();
            break;
        case "D":
            addNewAuction();
            break;
        case "E":
            closeAuction();
            break;
        case "X":
            System.out.println("Exiting Program...");
            fileWrite();
            System.exit(0);
            break;
        default:
            System.out.println("Invalid selection.");
            break;
        }

    }

    public static void addNewSale() {
        //solicit info from user
        System.out.println("Please enter a Sale ID");
        String saleID = console.nextLine();

        // validate saleID
        if (saleMap.containsKey(saleID)) {
            System.out.println("SaleID " + saleID + " is already in use\n");
            return;
        }
        //solicit more info
        System.out.println("Please enter a Property Address");
        String propertyAddress = console.nextLine();
        System.out.println("Please enter Reserve Price");
        int reservePrice = Integer.parseInt(console.nextLine());

        // add new sale
        Sale s = new Sale(saleID, propertyAddress, reservePrice);

        // insert into hash map and print details
        saleMap.put(s.getSaleID(), s);
        System.out.println(
                "\nNew Property Sale added successfully " + "for property at: \n" + s.getPropertyAddress() + "\n");

    }

    public static void submitOffer() {
        //get ID for sale object
        System.out.println("Please enter the Sale ID for the property");
        String saleID = console.nextLine();
        //validate saleID and retrieve sale object
        if (saleMap.containsKey(saleID)) {
            Sale s = saleMap.get(saleID);
            //enter offer and pass to makeOffer method then print results from caught exception
            System.out.print("Please enter an offer: $");
            int offer = Integer.parseInt(console.nextLine());         
            try {
                s.makeOffer(offer);
            }catch (OfferException e){
                System.out.println(e.getMessage()+"\n");
            }           
        }else
            System.out.println("Invalid Sale ID");

    }

    public static void displaySales() {
        
        if (saleMap.isEmpty()){
            System.out.println("No Sales Data!\n");
        }
        // for each key in hash map retrieve the sale object
        //and it's details
        for (String key : saleMap.keySet()) {
            Sale s = saleMap.get(key);
            System.out.println(s.getSaleDetails());
        }

    }

    public static void addNewAuction() {

        System.out.println("Enter Sale ID for new Auction Sale:");
        String saleID = console.nextLine();
        // validate saleID
        if (saleMap.containsKey(saleID)) {
            System.out.println("SaleID " + saleID + " is already in use\n");
            return;
        }
        //get other info
        System.out.println("Please enter a Property Address");
        String propertyAddress = console.nextLine();
        System.out.println("Please enter Reserve Price");
        int reservePrice = Integer.parseInt(console.nextLine());

        // create sale
        Sale s = new Auction(saleID, propertyAddress, reservePrice);

        // insert into hash map
        saleMap.put(s.getSaleID(), s);

        System.out.println("\nNew Property Sale added successfully " + "for property at: \n" +
                            s.getPropertyAddress() + "\n");
    }

    public static void closeAuction() {

        System.out.println("Enter Sale ID for Auction to be closed:");
        String saleID = console.nextLine();
        //validate saleID
        if(saleMap.containsKey(saleID)){
            Sale a = saleMap.get(saleID);
            //validate if sale is an auction. then closes.
            if (a instanceof Auction) {
                ((Auction) a).closeAuction();
                System.out.println("Auction " + a.getSaleID() + " has ended - property has been: "
                                    + a.getPropertyStatus());
            }else{
                System.out.println("This is not an Auction!");
            }
        }else
            System.out.println("Error - property sale ID" + saleID + "not found!");
    }
    
    public static void fileRead(){
        
        try{//finding the file and setting up scanner to read it
            String filename = "backup.txt";
            Scanner targetFile = new Scanner(new File(filename));
            targetFile.useDelimiter("#");
            System.out.println("Backup file found! Using backup file for loading data!");
            
            try {
                // if file has no content throw "file empty" exception
                if (targetFile.hasNextLine() == false) {
                    throw new Exception("File is empty!");
                    
                }else{//grab info from file if available
                    
                    while (targetFile.hasNextLine()) {
                        String saleID = targetFile.next();
                        String propertyAddress = targetFile.next();
                        int currentOffer = targetFile.nextInt();
                        int reservePrice = targetFile.nextInt();
                        boolean acceptingOffers = targetFile.nextBoolean();
                        String highestBidder = targetFile.next();
                        String saleType = targetFile.next();

                        if (saleType.equals("class Auction")) {
                            // recover auction from file
                            Auction s = new Auction(saleID, propertyAddress, reservePrice);
                            s.setAcceptingOffers(acceptingOffers);
                            s.setCurrentOffer(currentOffer);
                            s.setHighestBidder(highestBidder);
                            saleMap.put(s.getSaleID(), s);

                        } else {//recover sale from file
                            Sale s = new Sale(saleID, propertyAddress, reservePrice);
                            s.setAcceptingOffers(acceptingOffers);
                            s.setCurrentOffer(currentOffer);
                            saleMap.put(s.getSaleID(), s);
                        }
                    }
            }//print file empty message if file is empty
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                targetFile.close();
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not found! No Sale data was loaded");
           
        }
    }
    
    private static void fileWrite(){
        //setup file writing
        String filename = "backup.txt"; 
        PrintWriter outputStream = null;
        try{
            outputStream = new PrintWriter(new FileOutputStream(filename));
            //write sale info to 'backup.txt' for each object in hash map
            for (String key : saleMap.keySet()) {
                Sale s = saleMap.get(key);
                System.out.println(s.getSaleDetails());
                
                try {
                    outputStream.write(s.getSaleID() + '#' 
                    + s.getPropertyAddress() + '#'
                    + s.getCurrentOffer() + '#'
                    + s.getReservePrice() + '#'
                    + s.isAcceptingOffers() + '#'
                    + s.getHighestBidder() + '#'
                    + s.getClass() + '#');
                    
                    System.out.println("Yeah! Entry for " + s.getSaleID() 
                    + " was written to without error!");
                }
                
                finally {
                    System.out.println("Data saved to "+filename+"!\n");
                }
            }
            
       }catch (FileNotFoundException e){
            
        }finally{
            outputStream.close();
        }
    }
    
    
}
