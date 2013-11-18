package ai;

import model.playground.Coordinates;


public class AiEasy extends AiBase {

    @Override
    public void initialize(int r, int c) {
        baseInitialize(r, c);
    }

    @Override
    public Coordinates getCoordinates() {
        return getRandomCoordinates();
    }

    @Override
    public void shotResult(int status) {
        /* the easy AI do not save any status of a shot. */
    }

}