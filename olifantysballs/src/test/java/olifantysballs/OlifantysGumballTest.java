package olifantysballs;

import static org.hamcrest.CoreMatchers.containsString;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class OlifantysGumballTest {

    /**
     * Test of toString method, of class OlifantysGumball.
     */
    @Test
    public void testToString() {
        OlifantysGumball ball = new OlifantysGumball( "CORAL" );
        assertThat( "has colour", ball.toString(), containsString( 
                "CORAL" ) );
    }

}
