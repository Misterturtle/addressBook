import database_layer.DBControllerInterface;
import database_layer.models.DBModelInterface;
import database_layer.DBController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DBControllerTest {

    DBControllerInterface dbController;
    @Mock
    DBModelInterface dbModel;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        dbController = new DBController(dbModel);
    }

    @Test
    public void whenDBControllerAddsContactThenDBModelShouldAddAContact() {
        dbController.addContact("Cory");

        verify(dbModel).addNewContact("Cory");
    }

    @Test
    public void whenDBControllerAddsContactItShouldReturnTrueIfSuccessful(){
        when(dbModel.addNewContact("Cory")).thenReturn(true);

        boolean result = dbController.addContact("Cory");

        assertEquals(true, result);
    }
}
