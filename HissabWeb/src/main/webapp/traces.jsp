<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hissab.entity.Trace" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Historique HISSAB</title>
</head>
<body>
    <h1>Historique des calculs</h1>

    <p>
        <a href="index.jsp">Retour au calcul</a>
    </p>

    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Expression</th>
            <th>Résultat</th>
            <th>Date</th>
        </tr>

        <%
            List<Trace> traces = (List<Trace>) request.getAttribute("traces");

            if (traces != null && !traces.isEmpty()) {
                for (Trace t : traces) {
        %>
                    <tr>
                        <td><%= t.getId() %></td>
                        <td><%= t.getExpression() %></td>
                        <td><%= t.getResultat() %></td>
                        <td><%= t.getDateTraitement() %></td>
                    </tr>
        <%
                }
            } else {
        %>
                <tr>
                    <td colspan="4">Aucune trace enregistrée.</td>
                </tr>
        <%
            }
        %>
    </table>
</body>
</html>
