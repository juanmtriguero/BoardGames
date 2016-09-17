<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="business/register.do" modelAttribute="businessForm">
	
	<fieldset>
		<legend><spring:message code="business.form.user" /></legend>
		<br />
		<acme:textbox code="business.form.username" path="username" />
		<acme:password code="business.form.password" path="password" />
		<acme:password code="business.form.confirmPassword" path="confirmPassword" />
	</fieldset>
	<br />
	
	<fieldset>
		<legend><spring:message code="business.form.business" /></legend>
		<br />
		<acme:textbox code="business.form.name" path="name" />
		<acme:textbox code="business.form.email" path="email" />
		<acme:textbox code="business.form.avatar" path="avatar" />
		<acme:textbox code="business.form.cif" path="cif" />
		<acme:textbox code="business.form.street" path="street" />
		<acme:textbox code="business.form.zip" path="zip" />
		<acme:textbox code="business.form.city" path="city" />
		<acme:textbox code="business.form.web" path="web" />
		<acme:textbox code="business.form.phone" path="phone" />
	</fieldset>
	<br />
	
	<fieldset>
		<acme:checkbox code="business.form.acceptConditions" path="acceptConditions"/>
		<a href="legalTerms/legalTerms.do"><spring:message code="business.conditions" /></a>
	</fieldset>
	<br />
	
	<acme:submit name="save" code="business.save" />
	<acme:cancel code="business.cancel" url="welcome/index.do" />
	
</form:form>
