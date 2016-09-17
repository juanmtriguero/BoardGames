<%--
 * footer.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="date" class="java.util.Date" />

<hr />

<b>Copyright &copy; <fmt:formatDate value="${date}" pattern="yyyy" /> Board Games, Inc.</b>

&nbsp;<a href="legalTerms/cookies.do"><spring:message code="master.page.cookies" /></a>
&nbsp;<a href="legalTerms/personalData.do"><spring:message code="master.page.personalData" /></a>
&nbsp;<a href="legalTerms/legalInformation.do"><spring:message code="master.page.legalInformation" /></a>