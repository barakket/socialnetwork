# Simple social networking application

This program was written using Java 8 and maven 3.x. To execute please run following commands in your command-line:
```
mvn spring-boot:run
```

# API

**Post message** 
----
A user should be able to post a 140 character message.

* **URL**

  /:userId/post

* **Method:**

  `POST`
  
*  **URL Params**

   **Required:**
 
   `userId=[String]`

* **Data Params**

  'Sample message'

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```
    {
      "message": {
        "content": "Sample message",
        "created": "2017-07-16,22:11:08"
      },
      "_links": {
        "wall": {
          "href": "http://localhost:8080/jacek/wall"
        }
      }
    }
    ```
 
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** 
    ```
    {
      "timestamp": 1500247317933,
      "status": 400,
      "error": "Bad Request",
      "exception": "com.bojko.socialnetwork.errors.MessageTooLongException",
      "message": "Max number of chars in post is 140. You had 288",
      "path": "/jacek/post"
    }
    ```


* **Sample Call:**

  ```bash
  curl -H "Content-Type: application/json" -X POST http://localhost:8080/kamila/post -d 'Sample message' 
  ```

**View user wall**
----
A user should be able to see a list of the messages they've posted, in reverse chronological order.


* **URL**

  /:userId/wall

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `userId=[String]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```
    {
        "userId": "kamila",
        "wallMessages": [
            {
                "content": "Sample message ",
                "created": "2017-07-16,23:31:07"
            }
        ]
    }
    ```
 
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** 
    ```
    {
        "timestamp": 1500247925456,
        "status": 404,
        "error": "Not Found",
        "exception": "com.bojko.socialnetwork.errors.UserNotFoundException",
        "message": "could not find user 'kamila'.",
        "path": "/kamila/wall"
    }
    ```


* **Sample Call:**

  ```bash
  curl http://localhost:8080/kamila/wall 
  ```

**Following other user**
----
A user should be able to follow another user. Following doesn't have to be reciprocal: Alice can follow Bob without Bob having to follow Alice.

* **URL**

  /:followerId/follow/:followeeId

* **Method:**

  `PUT`
  
*  **URL Params**

   **Required:**
 
   `followerId=[String]` <br />
   `followeeId=[String]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** None
 
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** 
    ```
    {
        "timestamp": 1500248154370,
        "status": 400,
        "error": "Bad Request",
        "exception": "com.bojko.socialnetwork.errors.IllegalActionException",
        "message": "User cannot follow himself!",
        "path": "/kamila/follow/kamila"
    }
    ```
  * **Code:** 404 NOT FOUND <br />
    **Content:** 
    ```
    {
      "timestamp": 1500248402064,
      "status": 404,
      "error": "Not Found",
      "exception": "com.bojko.socialnetwork.domain.UserNotFoundException",
      "message": "could not find user 'ola'.",
      "path": "/jacek/follow/ola"
    }
    ```

* **Sample Call:**

  ```bash
  curl -X PUT http://localhost:8080/kamila/follow/oliwia 
  ```

**View user timeline**
----
A user should be able to see a list of the messages posted by all the people they follow, in reverse chronological order.


* **URL**

  /:userId/timeline

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `userId=[String]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
    ```
    {
        "userTimeline": {
            "userId": "kamila",
            "timelineMessages": [
                {
                    "author": "oliwia",
                    "message": {
                        "content": "Sample message ",
                        "created": "2017-07-16,23:39:34"
                    }
                }
            ]
        },
        "_links": {
            "oliwia-wall": {
                "href": "http://localhost:8080/oliwia/wall"
            }
        }
    }
    ```
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** 
    ```
    {
        "timestamp": 1500248278860,
        "status": 404,
        "error": "Not Found",
        "exception": "com.bojko.socialnetwork.errors.UserNotFoundException",
        "message": "could not find user 'kamila'.",
        "path": "/kamila/timeline"
    }
    ```


* **Sample Call:**

  ```bash
  curl http://localhost:8080/kamila/timeline
  ```
