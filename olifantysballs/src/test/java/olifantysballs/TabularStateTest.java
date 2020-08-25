package olifantysballs;

import java.io.PrintStream;
import java.util.function.BiConsumer;
import static olifantysballs.StateEnum.*;
import static olifantysballs.TabularStateTest.TestData.td;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import static org.junit.Assert.fail;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

/**
 * Example of using Parameterized tests with mockito.
 *
 * In this Junit test example we combine Mockito with a Parameretized test. The
 * advantages and disadvantages are:
 * <ul>
 * <li>
 * The state transition closely matches the one given on the web site. This
 * allows easy verification of completeness, in particular for those triggers
 * that should not result in a state change.
 * </li>
 * <li>
 * The test method is more complex because it must deal with all combinations of
 * triggers, target states and guard values. These can however easily be
 * expressed
 * </li>
 * <li> Because we want a parameterized test run with
 * {@code @RunWith(Parameterized.class)}, we must mock 'by hand' instead of with
 * a {@code @mock} annotation. In this case w do that in the constructor of the
 * test class which is needed anyway. It also makes the {@code @Before} method
 * obsolete.
 * </li>
 * </ul>
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */

@RunWith( Parameterized.class )
public class TabularStateTest {

    /**
     * Context of the state. Mocked by Mockito in the constructor.
     */
    final Context ctx;

    /**
     * The system under test, the state instances.
     */
    final State state;

    /**
     * output to be used.
     */
    StringOutput sout = new StringOutput(); // <3>
    PrintStream out = sout.asPrintStream(); // <4>

    /**
     * The all important test data. Each row is a test for one test. The meaning
     * of the data elements can be read in the TestData(olifantysballs.State,
     * olifantysballs.State, java.util.function.BiConsumer, boolean, boolean,
     * int, int) constructor, td(...) is shorthand for new TestData(...)
     * 
     * HINT: While you are developing the state (which is what this test class is all about),
     * hide (comment) those rows that have no relevance for your implementation.
     * At the end all test should pass.
     * 
     */
    //<editor-fold preserve-formatting='true'>
    static TestData[] testData = {
        //  init  , end    , trigger                      , empty, winner , dispense, addBalls
        td( 1, NoCoin, HasCoin, ( c, s ) -> s.insertCoin( c ), false, false, 0,
        0,
        "You inserted a coin" ),
        td( 2, HasCoin, NoCoin, ( c, s ) -> s.ejectCoin( c ), false, false, 0,
        0,
        "Quarter returned" ),
        td( 3, HasCoin, NoCoin, ( c, s ) -> s.draw( c ), false, false, 1, 0,
        "OlifantysGumball" ),
        td( 4, HasCoin, SoldOut, ( c, s ) -> s.draw( c ), true, false, 1, 0,
        "OlifantysGumball" ),
        td( 5, HasCoin, Winner, ( c, s ) -> s.draw( c ), false, true, 1, 0, "OlifantysGumball" ),
        td( 6, Winner, NoCoin, ( c, s ) -> s.draw( c ), false, false, 1, 0, "You got two gumballs for your coin" ),
        td( 7, Winner, SoldOut, ( c, s ) -> s.draw( c ), true, true, 1, 0, "OlifantysGumball" ),
        td( 8, SoldOut, NoCoin, ( c, s ) -> s.refill( c, 5 ), false, false, 0,
        1,
        "refilled" ),
        //td no state change: addBalls in 3 states
        td( 9, Winner, null, ( c, s ) -> s.refill( c, 5 ), false, false, 0, 1,
        "refilled" ),
        td( 10, HasCoin, null, ( c, s ) -> s.refill( c, 5 ), false, true, 0, 1,
        "refilled" ),
        td( 11, NoCoin, null, ( c, s ) -> s.refill( c, 5 ), false, true, 0, 1,
        "refilled" ),
        //td n no state change in 3 states
        td( 12, Winner, null, ( c, s ) -> s.insertCoin( c ), false, false, 0,
        0,
        "You should draw once more to get extra ball" ),
        td( 13, SoldOut, null, ( c, s ) -> s.insertCoin( c ), false, false, 0,
        0,
        "Machine is empty, waiting for refill" ),
        td( 14, HasCoin, null, ( c, s ) -> s.insertCoin( c ), false, false, 0,
        0,
        "You should draw to get your ball" ),
        //td no state change in 3 states
        td( 15, Winner, null, ( c, s ) -> s.ejectCoin( c ), false, false, 0, 0,
        "You should draw once more to get extra ball" ),
        td( 16, SoldOut, null, ( c, s ) -> s.ejectCoin( c ), false, false, 0,
        0,
        "Machine is empty, waiting for refill" ),
        td( 17, NoCoin, null, ( c, s ) -> s.ejectCoin( c ), false, false, 0, 0,
        "You must put in a coin before you can continue" )
    };

