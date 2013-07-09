package grails.example



import grails.transaction.*
import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ContactController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Contact.list(params), model:[contactInstanceCount: Contact.count()]
    }

    def show(Contact contactInstance) {
        respond contactInstance
    }

    def create() {        
        respond new Contact(params)
    }

    @Transactional
    def save(Contact contactInstance) {
        if(contactInstance.hasErrors()) {
            respond contactInstance.errors, view:'create'
        }
        else {
            contactInstance.save flush:true
            request.withFormat {
                form { 
                    flash.message = message(code: 'default.created.message', args: [message(code: 'contactInstance.label', default: 'Contact'), contactInstance.id])
                    redirect contactInstance
                }
                '*' { render status: CREATED }
            }
        }
    }

    def edit(Contact contactInstance) { 
        respond contactInstance  
    }

    @Transactional
    def update(Contact contactInstance) {
        if(contactInstance == null) {
            render status:404
        }
        else if(contactInstance.hasErrors()) {
            respond contactInstance.errors, view:'edit'
        }
        else {
            contactInstance.save flush:true
            request.withFormat {
                form { 
                    flash.message = message(code: 'default.updated.message', args: [message(code: 'Contact.label', default: 'Contact'), contactInstance.id])
                    redirect contactInstance 
                }
                '*'{ render status: OK }
            }
        }        
    }

    @Transactional
    def delete(Contact contactInstance) {
        if(contactInstance) {
            contactInstance.delete flush:true
            request.withFormat {
                form { 
                    flash.message = message(code: 'default.deleted.message', args: [message(code: 'Contact.label', default: 'Contact'), contactInstance.id])
                    redirect action:"index", method:"GET" 
                }
                '*'{ render status: NO_CONTENT }
            }                
        }
        else {
            render status: NOT_FOUND
        }
    }
}

