package it.polimi.ingsw.controller.action;

import it.polimi.ingsw.model.ModelGame;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.event.response.*;


/**Register Action
 * @author Piersilvio Mancuso
 */
public class RegisterAction extends Action{


    String userName;
    int age;
    String ipAddress;

    /**Register Action Constructor
     * @param data is the String that will be analyzed to set Register Action's fields
     */
    public RegisterAction(String data){
        String [] messageComponent = data.split(";");
        this.userName = messageComponent[0].substring(9);
        this.age = Integer.parseInt(messageComponent[1].substring(4));
        this.ipAddress = messageComponent[2].substring(10);
    }


    // -------------------- GETTER --------------------------

    /**User's Age Getter
     * @return the age of the player
     */
    public int getAge() {
        return age;
    }

    /**Server's Ip Address Getter
     * @return the IpAddress of the server to which to connect
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**Username Getter
     * @return the username of the player
     */
    public String getUserName() {
        return userName;
    }

    // -------------------- ACTION -------------------------

    /**Register the Player into the Game Lobby
     * @param modelGame is the model that will be changed
     * @return the Response of the model's modifies
     */
    public void executeAction(ModelGame modelGame){
        for (User user: modelGame.getUserList()){
            if (user.getUsername().equals(this.userName)) {
                throw new IllegalArgumentException("Username already used");
            }
        }
        User user = new User(userName);
        user.setAge(age);
        modelGame.addUser(user);
    }

}
