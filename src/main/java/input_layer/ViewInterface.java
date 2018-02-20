package input_layer;

public interface ViewInterface {
    public String waitForInput();

    public void displayMessage(ViewMessage... message);
}
