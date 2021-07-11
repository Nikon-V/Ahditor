package com.epita.assistants.ping.Frontend.panels.Undo;

import javax.swing.*;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction {
    UndoManager undoManager;
    public RedoAction(UndoManager undoManager){
        super("Redo");
        setEnabled(false);
        this.undoManager = undoManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.undoManager.redo();
        }
        catch (CannotUndoException evt){}
        updateUndoState();
        if (undoManager.canRedo()) {
            setEnabled(true);
            putValue(Action.NAME, undoManager.getUndoPresentationName());
        } else {
            setEnabled(false);
            putValue(Action.NAME, "Undo");
        }
    }

    public void updateUndoState(){
        if (this.undoManager.canRedo()){
            setEnabled(true);
            putValue(Action.NAME, this.undoManager.getRedoPresentationName());
        }else {
            setEnabled(false);
            putValue(Action.NAME, "Redo");
        }
    }
}
