<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="w" uri="/WEB-INF/tags/webgallery.tld" %>

<s:layout-render name="/WEB-INF/layout/default.jsp" title="">
<s:layout-component name="headCss">
</s:layout-component>
<s:layout-component name="headJs">
</s:layout-component>
<s:layout-component name="content">
<div id="albums"><div>
<c:forEach items="${actionBean.albums}" var="album"><a href="${(album.id == actionBean.albumId) ? '/' : w:url(album)}" class="album${(album.id == actionBean.albumId) ? " selected" : ""}">${album.name}</a></c:forEach>
</div></div>
<div id="photo">
<div><a href="/gallery/album/${actionBean.albumId}"><img src="/gallery/photo/${actionBean.photoId}_l.jpg"/></a></div>
<div id="thumbnails">
<c:forEach items="${actionBean.photos}" var="photo"><a href="/gallery/album/${actionBean.albumId}/photo/${photo.id}"><img src="/gallery/photo/${photo.id}_82x82.jpg"/></a></c:forEach>
</div>
</div>
</s:layout-component>
<s:layout-component name="footerLink">
<a href="/organize/album">organize</a>
</s:layout-component>
</s:layout-render>
