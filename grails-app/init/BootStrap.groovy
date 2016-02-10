import api.*
import groovy.json.JsonSlurper

class BootStrap {
    def grailsApplication

    def init = { servletContext ->
        if (!Employee.count()) {
            def filePath = "resources/employeeData.json"
            def text = grailsApplication.getParentContext().getResource("classpath:$filePath").getInputStream().getText()
            def data = new JsonSlurper().parseText(text)
            for (record in data) {
                def emp = new Employee(record)
                emp.id = record.id
                emp.save(failOnError: true);
            }
        }

        def admin, guest, roleAdmin, roleUser
        if (!User.count()) {
            admin = new User("admin", "admin").save(failOnError: true)
            guest = new User("guest", "guest").save(failOnError: true)
        }
        if (!Role.count()) {
            roleAdmin = new Role("ROLE_ADMIN").save(failOnError: true)
            roleUser = new Role("ROLE_USER").save(failOnError: true)
        }
        if (!UserRole.count()) {
            UserRole.create(admin, roleAdmin)
            UserRole.create(guest, roleUser)
        }
    }

    def destroy = {
    }
}
