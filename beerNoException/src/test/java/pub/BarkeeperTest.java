package pub;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.assertFalse;
import static pub.Pub.PINT;

/**
 * @author urs
 */
public class BarkeeperTest {
    
    private Barkeeper barkeeper;
    private Pub pub;

    @BeforeEach
    public void setUp() {
        pub = new Pub( 100.0 );
        barkeeper = new Barkeeper( pub );
    }

    @Test
    public void over_order_returns_null() {
        //TODO
        Beer beer = barkeeper.tapBeer(150.0);
        assertFalse("Not enough stock for this amount of beer", pub.getBeerStock() < beer.getSize());

       
    }

    @Test
    public void barkeeperTapsBeer() {
        //TODO

        Drinker drinker = new Drinker(20.0);

        barkeeper.tapBeer(2.0);
        barkeeper.serve(drinker,2.0);

        Assert.assertSame(98.0 ,pub.getBeerStock());
        //fail("test  not implemented");
    }

    @Test
    public void barkeeperServesDrinker() {
        //TODO

        Drinker drinker = new Drinker(20.0);

        barkeeper.tapBeer(2.0);
        barkeeper.serve(drinker,2.0);

        Assert.assertSame(98.0 ,pub.getBeerStock());
       // fail("test  not implemented");
    }
}
