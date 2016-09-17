<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<spring:message code="event.promotions" var="promotionsTitle" />
<h3>${promotionsTitle}</h3>

<display:table name="promotions" id="row" requestURI="/event/player/inscribed.do" pagesize="5" class="displaytag">
	
	<spring:message code="event.title" var="titleHeader" />
	<display:column title="${titleHeader}" sortable="true">${row[0].title}</display:column>
	
	<spring:message code="event.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}" sortable="false">${row[0].description}</display:column>
	
	<spring:message code="inscription.gameList" var="gameListHeader" />
	<display:column title="${gameListHeader}" sortable="false">
		<jstl:if test="${row[1]!=null && row[1]!=''}">
			<a href="${row[1]}" target="_blank"><spring:message code="inscription.gotolist" /></a>
		</jstl:if>
		<jstl:if test="${row[1]==null || row[1]==''}">
			<spring:message code="inscription.nolist" />
		</jstl:if>
	</display:column>
	
	<spring:message code="inscription.inscription" var="inscriptionHeader" />
	<display:column title="${inscriptionHeader}" sortable="false">
		<jstl:if test="${isClosed[row[0].id]==false}">
			<a href="event/player/unjoin.do?eventId=${row[0].id}">
				<spring:message code="inscription.unjoin" />
			</a>
		</jstl:if>
		<jstl:if test="${isClosed[row[0].id]==true}">
			<spring:message code="inscription.closed" />
		</jstl:if>
	</display:column>
	
</display:table>

<hr />

<spring:message code="event.tournaments" var="tournamentsTitle" />
<h3>${tournamentsTitle}</h3>

<display:table name="tournaments" id="row" requestURI="/event/player/inscribed.do" pagesize="5" class="displaytag">
	
	<spring:message code="event.title" var="titleHeader" />
	<display:column title="${titleHeader}" sortable="true">${row[0].title}</display:column>
	
	<spring:message code="event.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}" sortable="false">${row[0].description}</display:column>
	
	<spring:message code="inscription.gameList" var="gameListHeader" />
	<display:column title="${gameListHeader}" sortable="false">
		<jstl:if test="${row[1]!=null && row[1]!=''}">
			<a href="${row[1]}" target="_blank"><spring:message code="inscription.gotolist" /></a>
		</jstl:if>
		<jstl:if test="${row[1]==null || row[1]==''}">
			<spring:message code="inscription.nolist" />
		</jstl:if>
	</display:column>
	
	<spring:message code="inscription.inscription" var="inscriptionHeader" />
	<display:column title="${inscriptionHeader}" sortable="false">
		<jstl:if test="${isClosed[row[0].id]==false}">
			<a href="event/player/unjoin.do?eventId=${row[0].id}">
				<spring:message code="inscription.unjoin" />
			</a>
		</jstl:if>
		<jstl:if test="${isClosed[row[0].id]==true}">
			<spring:message code="inscription.closed" />
		</jstl:if>
	</display:column>
		
</display:table>
