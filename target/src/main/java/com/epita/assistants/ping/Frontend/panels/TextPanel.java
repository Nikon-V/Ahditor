package com.epita.assistants.ping.Frontend.panels;

import com.epita.assistants.ping.Frontend.panels.Undo.RedoAction;
import com.epita.assistants.ping.Frontend.panels.Undo.UndoAction;
import fr.epita.assistants.myide.domain.entity.Node;
import fr.epita.assistants.myide.domain.service.FrontendService;
import fr.epita.assistants.myide.domain.service.NodeService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;
import javax.xml.parsers.DocumentBuilder;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TextPanel extends JScrollPane {

    JTextComponent textPane;
    final UndoManager undoManager = new UndoManager();
    Node nodeFile = null;
    private boolean isSaved = true;
    UndoAction undoAction = new UndoAction(undoManager);
    RedoAction redoAction = new RedoAction(undoManager);
    TextLineNumber textLineNumber;

    public TextPanel(){
        super();
        textPane = new JTextArea();
        textLineNumber = new TextLineNumber(textPane);
        textLineNumber.setBorderGap(10);
        textLineNumber.setCurrentLineForeground(Color.orange);
        this.setRowHeaderView(textLineNumber);
        textPane.setOpaque(false);
        setViewportView(textPane);
        setOpaque(false);
        viewport.setOpaque(false);
        getVerticalScrollBar().setBlockIncrement(128);
        setDocumentListener();


        undoManager.addEdit(new SomeUndoableEdit());

        textPane.getDocument().addUndoableEditListener(new UndoableEditListener() {
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
                undoAction.updateUndoState();
                redoAction.updateUndoState();
            }
        });
        AbstractDocument document = (AbstractDocument) textPane.getDocument();
        document.setDocumentFilter(new TextPanel.Auto_indentFilterAndMore("crap"));
    }

    public boolean isSaved(){
        return isSaved;
    }

    void saveFile(){
        if (!isSaved()) {
            if (nodeFile != null) {
                try {
                    NodeService.override(nodeFile, textPane.getText());
                    isSaved = true;
                }
                catch (Exception ignored)
                {}
                //} else {
                //TODO
            }
        }
    }

    void readFile(Node nodeFile) throws IOException {
        if (nodeFile.isFolder())
            return;
        FileReader fr = new FileReader(nodeFile.getPath().toString());
        this.nodeFile = nodeFile;
        isSaved = true;
        this.textPane.read(fr, null);
        setDocumentListener();
        ((AbstractDocument) textPane.getDocument()).setDocumentFilter(new TextPanel.Auto_indentFilterAndMore("crap"));
        fr.close();
        textLineNumber.changedUpdate(new DocumentEvent() {
            @Override
            public int getOffset() {
                return 0;
            }

            @Override
            public int getLength() {
                return 0;
            }

            @Override
            public Document getDocument() {
                return null;
            }

            @Override
            public EventType getType() {
                return null;
            }

            @Override
            public ElementChange getChange(Element elem) {
                return null;
            }
        });
        FrontendService.mainWindow.searchPanel.clearSearchResults();
    }

    private void setDocumentListener()
    {
        textPane.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isSaved = false;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isSaved = false;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isSaved = false;
            }
        });
    }

    public ArrayList<Integer> searchOccurences(String key)
    {
        ArrayList<Integer> results = new ArrayList<Integer>();
        String data = textPane.getText();
        for (int i = 0; i < data.length(); i++) {
            int j;
            for (j = 0; (j < key.length()) && (i + j < data.length()); j++)
            {
                if (data.charAt(i + j) != key.charAt(j))
                    break;
            }
            if (j == key.length())
                results.add(i);
        }
        return results;
    }
    
    public static class Auto_indentFilterAndMore extends DocumentFilter {
        private String crap;

        public Auto_indentFilterAndMore(String supercrap) {
            crap = supercrap;
        }

        private String addIndentation(Document doc, int offset) throws BadLocationException {
            StringBuilder newindent = new StringBuilder("\n");

            Element rootElement = doc.getDefaultRootElement();
            int line = rootElement.getElementIndex( offset );
            int i = rootElement.getElement(line).getStartOffset();

            while (true)
            {
                String temp = doc.getText(i, 1);

                if (temp.equals(" ") || temp.equals("\t"))
                {
                    newindent.append(temp);
                    i++;
                }
                else
                    break;
            }
            return newindent.toString();
        }

        @Override
        public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
            String oldindent = addIndentation(fb.getDocument(), offs);
            if ("\n".equals(str)) {
                str = addIndentation(fb.getDocument(), offs);
            }
            else if ("(".equals(str)) {
                str = "()";
            }
            else if ("[".equals(str)) {
                str = "[]";
                //FrontendService.mainWindow.textPanel.textPane.setCaretPosition(offs - 1);
            }
            else if ("{".equals(str)) {
                str = "{" + addIndentation(fb.getDocument(), offs) + "\t"+ addIndentation(fb.getDocument(), offs)  +"}";
                //FrontendService.mainWindow.textPanel.textPane.setCaretPosition(offs - 1);
            }

            super.insertString(fb, offs, str, a);
            if ("()".equals(str) || "[]".equals(str)) {
                FrontendService.mainWindow.textPanel.textPane.setCaretPosition(FrontendService.mainWindow.textPanel.textPane.getCaretPosition() - 1);
            }
            else if (("{" + addIndentation(fb.getDocument(), offs) + "\t" + addIndentation(fb.getDocument(), offs) +"}").equals(str)) {
                FrontendService.mainWindow.textPanel.textPane.setCaretPosition(FrontendService.mainWindow.textPanel.textPane.getCaretPosition() - oldindent.length() - 1);
            }
        }
    }
}
