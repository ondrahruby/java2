package olifantysballs;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Head First Design Patterns Gum ball machine alternative implementation.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
class OlifantysBallMachine implements GumBallAPI, Context {

    State state;
    final List<OlifantysGumball> balls = new ArrayList<>();

    OlifantysBallMachine() {
        state = StateEnum.SoldOut;
    }

    @Override
    public void changeState( State newState ) {
        state.exit( this );
        state = newState;
        state.enter( this );
    }

    /**
     * for tests.
     *
     * @return
     */
    State getState() {
        return state;
    }

    public void draw() {
        state.draw( this );
    }

    @Override
    public int getBallCount() {
        return balls.size();
    }

    @Override
    public void addBalls( int count ) {
        balls.addAll( OlifantysGumball.newBalls( count ) );
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        int count = getBallCount();
        result.append( "Olifantys Magic Ball Dispenser, Inc." )
                .append( "\nJava-enabled Standing Gumball Model #2016" )
                .append( "\nInventory: " )
                .append( count )
                .append( " gumball" )
                .append( ( count != 1 ) ? "s" : "" )
                .append( "\n" )
                .append( "Machine is " + state + "\n" );
        return result.toString();
    }

    /**
     * Remove one (the first) ball.
     */
    @Override
    public OlifantysGumball dispense() {
        return balls.remove( 0 );
    }

    final Random rnd = new Random();

    @Override
    public boolean isWinner() {
        return !isEmpty() && rnd.nextInt( 10 ) == 9;
    }

    @Override
    public void ejectCoin() {
        state.ejectCoin( this );
    }

    @Override
    public void insertCoin() {
        state.insertCoin( this );
    }

    @Override
    public void refill( int count ) {
        this.state.refill( this, count );
    }

    PrintStream output = System.out;

    @Override
    public PrintStream getOutput() {
        return output;
    }

    @Override
    public void setOutput( PrintStream output ) {
        this.output = output;
    }
}
