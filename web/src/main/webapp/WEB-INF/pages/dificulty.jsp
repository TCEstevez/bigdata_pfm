<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<body>
<script type="text/javascript" src="<c:url value="/resources/js/d3.min.js"/>" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/js/d3pie.min.js"/>" charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.js"/>" charset="utf-8"></script>
<div>
    <ul>
        <li><a class="active" href="/mvc_FormExamen/examen.htm">Crear exam&eacute;n</a></li>
        <li><a href="">&Aacute;nalisis Datos</a>
            <ul>
                <li><a href="/mvc_FormExamen/grade.htm">Preguntas por tag</a></li>
                <li><a href="/mvc_FormExamen/dificulty.htm">Grados de dificultad</a></li>
            </ul>
        </li>
    </ul>
</div>
<div id="pieChart"></div>
<script>
    var data = [];
   var url="${home}/mvc_FormExamen/string.json"
    d3.json(url,function(json) {
         $.each(json, function(d,i){
            data.push({
                label: i.label,
                value: i.value
            })
        })


    var pie = new d3pie("pieChart", {
        "header": {
            "title": {
                "text": "Grados de dificultad",
                "fontSize": 22,
                "font": "verdana"
            },
        },
        "size": {
            "canvasHeight": 400,
            "canvasWidth": 590
        },
        "data": {
            "content": data
        },
        "labels": {
            "outer": {
                "pieDistance": 32
            }
        }
    });
    });
</script>

</body>
</html>