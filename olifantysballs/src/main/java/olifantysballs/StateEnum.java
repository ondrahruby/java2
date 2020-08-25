package olifantysballs;

/**
 * Implements State behaviors.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
enum StateEnum implements State {

    //<editor-fold defaultstate="expanded" desc="TASK_1A2; __STUDENT_ID__; WEIGHT 10;">
    NoCoin( "You must put in a coin before you can continue" ) {
        // TODO 1A2 implement state NoCoin
        public void insertCoin(Context gbc){
            gbc.getOutput().println("You inserted a coin");
            gbc.changeState(HasCoin);
        }
        
    },
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="TASK_1A3; __STUDENT_ID__; WEIGHT 10;">
    HasCoin( "You should draw to get your ball" ) {
        // TODO 1A3 implementg state HasCoin
        @Override
        public void ejectCoin(Context gbc){
            gbc.getOutput().println("Coin returned");
            gbc.changeState(NoCoin);
        }
        @Override 
        public void draw(Context gbc) {
            gbc.getOutput().println("Draw completed");
            if(gbc.isEmpty()) {
                gbc.changeState(SoldOut);
            } if(gbc.isWinner()) {
                gbc.changeState(Winner);
            } 
            else {
                gbc.changeState(NoCoin);
            }
        }
        
        
    },
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="TASK_1A4; __STUDENT_ID__; WEIGHT 10;">
    SoldOut( "Machine is empty, waiting for refill" ) {
        //TODO 1A4 implement state SoldOout
        public void refill(Context gbm, int content){
            super.refill(gbm, content);
            gbm.changeState(NoCoin);
        }
    },
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="TASK_1A5; __STUDENT_ID__; WEIGHT 10;">
    Winner( "You should draw once more to get extra ball" ) {
        //TODO 1A5 implement state Winner 
        public void draw(Context gbc){
            gbc.getOutput().println("You are a winner!");
            gbc.getOutput().println(gbc.dispense());
            if(gbc.isEmpty()){
                gbc.changeState(SoldOut);
            }else{
                gbc.changeState(NoCoin);
            }
        }
        
    };
    //</editor-fold>

    final String reason;

    private StateEnum( String reason ) {
        this.reason = reason;
    }

    @Override
    public String reason() {
        return reason;
    }
}
