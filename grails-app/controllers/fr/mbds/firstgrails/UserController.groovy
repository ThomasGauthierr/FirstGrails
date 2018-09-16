package fr.mbds.firstgrails

import grails.validation.ValidationException
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*

class UserController {

    UserService userService
    UploadUserProfileImageService uploadUserProfileImageService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userService.list(params), model:[userCount: userService.count()]
    }

    def show(Long id) {
        respond userService.get(id)
    }

    def create() {
        respond new User(params)
    }

    def save(User user) {
        //println cmd
        //if(cmd) {
            //println 'Yes the file'
            //println params.profileImageFile
            //String profileImageFilename = uploadUserProfileImageService.uploadProfileImage(params.profileImageFile)
        //}

        println params.profileImageFile.getClass()

        if (user == null) {
            notFound()
            return
        }

        try {
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond userService.get(id)
    }

    def update(User user) {
        if (user == null) {
            notFound()
            return
        }

        try {
            userService.save(user)
        } catch (ValidationException e) {
            respond user.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect user
            }
            '*'{ respond user, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        userService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }

   def uploadProfileImage(ProfileImageCommand cmd) {

       /*if(cmd.hasErrors()) {
           println 'Error occured during image upload: ' + cmd
           return
       }*/

       String profileImageFilename = uploadUserProfileImageService.uploadProfileImage(cmd)


       /*if(user == null) {
           notFound()
           return
       }

       if(user.hasErrors()) {
           println 'Error occured during image upload: ' + cmd
           return
       }*/

       //Locale locale = request.Local
       //flash.message = crudMessageService.message(CRUD.UPDATE, domainName(locale), user.id, locale);
       //redirect user;

       render contentType: "text/json", text: '{"name":"Afghanistan"}'
   }
}
