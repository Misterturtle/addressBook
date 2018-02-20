import database_layer.DBControllerInterface;
import input_layer.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static input_layer.ViewMessage.*;

// import static input_layer.ViewMessage.WELCOME_MESSAGE;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class InputControllerTest {

    InputController inputController;
    @Mock
    InputStateInterface state;
    @Mock
    ViewInterface view;
    @Mock
    DBControllerInterface dbController;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        inputController = new InputController(view, state, dbController);
    }

    @Test
    public void onStartupTheViewShouldDisplayTheWelcomeMessageWithOptions(){
        inputController.start();

        verify(view).displayMessage(WELCOME_MESSAGE, NEWLINE, MAIN_MENU_OPTIONS);
    }

    @Test
    public void onStartupTheControllerShouldSetTheStateToMainMenu() {
        inputController.start();

        verify(state).changeState(State.MAIN_MENU);
    }

    @Test
    public void givenAddContactStateTheViewShouldPromptForTheFirstName(){
        when(state.getState()).thenReturn(State.ADD_CONTACT);

        inputController.waitForInput();

        verify(view).displayMessage(FIRST_NAME);
    }

    @Test
    public void givenAddContactStateAfterPromptedForFirstNameTheDBControllerShouldAddContact() {
        when(state.getState()).thenReturn(State.ADD_CONTACT);
        when(view.waitForInput()).thenReturn("Cory");

        inputController.waitForInput();

        verify(dbController).addContact("Cory");
    }

    @Test
    public void givenAddContactStateAfterSuccessfulContactEntryTheViewShouldDisplaySuccessfulMessage() {
        when(state.getState()).thenReturn((State.ADD_CONTACT));
        when(view.waitForInput()).thenReturn("Cory");
        when(dbController.addContact("Cory")).thenReturn(true);

        inputController.waitForInput();

        verify(view).displayMessage(CONTACT_ADDED);
    }

    @Test
    public void givenAddContactStateAfterSuccessfulContactEntryTheViewShouldDisplayMainMenuMessageWithOptions(){
        when(state.getState()).thenReturn((State.ADD_CONTACT));
        when(view.waitForInput()).thenReturn("Cory");
        when(dbController.addContact("Cory")).thenReturn(true);

        inputController.waitForInput();

        verify(view).displayMessage(NEWLINE, MAIN_MENU, NEWLINE, MAIN_MENU_OPTIONS);
    }

    @Test
    public void givenAddContactStateAfterSuccessfulContactEntryTheStateShouldBeSetToMainMenu(){
        when(state.getState()).thenReturn((State.ADD_CONTACT));
        when(view.waitForInput()).thenReturn("Cory");
        when(dbController.addContact("Cory")).thenReturn(true);

        inputController.waitForInput();

        verify(state).changeState(State.MAIN_MENU);
    }


}
