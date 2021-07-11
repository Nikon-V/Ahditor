package com.epita.assistants.ping.Frontend.panels;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class SomeUndoableEdit extends AbstractUndoableEdit {
    public SomeUndoableEdit() {
        //System.out.println("SomeUndoableEdit has been created");
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
        //System.out.println("SomeUndoableEdit has been undone.");
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
        //System.out.println("SomeUndoableEdit has been redone.");
    }
}
