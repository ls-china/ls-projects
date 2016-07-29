package com.ls.downloadtxt.net.resp;

import java.io.Serializable;
import java.util.List;

public class ArticalPage implements Serializable {
    public int currentPage;
    public int totolCount;
    public int totolPage;
    public int size;
    public boolean hasNext;
    public boolean hasPre;
    public List<Items> items;
}
