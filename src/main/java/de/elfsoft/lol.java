package de.elfsoft;

import bluej.extensions.*;
import bluej.extensions.editor.*;
import bluej.extensions.event.PackageEvent;
import bluej.extensions.event.PackageListener;

public class lol extends Extension implements PackageListener {

    public boolean isCompatible() {
        return true;
    }

    public void startup(BlueJ blueJ) {
        System.out.println("Hello");
    }

    public String getName() {
        return "Hello World Extension";
    }

    public String getVersion() {
        return "0.0.1";
    }

    public void packageOpened(PackageEvent packageEvent) {

    }

    public void packageClosing(PackageEvent packageEvent) {

    }
}
