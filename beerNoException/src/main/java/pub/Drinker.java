package pub;

//@author urs
class Drinker {

    private double maxVolume;
    private double drinktvolume;

    Drinker( double maxDrinkVolume ) {
       this.maxVolume = maxDrinkVolume;
       this.drinktvolume = 0;
        
    }

    public boolean drinkBeer( Beer beer ) {
        if ((drinktvolume + beer.getSize()) < maxVolume){
            return true;
        } else {
            return false;
        }
    }

    public double getVolumeLeft() {

        return maxVolume - drinktvolume;
    }
}
