# Stream Deduplication Service

A modular Java service designed for **deduplicating top live streamers** in 17LIVE sections. It ensures that **no top-3 streamer appears in the top positions of more than one section**, thereby improving user experience by promoting diversity.

---

## Complete Solution Overview

This Java project was developed to solve the **17LIVE streamer duplication problem**, where multiple sections could display the same top streams. The solution ensures:

- No stream appears in the top 3 positions in more than one section.
- Section and stream order (except top 3) remains preserved.
- System remains scalable, modular, and extensible.

The implementation follows a **clean architecture**, with strong emphasis on:

- **SOLID principles**
- **Testability**
- **Design patterns**
- **Real-world maintainability**

---

## Key Features

### SOLID Principles Implementation

- **Single Responsibility**: Each class has one distinct role (e.g., parsing, deduplication, writing).
- **Open/Closed**: Easily extend the logic by adding new deduplication strategies without modifying existing logic.
- **Liskov Substitution**: Any new strategy can replace the existing strategy without breaking functionality.
- **Interface Segregation**: Small, focused interfaces. Strategy interface only exposes the method needed.
- **Dependency Inversion**: High-level modules (service) depend on abstractions, not concrete classes.

---

### Design Patterns Used

- **Strategy Pattern**: Swappable deduplication algorithms (e.g., `GreedyDeduplicationStrategy`).
- **Factory Pattern**: Central place to instantiate strategies. Easy to extend.
- **Dependency Injection**: Strategies and services injected via constructor for flexibility and testability.

---

## Algorithm Explanation: Greedy Deduplication

### Goal

Ensure that the **top 3 streamers in any section are unique across all sections**, while preserving:

- Original **section order**
- The order of streamers **after top 3**

---

### Step-by-Step Flow

1. **Initialization**
    - A global `Set<String> usedTopStreamers` is created to keep track of all streamers already used in top 3 positions.
2. **Iterate Through Sections (in Order)**
    - For each section:
        - Extract all streamers.
        - Attempt to fill the **first 3 positions** with streamers not in `usedTopStreamers`.
3. **Replace Conflicts**
    - If a top-3 streamer is already used:
        - Look ahead in the list to find a replacement streamer not in `usedTopStreamers`.
        - Swap it into the current position.
4. **Update Tracker**
    - Add the newly selected top-3 streamers to `usedTopStreamers`.
5. **Leave Rest Unchanged**
    - All streamers after position 3 remain in their original order.
6. **Collect Final Output**
    - Each section is mapped to its new list of streamers, with top 3 deduplicated.

---

### Time and Space Complexity

- **Time Complexity**: O(n × m)
    
    where `n` = number of sections, `m` = average streamers per section
    
- **Space Complexity**: O(k)
    
    where `k` = total number of unique top streamers stored in the used set
    

---

## Edge Cases Handled

1. **Null or Empty Input**: Handled gracefully; logs appropriate errors.
2. **Invalid Parameters**: Errors like negative top N values throw controlled exceptions.
3. **Too Few Streamers**: If fewer than 3 streamers are available, it fills as many as possible.
4. **Duplicate Streamers in Same Section**: Duplicates inside one section are preserved unless part of the top 3.
5. **Missing Section Metadata**: Defaults or throws custom error messages with logger help.

---

## Test Cases Included

| Type | Test Details |
| --- | --- |
| Normal Case | Two sections with overlapping top streamers |
| Empty Input | Input file has no sections or data |
| Null Input | Application receives null instead of parsed JSON |
| Invalid Top Count | `topCount = 0` or `topCount < 0` |
| Strategy Failure | Deduplication strategy throws internal error → verified with Mockito mocks |
| Same Streamer | Same streamer repeated in top 3 of many sections |

All tests use **JUnit 5** and **Mockito 4.12.0+**.

---

## Prerequisites

- Java 11 or later
- Maven 3.6 or later
- Mockito 4.12.0 or later (for testing)

---

## Project Setup

### 1. Clone the Repository

```bash
bash
CopyEdit
git clone https://github.com/your-username/stream-deduplication.git
cd stream-deduplication

```

---

### 2. Install Dependencies

```bash
bash
CopyEdit
mvn clean install

```

Installs all project dependencies and runs tests.

---

### 3. Build the Project

```bash
bash
CopyEdit
mvn clean package

```

Generates the executable JAR file at:

```
bash
CopyEdit
target/stream-deduplication-1.0.0.jar

```

---

## Running the Application

### Manual Run

```bash
bash
CopyEdit
java -jar target/stream-deduplication-1.0.0.jar input.json output.json

```

| Argument | Description |
| --- | --- |
| `input.json` | Path to the input dataset JSON file |
| `output.json` | Path to write the result |

---

### Quick Start Script (Optional)

```bash
bash
CopyEdit
chmod +x run.sh
./run.sh

```

A convenience wrapper script can be added to automate jar build and run.

---

## Input/Output Example

### Input (`input.json`)

```json
json
CopyEdit
[
  {
    "sectionData": [
      {"streamerID": "streamer1"},
      {"streamerID": "streamer2"},
      {"streamerID": "streamer3"}
    ],
    "sectionID": "Section1"
  },
  {
    "sectionData": [
      {"streamerID": "streamer2"},
      {"streamerID": "streamer3"},
      {"streamerID": "streamer4"}
    ],
    "sectionID": "Section2"
  }
]

```

---

### Output (`output.json`)

```json
json
CopyEdit
{
  "Section1": ["streamer1", "streamer2", "streamer3"],
  "Section2": ["streamer4", "streamer5", "streamer6"]
}

```

Note: If `streamer5` and `streamer6` exist in the list and weren’t used, they are promoted.

---

## Architecture Overview

```
css
CopyEdit
com.seventeenlive
├── application
│   └── StreamDeduplicationApplication.java
├── factory
│   └── DeduplicationStrategyFactory.java
├── strategy
│   ├── DeduplicationStrategy.java
│   └── GreedyDeduplicationStrategy.java
├── model
│   ├── Section.java
│   └── StreamerData.java
├── parser
│   └── JsonDataParser.java
├── writer
│   └── JsonOutputWriter.java
├── service
│   └── StreamDeduplicationService.java

```

---

## Author

**Amit Kumar Gupta**

Final-year B.Tech, NIT Calicut

Passionate about Java backend, clean code, and architecture design.

## **Input/Output Example**

**Input** (input.json):

`[  {    "sectionData": [      {"streamerID": "streamer1"},      {"streamerID": "streamer2"},      {"streamerID": "streamer3"}    ],    "sectionID": "Section1"  },  {    "sectionData": [      {"streamerID": "streamer2"},      {"streamerID": "streamer3"},      {"streamerID": "streamer4"}    ],    "sectionID": "Section2"  }]`

**Output** (output.json):

`{  "Section1": ["streamer1", "streamer2", "streamer3"],  "Section2": ["streamer4", "streamer5", "streamer6"]}`