package shilkin.element;

import shilkin.exception.ReadOnlyException;

public class Rectangle implements Clickable {
    protected final int x;
    protected final int y;
    protected final int height;
    protected final int width;
    protected final String caption;
    protected final boolean isEnabled;

    public Rectangle(int x, int y, int height, int width, String caption, boolean isEnabled) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.caption = caption;
        this.isEnabled = isEnabled;
    }

    @Override
    public void click() throws ReadOnlyException {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getCaption() {
        return caption;
    }

    public boolean isEnabled() {
        return isEnabled;
    }
}

