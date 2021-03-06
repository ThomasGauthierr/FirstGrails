<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'match.label', default: 'Match')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <a href="#create-match" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="content scaffold-create" role="main">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message && !flash.message2}">
                <div class="message" role="status">
                    <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
                </div>
            </g:if>
            <g:if test="${flash.message && flash.message2}">
                <div class="message" role="status">
                    <g:message code="${flash.message}" args="${flash.args}" default="${flash.default}"/>
                    <g:message code="${flash.message2}" args="${flash.args}" default="${flash.default}"/>
                </div>
            </g:if>
            <g:if test="${!flash.message && flash.message2}">
                <div class="message" role="status">
                    <g:message code="${flash.message2}" args="${flash.args}" default="${flash.default}"/>
                </div>
            </g:if>
            <g:hasErrors bean="${this.match}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.match}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.match}" method="POST">
                <fieldset class="form">
                    <f:all bean="match"/>
                </fieldset>
                <div id="create-match">
                    <button type="submit" class="save btn btn-primary">
                        Create <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                    </button>

                    <g:link action="index" class="no-underline">
                        <button type="button" class="btn btn-danger">
                            Cancel <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
                        </button>
                    </g:link>
                </div>
            </g:form>
        </div>
    </body>
</html>
