<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
    <meta content="utf-8" http-equiv="encoding">
    <script type="text/javascript" src="<c:url value="/resources/js/d3.min.js"/>" charset="utf-8"></script>
    <title>Buscador n&uacute;mero preguntas por tags</title>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">

</head>
<body>
<div>
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
    <form:form method="POST" action="grade.htm" commandName="examen">
    <c:forEach items="${examen.questions}" var="question" varStatus="uStatus">
        <div class="pregunta">
            <div id='tags' class='atributo'>
                <label for="tags" class='etiqueta'>Tags:</label>
                <form:input class="textarea" path="questions[${uStatus.index}].tags"
                            title="Tags a buscar separados por comas"/>
            </div>
        </div>
    </c:forEach>
    <input type="submit" name="accion" value="buscar" title="buscar"/>

    <form:errors path="*" cssClass="errorblock2" element="div"/>

</div>
</form:form>
<div id="wrapper">

</div>
<script>
    var tagsLong = ${tagsLong};
    var color = '#a6d1e1';
    var tagsString =${tagsString};
    if (typeof tagsString !== 'undefined' && tagsString.length > 0) {

        var grid = d3.range(25).map(function (i) {
            return {'x1': 0, 'y1': 0, 'x2': 0, 'y2': 480};
        });


        var xscale = d3.scale.linear()
                .domain([0, Math.max.apply(Math, tagsLong)])
                .range([0, 722]);

        var yscale = d3.scale.linear()
                .domain([0, tagsString.length])
                .range([0, 480]);

        var colorScale = d3.scale.quantize()
                .domain([0, tagsString.length])
                .range(color);

        var canvas = d3.select('#wrapper')
                .append('svg')
                .attr({'width': 900, 'height': 600});

        var grids = canvas.append('g')
                .attr('id', 'grid')
                .attr('transform', 'translate(150,10)')
                .selectAll('line')
                .data(grid)
                .enter()
                .append('line')
                .attr({
                    'x1': function (d, i) {
                        return i * 30;
                    },
                    'y1': function (d) {
                        return d.y1;
                    },
                    'x2': function (d, i) {
                        return i * 30;
                    },
                    'y2': function (d) {
                        return d.y2;
                    },
                })
                .style({'stroke': '#adadad', 'stroke-width': '1px'});

        var xAxis = d3.svg.axis();
        xAxis
                .orient('bottom')
                .scale(xscale)


        var yAxis = d3.svg.axis();
        yAxis
                .orient('left')
                .scale(yscale)
                .tickSize(2)
                .tickFormat(function (d, i) {
                    return tagsString[i];
                })
                .tickValues(d3.range(17));

        var y_xis = canvas.append('g')
                .attr("transform", "translate(150,0)")
                .attr('id', 'yaxis')
                .call(yAxis);

        var x_xis = canvas.append('g')
                .attr("transform", "translate(150,480)")
                .attr('id', 'xaxis')
                .call(xAxis);

        var chart = canvas.append('g')
                .attr("transform", "translate(150,0)")
                .attr('id', 'bars')
                .selectAll('rect')
                .data(tagsLong)
                .enter()
                .append('rect')
                .attr('height', 19)
                .attr({
                    'x': 0, 'y': function (d, i) {
                        return yscale(i) + 19;
                    }
                })
                .style('fill', function (d, i) {
                    return colorScale(i);
                })
                .attr('width', function (d) {
                    return 0;
                });


        var transit = d3.select("svg").selectAll("rect")
                .data(tagsLong)
                .transition()
                .duration(1000)
                .attr("width", function (d) {
                    return xscale(d);
                });

        var transitext = d3.select('#bars')
                .selectAll('text')
                .data(tagsLong)
                .enter()
                .append('text')
                .attr({
                    'x': function (d) {
                        return xscale(d) + 1;
                    }, 'y': function (d, i) {
                        return yscale(i) + 35;
                    }
                })
                .text(function (d) {
                    return d;
                }).style({'fill': '#fff', 'font-size': '14px'});
    }

</script>
</body>
</html>