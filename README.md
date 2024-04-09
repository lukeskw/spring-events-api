<h1 align="center">pass.in</h1>

<div align="center">

[![Status](https://img.shields.io/badge/status-active-success.svg)]() <br>

</div>

---

## 📝 Table of Contents <a name = "en"></a>

- **[About](#about_en)**
- **[Application Flow](#application_flow)**
- **[Getting Started](#getting_started_en)**
- **[Usage](#usage_en)**
- **[Requirements](#requirements)**

## 📚 About <a name = "about_en"></a>

Pass.in is an Event Management System that allows users to manage events, register attendees, and generate event badges. It provides a set of API endpoints for creating events, registering attendees, checking in attendees, and retrieving event and attendee information.

## ➡ Application Flow <a name = "application_flow"></a>

<p align="center">
  <a href="" rel="noopener">
 <img height="240" src="public/app_flow.svg" alt="App flow"></a>
</p>

## 🏁 Getting Started <a name = "getting_started_en"></a>

These instructions will allow you to get a copy of the project and run the application locally for development and testing purposes.

### Prerequisites

- First, you need to have Java JDK (preferably v17) installed on your machine. To do this, access the official Java website by clicking [here](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and follow the installation instructions for your operating system.<br />
- You'll also need to have an application to test HTTP requests, which is capable of making HTTP requests. For this, you can use Insomnia, Postman, Hoppscotch or any other application of your choice. You can also access Swagger documentation by visiting:

  > http://localhost:8080/swagger-ui/index.html

### Installation

1. Clone the repository using the command or download the .zip file and extract the contents:

   ```sh
   git clone https://github.com/lukeskw/spring-events-api.git
   ```

2. Access the project folder

3. Run the project through the main function

## 🎈 Usage <a name="usage_en"></a>

To use the application, you can utilize the following API endpoints:

### pass.in routes

<details>
<summary>Events</summary>

### Create Event

- **Method:** POST
- **Route:** `/events`
- **Request body:**

  ```json
  {
    "title": "Event Name",
    "details": "Event Details",
    "maximumAttendees": 50
  }
  ```

- Example response:

  ```json
  {
    "eventId": "c6d77ac0-d638-4d3e-9a15-a196a8cdff1a"
  }
  ```

### Get Event

- **Method:** GET
- **Route:** `/events/:eventId`
- **Request parameters:**
    - eventId: Event id that can be obtained when creating the event or by accessing the data in the database
- **Request body:** `Not Required`
- Example response:

  ```json
  {
    "event": {
      "id": "16b9c10f-c291-419a-b76d-48c7b80a9577",
      "title": "Event Title",
      "details": "Event Details",
      "slug": "event-title",
      "maximumAttendees": 50,
      "attendeesAmount": 1
    }
  }
  ```

### Get Event Attendees

- **Method:** GET
- **Route:** `/events/:eventId/attendees`
- **Request parameters:**
    - `eventId`: Event id that can be obtained when creating the event or by accessing the data in the database
- **Request body:** `Not Required`

- Example response:

  ```json
  {
    "attendees": [
      {
        "id": "125767ae-f407-42c8-8b78-92eeb08f588d",
        "name": "Attendee 1",
        "email": "Attendee1@email.com",
        "createdAt": "2024-04-03T21:09:57.423Z",
        "checkedInAt": "2024-04-03T21:11:08.734Z"
      },
      {
        "id": "415d5c38-4314-4b03-9c17-5bbed953b6c9",
        "name": "Attendee 2",
        "email": "Attendee2@email.com",
        "createdAt": "2024-04-04T21:09:57.423Z",
        "checkedInAt": "2024-04-04T21:11:08.734Z"
      }
    ]
  }
  ```

</details>

<details>
<summary>Attendees</summary>

### Register Attendee for Event

- **Method:** POST
- **Route:** `/events/:eventId/attendees`
- **Request parameters:**
    - `eventId`: Event id that can be obtained when creating the event or by accessing the data in the database
- **Request body:**

  ```json
  {
    "name": "Attendee Name",
    "email": "Attendee@email.com"
  }
  ```

- Example response:

  ```json
  {
    "attendeeId": "900eabee-83bf-442e-babd-a49879ed1640"
  }
  ```

### Get Attendee Badge

- **Method:** GET
- **Route:** `/attendees/:attendeeId/badge`
- **Request parameters:**

    - attendeeId: Attendee id that can be obtained when registering the attendee on an Event or by accessing the data in the database

- **Request body:** `Not required`

- Example response:

  ```json
  {
    "badge": {
      "name": "Attendee 1",
      "email": "Attendee1@email.com",
      "eventId": "2032292d-87c8-4c3c-94fd-4b31a278d676",
      "checkInURL": "http://localhost:8080/attendees/900eabee-83bf-442e-babd-a49879ed1640/check-in"
    }
  }
  ```

</details>

<details>
<summary>CheckIns</summary>

### Check In Attendee

- **Method:** GET
- **Route:** `attendees/:attendeeId/check-in`
- **Request parameters:**
    - `attendeeId`: Attendee id that can be obtained when registering the attendee on an Event or by accessing the data in the database
- **Request body:** `Not Required`

- Example response: `201 CREATED`

</details>

## 🔧 Requirements <a name = "requirements"></a>

### Functional Requirements

- The organizer must be able to register a new event;
- The organizer must be able to view event details;
- The organizer must be able to view the list of participants;
- The participant must be able to register for an event;
- The participant must be able to view their registration badge;
- The participant must be able to check in at the event;

### Non-Functional Requirements

- Event check-in will be done through a QRCode;

### Business Rules

- A participant can only register for an event once;
- A participant can only register for events with available spots;
- A participant can only check in at an event once;