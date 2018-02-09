public class AddressBookCLI implements InputInterface {

    public static final String WELCOME_MESSAGE = "Welcome to the best address book EVAR! What would you like to do?";
    public static final String ADD_CONTACT = "Add Contact";



    public String start(){

        String welcomeMessage = WELCOME_MESSAGE + "\n" + ADD_CONTACT;

        return welcomeMessage;
    }
}
