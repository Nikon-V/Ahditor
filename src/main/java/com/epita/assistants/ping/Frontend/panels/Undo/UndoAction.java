package com.epita.assistants.ping.Frontend.panels.Undo;

import com.epita.assistants.ping.Frontend.panels.TextPanel;

import javax.swing.*;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction{
    UndoManager undoManager;
    public UndoAction(UndoManager undoManager){
        super("Undo");
        setEnabled(false);
        this.undoManager = undoManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.undoManager.undo();
        }
        catch (CannotUndoException evt){}
        updateUndoState();
        if (undoManager.canRedo()) {
            setEnabled(true);
            putValue(Action.NAME, undoManager.getRedoPresentationName());
        } else {
            setEnabled(false);
            putValue(Action.NAME, "Redo");
        }
    }

    public void updateUndoState(){
        if (this.undoManager.canUndo()){
            setEnabled(true);
            putValue(Action.NAME, this.undoManager.getUndoPresentationName());
        }else {
            setEnabled(false);
            putValue(Action.NAME, "Undo");
        }
    }
}
