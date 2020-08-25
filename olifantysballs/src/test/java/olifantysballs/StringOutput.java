package olifantysballs;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Test helper, to be able to redirect a PrintStream into a String.
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
class StringOutput extends OutputStream {

    final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public void write( int b ) throws IOException {
        baos.write( b );
        System.out.print((char) b );
        System.out.flush();
    }

    void clear() {
        baos.reset();
    }

    @Override
    public String toString() {
        return new String( baos.toByteArray(), StandardCharsets.UTF_8 );
    }

    PrintStream asPrintStream() {
        return new PrintStream( this );
    }
}
