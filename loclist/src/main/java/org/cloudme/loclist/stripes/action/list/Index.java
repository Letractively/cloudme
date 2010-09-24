package org.cloudme.loclist.stripes.action.list;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.stripestools.AbstractActionBean;

@UrlBinding( "/action/list/index" )
public class Index extends AbstractActionBean {
    @DefaultHandler
    public Resolution show() {
        return jspForwardResolution();
    }
}
