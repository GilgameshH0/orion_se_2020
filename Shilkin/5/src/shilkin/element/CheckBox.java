package shilkin.element;

import shilkin.exception.ReadOnlyException;

public class CheckBox extends Rectangle implements Clickable {

    private boolean isPressed;

    public CheckBox(int x, int y, int height, int width, String caption, boolean isEnabled, boolean isPressed) {
        super(x, y, height, width, caption, isEnabled);
        this.isPressed = isPressed;
    }

    public boolean isPressed() {
        return isPressed;
    }

    @Override
    public void click() throws ReadOnlyException {

        if (!super.isEnabled){
            throw new ReadOnlyException("Элемент выключен");
        }
        this.isPressed = !this.isPressed;
    }

    @Override
    public String toString() {
        return "Галка " + "в кооординатах " + x + ", " + y + ", высота = " + height + ", ширина = " + width + ", название: " + caption;
    }
}
