package it.polimi.ingsw.model.event;

import it.polimi.ingsw.model.event.response.Ack;
import it.polimi.ingsw.model.event.response.Nack;
import it.polimi.ingsw.model.event.response.Response;
import it.polimi.ingsw.model.god.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Piersilvio Mancuso
 */
public class GodList {

    private List<God> godList;

    public GodList(){
        godList = new ArrayList<>();
        godList.add(new Apollo());
        godList.add(new Atlas());
        godList.add(new Athena());
        godList.add(new Artemis());
        godList.add(new Demeter());
        godList.add(new Hephaestus());
        godList.add(new Minotaur());
        godList.add(new Pan());
        godList.add(new Prometheus());
    }

    public List<God> getGodList() {
        return godList;
    }

    public void setGodList(List<Integer> godChosen){
        for (Integer i : godChosen){
            if (i >= godList.size()) throw new IllegalArgumentException();
        }

        List <God> res = new ArrayList<>();
        for (int i = 0; i < godList.size(); i++){
            if (godChosen.contains(i)) res.add(godList.get(i));
        }
        godList = res;
    }



}
