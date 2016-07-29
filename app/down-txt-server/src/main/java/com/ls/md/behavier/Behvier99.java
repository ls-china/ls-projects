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
public class Behvier99 implements IBehavier<Element> {

    final String downUrl = "http://downloads2.txt99.com/d/file/p/txt/2016/%s.txt";

    private Tag[] supportTags = {
            new Tag("穿越", "http://www.txt99.com/txt/Chuanyue/"),
            new Tag("重生", "http://www.txt99.com/txt/chongshengxiaoshuo/"),
            new Tag("历史架空", "http://www.txt99.com/txt/Lsjs/"),
            new Tag("总裁豪门", "http://www.txt99.com/txt/Qinggan/"),
            new Tag("言情", "http://www.txt99.com/txt/Young/"),
            new Tag("武侠仙侠", "http://www.txt99.com/txt/Wuxia/"),
            new Tag("耽美", "http://www.txt99.com/txt/dmtr/"),
            new Tag("玄幻", "http://www.txt99.com/txt/Xuanhuan/"),
            new Tag("都市", "http://www.txt99.com/txt/dushi/"),
            new Tag("军事", "http://www.txt99.com/txt/tiexue/"),
            new Tag("网游", "http://www.txt99.com/txt/Juben/"),
            new Tag("惊悚", "http://www.txt99.com/txt/Kongbu/"),
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
        Iterator<Element> articalIt = document.select("div.listbg").iterator();
        Pager pager = new Pager();
        while (articalIt.hasNext()) {
            Element artical = articalIt.next();
            Book book = new Book();
            book = confirmBook(book, artical);
            book.setTag(tag.name);
            pager.addItem(book);
//            break;
        }

        Element pagerUl = document.select("#pages").last().select("ul").get(0);
        if (null != pagerUl) {
            Elements pageChildren = pagerUl.children();
            pager.setTotolCount(
                    NumberUtils.toInt(pageChildren.get(0).html().replace("共", "").replace("部", ""))
            );
            pager.setCurrentPage(page);
            pager.setSize(10);
            pager.setHasPre(page > 1);
            pager.setHasNext(!(pageChildren.get(pageChildren.size() - 1).children().isEmpty()));
        }
        return pager;
    }

    @Override
    public Book confirmBook(Book book, Element data) {
        Elements children = data.children();
        int size = children.size();
        Element aEl = children.get(0);
        book.setTitle(aEl.attr("title"));
        book.setImage(aEl.child(0).attr("src"));
        book.setUpdateTime(children.get(size - 3).html().replace("[", "").replace("]", ""));
        book.setDescribtion(children.get(size - 2).html());

        String[] ass = children.get(size - 1).child(0).html()
                .replace("<small>小说作者：</small>", "")
                .replace("<small>小说进度：</small>", "")
                .replace("<small>文件大小：</small>", "")
                .replace("<small>下载次数：</small>", "")
                .split(" ");
        book.setAuthor(ass[0]);
        book.setStatus(ass[1]);
        book.setSize(ass[2]);
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
        Pager pager = new Behvier99().getPageByTag(new Tag("穿越", "http://www.txt99.com/txt/Chuanyue/"), 2);
        long end = System.currentTimeMillis();
        System.out.println("cost:" + (end - start));
        System.out.println(pager);
    }
}
