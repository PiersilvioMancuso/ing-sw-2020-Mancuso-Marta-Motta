package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.state.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**Abstract Power Class
 * @author Piersilvio Mancuso
 */
 public abstract class Power implements Serializable {

    private static boolean athenaEffect;
    protected List<State> stateList;
    protected int currentState;
    protected boolean activeEffect;
    protected String textEffect;


    // ------------ CONSTRUCTOR ---------------

    /**Power Constructor
     */
    public Power(){
        athenaEffect = false;
        stateList = new ArrayList<>();
        currentState = 0;
        activeEffect = true;
    }



    // ----------- Getter -------------------------

    /**Check if Athena Effect is Active
     * @return athenaEffect value
     */
    public static boolean isAthenaEffect() {
        return athenaEffect;
    }

    /**Check if Power is Active
     * @return the value of activeEffect: true means that's activated, false not
     */
    public boolean isActiveEffect(){
        return activeEffect;
    }

    /**Get the current state of the player's turn
     * @return the current state of the player turn
     */
    public State getCurrentState(){
        return stateList.get(currentState);
    }

    /**Get the valid Cells for the current Action belonged to the State
     * @return an unmodifiable list of the valid cells where to execute the Current State action
     * @param modelGame is the model of the game
     */
    public List<Cell> getValidCells(ModelGame modelGame){
        return modelGame.getValidCells();
    }

    /**StateList Getter
     * @return power's list of state
     */
    public List<State> getStateList(){
        return this.stateList;
    }


    // ------------------ Setter --------------------------

    /**
     *Set the boolean value of the Activation Effect
     * @param condition is the value of athenaEffect
     */
    public void setAthenaEffect(boolean condition){

        athenaEffect = condition;
    }

    /**Set true if power is activated
     * @param activeEffect is the value of the Activation Effect condition
     */
    public void setActiveEffect(boolean activeEffect) {
        this.activeEffect = activeEffect;
    }

    /**Set the turn state of the player */
    public void setStateList(){
        currentState = 0;
        List<State> states = new ArrayList<>();
        states.add(new MovementState());
        states.add(new BuildState());
        states.add(new EndState());
        this.stateList = states;
    }


    /**Set the valid Cells where a player can take the current State action
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     */
    public void setValidCells(ModelGame modelGame, Worker worker){
        List<Cell> validPositions = new ArrayList<>();

        //If the current State of the Game is a Setup it will set valid position as a list of all free Cell (Don't occupied by a worker)
        if (modelGame.getCurrentState() instanceof SetupState ){
            for (Cell cell : modelGame.getBoard().getBuildMap()){
                if (!modelGame.getWorkerListPosition().contains(cell)) validPositions.add(cell);
            }
            modelGame.setValidCells(validPositions);
        }

        //If the current State of the game is an End State, it will set valid position as a list of all user's worker Cells
        else if (modelGame.getCurrentState() instanceof EndState){
            modelGame.nextUser();
            for (Worker workers: modelGame.getWorkerList()){
                if (workers.getUser().equals(modelGame.getCurrentUser())){
                    validPositions.add(workers.getPosition());
                }
            }
            modelGame.setValidCells(validPositions);
        }


        //If the Current State of the Game is a Build or Movement State, it will set the valid cells allowed by rules
        else {
            Cell workerPosition = modelGame.getWorkerPosition(worker);
            int workerHeight = workerPosition.getHeight();

            for (Cell position: modelGame.getBoard().getNeighbourCell(workerPosition)){
                int positionHeight = position.getHeight();
                if (positionHeight <= workerHeight + 1 && positionHeight >= 0 && !modelGame.getWorkerListPosition().contains(position)) validPositions.add(position);
            }

            modelGame.setValidCells(validPositions);
            athenaEffectModification(modelGame, worker);
        }


    }


    /**Remove All Cells that are higher than worker's height
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     */
    public void athenaEffectModification(ModelGame modelGame, Worker worker){
        if (isAthenaEffect() && modelGame.getCurrentState() instanceof MovementState){
            List<Cell> validPosition = new ArrayList<>(modelGame.getValidCells());
            int workerHeight = modelGame.getWorkerPosition(worker).getHeight();
            for (Cell positionCell : validPosition){
                int positionHeight = positionCell.getHeight();
                if (positionHeight > workerHeight) modelGame.getValidCells().remove(positionCell);
            }
        }
    }

    // ------------ Action -------------------

    /**Change the State
     *
     * @param modelGame is the model of the game
     */
    public void setNextCurrentState(ModelGame modelGame){
        currentState++;
        modelGame.setCurrentState(stateList.get(currentState));
    }



    /**Initialize the Power
     * @param modelGame is the model of the Game
     * @param worker is the worker that will be used by the player
     */
    public void startPower(ModelGame modelGame, Worker worker){
        setStateList();
        modelGame.setCurrentState(stateList.get(0));
        setValidCells(modelGame, worker);

    }

    /**Execute the state action
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will take place
     * @exception IllegalArgumentException if position is not a valid cell
     */
    public void runPower(ModelGame modelGame, Worker worker, Cell position){
        if (!modelGame.getValidCells().contains(position)) throw new IllegalArgumentException("Position is Not in a ValidCell");

        modelGame.getCurrentState().executeState(modelGame, worker, position);
        setNextCurrentState(modelGame);
        setValidCells(modelGame, worker);


    }

    /**Check if the worker used by the player let him win the game
     *
     * @param modelGame is the game
     * @param worker is the worker used by the player
     * @param position is the position from where the worker moved up
     * @return true if the player win, otherwise false
     */
    public boolean isWinner(ModelGame modelGame, Worker worker, Cell position){
        int workerHeight = worker.getPosition().getHeight();
        return (workerHeight == 3 && position.getHeight() < 3);
    }

    /**TextEffect Getter
     * @return a string containing the effect of the Power
     */
    public String getTextEffect() {
        return textEffect;
    }

    /**CurrentState Setter
     * @param currentState is an Integer that denote the index of the Current State
     */
    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    /**StateList Setter
     * @param stateList is a list containing all the states of the power
     */
    public void setStateList(List<State> stateList) {
        this.stateList = stateList;
    }

    /**TextEffect Setter
     * @param textEffect is a String containing the Effect of the Power
     */
    public void setTextEffect(String textEffect) {
        this.textEffect = textEffect;
    }

    @Override
    public String toString() {
        return textEffect;
    }
}
