<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="player/register.do" modelAttribute="playerForm">
	
	<fieldset>
		<legend><spring:message code="player.form.user" /></legend>
		<br />
		<acme:textbox code="player.form.username" path="username" />
		<acme:password code="player.form.password" path="password" />
		<acme:password code="player.form.confirmPassword" path="confirmPassword" />
	</fieldset>
	<br />
	
	<fieldset>
		<legend><spring:message code="player.form.player" /></legend>
		<br />
		<acme:textbox code="player.form.name" path="name" />
		<acme:textbox code="player.form.email" path="email" />
		<acme:textbox code="player.form.avatar" path="avatar" />
	</fieldset>
	<br />
	
	<fieldset>
		<acme:checkbox code="player.form.acceptConditions" path="acceptConditions"/>
		<a href="legalTerms/legalTerms.do"><spring:message code="player.conditions" /></a>
	</fieldset>
	<br />
	
	<acme:submit name="save" code="player.save" />
	<acme:cancel code="player.cancel" url="welcome/index.do" />
	
</form:form>
