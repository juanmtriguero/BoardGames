<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="players" id="row" requestURI="/player/auth/list.do" pagesize="5" class="displaytag">
	
	<spring:message code="player.avatar" var="avatarHeader" />
	<display:column title="${avatarHeader}" sortable="false">
		<img src="<jstl:out value="${row.avatar}" />" width="50" height="50" />
	</display:column>
	
	<spring:message code="player.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="player.email" var="emailHeader" />
	<display:column property="email" title="${emailHeader}" sortable="true" />
	
	<security:authorize access="hasRole('PLAYER')">
		
		<spring:message code="player.social" var="socialHeader" />
		<display:column title="${socialHeader}" sortable="false">
			<jstl:if test="${row.id==principalId}">
				<spring:message code="player.itsme" />
			</jstl:if>
			<jstl:if test="${row.id!=principalId}">
				<jstl:if test="${follows[row.id]==false}">
					<a href="player/player/follow.do?playerId=${row.id}">
						<spring:message code="player.follow" />
					</a>
				</jstl:if>
				<jstl:if test="${follows[row.id]==true}">
					<a href="player/player/unfollow.do?playerId=${row.id}">
						<spring:message code="player.unfollow" />
					</a>
				</jstl:if>
			</jstl:if>
		</display:column>
		
	</security:authorize>
		
</display:table>
