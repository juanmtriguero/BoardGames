<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="players" id="row" requestURI="/player/auth/participants.do" pagesize="5" class="displaytag">
	
	<spring:message code="player.avatar" var="avatarHeader" />
	<display:column title="${avatarHeader}" sortable="false">
		<img src="<jstl:out value="${row[0].avatar}" />" width="50" height="50" />
	</display:column>
	
	<spring:message code="player.name" var="nameHeader" />
	<display:column title="${nameHeader}" sortable="true">${row[0].name}</display:column>
	
	<spring:message code="inscription.gameList" var="gameListHeader" />
	<display:column title="${gameListHeader}" sortable="false">
		<jstl:if test="${row[1]!=null && row[1]!=''}">
			<a href="${row[1]}" target="_blank"><spring:message code="inscription.gotolist" /></a>
		</jstl:if>
		<jstl:if test="${row[1]==null || row[1]==''}">
			<spring:message code="inscription.nolist" />
		</jstl:if>
	</display:column>
		
</display:table>
