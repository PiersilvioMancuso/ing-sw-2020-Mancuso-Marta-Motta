package it.polimi.ingsw.messages;

import java.io.Serializable;

/**Message Abstract Class
 */
public class Message implements Serializable {
    protected String className;

    /**ClassNameGetter
     * @return the name of the class
     */
    public String getClassName() {
        return className;
    }

}