<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${message==null || message=='' || message=='event.org.error.setWinner'}">

	<jstl:forEach items="${stages}" var="stage">
		
		<hr />
		<h2><spring:message code="event.org.stage" />&nbsp;<jstl:out value="${stage.number}" /></h2>
		
			<jstl:forEach items="${stage.games}" var="game">
			
				<h3><spring:message code="event.org.game" /></h3>
			
				<display:table name="game${game.id}" id="row" requestURI="${requestURI}" pagesize="5" class="displaytag">
					
					<spring:message code="event.org.player.avatar" var="avatarHeader" />
					<display:column title="${avatarHeader}" sortable="false">
						<img src="<jstl:out value="${row.avatar}" />" width="50" height="50" />
					</display:column>
					
					<spring:message code="event.org.player.name" var="nameHeader" />
					<display:column property="name" title="${nameHeader}" sortable="true" />
					
					<jstl:if test="${stage.number<=(lastStageFinished+1)}">
					
						<display:column sortable="false">
						
							<jstl:if test="${game.gameWinner==null}">
								<form:form action="event/business/setWinner.do">
									<input type="hidden" value="${row.id}" name="player" />
									<input type="hidden" value="${game.id}" name="game" />
									<input type="submit" value="<spring:message code="event.org.player.winner" />" />
								</form:form>
							</jstl:if>
							
							<jstl:if test="${game.gameWinner.id==row.id}">
								<spring:message code="event.org.player.winner" />
							</jstl:if>
						
						</display:column>
					
					</jstl:if>
				
				</display:table>
			
			</jstl:forEach>
			
	</jstl:forEach>
	
	<jstl:if test="${tournamentWinner!=null}">
		
		<hr />
		<h2><spring:message code="event.org.tournamentWinner" /></h2>
		<img src="<jstl:out value="${tournamentWinner.avatar}" />" width="75" height="75" />
		<h3><jstl:out value="${tournamentWinner.name}" /></h3>
		
	</jstl:if>

</jstl:if>
