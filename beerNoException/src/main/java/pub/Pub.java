package pub;

/**
 * @author urs
 */
public class Pub {

    public static final double PINT = 0.57;
    public static final double SMALL = 0.2;
    private double stock;

    public Pub( double beerStock ) {

        this.stock = beerStock;
        
    }
    public double getBeerStock(){
        return this.stock;
    }
    public void setBeerStock(double amount){
        this.stock = this.stock - amount;
    }

    public boolean canDrawBeer( double amount ) {
        //TODO
        if(this.getBeerStock() > amount){
            return true;
        } else {
            return false;
        }
    }
}
