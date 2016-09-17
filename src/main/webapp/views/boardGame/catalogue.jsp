<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:forEach items="${categories}" var="category">
	
	<hr />
	<img src="<jstl:out value="${category.photo}" />" width="75" height="75" />
	<h3><jstl:out value="${category.name}" /></h3>
	<p><jstl:out value="${category.description}" /></p>
	
	<display:table name="${category.name}" id="row" requestURI="/boardGame/catalogue.do" pagesize="5" class="displaytag">
		
		<spring:message code="boardGame.photo" var="photoHeader" />
		<display:column title="${photoHeader}" sortable="false">
			<img src="<jstl:out value="${row.photo}" />" width="50" height="50" />
		</display:column>

		<spring:message code="boardGame.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true" />

		<spring:message code="boardGame.description" var="descriptionHeader" />
		<display:column property="description" title="${descriptionHeader}" sortable="false" />

		<spring:message code="boardGame.numberMaxPlayers" var="numberMaxPlayersHeader" />
		<display:column property="numberMaxPlayers" title="${numberMaxPlayersHeader}" sortable="true" />
		
		<security:authorize access="hasRole('ADMIN')">
		
			<display:column sortable="false">
				<form:form action="boardGame/admin/edit.do" method="get">
					<input type="hidden" value="${row.id}" name="boardGameId" />
					<input type="submit" value="<spring:message code="boardGame.edit" />" />
				</form:form>
			</display:column>
			
			<display:column sortable="false">
				<form:form action="boardGame/admin/remove.do">
					<input type="hidden" value="${row.id}" name="boardGame" />
					<input type="submit" value="<spring:message code="boardGame.delete" />" />
				</form:form>
			</display:column>
		
		</security:authorize>
	
	</display:table>
	
</jstl:forEach>

<security:authorize access="hasRole('ADMIN')">
	<hr />
	<a href="boardGame/admin/create.do"><spring:message code="boardGame.create" /></a>
</security:authorize>