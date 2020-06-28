package it.polimi.ingsw.messages;

import java.io.Serializable;

/**Message Class
 */
public class Message implements Serializable {
    protected String className;

    /**ClassName Getter
     * @return the name of the class
     */
    public String getClassName() {
        return className;
    }

}
