package olifantysballs;

/**
 * Abstract state that accepts these events (method calls).
 *
 *
 */
interface State {

    /**
     * Coin insert.
     *
     * @param gbm context of this state.
     */
    default void insertCoin( Context gbm ) {
        printreason( gbm );
    }

    /**
     * Coin eject.
     *
     * @param gbm context of this state.
     */
    default void ejectCoin( Context gbm ) {
        printreason( gbm );
    }

    /**
     * Release gum ball.
     *
     * @param gbm context of this state.
     */
    default void draw( Context gbm ) {
        printreason( gbm );
    }

    /**
     * Fill the machine.
     *
     * @param gbm context of this state.
     * @param count of balls to add.
     */
    default void refill( Context gbm, int count ) {
        gbm.addBalls( count );
        gbm.getOutput().println( "refilled with " + count + " balls" );
    }

    default void printreason( Context gbm ) {
        gbm.getOutput().println( reason() );
    }

    /**
     * Method giving a reason why a method has no effect.
     *
     * @return the reason
     */
    String reason();

    /**
     * Invoke on change state away from this state.
     *
     * This implementation is a No-Op.
     *
     * @param ctx the context of this state
     */
    default void exit( Context ctx ) {
    }

    /**
     * Invoke on change state into this state.
     *
     * This implementation is a No-Op.
     *
     * @param ctx the context of this state
     */
    default void enter( Context ctx ) {
    }
}
