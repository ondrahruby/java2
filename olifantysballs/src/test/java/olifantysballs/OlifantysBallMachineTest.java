package olifantysballs;

import java.io.PrintStream;
import static olifantysballs.StateEnum.HasCoin;
import static olifantysballs.StateEnum.NoCoin;
import static olifantysballs.StateEnum.SoldOut;
import static olifantysballs.StateEnum.Winner;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.Mockito;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Test class for Ball Machine. Method names should say it all.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
//@Ignore
public class OlifantysBallMachineTest {

    OlifantysBallMachine instance = (OlifantysBallMachine) GumBallAPI.createMachine();

    //<editor-fold defaultstate="expanded" desc="TASK_1B1; __STUDENT_ID__; WEIGHT 10;">
    /**
     * Fill with enough balls, insert coin and draw repeatedly until empty.
     */
    @Test
    public void get_some_balls() {
        //TODO 1B1 test what happens when we have balls or not
        instance.refill(2); //Adding balls
        assertSame("state no coin", NoCoin, instance.getState());
        instance.insertCoin();
        assertSame("state coin", HasCoin, instance.getState()); //Added coin
        instance.draw(); //Drawing balls
        instance.insertCoin();
        instance.draw();
        assertTrue(instance.isEmpty());

    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="TASK_1B2; __STUDENT_ID__; WEIGHT 10;">
    @Test
    public void gimme_my_money_back() {
        //TODO 1B2 test what happens on eject coin
        System.out.println("give me money");
        instance.ejectCoin();
        instance.refill(10);
        assertSame("state no coin", NoCoin, instance.getState());
        instance.insertCoin();
        assertSame("state coin", HasCoin, instance.getState());
        instance.ejectCoin();
        assertSame("state no coin", NoCoin, instance.getState());
        instance.ejectCoin();
        assertSame("state no coin", NoCoin, instance.getState());
    }
    //</editor-fold>

    /**
     * fill in three portions. should go from SoldOut to NoCoin and stay there.
     */
    @Test
    public void fillup() {
        assertSame( "Starts in Sold out", SoldOut, instance.getState() );
        instance.refill( 2 );
        assertSame( "Not in waiting for coin", NoCoin, instance.getState() );
        instance.insertCoin(); // addBalls in HasCoin state for coverage
        assertSame( "Should be in HasCoin", HasCoin, instance.getState() );
        instance.refill( 2 );
        assertSame( "Should stay in HasCoin", HasCoin, instance.getState() );
        instance.refill( 2 );
        assertEquals( "Enough for three winners ", 6, instance.getBallCount() );
    }

    /**
     * Use a spy to monitor what happens in a instance and if indeed the proper
     * methods a are being called. We call the instance buggedInstance to show
     * that it is being watched.
     */
    @Test
    public void make_me_rich_aka_try_insert_coin_in_every_state() {
        OlifantysBallMachine m = new OlifantysBallMachine();
        OlifantysBallMachine buggedInstance = Mockito.spy( m ); // <1>
        buggedInstance.refill( 5 );
        buggedInstance.insertCoin(); // <2>
        buggedInstance.insertCoin(); // <3>
        verify( buggedInstance, times( 1 ) ).changeState( HasCoin ); // <4>
        assertSame( "should be stubbornly staying in HasCoin", HasCoin,
                buggedInstance.getState() ); // <5>
    }

    /**
     * For coverage, test with zero and one ball(s), to see the plural s coming
     * and going. For coverage.
     */
    @Test
    public void tostring_of_empty_machine() {
        // for coverage
        OlifantysBallMachine m = new OlifantysBallMachine();
        assertTrue( m.toString().contains( "gumballs" ) );
        m.addBalls( 1 );
        assertFalse( m.toString().contains( "gumballs" ) );
    }

    /**
     * isWinner uses a random generator with a 10% lucky chance. Try to become a
     * winner for some acceptable testing time.
     */
    @Test( timeout = 200 )
    public void wait_for_winner() {
        OlifantysBallMachine m = new OlifantysBallMachine();
        m.addBalls( 1 );
        boolean succes = false;
        while ( true ) {
            if ( succes |= m.isWinner() ) {
                break;
            }
        }
        assertTrue( "perseverance wins", succes );
    }

    @Test
    public void empty_machine_has_no_winner() {
        OlifantysBallMachine m = new OlifantysBallMachine();
        assertFalse( "no winners here", m.isWinner() );
    }

    /**
     * Test that methods that should not result in a state change indeed do not.
     * Use a helper method to invoke the methods as Runnable.
     */
    @Test
    public void ignored_events_in_has_coin() {
        instance.changeState( HasCoin );
        OlifantysBallMachine spy = Mockito.spy( this.instance );
        assertNoStateChange( spy,
                spy::insertCoin, // void()
                () -> {
            spy.addBalls( 12 );
        } // wrapped in runnable
        );
    }

    //<editor-fold defaultstate="expanded" desc="TASK_1B3; __STUDENT_ID__; WEIGHT 10;">
    /**
     * Test that methods that should not result in a state change indeed do not.
     * Use a helper method to invoke the methods as Runnable.
     */
    @Test
    public void ignored_events_in_soldout() {
        //TODO 1B3 test that soldout is stubborn
        instance.changeState( SoldOut );
        OlifantysBallMachine spy = Mockito.spy(this.instance);
        assertNoStateChange( spy,
                spy::insertCoin,
                spy::draw,
                spy::ejectCoin ); //Man cannot insertCoin, draw balls or ejectCoin when SoldOut
        
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="expanded" desc="TASK_1B4; __STUDENT_ID__; WEIGHT 10;">
    /**
     * Test that methods that should not result in a state change indeed do not.
     * Use a helper method to invoke the methods as Runnable.
     */
    @Test
    public void ignored_events_in_nocoin() {
        //TODO 1B4 test that nocoin insists on coins and nothing else
        
        instance.changeState(NoCoin);
        //OlifantysBallMachine m = OlifantysBallMachine();
        OlifantysBallMachine spy = Mockito.spy(this.instance);
        assertNoStateChange(spy,
                spy::ejectCoin,
                spy::draw, () -> { spy.addBalls(2);}); //Man cannot draw balls or ejectCoin when NoCoin
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="expanded" desc="TASK_1B5; __STUDENT_ID__; WEIGHT 10;">
    /**
     * Test that methods that should not result in a state change indeed do not.
     * Use a helper method to invoke the methods as Runnable.
     */
    @Test
    public void ignored_events_in_winner() {
        //TODO 1B5 test is winner state
        instance.changeState( Winner );
        
        //OlifantysBallMachine m = OlifantysBallMachine();
        OlifantysBallMachine spy = Mockito.spy(this.instance);
        System.out.println("sinstance" + spy.getClass().getCanonicalName());
        assertNoStateChangeSpied(spy, spy::ejectCoin, spy::insertCoin, () -> { spy.addBalls(2);}); //Man cannot insertCoin, ejectCoin
        
    }
    //</editor-fold>
    
    /**
     * Make sure that the context stick with trhe design contract, calling exit
     * and enter on state change.
     */
    @Test
    public void testChangeState() {
        State state1 = Mockito.mock( State.class );
        State state2 = Mockito.mock( State.class );
        OlifantysBallMachine ctx = new OlifantysBallMachine();
        ctx.changeState( state1 );
        verify( state1, times( 1 ) ).enter( ctx );
        verify( state2, never() ).enter( ctx );

        ctx.changeState( state2 );
        verify( state1, times( 1 ) ).exit( ctx );
        verify( state2, times( 1 ) ).enter( ctx );
    }

    @Test
    public void testPrintToOutput() {
        OlifantysBallMachine ctx = new OlifantysBallMachine();
        assertSame( System.out, ctx.getOutput() );
        StringOutput sout = new StringOutput();
        PrintStream out = sout.asPrintStream();
        ctx.setOutput( out );
        ctx.refill( 2 );
        ctx.insertCoin();
        ctx.draw();
        assertThat( sout.toString(), containsString( "OlifantysGumball" ) );

    }

    /**
     * Helper to invoke methods on context that should not change from its
     * initial state. This method only tests if the state before and after are
     * the same, which is is does not ensure that the machine changed to through
     * the actions of the runnable.
     *
     * @param ctx context of the state
     * @param operations list of runnables to be invoked on the instance.
     *
     */
    static void assertNoStateChange( Context ctx, Runnable... operations ) {
        OlifantysBallMachine obm = (OlifantysBallMachine) ctx;
        State initial = obm.getState();
        for ( Runnable op : operations ) {
            op.run();
            assertSame( "invocation effected state change", initial,
                    obm.getState() );
        }
    }

    /**
     * Helper to invoke methods on context that should not change from its
     * initial state.
     *
     * When using this method, make sure you start spying on the context AFTER
     * you moved it to the proper state. Otherwise the spy will rightfully fail
     * because a set or change state did happen.
     *
     * @param ctx spied (mocked) upon context
     * @param operations list of runnables to be invoked on the instance.
     *
     * @throws org.mockito.exceptions.misusing.NotAMockException when ctx is not
     * a mock as in not spied upon. Consider this a error.
     */
    static void assertNoStateChangeSpied( Context ctx, Runnable... operations ) {
        OlifantysBallMachine obm = (OlifantysBallMachine) ctx;
        State initial = obm.getState();
        for ( Runnable op : operations ) {
            op.run();
            verify( ctx, never() ).changeState( any() );
            assertSame( "invocation effected state change", initial,
                    obm.getState() );
        }
    }
}
