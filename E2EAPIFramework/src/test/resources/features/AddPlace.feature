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
    When I send a "POST" request to "addPlaceAPI"
    Then the API response status code should be 200
    And the response body field "status" should be "OK"
    And the response body field "scope" should be "APP"
    And I store the "place_id" value for downstream API tests
    Examples:
      | lat       | lng       | accuracy | name    | phone_number       | address             | types           | website            | language |
      | 51.507351 | -0.127758 | 32       | Test  2 | (+44) 20 7946 0192 | 10 Baker St, London | clinic,pharmacy | https://apexmed.co | English  |

  Scenario Outline: Successfully retrieve the place with valid details
    Given the request body contains the stored place id
    When I send a "GET" request to "getPlaceAPI"
    Then the API response status code should be 200
    And the response body contains the following location details:
      | lat   | lng   | accuracy   | name   | phone_number   | address   | types   | website   | language   |
      | <lat> | <lng> | <accuracy> | <name> | <phone_number> | <address> | <types> | <website> | <language> |

    Examples:
      | lat       | lng       | accuracy | name    | phone_number       | address             | types           | website            | language |
      | 51.507351 | -0.127758 | 32       | Test  2 | (+44) 20 7946 0192 | 10 Baker St, London | clinic,pharmacy | https://apexmed.co | English  |

  Scenario: Successfully delete the place from the server
    Given delete place payload
    When I send a "DELETE" request to "deletePlaceAPI"
    Then the API response status code should be 200
    And the response body field "status" should be "OK"
