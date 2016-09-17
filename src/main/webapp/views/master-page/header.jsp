<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<a href="welcome/index.do"><img src="images/logo.png" alt="Board Games, Inc." height="200" /></a>
</div>

<br />

<div>
	<ul id="jMenu">
	
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="boardGame/catalogue.do"><spring:message code="master.page.catalogue" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="player/register.do"><spring:message code="master.page.register.player" /></a></li>
					<li><a href="business/register.do"><spring:message code="master.page.register.business" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="event/auth/list.do"><spring:message code="master.page.events" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.users" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="player/auth/list.do"><spring:message code="master.page.players" /></a></li>
					<li><a href="business/auth/list.do"><spring:message code="master.page.businesses" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="boardGame/catalogue.do"><spring:message code="master.page.catalogue" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('BUSINESS')">
			<li><a class="fNiv" href="event/business/myEvents.do"><spring:message code="master.page.myevents" /></a></li>
			<li><a class="fNiv" href="bulletin/business/myBulletins.do"><spring:message code="master.page.mybulletins" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('PLAYER')">
			<li><a class="fNiv" href="event/player/inscribed.do"><spring:message code="master.page.inscribed" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.social" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="player/player/followeds.do"><spring:message code="master.page.followeds" /></a></li>
					<li><a href="player/player/followers.do"><spring:message code="master.page.followers" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv" href="category/admin/list.do"><spring:message code="master.page.categories" /></a></li>
			<li><a class="fNiv" href="admin/admin/dashboard.do"><spring:message code="master.page.dashboard" /></a></li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"><spring:message code="master.page.profile" /> (<security:authentication property="principal.username" />)</a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
		
	</ul>
</div>

<br />

<div>
	<spring:message code="master.page.language" />: <a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

<hr />