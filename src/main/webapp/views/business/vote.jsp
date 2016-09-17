<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="business/player/vote.do" modelAttribute="userVote">

	<form:hidden path="player" />
	<form:hidden path="business" />
	
	<acme:textbox code="vote.form.score" path="score" />
	
	<acme:submit name="save" code="vote.save" />
	<acme:cancel code="vote.cancel" url="business/auth/list.do" />
	
</form:form>
