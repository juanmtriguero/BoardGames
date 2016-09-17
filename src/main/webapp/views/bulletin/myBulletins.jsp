<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<display:table name="bulletins" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
	<spring:message code="bulletin.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	<spring:message code="bulletin.releaseDate" var="releaseDateHeader" />
	<display:column title="${releaseDateHeader}" sortable="true">
		<fmt:formatDate value="${row.releaseDate}" pattern="dd/MM/yyyy HH:mm" />
	</display:column>
	
	<spring:message code="bulletin.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="false" />
	
	<spring:message code="bulletin.photo" var="photoHeader" />
	<display:column title="${photoHeader}" sortable="false">
		<img src="<jstl:out value='${row.photo}' />" width="50" height="50" />
	</display:column>
	
	<spring:message code="bulletin.events" var="eventsHeader" />
	<display:column title="${eventsHeader}" sortable="false">
		<a href="event/auth/announced.do?bulletinId=${row.id}">
			<spring:message code="bulletin.listevents" />
		</a>
	</display:column>
	
	<display:column sortable="false">
		<form:form action="bulletin/business/edit.do" method="get">
			<input type="hidden" value="${row.id}" name="bulletinId" />
			<input type="submit" value="<spring:message code="bulletin.edit" />" />
		</form:form>
	</display:column>
			
	<display:column sortable="false">
		<form:form action="bulletin/business/remove.do">
			<input type="hidden" value="${row.id}" name="bulletin" />
			<input type="submit" value="<spring:message code="bulletin.delete" />" />
		</form:form>
	</display:column>
		
</display:table>

<a href="bulletin/business/create.do"><spring:message code="bulletin.create" /></a>
