<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Exchange Rate Viewer</title>
</head>
<body>


    <h2>Base : ${exchangeRates.base}</h2>
    
    
    <table border="1">
    	<tr>
    		<td>Currency</td>
    		<td>${exchangeRates.date}</td>
    	</tr>
    	
    <c:forEach var="entry" items="${exchangeRates.rates}">
    	<tr>
    		<td><c:out value="${entry.key}"/></td>
    		<td><c:out value="${entry.value}"/></td>
    	</tr>
    </c:forEach>
    
    </table>
    
    <br>
    <button onClick="window.location='/historicalexchangerates';">View Historical Exchange Rates</button>

</body>
</html>