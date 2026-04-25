# Example Spring Boot Service
This project is intended to be a very simple REST service showing examples of the following.

* Consolidation of data from a pair of downstream requests.
* Using Caffeine to cache the consolidated data.
* Response DTOs to insulate consumers from downstream contract and response changes.
* Separation of concerns between beans/components to simplify testing and feature additions.

# How-to Use this Serrvice
This simple service can be started locally by running the 'bootRun' Gradle task.

```
./gradlew bootRun
```

It has a single endpoint (/users/{userId}) that you can test with the following URI.

```
http://localhost:8080/users/octocat
```
The service should respond with a JSON document that resembles the following.

```JSON
{
  "user_name": "octocat",
  "display_name": "The Octocat",
  "avatar": "https://avatars.githubusercontent.com/u/583231?v=4",
  "geo_location": "San Francisco",
  "email": null,
  "url": "https://api.github.com/users/octocat",
  "created_at": "2011-01-25T18:44:36Z",
  "repos": [
    {
      "name": "boysenberry-repo-1",
      "url": "https://api.github.com/repos/octocat/boysenberry-repo-1"
    },
    {
      "name": "git-consortium",
      "url": "https://api.github.com/repos/octocat/git-consortium"
    },
    {
      "name": "hello-worId",
      "url": "https://api.github.com/repos/octocat/hello-worId"
    },
    {
      "name": "Hello-World",
      "url": "https://api.github.com/repos/octocat/Hello-World"
    },
    {
      "name": "linguist",
      "url": "https://api.github.com/repos/octocat/linguist"
    },
    {
      "name": "octocat.github.io",
      "url": "https://api.github.com/repos/octocat/octocat.github.io"
    },
    {
      "name": "Spoon-Knife",
      "url": "https://api.github.com/repos/octocat/Spoon-Knife"
    },
    {
      "name": "test-repo1",
      "url": "https://api.github.com/repos/octocat/test-repo1"
    }
  ]
}
```

# Miscellaneous Implementation Notes
1) GitHub "Repositories" are referred to as "repos" to avoid potential confusion with Database repository classes.

# To-do
1) Add tests for the cache in GitHubUsersService.
2) GitHubClient
   1) Add configurable HTTP connection pools
   2) Switch from RestTemplate to WebClient if required for performance.
3) Add @Async to GitHubUsersService and run requests in parallel with a virtual thread pool.
4) Add viable JavaDoc to each class.
