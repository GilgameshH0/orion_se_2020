package shilkin;

import shilkin.element.Rectangle;
import shilkin.exception.ElementsOverlapException;
import shilkin.exception.OutOfSceneException;

public class UI {

    private Rectangle[] elements;

    public Rectangle[] getAllElements() {
        return this.elements;
    }

    public void addElement(Rectangle rectangle) throws ElementsOverlapException, OutOfSceneException {
        if (this.elements == null) {
            Rectangle[] temporaryArray = new Rectangle[1];
            temporaryArray[0] = rectangle;
            this.elements = temporaryArray;
            return;
        }

        if (getOutScene(rectangle)) {
            throw new OutOfSceneException("Элемент " + rectangle.getCaption() + " не может быть добавлен, т.к. выходит за границы сцены");
        }
        if (getIntersection(rectangle).isIntersect()) {
            throw new ElementsOverlapException("Элемент " + rectangle.getCaption() + " не может быть добавлен, т.к. пересекается с элементом " + getIntersection(rectangle).getElement());
        }

        Rectangle[] temporaryArray = new Rectangle[this.elements.length + 1];
        System.arraycopy(this.elements, 0, temporaryArray, 0, this.elements.length);
        temporaryArray[this.elements.length] = rectangle;
        this.elements = temporaryArray;
    }

    private InterCheckResult getIntersection(Rectangle rectangle) {
        InterCheckResult checkResult = new InterCheckResult();
        for (Rectangle element : this.elements) {
            if ((rectangle.getX() < element.getX() + element.getWidth()) && (rectangle.getX() + rectangle.getWidth() > element.getX())
                    && (rectangle.getY() < element.getY() + element.getHeight()) && (rectangle.getY() + rectangle.getHeight() > element.getY())) {
                checkResult.setElement(element.getCaption());
                checkResult.setIntersect(true);
                break;
            }
        }
        return checkResult;
    }

    boolean getOutScene(Rectangle rectangle){
        int maxX = 100;
        int maxY = 100;
        return (rectangle.getX() + rectangle.getWidth() > maxX) || (rectangle.getX() + rectangle.getHeight() > maxY);
    }
}
