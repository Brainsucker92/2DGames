package util;

import java.io.PrintStream;

/**
 * Indicates an object can be printed to an output.
 */
public interface Printable {

    void print();

    void print(PrintStream stream);
}
