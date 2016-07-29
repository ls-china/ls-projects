package com.ls.md.behavier;

import com.ls.md.entity.Book;
import com.ls.md.entity.Pager;

/**
 * Created by hx on 16-7-20.
 */
public interface IBehavier<D> {

    public Tag[] getSupportTags();

    public Pager getPageByTag(Tag tag, int page) throws Throwable;

    public Book confirmBook(Book book, D data);

    public Pager getPageByKeyword(String keyword, int page);

}
