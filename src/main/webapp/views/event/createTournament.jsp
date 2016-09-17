<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="event/business/createTournament.do" modelAttribute="tournamentForm">
	
	<acme:textbox code="event.form.title" path="title" />
	<acme:textarea code="event.form.description" path="description" />
	<acme:textbox code="event.form.startMoment" placeholder="dd/mm/yyyy hh:mm" path="startMoment" />
	<acme:textbox code="event.form.finishMoment" placeholder="dd/mm/yyyy hh:mm" path="finishMoment" />
	<acme:textbox code="event.form.inscriptionDeadline" placeholder="dd/mm/yyyy hh:mm" path="inscriptionDeadline" />
	<spring:message code="event.form.inmutable" /><br /><br />
	<acme:textbox code="event.form.numberMaxParticipants" path="numberMaxParticipants" />
	<spring:message code="event.form.inmutable" /><br /><br />
	<acme:textbox code="event.form.price" path="price" />
	<acme:textbox code="event.form.location" path="location" />
	<spring:message code="event.form.location.explain" />&nbsp;<a href="https://developers.google.com/places/place-id" target="_blank"><spring:message code="event.form.location.here" /></a><br /><br />
	<acme:select items="${boardGames}" itemLabel="title" code="event.form.boardGame" path="boardGame" />
	<spring:message code="event.form.inmutable" /><br /><br />
	<acme:textbox code="event.form.award" path="award" />
	
	<acme:submit name="save" code="event.save" />
	<acme:cancel code="event.cancel" url="event/business/myEvents.do" />
	
</form:form>
