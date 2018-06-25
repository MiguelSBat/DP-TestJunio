<%--
 * list.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" 
	name="cosaXs" requestURI="cosax/list.do" id="row">

	<spring:message code="cosax.title" var="titleHeader" />
	<display:column title="${titleHeader}">
	<jstl:out value="${row.title}"></jstl:out>
	</display:column>

	<spring:message code="cosax.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />
	
	<spring:message code="cosax.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}" />

	<spring:message code="cosax.publicationDate"
		var="publicationDateHeader" />
	<spring:message code="cosax.page.date.format" var="dateFormat" />
	<display:column property="publicationDate"
		format="{0,date,${dateFormat}}" title="${publicationDateHeader}" />

	<spring:message code="cosax.gauge" var="cosaxGauge" />
	<display:column title="${cosaxGauge}" sortable="false">
		<jstl:set value="${row.gauge}" var="color"/>
		<div class="gauge${color}">${row.gauge}</div>
	</display:column>

	<security:authorize access="hasRole('ADMIN')">
		
		<display:column>
		<jstl:if test="${row.draftMode==true}">	
			<a href="administrator/cosax/edit.do?cosaxId=${row.id}"><spring:message
					code="cosax.edit" /></a>
		</jstl:if>
		</display:column>
		
		<display:column>
		<jstl:if test="${row.draftMode==true}">	
			<a href="administrator/cosax/delete.do?cosaxId=${row.id}"><spring:message
					code="chirp.delete" /></a>
		</jstl:if>
		</display:column>
		
	</security:authorize>
</display:table>


