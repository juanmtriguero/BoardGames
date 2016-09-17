<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="chronicle/business/write.do" modelAttribute="chronicle">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="event" />
	
	<acme:textbox code="chronicle.form.title" path="title" />
	<acme:textarea code="chronicle.form.text" path="text" />
	<acme:textarea code="chronicle.form.photos" path="photos" />
	
	<acme:submit name="save" code="chronicle.save" />
	<acme:cancel code="chronicle.cancel" url="event/business/myEvents.do" />
	
</form:form>
