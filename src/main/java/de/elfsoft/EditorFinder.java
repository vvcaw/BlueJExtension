package de.elfsoft;

import bluej.extensions.*;
import bluej.extensions.editor.Editor;
import bluej.extensions.editor.TextLocation;
import bluej.extensions.event.ClassEvent;
import bluej.extensions.event.ClassListener;
import bluej.extensions.event.ExtensionEvent;
import bluej.extensions.event.ExtensionEventListener;
import bluej.extensions.painter.ExtensionClassTargetPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class EditorFinder {

    BClass activeClass;
    Editor activeEditor;
    int lineCount = 0;

    EditorFinder(BlueJ blueJ) {
        blueJ.addClassListener(new ClassListener() {
            @Override
            public void classStateChanged(ClassEvent classEvent) {
                handleClassStateChange(classEvent);
            }
        });
        //customThread.start();
    }

    void handleClassStateChange(ClassEvent classEvent){
        if(!classEvent.getBClass().equals(activeClass)){
            activeClass = classEvent.getBClass();
            try {
                activeEditor = activeClass.getEditor();
            } catch (ProjectNotOpenException | PackageNotFoundException e) {
                e.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Active Class: " + activeClass.getName() + ", line count: " + activeEditor.getLineCount());
        }
    }

    /*private final Thread customThread = new Thread(new Runnable() {
        @Override
        public void run() {
            JOptionPane.showMessageDialog(null, "Thread started!");
            while (!Thread.currentThread().isInterrupted()){
                if(activeEditor.getLineCount() != lineCount){
                    lineCount = activeEditor.getLineCount();
                    JOptionPane.showMessageDialog(null, activeEditor.getLineCount());
                }
            }
        }
    });*/
}