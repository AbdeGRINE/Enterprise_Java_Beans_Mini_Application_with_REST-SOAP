<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8">
<title>HISSAB - Apprendre le calcul</title>

<style>
    body {
        margin: 0;
        padding: 0;
        font-family: "Trebuchet MS", Arial, sans-serif;
        background-color: #fdf6e3;
        color: #333;
    }

    .page {
        width: 95%;
        max-width: 1100px;
        margin: 30px auto;
        background: #ffffff;
        border: 3px solid #f4c542;
        border-radius: 12px;
        overflow: hidden;
    }

    .header {
        background-color: #4a90e2;
        color: white;
        text-align: center;
        padding: 25px;
    }

    .header h1 {
        margin: 0;
        font-size: 38px;
    }

    .header p {
        margin-top: 10px;
        font-size: 18px;
    }

    .content {
        padding: 30px;
    }

    .layout {
        display: flex;
        gap: 20px;
        align-items: stretch;
    }

    /* GAUCHE = formulaires */
    .left {
        flex: 2;
        display: flex;
        flex-direction: column;
        gap: 20px;
    }

    /* DROITE = résultats */
    .right {
        flex: 1;
    }

    .box {
        padding: 20px;
        background-color: #fffdf5;
        border: 2px dashed #ffd166;
        border-radius: 10px;
    }

    .result-box {
        background-color: #eafaf1;
        border: 2px solid #6ecb63;
        height: 100%;
    }

    h2 {
        margin-top: 0;
        color: #ff8c42;
        font-size: 22px;
    }

    label {
        display: block;
        margin-bottom: 8px;
        font-weight: bold;
    }

    input[type="text"],
    input[type="file"] {
        width: 100%;
        padding: 10px;
        border-radius: 8px;
        border: 2px solid #bcdffb;
    }

    .btn {
        margin-top: 12px;
        padding: 10px 18px;
        background-color: #6ecb63;
        border: none;
        color: white;
        border-radius: 8px;
        cursor: pointer;
        font-weight: bold;
    }

    .btn:hover {
        background-color: #5ab854;
    }

    .history {
        text-align: center;
        margin-top: 25px;
    }

    .history a {
        padding: 10px 18px;
        background-color: #4a90e2;
        color: white;
        text-decoration: none;
        border-radius: 8px;
        font-weight: bold;
    }

    .result-value {
        font-size: 28px;
        font-weight: bold;
        color: #2e7d32;
    }

    .error {
        margin-top: 15px;
        padding: 10px;
        background: #fff0f0;
        border: 1px solid #cc0000;
        color: #cc0000;
        border-radius: 8px;
    }

    @media (max-width: 900px) {
        .layout {
            flex-direction: column;
        }
    }
</style>
</head>

<body>

<div class="page">

    <div class="header">
        <h1>🧮 HISSAB</h1>
        <p>Apprendre et vérifier les calculs facilement 📘</p>
    </div>

    <div class="content">

        <div class="layout">

            <!-- ================= GAUCHE ================= -->
            <div class="left">

                <div class="box">
                    <h2>✍️ Écrire un calcul</h2>
                    <form action="calculer" method="post">
                        <label>Expression :</label>
                        <input type="text" name="expression" value="5 + 2 * 6 - 3">
                        <button class="btn" type="submit">Calculer</button>
                    </form>
                </div>

                <div class="box">
                    <h2>📄 Lire un fichier</h2>
                    <form action="upload" method="post" enctype="multipart/form-data">
                        <label>Fichier (PDF / image / TXT)</label>
                        <input type="file" name="fichier" accept=".pdf,.txt,.png,.jpg,.jpeg">
                        <button class="btn" type="submit">Extraire</button>
                    </form>
                </div>

            </div>

            <!-- ================= DROITE (1 SEUL BLOC RESULTAT) ================= -->
            <div class="right">

                <div class="box result-box">
                    <h2>🎯 Résultat</h2>

                    <% if (request.getAttribute("resultat") != null) { %>

                        <p><strong>Expression :</strong></p>
                        <p><%= request.getAttribute("expression") %></p>

                        <p style="margin-top:10px;"><strong>Résultat :</strong></p>
                        <p class="result-value">
                            <%= request.getAttribute("resultat") %>
                        </p>

                    <% } else { %>
                        <p style="color:#777;">
                            Les résultats des calculs apparaîtront ici.
                        </p>
                    <% } %>

                    <% if (request.getAttribute("erreur") != null) { %>
                        <div class="error">
                            ❌ <%= request.getAttribute("erreur") %>
                        </div>
                    <% } %>

                </div>

            </div>

        </div>

        <div class="history">
            <a href="traces">📚 Voir l'historique</a>
        </div>

    </div>
</div>

</body>
</html>