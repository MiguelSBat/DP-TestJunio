<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
	
<form:form action="${requestURI}" modelAttribute="cosax">
	
	<form:hidden path="id" /> 
	
	<acme:textbox code="cosax.title" path="title"/>
	
	<acme:textarea code="cosax.description" path="description"/>
	
	<acme:textbox code="cosax.gauge" path="gauge"/>
	
	<acme:textbox code="cosax.publicationDate" path="publicationDate"/>
	
	<input type="checkbox" name="draftMode" id="draftMode" value="True"><spring:message code="cosax.draftMode"/>
	<br/>
	
	<acme:submit name="save" code="cosax.save"/>
	
	<spring:message code="cosax.cancel" var="cancel"/>
	<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('admin/cosax/list.do');" />

	</form:form>
	
