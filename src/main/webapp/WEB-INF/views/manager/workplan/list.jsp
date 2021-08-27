<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="manager.workplan.list.label.startDate" path="startDate" width="20%"/>
	<acme:list-column code="manager.workplan.list.label.endDate" path="endDate" width="20%"/>
	<acme:list-column code="manager.workplan.list.label.workLoad.entera" path="workLoad.entera" width="20%"/>
	<acme:list-column code="manager.workplan.list.label.workLoad.decimal" path="workLoad.decimal" width="20%"/>
</acme:list>

