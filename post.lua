-- example HTTP POST script which demonstrates setting the
-- HTTP method, body, and adding a header
-- Example cmd:   wrk2 -c 150 -t1 -s post.lua -d 5s --rate 1 "http://localhost:8080/rabbitdemo/api/users"

wrk.method = "POST"
wrk.body   = '{"name": "teste","login": "teste","password": "teste"}'
wrk.headers["Content-Type"] = "application/json"
