package database_layer;

import database_layer.models.DBModelInterface;

public class DBController implements DBControllerInterface {

    DBModelInterface model;

    public DBController(DBModelInterface model){
        this.model = model;
    }

    @Override
    public boolean addContact(String firstName) {
        return model.addNewContact(firstName);
    }
}
