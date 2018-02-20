package input_layer;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CLIView implements ViewInterface {

    @Override
    public String waitForInput() {
        throw new NotImplementedException();
    }

    @Override
    public void displayMessage(ViewMessage... message) {
        throw new NotImplementedException();
    }
}
