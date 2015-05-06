package org.collaborative.cycling.webapp.controllers;

public class HelpMsgDto {
    public boolean nearby;
    public boolean group;
    public String text;

    @Override
    public String toString() {
        return "HelpMsgDto{" +
                "nearby=" + nearby +
                ", group=" + group +
                ", text='" + text + '\'' +
                '}';
    }
}
