package grails.example

import grails.rest.*

@Resource(uri='/contacts')
class Contact {
	String firstName
	String lastName
	String jobTitle
	String phoneNumber
	Address address

    static constraints = {
    }
}