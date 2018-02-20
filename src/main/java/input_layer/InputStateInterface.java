package input_layer;

import java.util.List;

public interface InputStateInterface {
    public State getState();
    public void changeState(State newState);
}
