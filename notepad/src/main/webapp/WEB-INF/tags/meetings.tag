<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="groups" required="true" type="java.util.List" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="f" uri="/WEB-INF/tags/functions.tld"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<ul class="meetingGroups">
  <c:forEach items="${groups}" var="group">
    <li class="meetingGroup">
      <div class="date"><t:date date="${group.date}" /></div>
      <ul class="meetings">
        <c:forEach items="${group.meetings}" var="meeting">
          <li class="meeting">
            <s:link beanclass="org.cloudme.notepad.stripes.action.meeting.MeetingActionBean" event="show">
              <s:param name="meeting.id">${meeting.id}</s:param>
              ${f:escapeHtml(meeting.topic)}
            </s:link>
          </li>
        </c:forEach>
      </ul>
    </li>
  </c:forEach>
</ul>
