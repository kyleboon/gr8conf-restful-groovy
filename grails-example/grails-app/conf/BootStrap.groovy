import grails.example.*

class BootStrap {

    def init = { servletContext ->
    	new Contact(firstName: 'Kyle',
					lastName: 'Boon',
					jobTitle: 'jobTitle',
					phoneNumber: '999-999-9999',
					address: new Address(
						address1: '15 South 5th St.',
						address2: 'Suite 300',
						city: 'Minneapolis',
						state: 'MN',
						county: 'Hennepin',
						zipCode: '55402')).save()
    }
    def destroy = {
    }
}
