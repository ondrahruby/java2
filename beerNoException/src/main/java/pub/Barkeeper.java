package pub;

/**
 * @author urs
 */
class Barkeeper {

    private final Pub pub;
    public Barkeeper(Pub pub) {
        this.pub = pub;
    }

    public Beer tapBeer(double amount) {
            Beer beer = new Beer(amount);
            if(pub.getBeerStock() > amount){

                return beer;
            } else {
                return null;
            }
       

    }

    public void serve(Drinker drinker, Double beerAmount) {
        Beer beer = new Beer(beerAmount);
        pub.setBeerStock(beerAmount);
        drinker.drinkBeer(beer);
        
    }
}
