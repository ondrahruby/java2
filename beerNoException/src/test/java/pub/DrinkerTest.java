package pub;

//@author urs
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.Assertions.within;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DrinkerTest {
    private Drinker drinker;
    
    @BeforeEach
    public void setUp() {
        Pub pub = new Pub(100.0);
        drinker = new Drinker(3.0);

    }

    @Test
    public void drinkerDrinksBeer() {
        Beer beer = new Beer(1.0);
        double volume = 2.0;
        drinker.drinkBeer(beer);
        double expected =3.0;

        assertSame(expected, drinker.getVolumeLeft());

        //fail("test  not implemented");
    }
    
    @Test
    public void isFull(){
        Beer beer = new Beer(4.0);

        drinker.drinkBeer(beer);
        assertFalse(beer.getSize() > drinker.getVolumeLeft());
        //fail("test  not implemented");
    }
}
