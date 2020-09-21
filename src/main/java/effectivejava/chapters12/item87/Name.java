package effectivejava.chapters12.item87;

import java.io.Serializable;

//Good candidate for default serialized form
public class Name implements Serializable {
    /**
     * Last name. Must be non-null.
     * 
     * @serial
     */
    private final String lastName = "";
    /**
     * First name. Must be non-null.
     * 
     * @serial
     */
    private final String firstName = "";

    /**
     * Middle name, or null if there is none.
     * 
     * @serial
     */
    private final String middleName = "";
    // Remainder omitted
}
