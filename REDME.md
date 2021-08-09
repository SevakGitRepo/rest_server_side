POSTMAN 
GET /users -------------->return All Users

POST /users + body json {
                            "id": 1,
                            "name":"Sevak",
                            "surname":"Tovmasyan",
                            "age": 25
                        }
                        --------->Add User if id is not exist else answer error
                        
PUT /users + body json {
                            "id": 1,
                            "name":"Sevak",
                            "surname":"Tovmasyan",
                            "age": 25
                        }
                        --------->UPDATE User if id exist else answer error
                        
DELETE /users/id ----------> Delete user if id exist else answer error 
        
ANOTHER -----------> answer only 4 methods        