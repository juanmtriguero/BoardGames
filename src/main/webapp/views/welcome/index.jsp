<%--
 * index.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
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
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<p><spring:message code="welcome.greeting.prefix" />${name}<spring:message code="welcome.greeting.suffix" /></p>

<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p>

<security:authorize access="hasRole('PLAYER')">

	<hr />
	<h3><spring:message code="event.next" /></h3>

	<display:table name="nextEvents" id="row" requestURI="/welcome/index.do" pagesize="5" class="displaytag">
	
		<spring:message code="event.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true" />
		
		<spring:message code="event.description" var="descriptionHeader" />
		<display:column property="description" title="${descriptionHeader}" sortable="false" />
		
		<spring:message code="event.creationMoment" var="creationMomentHeader" />
		<display:column title="${creationMomentHeader}" sortable="true">
			<fmt:formatDate value="${row.creationMoment}" pattern="dd/MM/yyyy HH:mm" />
		</display:column>
		
		<spring:message code="event.startMoment" var="startMomentHeader" />
		<display:column title="${startMomentHeader}" sortable="true">
			<fmt:formatDate value="${row.startMoment}" pattern="dd/MM/yyyy HH:mm" />
			<br />
			<jstl:if test="${countdown[row.id][0]==true}">
				<b><spring:message code="event.countdown.started" /></b>
			</jstl:if>
			<jstl:if test="${countdown[row.id][0]==false}">
				<b><spring:message code="event.countdown.remaining" />
				<jstl:if test="${countdown[row.id][1]>0}">
					&nbsp;<jstl:out value="${countdown[row.id][1]}" />
					&nbsp;<spring:message code="event.countdown.days" />
				</jstl:if>
				<jstl:if test="${countdown[row.id][2]>0}">
					&nbsp;<jstl:out value="${countdown[row.id][2]}" />
					&nbsp;<spring:message code="event.countdown.hours" />
				</jstl:if>
				<jstl:if test="${countdown[row.id][3]>0}">
					&nbsp;<jstl:out value="${countdown[row.id][3]}" />
					&nbsp;<spring:message code="event.countdown.minutes" />
				</jstl:if></b>
			</jstl:if>
		</display:column>
			
		<spring:message code="event.finishMoment" var="finishMomentHeader" />
		<display:column title="${finishMomentHeader}" sortable="true">
			<fmt:formatDate value="${row.finishMoment}" pattern="dd/MM/yyyy HH:mm" />
		</display:column>
		
		<spring:message code="event.inscriptionDeadline" var="inscriptionDeadlineHeader" />
		<display:column title="${inscriptionDeadlineHeader}" sortable="true">
			<fmt:formatDate value="${row.inscriptionDeadline}" pattern="dd/MM/yyyy HH:mm" />
		</display:column>
		
		<spring:message code="event.numberMaxParticipants" var="numberMaxParticipantsHeader" />
		<display:column property="numberMaxParticipants" title="${numberMaxParticipantsHeader}" sortable="true" />
		
		<spring:message code="event.price" var="priceHeader" />
		<display:column property="price" title="${priceHeader}" sortable="true" />
		
		<spring:message code="event.location" var="locationHeader" />
		<display:column title="${locationHeader}" sortable="false">
			<iframe src="https://www.google.com/maps/embed/v1/place?key=AIzaSyBezEs64wNK_fpDblwfwgnaUoiRLe1lZxw&q=place_id:${row.location}" width="300" height="200" style="border:0"></iframe>
		</display:column>
		
		<spring:message code="event.boardGame" var="boardGameHeader" />
		<display:column property="boardGame.title" title="${boardGameHeader}" sortable="true" />
		
		<spring:message code="event.business" var="businessHeader" />
		<display:column property="business.name" title="${businessHeader}" sortable="true" />
	
	</display:table>

	<hr />
	<h3><spring:message code="bulletin.last" /></h3>
	
	<display:table name="lastBulletins" id="row" requestURI="/welcome/index.do" pagesize="5" class="displaytag">
	
		<spring:message code="bulletin.title" var="titleHeader" />
		<display:column property="title" title="${titleHeader}" sortable="true" />
		
		<spring:message code="bulletin.releaseDate" var="releaseDateHeader" />
		<display:column title="${releaseDateHeader}" sortable="true">
			<fmt:formatDate value="${row.releaseDate}" pattern="dd/MM/yyyy HH:mm" />
		</display:column>
		
		<spring:message code="bulletin.text" var="textHeader" />
		<display:column property="text" title="${textHeader}" sortable="false" />
		
		<spring:message code="bulletin.photo" var="photoHeader" />
		<display:column title="${photoHeader}" sortable="false">
			<img src="<jstl:out value='${row.photo}' />" width="50" height="50" />
		</display:column>
		
		<spring:message code="bulletin.business" var="businessHeader" />
		<display:column property="business.name" title="${businessHeader}" sortable="false" />
		
		<spring:message code="bulletin.events" var="eventsHeader" />
		<display:column title="${eventsHeader}" sortable="false">
			<a href="event/auth/announced.do?bulletinId=${row.id}">
				<spring:message code="bulletin.listevents" />
			</a>
		</display:column>
	
	</display:table>

	<hr />
	<h3><spring:message code="activity" /></h3>
	
	<jstl:if test="${empty followedActivity}">
		<spring:message code="activity.noactivity" />
	</jstl:if>
	
	<ul>
	<jstl:forEach items="${followedActivity}" var="act">
		<li>
			<b><jstl:out value="${act[0]}" /></b>&nbsp;
			<spring:message code="activity.${act[1]}" />
			&nbsp;<b><jstl:out value="${act[2]}" /></b>
		</li>
	</jstl:forEach>
	</ul>

</security:authorize>
