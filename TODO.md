# TODO:

## Implement login tokens

### Changes:

* Create TokenController with endpoints for: creating tokens, invalidating tokens, refreshing tokens, getting token details
* Create TokenAuthenticationFilter get the access token from headers and create authentication
* Create token service and token in memory repository

## Messagebox:

* Create controller for getting messages, for reading messages and for creating messages
* Creating a message can be done to yourself or to others if ROLE_SERVICE