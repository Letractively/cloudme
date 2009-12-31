package org.cloudme.webgallery.stripes.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.integration.spring.SpringBean;

import org.cloudme.webgallery.Photo;
import org.cloudme.webgallery.service.PhotoService;

@UrlBinding("/gallery/home")
public class HomeActionBean extends AbstractActionBean {
    @SpringBean
    private PhotoService service;
    
    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution(getJspPath("/gallery/home"));
    }
    
    public Collection<Photo> getPhotos() {
        ArrayList<Photo> photos = new ArrayList<Photo>(service.findAll());
        Collections.shuffle(photos);
        if (photos.size() <= 16) {
            return photos;
        }
        return photos.subList(0, 16);
    }
}
