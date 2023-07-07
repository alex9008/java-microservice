# java-microservice


## Branch: Idempotency
In this branch, you can see how we can resolve the idempotency key using the Distribute Lock pattern and Redis

* Run Redis in docker
``docker run -d -p 6379:6379 --name myredis redis``

* Endpoint: http://localhost:8090/check_idempotency
* Request:
  {
    "key": "123"
  }
