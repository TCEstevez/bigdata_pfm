<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>


    </style>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
</head>

<body>
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
<form:form method="POST" action="examen.htm" commandName="examen">



    <div class="formulario">
        <div class="posts">
        <div class="preguntas">
            <c:forEach items="${examen.questions}" var="question" varStatus="uStatus">
                <div class="pregunta">
                    <div id='dificulty' class='atributo'>
                        <label for="dificulty" >Dificultad:</label>
                        <form:input path="questions[${uStatus.index}].dificulty" title="De 0.00 a 9.99" />
                        <label for="dificulty" >a:</label>
                        <form:input path="questions[${uStatus.index}].dificultyto" title="De 0.00 a 9.99" />
                    </div>
                    <div id='number' class='atributo'>
                        <label for="number" class='etiqueta'>Numero de preguntas:</label>
                        <form:input path="questions[${uStatus.index}].number"  />
                    </div>
                    <div id='tags' class='atributo'>
                        <label for="tags" class='etiqueta'>Tags:</label>
                        <form:input class="textarea" path="questions[${uStatus.index}].tags" title="Tags a buscar separados por comas" />
                    </div>
                    <input type="submit" id="eliminar" name="accion" value="eliminar_${uStatus.index}" title="Elimina la fila"/>
                    <input type="submit" id="consultar" name="accion" value="consultar_${uStatus.index}" title="Consulta si existen elementos"/>
                </div>
            </c:forEach>
                <input type="submit" id="anadir" name="accion" value="anadir"/>
        </div>
            <div class="respuestas">
                <label class="lrespuesta">Respuesta a la consulta</label>
                <div class="resp">${examen.result}</div>
                <form:errors path="*" cssClass="errorblock" element="div"/>
            </div>
        </div>

        <div id='submit' class='outerDiv'>
            <input type="submit" class="acciones" name="accion" value="crear examen"/>
        </div>

    </div>

</form:form>

</body>
</html>
