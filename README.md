# Proposal Manager Application

This README outlines the steps to run the Proposal Manager application.

## Requirements

- Docker
- Docker Compose

## Instructions
### Run the Application Using Docker Compose

run the following command to start the application.

```
docker-compose up
```

This will start the application with database defined in the `docker-compose.yml` file.

## API Endpoints

### 1. Get All Proposals
**Endpoint:** `GET /proposal/getAll`

Retrieves a paginated list of proposals based on provided filter criteria and pagination parameters.

**Query Parameters:**
- `names`: List of Strings - Filter by list of proposal names.
- `proposalStatuses`: List of `ProposalStatusEnum` - Filter by list of proposal statuses.
- Pagination parameters (`page`, `size`, `sort`, etc.)

**Response:**
A `Page` object containing an array of `ProposalModel` objects.

---

### 2. Create Proposal
**Endpoint:** `POST /proposal/add`

Creates a new proposal in the system.

**Request Body:**
- `ProposalModel`: Object containing proposal details. Must be validated before submission.
    - `name` (String): The name of the proposal. Required.
    - `content` (String): The content of the proposal. Required.

**Response:**
HTTP Status 200 OK if the proposal is successfully created.

---

### 3. Delete Proposal
**Endpoint:** `DELETE /proposal/delete/{id}`

Deletes a proposal from the system based on the provided ID.

**Path Variable:**
- `id` (Long): The ID of the proposal to delete.

**Request Body:**
- `ActionReason`: Object containing the reason for deletion.
    - `reason` (String): The reason for the action.

**Response:**
HTTP Status 200 OK if the proposal is successfully deleted.

---

### 4. Reject Proposal
**Endpoint:** `POST /proposal/reject/{id}`

Rejects a proposal in the system based on the provided ID.

**Path Variable:**
- `id` (Long): The ID of the proposal to reject.

**Request Body:**
- `ActionReason`: Object containing the reason for rejection.
    - `reason` (String): The reason for the action.

**Response:**
HTTP Status 200 OK if the proposal is successfully rejected.

---

### 5. Verify Proposal
**Endpoint:** `POST /proposal/verify/{id}`

Verifies a proposal in the system based on the provided ID.

**Path Variable:**
- `id` (Long): The ID of the proposal to verify.

**Response:**
HTTP Status 200 OK if the proposal is successfully verified.

---

### 6. Accept Proposal
**Endpoint:** `POST /proposal/accept/{id}`

Accepts a proposal in the system based on the provided ID.

**Path Variable:**
- `id` (Long): The ID of the proposal to accept.

**Response:**
HTTP Status 200 OK if the proposal is successfully accepted.

---

### 7. Publish Proposal
**Endpoint:** `POST /proposal/publish/{id}`

Publishes a proposal in the system based on the provided ID.

**Path Variable:**
- `id` (Long): The ID of the proposal to publish.

**Response:**
HTTP Status 200 OK if the proposal is successfully published.

---

### 8. Update Proposal
**Endpoint:** `PATCH /proposal/update`

Updates a proposal in the system.

**Request Body:**
- `ProposalModel`: Object containing updated proposal details.
    - `name` (String): The name of the proposal. Required.
    - `content` (String): The updated content of the proposal. Required.


**Response:**
HTTP Status 200 OK if the proposal is successfully updated.

---