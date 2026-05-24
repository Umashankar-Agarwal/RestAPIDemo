Feature: Add New Place via Maps API
  As an application client
  I want to submit new location coordinates and metadata
  So that the system stores the place and returns a tracking identifier

  Background:
    Given the Maps API base URI is configured with query parameter

  Scenario Outline: Successfully add a new place with valid details
    Given the request body contains the following location details:
      | lat   | lng   | accuracy   | name   | phone_number   | address   | types   | website   | language   |
      | <lat> | <lng> | <accuracy> | <name> | <phone_number> | <address> | <types> | <website> | <language> |
    When I send a POST request to "maps/api/place/add/json"
    Then the API response status code should be 200
    And the response body field "status" should be "OK"
    And the response body field "scope" should be "APP"
    And I store the "place_id" value for downstream API tests
    Examples:
      | lat        | lng       | accuracy | name        | phone_number       | address           | types                  | website            | language |
      | -38.383494 | 33.427362 | 50       | Add 5 | (+91) 984 893 1232 | park hospital     | Residential,commercial | http://google.com  | English  |
#      | -34.567890 | 20.123456 | 60       | Add Place 6 | (+91) 999 888 7777 | Tech Park Phase 1 | Office,commercial      | http://office.com  | Spanish  |
#      | 40.712776  | -74.00597 | 40       | Add Place 8 | (+1) 212 555 0199  | 5th Avenue NY     | Warehouse              | http://storage.com | French   |
#
#  @Negative
#  Scenario: Fail to add a place with an invalid authentication key
#    Given the authentication query parameter "key" is set to "invalid_key_123"
#    And the request body contains valid location details
#    When I send a "POST" request to "/maps/api/place/add/json"
#    Then the API response status code should be 404
#
#  @Negative
#  Scenario Outline: Fail to add a place due to missing mandatory information
#    Given the request body is missing the "<missing_field>" field
#    When I send a "POST" request to "/maps/api/place/add/json"
#    Then the API response status code should be 400
#    And the response body field "msg" should indicate a validation failure
#
#    Examples:
#
#      | missing_field |
#      | location      |
#      | address       |
#      | name          |
