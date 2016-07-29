package com.ls.md.behavier;

import com.ls.md.entity.Book;
import com.ls.md.entity.Pager;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Iterator;

/**
 * Created by hx on 16-7-20.
 */
public class BehvierQisuu implements IBehavier<Element> {

    final String downUrl = "http://dzs.qisuu.com/txt/%s.txt";

    private Tag[] supportTags = {
            new Tag("玄幻", "http://www.qisuu.com/soft/sort01/"),
            new Tag("武侠仙侠", "http://www.qisuu.com/soft/sort02/"),
            new Tag("言情", "http://www.qisuu.com/soft/sort03/"),
            new Tag("都市", "http://www.qisuu.com/soft/sort04/"),
            new Tag("历史架空", "http://www.qisuu.com/soft/sort05/"),
            new Tag("网游", "http://www.qisuu.com/soft/sort06/"),
            new Tag("惊悚", "http://www.qisuu.com/soft/sort07/"),
            new Tag("同人", "http://www.qisuu.com/soft/sort08/"),
            new Tag("文学", "http://www.qisuu.com/soft/sort10/"),
    };

    @Override
    public Tag[] getSupportTags() {
        return supportTags;
    }

    @Override
    public Pager getPageByTag(Tag tag, int page) throws Throwable {
        String url = tag.url;
        if (page > 1) {
            url = url + "index_" + page + ".html";
        }
        Document document = Jsoup.parse(new URL(url), 30000);
        Elements listBoxEl = document.select("div.listBox").first().children();
        Pager pager = new Pager();
        Iterator<Element> articalIt = listBoxEl.get(1).children().iterator();
        while (articalIt.hasNext()) {
            Element artical = articalIt.next();
            Book book = new Book();
            book = confirmBook(book, artical);
            book.setTag(tag.name);
            pager.addItem(book);
            break;
        }

        Element pagerDiv = listBoxEl.get(2);
        pager.setCurrentPage(page);
        pager.setSize(15);
        if (null != pagerDiv) {
            String pageText = pagerDiv.text();
            pageText = pageText.substring(3, pageText.indexOf("首页")).trim();
            int indexEvery = pageText.indexOf("每页");
            int indexAll = pageText.indexOf("总数");
            String[] pageTextArr = new String[3];
            pageTextArr[0] = pageText.substring(0, indexEvery - 1);
            pageTextArr[1] = pageText.substring(indexEvery + 2, indexAll - 1);
            pageTextArr[2] = pageText.substring(indexAll);
            pager.setTotolPage(
                    NumberUtils.toInt(pageTextArr[0].substring(pageTextArr[0].indexOf("/") + 1))
            );
            pager.setHasPre(page > 1);
            pager.setHasPre(page < pager.getTotolPage());
            pager.setTotolCount(
                    NumberUtils.toInt(pageTextArr[2].replace("总数", "").replaceAll("\\W", ""))
            );
        }
        return pager;
    }

    @Override
    public Book confirmBook(Book book, Element data) {
        Elements children = data.children();
        String[] ass = children.get(0).text()
                .replace("作者：", "")
                .replace("大小：", "")
                .replace("等级：", "")
                .replace("更新：", "")
                .split(" ");
        book.setAuthor(ass[0]);
        book.setStatus("全本");
        book.setSize(ass[1]);
        book.setUpdateTime(ass[3]);

        Element ael = children.get(1);
        String title = ael.text();
        title = title.substring(1, title.indexOf("》"));
        book.setTitle(title);
        book.setImage(ael.child(0).attr("src"));

        book.setDescribtion(children.get(2).html());
        book.setUrl(
                String.format(downUrl, book.getTitle())
        );
        return book;
    }

    @Override
    public Pager getPageByKeyword(String keyword, int page) {
        return null;
    }

    public static void main(String[] args) throws Throwable {
        long start = System.currentTimeMillis();
        Pager pager = new BehvierQisuu().getPageByTag(new Tag("玄幻", "http://www.qisuu.com/soft/sort01/"), 2);
        long end = System.currentTimeMillis();
        System.out.println("cost:" + (end - start));
        System.out.println(pager);
    }
}
