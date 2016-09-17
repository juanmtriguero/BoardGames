<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="bulletin/business/create.do" modelAttribute="bulletinForm">
	
	<acme:textbox code="bulletin.form.title" path="title" />
	<acme:textarea code="bulletin.form.text" path="text" />
	<acme:textbox code="bulletin.form.photo" path="photo" />
	
	<acme:checkboxes code="bulletin.form.events" path="events" 
			items="${events}" itemLabel="title" delimiter="<br />" />
	<jstl:if test="${empty events}">
		<spring:message code="bulletin.form.noevents" />
		<br /><br />
	</jstl:if>
	
	<acme:submit name="save" code="bulletin.save" />
	<acme:cancel code="bulletin.cancel" url="bulletin/business/myBulletins.do" />
	
</form:form>
