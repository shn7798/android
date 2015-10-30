package shn.study.jandan2.api.Vote;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import shn.study.jandan2.beans.Vote;

/**
 * Created by shn7798 on 15-10-30.
 */
public class BaseVote{
    public static Vote parse(Element e) throws NullPointerException{
        Vote vote = new Vote();
        String oo = e.select("span[id~=cos_support-\\d+").text();
        String xx = e.select("span[id~=cos_unsupport-\\d+").text();
        vote.setOo(oo);
        vote.setXx(xx);
        return vote;
    }
}
