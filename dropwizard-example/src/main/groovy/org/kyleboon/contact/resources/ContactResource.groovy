package org.kyleboon.contact.resources

import org.kyleboon.contact.core.Contact
import org.kyleboon.contact.core.SearchCommand
import org.kyleboon.contact.db.ContactDAO

import javax.validation.Valid
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import com.yammer.dropwizard.hibernate.UnitOfWork
import javax.ws.rs.GET
import com.yammer.metrics.annotation.Timed

/**
 * User: kboon
 * Date: 11/19/12
 */
@Path('/contacts')
@Produces(MediaType.APPLICATION_JSON)
class ContactResource {
    private final ContactDAO contactDAO

    public ContactResource(ContactDAO contactDAO) {
        this.contactDAO = contactDAO
    }

    /**
     * Add or update a contact in the system.
     *
     * @param contact
     * <p>The contact to add to the system.</p>
     * <p> Example: {
        "firstName": "Kyle",
        "lastName": "Boon",
        "jobTitle": "developer",
        "phoneNumber": "999-999-9999",
        "address": {
            "address1":"15 South 5th Street",
            "address2":"",
            "city":"Minneapolis",
            "state":"MN",
            "county":"Hennepin",
            "zipCode":"55402"
        }
     }
     * </p>
     * @return
     */
    @Timed(name = 'createContact')
    @POST
    @UnitOfWork
    public Contact createContact(@Valid Contact contact) {
        return contactDAO.saveOrUpdate(contact)
    }

    /**
     * Get a known Contact by id
     *
     *  @param {contactId}
     * <p> The id of the contact
     * <p> Example: 100
     * @return the contact
     */
    @GET
    @Path('/{contactId}')
    @Timed
    @UnitOfWork
    public Contact getContact(@PathParam(value = 'contactId') Long contactId) {
        Contact contact = contactDAO.findById(contactId)
        return contact
    }

    /**
     * Returns a list of all contacts. Warning, this could be a BIG list!
     * @return all the contact
     */
    @Timed(name = 'listContact')
    @GET
    @UnitOfWork
    public List<Contact> listContact() {
        return contactDAO.list()
    }

    /**
     * Searches the contact list by first name and returns a list of matching contacts.
     * @param searchCommand
     * <p> a command the includes the first name to search for. </p>
     * <p> { "firstName" : "kyle" } </p>
     * @return
     */
    @Timed(name = 'searchForContact')
    @Path('/search')
    @POST
    @UnitOfWork
    public List<Contact> search(@Valid SearchCommand searchCommand) {
        return contactDAO.findByFirstName(searchCommand.firstName)
    }
}
