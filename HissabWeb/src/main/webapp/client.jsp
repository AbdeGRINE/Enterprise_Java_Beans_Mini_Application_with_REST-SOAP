<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Client CALC WS</title>
</head>
<body>
    <h1>Client graphique CALC - REST / SOAP</h1>

    <form action="clientCalc" method="post">
        <label>Expression :</label><br>
        <input type="text" name="expression" value="5 + 2 * 6 - 3" size="40">
        <br><br>

        <label>Type de service :</label><br>
        <select name="typeService">
            <option value="REST">REST</option>
            <option value="SOAP">SOAP</option>
        </select>

        <br><br>
        <button type="submit">Calculer</button>
    </form>

    <% if (request.getAttribute("resultat") != null) { %>
        <hr>
        <h2>Résultat</h2>
        <p><strong>Service utilisé :</strong> <%= request.getAttribute("typeService") %></p>
        <p><strong>Expression :</strong> <%= request.getAttribute("expression") %></p>
        <p><strong>Résultat :</strong> <%= request.getAttribute("resultat") %></p>
    <% } %>

    <% if (request.getAttribute("erreur") != null) { %>
        <hr>
        <p style="color:red;"><%= request.getAttribute("erreur") %></p>
    <% } %>
</body>
</html>