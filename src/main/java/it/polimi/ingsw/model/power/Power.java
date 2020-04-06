package it.polimi.ingsw.model.power;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**Generic Power Class
 * @author Piersilvio Mancuso
 */
abstract public class Power {

    private static boolean athenaEffect;
    protected List<int[]> validCells;
    protected List<State> stateList;
    protected int currentState;
    protected boolean activeEffect;


    public Power(){
        athenaEffect = false;
        validCells = new ArrayList<int[]>();
        stateList = new ArrayList<State>();
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
     */
    public List<int[]> getValidCells(){
        final List<int[]> res = new ArrayList<int[]>(validCells);
        return res;
    }


    //Setter

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
        List<State> states = new ArrayList<State>();
        states.add(new MovementState());
        states.add(new BuildState());
        states.add(new EndState());
    }


    /**Set the valid Cells where a player can take the current State action
     * @param modelGame
     * @param worker
     */
    public void setValidCells(ModelGame modelGame, Worker worker){
        List<int[]> validPositions = new ArrayList<int[]>();
        int[] workerPosition = modelGame.getWorkerPosition(worker);
        int workerHeight = modelGame.getBoard().getBuildHeight(workerPosition);

        for (int[] position: modelGame.getBoard().getNeighbourCell(workerPosition)){
            int positionHeight = modelGame.getBoard().getBuildHeight(position);
            if (positionHeight > workerHeight + 1 || positionHeight == 4 || modelGame.getWorkerListPosition().contains(position)) continue;
            else validPositions.add(position);
        }

        this.validCells = validPositions;
        AthenaEffectModification(modelGame, worker);
    }

    public void AthenaEffectModification(ModelGame modelGame, Worker worker){
        if (isAthenaEffect() && modelGame.getCurrentState() instanceof MovementState){
            int workerHeight = modelGame.getBoard().getBuildHeight(modelGame.getWorkerPosition(worker));
            for (int[] positionCell : getValidCells()){
                int positionHeight = modelGame.getBoard().getBuildHeight(positionCell);
                if (positionHeight > workerHeight) validCells.remove(positionCell);
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
        modelGame.setCurrentState(getCurrentState());
    }

    /**Initialize the Power
     *
     * @param modelGame is the model of the Game
     * @param worker is the worker that will be used by the player
     */
    public void startPower(ModelGame modelGame, Worker worker){
        setStateList();
        setValidCells(modelGame, worker);
        if (isAthenaEffect() && modelGame.getCurrentState() instanceof MovementState){
            int workerHeight = modelGame.getBoard().getBuildHeight(modelGame.getWorkerPosition(worker));
            for (int[] position : getValidCells()){
                int positionHeight = modelGame.getBoard().getBuildHeight(position);
                if (positionHeight > workerHeight) validCells.remove(position);
            }
        }
    }

    /**Execute the state action
     *
     * @param modelGame is the model of the game
     * @param worker is the worker used by the player
     * @param position is the position where the action will be acted
     */
    public void runPower(ModelGame modelGame, Worker worker, int[] position){
        if (modelGame.getCurrentState() instanceof MovementState || modelGame.getCurrentState() instanceof BuildState){
            modelGame.getCurrentState().executeState(modelGame, worker, position);
            setNextCurrentState(modelGame);
            setValidCells(modelGame, worker);

            if (isAthenaEffect() && modelGame.getCurrentState() instanceof MovementState){
                int workerHeight = modelGame.getBoard().getBuildHeight(modelGame.getWorkerPosition(worker));
                for (int[] positionCell : getValidCells()){
                    int positionHeight = modelGame.getBoard().getBuildHeight(positionCell);
                    if (positionHeight > workerHeight) validCells.remove(positionCell);
                }
            }

        }
    }

    /**Check if the worker used by the player let him win the game
     *
     * @param modelGame is the game
     * @param worker is the worker used by the player
     * @param position is the position from where the worker moved up
     * @return true if the player win, otherwise false
     */
    public boolean isWinner(ModelGame modelGame, Worker worker, int[] position){
        int workerHeight = modelGame.getBoard().getBuildHeight(modelGame.getWorkerPosition(worker));
        if(workerHeight == 3 && modelGame.getBoard().getBuildHeight(position) < 3) return true;

        return false;
    }
}