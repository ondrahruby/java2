package olifantysballs;

import java.io.PrintStream;

/**
 * Context interface for the olifantys gum ball machine. Abstract 'super' of the
 * context object objects. Java 8 style interface with default methods where
 * relevant and meaningful. This interface is package private and faces the state machine and states.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
interface Context {

    /**
     * Add balls.
     *
     * @param count of balls to add.
     *
     * @return this context implementation
     */
    void addBalls( int count );

    /**
     * Count your balls left.
     *
     * @return the ball count.
     */
    int getBallCount();

    /**
     * Check if the customer is lucky and you can dispense another ball.
     *
     * @return true iff another ball can and should be dispensed.
     */
    boolean isWinner();

    /**
     * Produce a ball.
     */
    OlifantysGumball dispense();

    /**
     * Get the print stream. The implementation may default to System.out.
     *
     * @return the print stream defaults to system out.
     */
    PrintStream getOutput();

    /**
     * Check if all balls are gone.
     *
     * @return true if ball count == 0, false otherwise.
     */
    default boolean isEmpty() {
        return 0 == getBallCount();
    }

    /**
     * Change state, executing exit and entry methods on the go.
     *
     * @param newState next state
     *
     */
    void changeState( State newState );

}
