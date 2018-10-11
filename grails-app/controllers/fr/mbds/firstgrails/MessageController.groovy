package fr.mbds.firstgrails

import grails.util.Holders
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class MessageController {

    def springSecurityService = Holders.applicationContext.springSecurityService

    MessageService messageService
    MessageCustomService messageCustomService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond messageService.list(params), model:[messageCount: messageService.count()]
    }

    def show(Long id) {
        def user = springSecurityService.currentUser
        if (!user.authorities.any { it.authority == "ROLE_ADMIN" }) {
            if (!messageCustomService.checkAccess(user, id)) {
                redirect action: "display"
                return
            }
        }

        messageCustomService.checkRead(springSecurityService.currentUser.id, id)

        respond messageService.get(id)
    }

    def create() {
        respond new Message(params)
    }

    def save(Message message) {
        if (message == null) {
            notFound()
            return
        }

        if (message.author == message.target) {
            flash.message = "message.create.error.same"
            redirect action: "create"
            return
        }

        if (message.content == null) {
            flash.message = "message.create.error.content"
            redirect action: "create"
            return
        }

        try {
            messageService.save(message)
        } catch (ValidationException e) {
            respond message.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'message.label', default: 'Message'), message.id])
                redirect message
            }
            '*' { respond message, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond messageService.get(id)
    }

    def update(Message message) {
        if (message == null) {
            notFound()
            return
        }

        if (message.author == message.target) {
            flash.message = "message.create.error.same"
            redirect action: "edit", id: message.id
            return
        }

        if (message.content == null) {
            flash.message = "message.create.error.content"
            redirect action: "edit", id: message.id
            return
        }

        try {
            messageService.save(message)
        } catch (ValidationException e) {
            respond message.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'message.label', default: 'Message'), message.id])
                redirect message
            }
            '*'{ respond message, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        messageService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'message.label', default: 'Message'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'message.label', default: 'Message'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    //ToDo : separate sent and received messages
    def display() {
        def user = User.get(springSecurityService.currentUser.id)
        def results = messageCustomService.getAllUserMessages(user)

        respond results
    }
}
