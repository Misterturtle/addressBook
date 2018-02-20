package input_layer;

import database_layer.DBControllerInterface;

import static input_layer.ViewMessage.*;

public class InputController {

    private final DBControllerInterface dbController;
    private final InputStateInterface state;
    private final ViewInterface view;

    public InputController(ViewInterface view, InputStateInterface state, DBControllerInterface dbController){
        this.view = view;
        this.state = state;
        this.dbController = dbController;
    }

    public void start(){
        view.displayMessage(WELCOME_MESSAGE, NEWLINE, MAIN_MENU_OPTIONS);
        state.changeState(State.MAIN_MENU);
    }

    public void waitForInput(){
        switch (state.getState()){
            case MAIN_MENU:
                view.displayMessage(MAIN_MENU_OPTIONS);
                String userInput = view.waitForInput();
                reactToMainMenuMessage(userInput);

            case ADD_CONTACT:
                view.displayMessage(FIRST_NAME);
                String firstName = view.waitForInput();
                reactToFirstName(firstName);
        }
    }

    private void reactToMainMenuMessage(String message){
        switch (message) {
            case "Add Contact":
                state.changeState(State.ADD_CONTACT);
                waitForInput();
        }
    }


    private void reactToFirstName(String firstName){
        boolean success = dbController.addContact(firstName);
        if(success){
            view.displayMessage(CONTACT_ADDED);
            view.displayMessage(NEWLINE, MAIN_MENU, NEWLINE, MAIN_MENU_OPTIONS);
            state.changeState(State.MAIN_MENU);
        }
    }
}
