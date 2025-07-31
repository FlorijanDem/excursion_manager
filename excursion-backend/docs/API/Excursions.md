### 📄 `excursion-api-docs.md`

```markdown
# 📁 Excursion API Documentation

Base URL: `/api/excursions`

---

## 🔹 GET `/api/excursions`

**Description**: Retrieve all excursions.

**Responses**:
- `200 OK`: Returns a list of all excursions.

**Example Response**:
```json
[
  {
    "id": 1,
    "title": "Vilnius Old Town Walk",
    "price": 20.0,
    "duration": 2,
    "photoUrl": "http://example.com/image.jpg",
    "category": {
      "id": 1,
      "name": "Historical Tours"
    }
  }
]
````

---

## 🔹 GET `/api/excursions/{id}`

**Description**: Retrieve a specific excursion by ID.

**Path Parameters**:

* `id` (integer) – Excursion ID.

**Responses**:

* `200 OK`: Returns the excursion object.
* `404 Not Found`: If no excursion is found with the given ID.

---

## 🔹 POST `/api/excursions`

**Description**: Create a new excursion.

**Validations**:

* `title` must not be null or blank.
* `category.id` must refer to an existing category.

**Request Body**:

```json
{
  "title": "Trakai Castle Visit",
  "price": 30.0,
  "duration": 3,
  "photoUrl": "http://example.com/trakai.jpg",
  "category": {
    "id": 2
  }
}
```

**Responses**:

* `200 OK`: Excursion created successfully.
* `400 Bad Request`: Validation failure (e.g., missing title or invalid category ID).

**Example Error Responses**:

```json
{
  "error": "Title is required"
}
```

```json
{
  "error": "Category with ID 99 does not exist."
}
```
