package com.ls.md.service;

import com.ls.md.behavier.*;
import com.ls.md.entity.Pager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hx on 16-7-20.
 */
@Service
public class RouteService {
    private ArrayList<IBehavier> behaviers = new ArrayList<IBehavier>(0);

    public RouteService() {
        behaviers.add(new Behvier99());
        behaviers.add(new BehvierQisuu());
        behaviers.add(new BehvierBookBen());
    }

    public List<String> getTags() {
        ArrayList<String> resultTags = new ArrayList<String>(0);

        for (IBehavier behavier : behaviers) {
            Tag[] tags = behavier.getSupportTags();
            for (Tag tag : tags) {
                if (!resultTags.contains(tag.name)) {
                    resultTags.add(tag.name);
                }
            }
        }

        return resultTags;
    }

    public Pager getPage(String tagName, int page) {
        Pager pager = new Pager();
        for (IBehavier behavier : behaviers) {
            Tag[] tags = behavier.getSupportTags();
            for (Tag tag : tags) {
                if (tag.name.equals(tagName)) {
                    try {
                        pager.merge(
                                behavier.getPageByTag(tag, page)
                        );
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return pager;
    }
}
