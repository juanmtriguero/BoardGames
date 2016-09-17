<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message code="event.promotions" var="promotionsTitle" />
<h3>${promotionsTitle}</h3>

<display:table name="promotions" id="row" requestURI="/event/business/myEvents.do" pagesize="5" class="displaytag">
	
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
	
	<spring:message code="event.sponsor" var="sponsorHeader" />
	<display:column title="${sponsorHeader}" sortable="false">
		<jstl:if test="${row.sponsorName==null || row.sponsorLogo==null || row.sponsorPayment==null}">
			<spring:message code="event.nosponsor" />
		</jstl:if>
		<jstl:if test="${row.sponsorName!=null && row.sponsorLogo!=null && row.sponsorPayment!=null}">
			<img src="<jstl:out value="${row.sponsorLogo}" />" width="35" height="35" /><br />
			<jstl:out value="${row.sponsorName}" />&nbsp;(<jstl:out value="${row.sponsorPayment}" />&nbsp;&euro;)
		</jstl:if>
	</display:column>
	
	<spring:message code="event.players" var="playersHeader" />
	<display:column title="${playersHeader}" sortable="false">
		<a href="player/auth/participants.do?eventId=${row.id}">
			<spring:message code="event.listplayers" />
		</a>
	</display:column>
	
	<spring:message code="event.chronicle" var="chronicleHeader" />
	<display:column title="${chronicleHeader}" sortable="false">
		<jstl:if test="${row.chronicle==null}">
			<a href="chronicle/business/write.do?eventId=${row.id}">
				<spring:message code="event.writechronicle" />
			</a>
		</jstl:if>
		<jstl:if test="${row.chronicle!=null}">
			<a href="chronicle/auth/see.do?eventId=${row.id}">
				<spring:message code="event.seechronicle" />
			</a>
		</jstl:if>
	</display:column>
	
	<display:column sortable="false">
		<form:form action="event/business/editPromotion.do" method="get">
			<input type="hidden" value="${row.id}" name="promotionId" />
			<input type="submit" value="<spring:message code="event.edit" />" />
		</form:form>
	</display:column>
			
	<display:column sortable="false">
		<form:form action="event/business/removePromotion.do">
			<input type="hidden" value="${row.id}" name="promotion" />
			<input type="submit" value="<spring:message code="event.delete" />" />
		</form:form>
	</display:column>
		
</display:table>

<a href="event/business/createPromotion.do"><spring:message code="event.createPromotion" /></a>

<hr />

<spring:message code="event.tournaments" var="tournamentsTitle" />
<h3>${tournamentsTitle}</h3>

<display:table name="tournaments" id="row" requestURI="/event/business/myEvents.do" pagesize="5" class="displaytag">
	
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
	
	<spring:message code="event.award" var="awardHeader" />
	<display:column property="award" title="${awardHeader}" sortable="true" />
	
	<spring:message code="event.winner" var="winnerHeader" />
	<display:column property="winner.name" title="${winnerHeader}" sortable="true" />
	
	<spring:message code="event.players" var="playersHeader" />
	<display:column title="${playersHeader}" sortable="false">
		<a href="player/auth/participants.do?eventId=${row.id}">
			<spring:message code="event.listplayers" />
		</a>
	</display:column>
	
	<spring:message code="event.chronicle" var="chronicleHeader" />
	<display:column title="${chronicleHeader}" sortable="false">
		<jstl:if test="${row.chronicle==null}">
			<a href="chronicle/business/write.do?eventId=${row.id}">
				<spring:message code="event.writechronicle" />
			</a>
		</jstl:if>
		<jstl:if test="${row.chronicle!=null}">
			<a href="chronicle/auth/see.do?eventId=${row.id}">
				<spring:message code="event.seechronicle" />
			</a>
		</jstl:if>
	</display:column>
	
	<spring:message code="event.organisation" var="organisationHeader" />
	<display:column title="${organisationHeader}" sortable="false">
		<a href="event/business/organisation.do?tournamentId=${row.id}">
			<spring:message code="event.gotoorg" />
		</a>
	</display:column>
	
	<display:column sortable="false">
		<form:form action="event/business/editTournament.do" method="get">
			<input type="hidden" value="${row.id}" name="tournamentId" />
			<input type="submit" value="<spring:message code="event.edit" />" />
		</form:form>
	</display:column>
			
	<display:column sortable="false">
		<form:form action="event/business/removeTournament.do">
			<input type="hidden" value="${row.id}" name="tournament" />
			<input type="submit" value="<spring:message code="event.delete" />" />
		</form:form>
	</display:column>
		
</display:table>

<a href="event/business/createTournament.do"><spring:message code="event.createTournament" /></a>