    BiConsumer<Context, State> mrX = 
            ( Context c, State s ) -> { s.insertCoin( c );  };
    //</editor-fold>
    final TestData myData;

    /**
     * Constructor setting up the data for the test and creating and setting up
     * the mocked context.
     *
     * @param data for one test.
     */
    public TabularStateTest( TestData data ) {
        this.myData = data;
        state = data.initial;
        ctx = Mockito.mock( Context.class );
        when( ctx.getOutput() ).thenReturn( out );
        // any colour will do.
        when(ctx.dispense()).thenReturn(new OlifantysGumball("RED"));
    }

    /**
     * The only test method. Continues with mock training and then does the
     * interaction with the sut once by invoking trigger.accept. After the
     * invocation it verifies the invocation counts of refills and drawcount. If
     * the end state is not null, the state change is tested as well.
     * 
     * HINTS: 
     * <ul>
     * <li>Set up: Mock the context and teach it to return the proper guard values
     * for empty and winner.</li>
     * <li>do the interaction</li>
     * <li>verify or assert that the proper end state is reached if the end state is not null.</li>
     * <li>verify the assert that changestate is not invoked when end state is null</li>
     * <li>verify or assert that the correct number of invocations of addBalls.</li>
     * <li>verify or assert that the correct number of invocations of dispense.</li>
     * <li>verify or assert that the output *contains* the proper message</li>
     *</ul>
     */
    //<editor-fold defaultstate="expanded" desc="TASK_1A1; __STUDENT_ID__; WEIGHT 10;">
    @Test
    public void testState() {
        System.out.println( myData.toString() );
        //TODO 1A1 write testmethod for parameterized test
        fail("Test method testState not implemented" );
    }
    //</editor-fold>
    /**
     * Hand the test data to the runner, which passes them back through the sole
     * constructor of this test class.
     *
     * @return all test data.
     */
    @Parameters
    public static TestData[] getTestData() {
        return testData;

    }

    /**
     * Data set for one test.
     */
    static class TestData {

        final int nr;
        /**
         * Starting state.
         */
        final State initial;
        /**
         * End state, if not null, Null signal that state change should not be
         * called.
         */
        final State end;
        /**
         * Trigger. Specified as a {@link java.util.function.BiConsumer} to
         * allow us to pass in both context and state. The body of the method
         * returns nothing, so all in all, the lambda expression has the shape
         * of a BiConsumer.
         */
        final BiConsumer<Context, State> triggerAction;
        /**
         * The value for the guard expression [empty], trained to the context.
         */
        final boolean empty;
        /**
         * The value for the guard expression [winner], trained to the context.
         */
        final boolean winner;
        /**
         * The count for the draw method on the context. Used in the verify
         * steps.
         */
        final int drawCount;
        /**
         * The count for the addBalls method on the context. Used in the verify
         * steps.
         */
        final int addBallsCount;

        final String msg;

        /**
         * Ctor taking all
         *
         * @param n test number
         * @param initial see field doc
         * @param end see field doc
         * @param triggerAction see field doc
         * @param empty see field doc
         * @param winner see field doc
         * @param drawCount see field doc
         * @param refillsCount see field doc
         * @param m message
         */
        TestData( int n, State initial, State end,
                BiConsumer<Context, State> triggerAction,
                boolean empty, boolean winner, int drawCount, int refillsCount,
                String m ) {
            this.nr = n;
            this.initial = initial;
            this.end = end;
            this.triggerAction = triggerAction;
            this.winner = winner;
            this.empty = empty;
            this.drawCount = drawCount;
            this.addBallsCount = refillsCount;
            this.msg = m;
        }

        /**
         * Helper method to ease filling a table.
         * @param n test number 
         * @param initial state
         * @param end final state
         * @param triggerAction the action happening takeing the context and state as parameters
         * @param empty guard value for this test
         * @param winner guard value for this test
         * @param drawCount how many times to draw
         * @param refillsCount how often refill is called.
         * @param m message output 
         * @return a test data row
         */
        static TestData td( int n, State initial, State end,
                BiConsumer<Context, State> triggerAction,
                boolean empty, boolean winner, int drawCount, int refillsCount,
                String m ) {
            return new TestData( n, initial, end, triggerAction,
                    empty, winner, drawCount, refillsCount, m );
        }

        @Override
        public String toString() {
            return "TestData{ " + nr + ", " + initial + ", " + end + ", "
                    + ", " + msg + '}';
        }
    }
}
