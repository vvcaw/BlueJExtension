package de.elfsoft;

import bluej.extensions.*;
import bluej.extensions.editor.Editor;
import bluej.extensions.editor.TextLocation;
import bluej.extensions.event.*;
import bluej.extensions.painter.ExtensionClassTargetPainter;
import org.graalvm.compiler.java.BciBlockMapping;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class EditorFinder {

    BClass activeClass;
    Editor activeEditor;
    int lineCount = 0;
    BClass[] currentClasses;

    EditorFinder(BlueJ blueJ) {
        blueJ.addClassListener(new ClassListener() {
            @Override
            public void classStateChanged(ClassEvent classEvent) {
                handleClassStateChange(classEvent);
            }
        });
        blueJ.addPackageListener(new PackageListener() {
            @Override
            public void packageOpened(PackageEvent packageEvent) {
                try {
                    currentClasses = packageEvent.getPackage().getClasses();
                } catch (ProjectNotOpenException | PackageNotFoundException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void packageClosing(PackageEvent packageEvent) {

            }
        });
        //customThread.start();
    }

    void handleClassStateChange(ClassEvent classEvent){
        for (BClass wantedClass:currentClasses) {
            if(classEvent.getBClass().equals(wantedClass)){
            }
            else {
                BClass newClass = classEvent.getBClass();
                try {
                    newClass.getEditor().setText(new TextLocation(0, 0), new TextLocation(newClass.getEditor().getLineCount(), newClass.getEditor().getLineLength(newClass.getEditor().getLineCount())), "hallo");
                } catch (ProjectNotOpenException | PackageNotFoundException e) {
                    e.printStackTrace();
                }
                currentClasses[currentClasses.length -1] = classEvent.getBClass();
            }
        }

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