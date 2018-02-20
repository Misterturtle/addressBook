package input_layer;

public enum ViewMessage {

    WELCOME_MESSAGE("Welcome to the best address book EVAR!"),
    MAIN_MENU("What would you like to do?"),
    MAIN_MENU_OPTIONS("Add Contact"),
    FIRST_NAME("First Name?"),
    CONTACT_ADDED("Successfully Added Contact"),
    NEWLINE("\n");


    private final String message;

    private ViewMessage(String message){
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
