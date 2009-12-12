package org.cloudme.webgallery.stripes.action.organize;

import java.io.IOException;
import java.util.List;

import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.apache.commons.io.IOUtils;
import org.cloudme.webgallery.Photo;

@UrlBinding("/organize/{galleryId}/photo/${event}/{id}")
public class PhotoActionBean extends AbstractOrganizeActionBean<Photo> {
	private List<Photo> items;
	private Photo photo;

	public PhotoActionBean() {
		super("/organize/photo");
	}

    public void setPhotoFile(FileBean photoFile) throws IOException {
        System.out.println(photoFile.getFileName());
        System.out.println(IOUtils.toByteArray(photoFile.getInputStream()).length);
        System.out.println(photoFile.getContentType());
        System.out.println(photoFile.getSize());
        photo = new Photo();
        photo.setImageDataAsArray(IOUtils.toByteArray(photoFile.getInputStream()));
    }
    
    public Resolution upload() {
        System.out.println("Upload complete.");
		getService().save(photo);
        return new RedirectResolution(GalleryActionBean.class);
    }

	@Override
	public List<Photo> getItems() {
		return items;
	}

	@Override
	public void setItems(List<Photo> items) {
		this.items = items;
	}
}
