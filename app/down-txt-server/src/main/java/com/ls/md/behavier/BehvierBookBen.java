package com.ls.md.behavier;

import com.ls.md.entity.Book;
import com.ls.md.entity.Pager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.Iterator;

/**
 * Created by hx on 16-7-20.
 */
public class BehvierBookBen implements IBehavier<Element> {

    final String downUrl = "http://txt.bookben.com/c_down/2016/06/%s/%s.txt";

    private Tag[] supportTags = {
            new Tag("言情", "http://www.bookben.com/yanqingxiaoshuo/"),
            new Tag("都市", "http://www.bookben.com/dushixiaoshuo/"),
            new Tag("穿越", "http://www.bookben.com/chuanyueshikong/"),
            new Tag("耽美", "http://www.bookben.com/danmei/"),
            new Tag("玄幻", "http://www.bookben.com/xuanhuanqihuan/"),
            new Tag("武侠仙侠", "http://www.bookben.com/wuxiaxianxia/"),
            new Tag("网游", "http://www.bookben.com/youxijingji/"),
            new Tag("历史架空", "http://www.bookben.com/lishijunshi/"),
            new Tag("科幻悬疑", "http://www.bookben.com/kehuanxuanyi/"),
            new Tag("同人", "http://www.bookben.com/tongrenxiaoshuo/"),
            new Tag("文学", "http://www.bookben.com/wenxue/"),
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
        Iterator<Element> articalIt = document.select("div.lblr").get(0).children().iterator();
        Pager pager = new Pager();
        while (articalIt.hasNext()) {
            Element artical = articalIt.next();
            Book book = new Book();
            book = confirmBook(book, artical);
            book.setTag(tag.name);
            pager.addItem(book);
        }
        pager.setSize(20);
        pager.setTotolPage(100);
        pager.setCurrentPage(page);
        pager.setHasPre(page > 1);
        pager.setHasNext(page < 100);
        return pager;
    }

    @Override
    public Book confirmBook(Book book, Element data) {
        Elements children = data.children();
        Element aEl = children.get(0).child(0);
        book.setTitle(aEl.attr("title"));
        String id = aEl.attr("href").replace("/txt/", "").replace(".shtml", "");
        book.setImage(children.get(1).child(0).child(0).attr("src"));
        book.setAuthor(children.get(3).html().replace("作者：",""));
        book.setSize(children.get(4).html().replace("文件大小：",""));
        book.setUpdateTime(children.get(5).html().replace("更新日期：",""));
        book.setUrl(
                String.format(downUrl, id,  book.getTitle())
        );
        return book;
    }

    @Override
    public Pager getPageByKeyword(String keyword, int page) {
        return null;
    }

    public static void main(String[] args) throws Throwable {
        long start = System.currentTimeMillis();
        Pager pager = new BehvierBookBen().getPageByTag(new Tag("都市", "http://www.bookben.com/dushixiaoshuo/"), 1);
        long end = System.currentTimeMillis();
        System.out.println("cost:" + (end - start));
        System.out.println(pager);
    }
}
