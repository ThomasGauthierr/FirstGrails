<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'match.label', default: 'Match')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#list-match" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div id="list-match" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <table >
                <tr>
                    <th>Winner / score</th>
                    <th>Looser / score</th>
                    <th>Date</th>
                    <th/>
                </tr>
                <g:each in="${matchList}">
                    <tr>
                        <td>
                            <% try{ %>
                                <g:if test="${!it.winner.isDeleted}">
                                    <g:link action="show" controller="user" params="${[id: it.winner.id]}">${it.winner.username}</g:link> / ${it.winnerScore}
                                </g:if>

                                <g:else>
                                    ${it.winner.username} / ${it.winnerScore}
                                </g:else>
                            <%} catch(Exception e){%>
                            <%}
                            %>
                        </td>
                        <td>
                            <% try{ %>
                                <g:if test="${!it.looser.isDeleted}">
                                    <g:link action="show" controller="user" params="${[id: it.looser.id]}">${it.looser.username}</g:link> / ${it.looserScore}
                                </g:if>

                                <g:else>
                                    ${it.looser.username} / ${it.looserScore}
                                </g:else>
                            <%} catch(Exception e){%>
                            <%}
                            %>
                        </td>
                        <td>${it.dateCreated}</td>
                        <td>
                            <g:form resource="${it}" method="DELETE">
                                <fieldset class="buttons-container">
                                    <g:link action="show" params="${[id: it.id]}" class=" no-underline">
                                        <button type="button" class="btn btn-success">Show <span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>
                                    </g:link>
                                    <g:link action="edit" params="${[id: it.id]}" class=" no-underline">
                                        <button type="button" class="btn btn-primary">Edit <span class="glyphicon glyphicon-edit" aria-hidden="true"></span></button>
                                    </g:link>
                                    <button type="submit" class="btn btn-danger">Delete <span class="glyphicon glyphicon-trash" aria-hidden="true"></span></button>
                                </fieldset>
                            </g:form>
                        </td>
                    </tr>
                </g:each>
            </table>

            %{--<div class="pagination">--}%
                %{--<g:paginate total="${matchCount ?: 0}" />--}%
            %{--</div>--}%
        </div>
    </body>
</html>