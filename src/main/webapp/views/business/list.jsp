<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<display:table name="businesses" id="row" requestURI="/business/auth/list.do" pagesize="5" class="displaytag">
	
	<spring:message code="business.avatar" var="avatarHeader" />
	<display:column title="${avatarHeader}" sortable="false">
		<img src="<jstl:out value="${row.avatar}" />" width="50" height="50" />
	</display:column>
	
	<spring:message code="business.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="business.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />
	
	<spring:message code="business.cif" var="cifHeader" />
	<display:column property="cif" title="${cifHeader}" sortable="true" />
	
	<spring:message code="business.street" var="streetHeader" />
	<display:column property="street" title="${streetHeader}" sortable="true" />
	
	<spring:message code="business.zip" var="zipHeader" />
	<display:column property="zip" title="${zipHeader}" sortable="true" />
	
	<spring:message code="business.city" var="cityHeader" />
	<display:column property="city" title="${cityHeader}" sortable="true" />
	
	<spring:message code="business.phone" var="phoneHeader" />
	<display:column property="phone" title="${phoneHeader}" sortable="true" />
	
	<spring:message code="business.web" var="webHeader" />
	<display:column title="${webHeader}" sortable="false">
		<a href="${row.web}" target="_blank">
			<spring:message code="business.gotoweb" />
		</a>
	</display:column>
	
	<spring:message code="business.rating" var="ratingHeader" />
	<display:column title="${ratingHeader}" sortable="true">
		<fmt:formatNumber value="${row.rating}" maxFractionDigits="2" />
	</display:column>
	
	<spring:message code="business.events" var="eventsHeader" />
	<display:column title="${eventsHeader}" sortable="false">
		<a href="event/auth/byBusiness.do?businessId=${row.id}">
			<spring:message code="business.listevents" />
		</a>
	</display:column>
	
	<spring:message code="business.bulletins" var="bulletinsHeader" />
	<display:column title="${bulletinsHeader}" sortable="false">
		<a href="bulletin/auth/byBusiness.do?businessId=${row.id}">
			<spring:message code="business.listbulletins" />
		</a>
	</display:column>
	
	<security:authorize access="hasRole('PLAYER')">
		
		<spring:message code="business.vote" var="voteHeader" />
		<display:column title="${voteHeader}" sortable="false">
			<jstl:if test="${hasVoted[row.id]==false}">
				<a href="business/player/vote.do?businessId=${row.id}">
					<spring:message code="business.vote" />
				</a>
			</jstl:if>
			<jstl:if test="${hasVoted[row.id]==true}">
				<spring:message code="business.hasvoted" />
			</jstl:if>
		</display:column>
		
	</security:authorize>
		
</display:table>
