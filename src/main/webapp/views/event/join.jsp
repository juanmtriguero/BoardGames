<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="event/player/join.do" modelAttribute="inscription">

	<form:hidden path="event" />
	<form:hidden path="player" />

	<p><spring:message code="inscription.explainGameList" /></p>
	
	<acme:textbox code="inscription.form.gameList" path="gameList" />
	
	<acme:submit name="save" code="inscription.save" />
	<acme:cancel code="inscription.cancel" url="event/auth/list.do" />
	
</form:form>
