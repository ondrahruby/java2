package pub;

/**
 * @author urs
 */
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertFalse;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class PubTest {

    @Test
    public void stockEmptyNoBeer() {
        Pub emptyPub = new Pub(0);
        double amount = 2.0;
        assertFalse("Stock is empty", emptyPub.canDrawBeer(amount));

        //fail("test  not implemented");
    }

    @Test //or barkeeper serves
    public void drinkerOrdersBeer() {
        //TODO
        Pub pub = new Pub(20.0);
        Drinker drinker = new Drinker(20.0);
        Barkeeper barkeeper = new Barkeeper(pub);
        barkeeper.tapBeer(2.0);
        barkeeper.serve(drinker,2.0);

        Assert.assertSame(18.0, pub.getBeerStock());
        //fail("test  not implemented");

    }
}
