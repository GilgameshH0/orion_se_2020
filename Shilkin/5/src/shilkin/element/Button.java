package shilkin.element;

import shilkin.exception.ReadOnlyException;

public class Button extends Rectangle implements Clickable {
    private final ButtonClickCallback callback;

    public Button(int x, int y, int height, int width, String caption, boolean isEnabled, ButtonClickCallback callback) {
        super(x, y, height, width, caption, isEnabled);
        this.callback = callback;
    }

    @Override
    public void click() throws ReadOnlyException {
        if (!super.isEnabled){
            throw new ReadOnlyException("Элемент выключен");
        }
        callback.onButtonClick(this);
    }

    @Override
    public String toString() {
        return "Кнопка " + "в кооординатах " + x + "," + y + ", высота = " + height + ", ширина = " + width + ", название: " + caption;
    }
}
