<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="boardGame/admin/edit.do" modelAttribute="boardGameEditForm">

	<form:hidden path="id" />
	
	<acme:textbox code="boardGame.form.title" path="title" />
	<acme:textarea code="boardGame.form.description" path="description" />
	<acme:textbox code="boardGame.form.photo" path="photo" />
	<acme:textbox code="boardGame.form.numberMaxPlayers" path="numberMaxPlayers" />
	
	<acme:checkboxes code="boardGame.form.categories" path="categories" 
			items="${categories}" itemLabel="name" delimiter="<br />" />
	
	<acme:submit name="save" code="boardGame.save" />
	<acme:submit name="delete" code="boardGame.delete" />
	<acme:cancel code="boardGame.cancel" url="boardGame/catalogue.do" />
	
</form:form>
