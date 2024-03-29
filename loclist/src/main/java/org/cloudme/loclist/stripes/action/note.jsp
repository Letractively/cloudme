<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<s:layout-render name="/layout/iphone.jsp">
<s:layout-component name="title">&#9664; ${actionBean.note.name}</s:layout-component>
<s:layout-component name="button"><a href="/action/edit/${actionBean.note.id}">Edit</a></s:layout-component>
<s:layout-component name="content">
<ul class="edgetoedge">
<c:forEach var="noteItem" items="${actionBean.noteItems}">
<li>
<c:if test="${!noteItem.ticked}">
<a class="unticked" href="!/action/item/tick/${actionBean.location.id}/${noteItem.id}">
</c:if>
<c:if test="${noteItem.ticked}">
<a class="ticked" name="noteItem_${noteItem.id}">
</c:if>
<c:if test="${not empty noteItem.attribute}"><span class="attribute">${noteItem.attribute}</span> </c:if>${noteItem.text}
</a>
</li>
</c:forEach>
</ul>
<div class="thumbnail"><img src="/action/image/location_${actionBean.location.id}.jpg" class="thumbnail"/></div>
<a href="/action/note/reset/${actionBean.id}/${actionBean.latitude}/${actionBean.longitude}" class="edgetoedge" title="Do you want to reset this list?">Reset</a>
</s:layout-component>
<s:layout-component name="footer">
${actionBean.longitude},${actionBean.latitude} &bull;
</s:layout-component>
</s:layout-render>
