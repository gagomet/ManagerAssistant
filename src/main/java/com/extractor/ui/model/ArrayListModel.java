package com.extractor.ui.model;

import javax.swing.AbstractListModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Padonag on 10.11.2014.
 */
public class ArrayListModel<E> extends AbstractListModel {
    private List<E> list = new ArrayList<E>();

    public void setList(List<E> input) {
        this.list.clear();
        this.list = input;
        fireListDataChanged();
    }

    public void clear() {
        this.list.clear();
        fireListDataChanged();
    }

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public E getElementAt(int index) {
        return list.get(index);
    }

    public List<E> getList() {
        return list /*Collections.unmodifiableList(list)*/;
    }

    public void addAll(List<E> elements) {
        if (this.list.addAll(elements)) {
            fireIntervalAdded(elements.size());
        }
        fireListDataChanged();
    }

    public void addOneElement(E newElement){
        this.list.add(newElement);
        fireListDataChanged();
    }

    public int indexOf(E element) {
        return list.indexOf(element);
    }

    protected void fireIntervalAdded(int elementCount) {
        int index0 = list.size() - elementCount;
        fireIntervalAdded(this, Math.max(0, index0),
                Math.max(0, list.size() - 1));
    }

    private void fireListDataChanged() {
        fireContentsChanged(this, 0, Math.max(list.size() - 1, 0));
    }
}
