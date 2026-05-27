Feature: Place Validations via Maps API
  As an application client
  I want to submit new location coordinates and metadata
  I want to check the new place is added with the correct details
  I want to update the details
  I want to delete the details for the tracking ID
  So that the system stores the place and returns a tracking identifier

  Background:
    Given the Maps API base URI is configured with query parameter

  Scenario Outline: Successfully add a new place with valid details
    Given the request body contains the following location details:
      | lat   | lng   | accuracy   | name   | phone_number   | address   | types   | website   | language   |
      | <lat> | <lng> | <accuracy> | <name> | <phone_number> | <address> | <types> | <website> | <language> |
    When I send a "POST" request to "AddPlaceAPI"
    Then the API response status code should be 200
    And the response body field "status" should be "OK"
    And the response body field "scope" should be "APP"
    And I store the "place_id" value for downstream API tests
    Examples:
      | lat        | lng        | accuracy | name            | phone_number        | address              | types               | website             | language |
      | 51.507351  | -0.127758  | 30       | Apex Medical    | (+44) 20 7946 0192  | 10 Baker St, London  | clinic,pharmacy     | https://apexmed.co  | English  |
      | 35.676192  | 139.650381 | 15       | Sakura Dining   | (+81) 3 5555 0143   | Shibuya 2-Chome, TYO | restaurant,food     | https://sakurad.jp  | Japanese |
      | -22.906847 | -43.172896 | 45       | Porto Logistics | (+55) 21 99988 1111 | Rio Harbour Hub A    | industrial,shipping | https://portolog.br | Spanish  |


  Scenario Outline: Successfully retrieve the place with valid details
    Given the request body contains the "place_id"
    When I send a "POST" request to "AddPlaceAPI"
    Then the API response status code should be 200
    And the response body field "status" should be "OK"
    And the response body field "scope" should be "APP"
    And I store the "place_id" value for downstream API tests





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
