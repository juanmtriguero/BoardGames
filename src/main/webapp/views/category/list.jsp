<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="categories" id="row" requestURI="/category/admin/list.do" pagesize="5" class="displaytag">
	
	<spring:message code="category.photo" var="photoHeader" />
	<display:column title="${photoHeader}" sortable="false">
		<img src="<jstl:out value="${row.photo}" />" width="50" height="50" />
	</display:column>
	
	<spring:message code="category.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="category.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="false" />
		
	<display:column sortable="false">
		<form:form action="category/admin/edit.do" method="get">
			<input type="hidden" value="${row.id}" name="categoryId" />
			<input type="submit" value="<spring:message code="category.edit" />" />
		</form:form>
	</display:column>
	
	<display:column sortable="false">
		<form:form action="category/admin/edit.do">
			<input type="hidden" value="${row.id}" name="category" />
			<input type="submit" name="delete" value="<spring:message code="category.delete" />" />
		</form:form>
	</display:column>
	
</display:table>

<a href="category/admin/create.do"><spring:message code="category.create" /></a>
