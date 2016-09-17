<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${chronicle!=null}">
	
	<h3><jstl:out value="${chronicle.title}" /></h3>
	
	<p><jstl:out value="${chronicle.text}" /></p>
	
	<h4><spring:message code="chronicle.photos" /></h4>
	<hr /><br />
	<jstl:if test="${empty chronicle.photos}">
		<spring:message code="chronicle.nophotos" />
	</jstl:if>
	<jstl:forEach items="${chronicle.photos}" var="photo">
		<img src="${photo}" height="300" />
		<br />
	</jstl:forEach>
	
	<jstl:if test="${isMine}">
		<br /><br /><hr />
		<form:form action="chronicle/business/delete.do">
			<input type="hidden" value="${chronicle.id}" name="chronicle" />
			<input type="submit" name="delete" value="<spring:message code='chronicle.delete' />" />
		</form:form>
	</jstl:if>
	
</jstl:if>