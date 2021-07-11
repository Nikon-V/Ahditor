package com.epita.assistants.ping.Frontend.panels;

import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchPanel extends JPanel {
    JTextField textField;
    TextPanel textPanel_;
    JButton nextResult;
    JButton prevResult;
    JButton searchButton;
    JButton clearButton;
    ArrayList<Integer> results;
    int selectedResult;

    Highlighter.HighlightPainter YellowPainter;
    Highlighter.HighlightPainter GreenPainter;

    //TODO Search & Replace?

    public SearchPanel(TextPanel textPanel)
    {
        selectedResult = 0;
        results = null;
        textPanel_ = textPanel;
        YellowPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
        GreenPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN);

        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        textField = new JTextField();

        searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            String key = textField.getText();
            if (key.length() <= 0) {
                results = null;
                selectedResult = 0;
                updateHighlights();
                return;
            }
            results = textPanel_.searchOccurences(key);
            selectedResult = 0;
            updateHighlights();
            if (results.size() > 0) {
                scrollToIthResult(selectedResult);
            }
        });
        add(searchButton);

        prevResult = new JButton("Prev");
        prevResult.addActionListener(e -> {
            if (results != null && results.size() > 1){
                selectedResult--;
                if (selectedResult < 0)
                    selectedResult += results.size();
                selectedResult = selectedResult % results.size();
                scrollToIthResult(selectedResult);
                updateHighlights();
            }
        });
        add(prevResult);

        add(textField);
        nextResult = new JButton("Next");
        nextResult.addActionListener(e -> {
            if (results != null && results.size() > 1){
                selectedResult++;
                selectedResult = selectedResult % results.size();
                scrollToIthResult(selectedResult);
                updateHighlights();
            }
        });
        add(nextResult);

        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            clearSearchResults();
            textField.setText("");
        });
        add(clearButton);

        textField.addActionListener(e -> {
            String key = textField.getText();
            if (key.length() <= 0) {
                results = null;
                selectedResult = 0;
                updateHighlights();
                return;
            }
            results = textPanel_.searchOccurences(key);
            selectedResult = 0;
            updateHighlights();
            if (results.size() > 0) {
                scrollToIthResult(selectedResult);
            }
        });

        setMinimumSize(new Dimension(0, 30));
        setMaximumSize(new Dimension(1000, 30));

    }

    public void textAreaRequestFocus(){
        textField.requestFocus();
    }

    public void scrollToIthResult(int r) {
        try {
            if (r < results.size()) {
                var v = textPanel_.textPane.modelToView2D(results.get(r)).getBounds();
                textPanel_.getVerticalScrollBar().setValue(v.y);
                var visibleRec = textPanel_.getVisibleRect();
                if (textPanel_.getHorizontalScrollBar().getValue() + visibleRec.width < v.x || textPanel_.getHorizontalScrollBar().getValue() > v.x){
                    textPanel_.getHorizontalScrollBar().setValue(v.x);
                }
            }
        }
        catch (Exception ignored) { }
    }

    public void clearSearchResults(){
        results = null;
        selectedResult = 0;
        updateHighlights();
    }

    public void updateHighlights() {
        if (results == null)
        {
            textPanel_.textPane.getHighlighter().removeAllHighlights();
        }
        else
        {
            String Key = textField.getText();
            Highlighter highlighter = textPanel_.textPane.getHighlighter();
            highlighter.removeAllHighlights();
            for (int i = 0; i < results.size(); i++){
                try {
                    if (i == selectedResult)
                        highlighter.addHighlight(results.get(i), results.get(i) + Key.length(), GreenPainter);
                    else
                        highlighter.addHighlight(results.get(i), results.get(i) + Key.length(), YellowPainter);
                } catch (Exception ignored) {}

            }
        }
    }

}
