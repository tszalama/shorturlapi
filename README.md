# ShortUrlApi
ShortUrlApi is a Spring Boot based API designed for shortening long URLs.

## How to Run
Follow the steps below to run the API:

1. Clone the repository.
2. Install Docker Desktop. (used for running java app and mongodb)
3. Run `docker-compose up -d` in project root.

## Example Requests

Here are some example requests you can make:

1. **Create Short URL**

   Send a POST request to `localhost:9090` with the following JSON body:

   ```json
   {
   "url":"https://github.com/tszalama/shorturlapi/tree/main"
   }
   ```

2. **Use Short URL**

   Send a GET request to `localhost:9090/oDj35mzX` (replace oDj35mzX with the id you got from the first request).
