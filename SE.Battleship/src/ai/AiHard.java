package ai;

import model.general.Constances;
import model.playground.Coordinates;

public class AiHard extends AiBase {

    private Coordinates firstHit = null;
    private Coordinates nextShot = null;
    
    @Override
    public void initialize(int r, int c) {
        baseInitialize(r, c);
    }

    @Override
    public Coordinates getCoordinates() {
        nextShot = (null == firstHit) ? getRandomCoordinates() : getLogicChoosenCoordinates();
        checkCoordinates();
        return (null == nextShot) ? getRandomCoordinates() : nextShot;
    }

    private Coordinates getLogicChoosenCoordinates() {
        randomDirection();
        return (null == nextShot) ? getRandomCoordinates() : nextShot;
    }
    
    private void checkCoordinates() {
        if (removeCoordinates(nextShot)) {
            nextShot = null;
        }
    }
    
    private Coordinates followPosition() {
        int rowDiv = (firstHit.getRow() > nextShot.getRow()) ? -1 : 1;
        int columnDiv = (firstHit.getColumn() > nextShot.getColumn()) ? -1 : 1;
        return new Coordinates(firstHit.getRow() + rowDiv, firstHit.getColumn() + columnDiv);
    }
    
    private Coordinates reversePosition() {
        int rowDiv = (firstHit.getRow() > nextShot.getRow()) ? -1 : 1;
        int columnDiv = (firstHit.getColumn() > nextShot.getColumn()) ? -1 : 1;
        return new Coordinates(firstHit.getRow() + rowDiv, firstHit.getColumn() + columnDiv);
    }        

    private void randomDirection() {
        if (null == firstHit){
            return;
        }
        Coordinates left = new Coordinates(firstHit.getRow(), firstHit.getColumn() - 1);
        if (removeCoordinates(left)) {
            nextShot = new Coordinates(left);
            return;
        }
        Coordinates up = new Coordinates(firstHit.getRow() - 1, firstHit.getColumn());
        if (removeCoordinates(up)) {
            nextShot = new Coordinates(up);
            return;
        }
        Coordinates right = new Coordinates(firstHit.getRow(), firstHit.getColumn() + 1);
        if (removeCoordinates(right)) {
            nextShot = new Coordinates(right);
            return;
        }        

        Coordinates down = new Coordinates(firstHit.getRow() + 1, firstHit.getColumn());
        if (removeCoordinates(down)) {
            nextShot = new Coordinates(down);
            return;
        }
    }
    
    @Override
    public void shotResult(int status) {
        lastShotHit(status);
        lastShotDestroyed(status);
        lastShotMissed(status);
        lastShotInvalid(status);
    }

    private void lastShotMissed(int status) {
        if (Constances.SHOOT_MISS != status) {
            return;
        }
        if (null != firstHit) {
            nextShot = reversePosition();          
        }
    }

    private void lastShotInvalid(int status) {
        if (Constances.SHOOT_INVALID != status) {
            return;
        }
        if (null != firstHit) {
            nextShot = reversePosition();          
        }
    }
    
    private void lastShotDestroyed(int status) {
        if (Constances.SHOOT_DESTROYED == status) {
            nextShot = null;
            firstHit = null;
        }
    }

    private void lastShotHit(int status) {
        if (Constances.SHOOT_HIT != status) {
            return;
        }
        if (null == firstHit) {
            firstHit = new Coordinates(nextShot);  
        } else {
            nextShot = followPosition();
        }
    }

}
