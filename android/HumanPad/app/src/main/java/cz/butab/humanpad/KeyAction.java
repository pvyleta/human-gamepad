package cz.butab.humanpad;

import android.view.MotionEvent;

public class KeyAction {
    public static final String KeyPressed = "down";
    public static final String KeyRelease = "up";
    public static final String KeyClick = "toggle";

    public static String from(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_BUTTON_PRESS:
            case MotionEvent.ACTION_DOWN:
                return KeyPressed;

            case MotionEvent.ACTION_BUTTON_RELEASE:
            case MotionEvent.ACTION_UP:
                return KeyRelease;

            default:
                return "";
        }
    }
}
