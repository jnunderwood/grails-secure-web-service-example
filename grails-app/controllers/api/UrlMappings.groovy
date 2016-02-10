package api

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/api/employees/count"( controller: 'employee', action: 'count' )
        "/api/employees/list/$max?/$offset?"( controller: 'employee', action: 'list' )
        "/api/employees/findBy$name/$value"( controller: 'employee', action: 'findByField' )
        "/api/employees/findByName/$lastName/$firstName?"( controller: 'employee', action: 'findByName' )

        "/"(controller: 'application', action:'index')
        "500"(view: '/application/serverError')
        "404"(view: '/application/notFound')
    }
}
