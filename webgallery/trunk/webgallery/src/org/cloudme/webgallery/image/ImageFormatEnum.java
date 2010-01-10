package org.cloudme.webgallery.image;

public enum ImageFormatEnum implements ImageFormat {
    THUMBNAIL("t", 210, true), LARGE("l", 894, false);
    
    private final String id;
    private final boolean isCrop;
    private final int width;
    private final int height;

    private ImageFormatEnum(String id, int size, boolean isCrop) {
        this(id, size, size, isCrop);
    }
    
    private ImageFormatEnum(String id, int width, int height, boolean isCrop) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.isCrop = isCrop;
    }

    public String getId() {
        return id;
    }

    public boolean isCrop() {
        return isCrop;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
