<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<acme:form>
	<acme:form-textbox code= "administrator.spamfilter.form.label.threshold" path="value"/>
	
	
	<acme:form-submit  code="administrator.spamfilter.form.button.update" action="/administrator/threshold/update"/>
	<acme:form-return  code= "administrator.spamfilter.form.button.return"/>
</acme:form>