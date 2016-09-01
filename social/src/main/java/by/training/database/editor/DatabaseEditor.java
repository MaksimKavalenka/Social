package by.training.database.editor;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class DatabaseEditor {

    @Autowired
    protected SessionFactory sessionFactory;

    public DatabaseEditor() {
    }

    public DatabaseEditor(final SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
