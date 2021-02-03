package shilkin.element;

import shilkin.exception.ReadOnlyException;

public interface Clickable {
    void click() throws ReadOnlyException;
}
