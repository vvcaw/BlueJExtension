package de.elfsoft;

import bluej.extensions.BlueJ;
import bluej.extensions.Extension;
import bluej.extensions.ExtensionException;
import bluej.extensions.event.ClassEvent;
import bluej.extensions.event.ClassListener;
import bluej.extensions.event.PackageEvent;
import bluej.extensions.event.PackageListener;

import javax.swing.*;


public class lol extends Extension implements PackageListener {

    public boolean isCompatible() {
        return true;
    }

    public void startup(final BlueJ blueJ) {
        EditorFinder editorFinder = new EditorFinder(blueJ);
        blueJ.addPackageListener(this);
    }

    public String getName() {
        return "No More Sample Classes";
    }

    public String getVersion() {
        return "1.0.0";
    }

    public void packageOpened(PackageEvent packageEvent) {
        try {
            System.out.println ("Project " + packageEvent.getPackage().getProject().getName() + " opened.");
        } catch (ExtensionException e) {
            System.out.println("Project closed by BlueJ");
        }
    }

    public void packageClosing(PackageEvent packageEvent) {

    }
}


