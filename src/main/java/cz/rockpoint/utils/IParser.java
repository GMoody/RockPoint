package cz.rockpoint.utils;

import org.jsoup.select.Elements;

// Interface that will handle receiving and processing information from the source
public interface IParser {
    Elements getTableDataFromURL(String URL);
    void updateDataFromLong();
    void updateDataFromHalf();
    void updateDataFromShort();
    void updateDataFromAllURLs();
    void updateURL(int URLid);
    void clearEntities();
    int getURLid();
}
