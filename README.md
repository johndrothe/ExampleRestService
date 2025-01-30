# Example Spring Boot Service
This project is intended to be a very simple REST service showing examples of the following.

* Consolidation of data from a pair of downstream requests.
* Using Caffeine to cache the consolidated data.
* Response DTOs to insulate consumers from downstream contract and response changes.
* Separation of concerns between beans/components to simplify testing and feature additions.

# How-to Run this Service
This simple service can be started locally by running the 'bootRun' Gradle task.

```
./gradlew bootRun
```

# Miscellaneous Implementation Notes
1) GitHub "Repositories" are referred to as "repos" to avoid potential confusion with Database repository classes.

# To-do
1) Add tests for the cache in GitHubUsersService.
2) GitHubClient
   1) Add Resilience 4j Retry
   2) Add configurable HTTP connection pools
   3) Switch from RestTemplate to WebClient if required for performance.
3) Add an "Error Response" DTO to use as a standard format for failures.
4) Add @Async to GitHubUsersService and run requests in parallel with a virtual thread pool.
5) Add viable JavaDoc to each class.
