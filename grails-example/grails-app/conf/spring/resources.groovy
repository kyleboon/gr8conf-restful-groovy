// Place your Spring DSL code here
import grails.rest.render.hal.*
beans = {
    halContactRenderer(HalJsonRenderer, grails.example.Contact)
    halContactRenderer(HalJsonRenderer, grails.example.Address)
}