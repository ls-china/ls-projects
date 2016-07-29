package com.ls.md.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ls.core.entity.BasicEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hx on 16-7-20.
 */
public class Pager extends BasicEntity {
    private int currentPage = Integer.MAX_VALUE;
    private int totolCount;
    private int totolPage;
    private int size;
    private boolean hasNext;
    private boolean hasPre;
    private List<Book> items = new ArrayList<Book>(0);

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotolCount() {
        return totolCount;
    }

    public void setTotolCount(int totolCount) {
        this.totolCount = totolCount;
    }

    public int getTotolPage() {
        return totolPage;
    }

    public void setTotolPage(int totolPage) {
        this.totolPage = totolPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPre() {
        return hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }

    @JsonIgnore
    public void addItem(Book item) {
        if (null == item) {
            return;
        }
        this.items.add(item);
    }

    @JsonIgnore
    public void addItems(Collection<Book> items) {
        if (null == items) {
            return;
        }
        this.items.addAll(items);
    }

    @JsonIgnore
    public void merge(Pager pager) {
        if (null == pager) {
            return;
        }
        this.currentPage = Math.min(pager.currentPage, currentPage);
        this.size += pager.size;
        this.totolCount += pager.totolCount;
        this.totolPage += pager.totolPage;
        this.hasNext |= pager.hasNext;
        this.hasPre |= pager.hasPre;
        this.addItems(pager.getItems());
    }

    @Override
    @JsonIgnore
    public String toString() {
        return "Pager{" +
                "currentPage=" + currentPage +
                ", totolCount=" + totolCount +
                ", totolPage=" + totolPage +
                ", size=" + size +
                ", hasNext=" + hasNext +
                ", hasPre=" + hasPre +
                ", items=" + items +
                '}';
    }
}
