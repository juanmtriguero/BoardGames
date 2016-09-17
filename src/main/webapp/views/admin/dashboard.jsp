<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<hr /><h3><spring:message code="dashboard.usedInMostEvents" /></h3>
<display:table name="usedInMostEvents" id="uime" requestURI="/admin/admin/dashboard.do" pagesize="5" class="displaytag">
	
	<spring:message code="boardGame.photo" var="photoHeader" />
	<display:column title="${photoHeader}" sortable="false">
		<img src="<jstl:out value="${uime.photo}" />" width="50" height="50" />
	</display:column>

	<spring:message code="boardGame.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	<spring:message code="dashboard.numEvents" var="numEventsHeader" />
	<display:column title="${numEventsHeader}" sortable="true">
		<jstl:out value="${uime.events.size()}" />
	</display:column>

</display:table>

<hr /><h3><spring:message code="dashboard.usedInLessEvents" /></h3>
<display:table name="usedInLessEvents" id="uile" requestURI="/admin/admin/dashboard.do" pagesize="5" class="displaytag">
	
	<spring:message code="boardGame.photo" var="photoHeader" />
	<display:column title="${photoHeader}" sortable="false">
		<img src="<jstl:out value="${uile.photo}" />" width="50" height="50" />
	</display:column>

	<spring:message code="boardGame.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />
	
	<spring:message code="dashboard.numEvents" var="numEventsHeader" />
	<display:column title="${numEventsHeader}" sortable="true">
		<jstl:out value="${uile.events.size()}" />
	</display:column>

</display:table>

<hr /><h3><spring:message code="dashboard.joinedMostEvents" /></h3>
<display:table name="joinedMostEvents" id="jme" requestURI="/admin/admin/dashboard.do" pagesize="5" class="displaytag">

	<spring:message code="player.avatar" var="avatarHeader" />
	<display:column title="${avatarHeader}" sortable="false">
		<img src="<jstl:out value="${jme.avatar}" />" width="50" height="50" />
	</display:column>

	<spring:message code="player.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="dashboard.joinedEvents" var="joinedEventsHeader" />
	<display:column title="${joinedEventsHeader}" sortable="true">
		<jstl:out value="${jme.inscriptions.size()}" />
	</display:column>

</display:table>

<hr /><h3><spring:message code="dashboard.joinedLessEvents" /></h3>
<display:table name="joinedLessEvents" id="jle" requestURI="/admin/admin/dashboard.do" pagesize="5" class="displaytag">

	<spring:message code="player.avatar" var="avatarHeader" />
	<display:column title="${avatarHeader}" sortable="false">
		<img src="<jstl:out value="${jle.avatar}" />" width="50" height="50" />
	</display:column>

	<spring:message code="player.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="dashboard.joinedEvents" var="joinedEventsHeader" />
	<display:column title="${joinedEventsHeader}" sortable="true">
		<jstl:out value="${jle.inscriptions.size()}" />
	</display:column>

</display:table>

<hr /><h3><spring:message code="dashboard.playedMostBoardGames" /></h3>
<display:table name="playedMostBoardGames" id="pmbg" requestURI="/admin/admin/dashboard.do" pagesize="5" class="displaytag">

	<spring:message code="player.avatar" var="avatarHeader" />
	<display:column title="${avatarHeader}" sortable="false">
		<img src="<jstl:out value="${pmbg.avatar}" />" width="50" height="50" />
	</display:column>

	<spring:message code="player.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="dashboard.boardGamesPlayed" var="boardGamesPlayedHeader" />
	<display:column title="${boardGamesPlayedHeader}" sortable="true">
		<jstl:out value="${boardGamesPlayed}" />
	</display:column>

</display:table>

<hr /><h3><spring:message code="dashboard.businessBestRated" /></h3>
<display:table name="businessBestRated" id="bbr" requestURI="/admin/admin/dashboard.do" pagesize="5" class="displaytag">

	<spring:message code="business.avatar" var="avatarHeader" />
	<display:column title="${avatarHeader}" sortable="false">
		<img src="<jstl:out value="${bbr.avatar}" />" width="50" height="50" />
	</display:column>

	<spring:message code="business.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="dashboard.rating" var="ratingHeader" />
	<display:column title="${ratingHeader}" sortable="true">
		<jstl:out value="${bbr.rating}" />
	</display:column>

</display:table>

<hr /><h3><spring:message code="dashboard.businessWorstRated" /></h3>
<display:table name="businessWorstRated" id="bwr" requestURI="/admin/admin/dashboard.do" pagesize="5" class="displaytag">

	<spring:message code="business.avatar" var="avatarHeader" />
	<display:column title="${avatarHeader}" sortable="false">
		<img src="<jstl:out value="${bwr.avatar}" />" width="50" height="50" />
	</display:column>

	<spring:message code="business.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="dashboard.rating" var="ratingHeader" />
	<display:column title="${ratingHeader}" sortable="true">
		<jstl:out value="${bwr.rating}" />
	</display:column>

</display:table>

<hr /><h3><spring:message code="dashboard.averages" /></h3>
<display:table name="averages" id="avg" requestURI="/admin/admin/dashboard.do"  class="displaytag">
	
	<spring:message code="dashboard.property" var="propertyHeader" />
	<display:column title="${propertyHeader}" sortable="false">
		<spring:message code="dashboard.${avg[0]}" />
	</display:column>
	
	<spring:message code="dashboard.value" var="valueHeader" />
	<display:column title="${valueHeader}" sortable="false">
		<jstl:out value="${avg[1]}" />
	</display:column>
	
</display:table>