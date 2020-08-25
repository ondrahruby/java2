package client;

import olifantysballs.GumBallAPI;

/**
 * The application entrypoint. Not really very useful. This project is about
 * testing and using Java 8 features.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class Main {

    /**
     * Start the application.
     * @param args not used
     */
    public static void main( String[] args ) {
        GumBallAPI machine = GumBallAPI.createMachine();// OlifantysBallMachine().refill( 10 );

        System.out.println( machine );

        machine.insertCoin();
        machine.draw();
        machine.insertCoin();
        machine.draw();
        System.out.println( machine );

        machine.insertCoin();
        machine.draw();
        machine.insertCoin();
        machine.draw();

        System.out.println( machine );

        machine.insertCoin();
        machine.draw();
        machine.insertCoin();
        machine.draw();

        System.out.println( machine );

        machine.insertCoin();
        machine.draw();
        machine.insertCoin();
        machine.draw();

        System.out.println( machine );

        machine.insertCoin();
        machine.draw();
        machine.insertCoin();
        machine.draw();

        System.out.println( "\nthis is cheating" );
        // and now allowed when main does not share package.
        
//        machine = new OlifantysBallMachine() {
//            @Override
//            public boolean isWinner() {
//                return true;
//            }
//        }.refill( 10 );
        System.out.print( "new machine = " + machine.getClass().getName()
                + " does not behave well \n" + machine.toString() );
        System.out.println();
        //No get two for one
        machine.insertCoin();
        machine.draw();
        machine.draw();
    }
}
