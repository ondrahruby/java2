package olifantysballs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 * Magically colored ball.
 *
 * @author Pieter van den Hombergh  {@code p.vandenhombergh@fontys.nl>}
 */
public class OlifantysGumball {

    static Random rnd = new Random();
    final Color color;
    final String colorName;

    OlifantysGumball( String colorName ) {
        this.colorName= colorName;
        this.color = Color.valueOf( colorName );
    }

    static String[] availableColors = { "ALICEBLUE", "AZURE", "BLACK", "CHOCOLATE",
        "VIOLET", "RED", "ROSYBROWN","YELLOW", "GREEN" };

    static Collection newBalls( int amount ) {
        Collection<OlifantysGumball> result = new ArrayList<>( amount );
        for ( int i = 0; i < amount; i++ ) {
            final String bc = availableColors[ rnd.nextInt( availableColors.length ) ];
            final OlifantysGumball ball = new OlifantysGumball( bc );
            result.add( ball );
        }
        return result;
    }

    @Override
    public String toString() {
        return "OlifantysGumball{" + "color=" + colorName + '}';
    }
    
}
