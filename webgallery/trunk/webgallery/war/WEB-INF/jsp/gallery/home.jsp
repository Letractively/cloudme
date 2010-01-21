<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<s:layout-render name="/WEB-INF/layout/default.jsp" title="">
<s:layout-component name="headCss">
</s:layout-component>
<s:layout-component name="headJs">
<script type="text/javascript" src="/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
  $(window).bind("resize", updateBackground);
  updateBackground();
});

function updateBackground() {
  var id = "${actionBean.randomPhotoId}";
  var dim = $(document).width() + "x" + $(document).height();
  var img = "/gallery/photo/" + id + "_" + dim + ".jpg";
  $("body").css("background", "url(" + img + ") repeat fixed");
}
</script>
</s:layout-component>
<s:layout-component name="content">
<div id="albums">
<ul>
<c:forEach items="${actionBean.albums}" var="album">
<li>
  	<a href="/gallery/album/${album.id}" class="album">${album.name}</a>
</li>
</c:forEach>
</ul>
</div>
</s:layout-component>
<s:layout-component name="footerLink">
<a href="/organize/album">organize</a>
</s:layout-component>
</s:layout-render>
