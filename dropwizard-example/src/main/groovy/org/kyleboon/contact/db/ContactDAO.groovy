package org.kyleboon.contact.db

import org.kyleboon.contact.core.Contact
import com.yammer.dropwizard.hibernate.AbstractDAO
import org.hibernate.SessionFactory
import org.hibernate.criterion.MatchMode
import org.hibernate.criterion.Restrictions

/**
 * User: kboon
 * Date: 11/19/12
 */
// TODO: write a unit test for this class
class ContactDAO extends AbstractDAO<Contact> {

    ContactDAO(SessionFactory factory) {
        super(factory)
    }

    Contact saveOrUpdate(Contact contact) {
        return persist(contact)
    }

    Contact findById(Long id) {
        return currentSession()
                .createCriteria(Contact)
                .add(Restrictions.eq('id', id))
                .uniqueResult() as Contact
    }

    List<Contact> list() {
        return currentSession()
                .createCriteria(Contact).list() as List<Contact>
    }

    List<Contact> findByFirstName(String firstName) {
        return currentSession()
                .createCriteria(Contact)
                .add(Restrictions.ilike('firstName', firstName, MatchMode.ANYWHERE))
                .list() as List<Contact>
    }
}
