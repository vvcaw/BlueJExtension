package de.elfsoft;

import bluej.extensions.*;
import bluej.extensions.ClassNotFoundException;
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
    BClass lasClass;
    boolean lastTime = true;

    EditorFinder(BlueJ blueJ) {
        currentClasses = new BClass[100];
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
        boolean lastClassIsSuperClass = false;

        try {
            if(!lastClassIsSuperClass){
                BMethod[] methods = classEvent.getBClass().getMethods();
                for(BMethod bMethod:methods){
                    if(bMethod.getName().equals("beispielMethode")){
                        String bClassName = classEvent.getBClass().getName();
                        Editor e = classEvent.getBClass().getEditor();
                        int lineCount = e.getLineCount() -1;
                        int lineLenght = e.getLineLength(e.getLineCount() -1);
                        if(lineLenght == -1) {
                            lineLenght = 0;
                        }
                        e.setText(new TextLocation(0, 0), new TextLocation(lineCount, lineLenght), "class " + bClassName + "{" + "\n    " + bClassName + "(){" + "\n    " + "        " + "\n    " + "}" + "\n" + "}");
                        break;
                    }
                }
            }
        } catch (ProjectNotOpenException | ClassNotFoundException | PackageNotFoundException e) {
            e.printStackTrace();
        }


        if(!classEvent.getBClass().equals(activeClass) && !lastClassIsSuperClass){
            lasClass = classEvent.getBClass();
            activeClass = classEvent.getBClass();
            try {
                activeEditor = activeClass.getEditor();
                activeEditor.showMessage("HELPER IS ACTIVATED!");
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