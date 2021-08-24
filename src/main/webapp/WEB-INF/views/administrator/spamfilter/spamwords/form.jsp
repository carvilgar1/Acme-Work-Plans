<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<acme:form>
	<acme:form-textbox code= "administrator.spamfilter.form.label.spamword" path="spamword"/>
	
	<acme:form-submit  test="${command == 'show' || command=='delete'}" code="administrator.spamfilter.form.button.delete" action="/administrator/spamword/delete"/>
	<acme:form-submit  test="${command == 'show' || command=='update'}" code="administrator.spamfilter.form.button.update" action="/administrator/spamword/update"/>
	<acme:form-submit  test="${command == 'create'}" code="administrator.spamfilter.form.button.create" action="/administrator/spamword/create"/>
	<acme:form-return  code= "administrator.spamfilter.form.button.return"/>
</acme:form>