<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Exchange Rate Viewer</title>
</head>
<body>
    <table border="1">
    	<tr>
    		<td>Currency</td>
    		
    	<c:forEach var="exchangeRate" items="${historicalExchangeRates}">
    		<td>${exchangeRate.date}</td>
    	</c:forEach>
    	</tr>
    	
    	
		<c:forTokens var="symbol" items="${symbols}" delims=",">
		<tr>
			<td><c:out value="${symbol}"/></td>
			
		<c:forEach var="exchangeRate" items="${historicalExchangeRates}">
			<td><c:out value="${exchangeRate.rates[symbol]}"/></td>
		</c:forEach>
		
		</tr>
    	</c:forTokens>
    
    </table>

</body>
</html>