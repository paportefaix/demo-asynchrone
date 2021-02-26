# Appel asynchrone

## Benchmark

Bench fait en ligne de comande à l'aide de la commande suviante au deuxième lancement afin de faire chauffer la JVM:
ab -c 100 -n 10000 http://localhost:8081/projects-async

Server Software:        
Server Hostname:        localhost
Server Port:            8081

Document Path:          /projects-async
Document Length:        128 bytes

Concurrency Level:      100
Time taken for tests:   5.035 seconds
Complete requests:      10000
Failed requests:        378
(Connect: 0, Receive: 0, Length: 378, Exceptions: 0)
Non-2xx responses:      9622
Total transferred:      2982050 bytes
HTML transferred:       1932050 bytes
Requests per second:    1986.00 [#/sec] (mean)
Time per request:       50.352 [ms] (mean)
Time per request:       0.504 [ms] (mean, across all concurrent requests)
Transfer rate:          578.36 [Kbytes/sec] received

Connection Times (ms)
min  mean[+/-sd] median   max
Connect:        0    1   0.8      0      15
Processing:     1   45 159.7     11    1057
Waiting:        0   44 159.6     11    1056
Total:          1   45 159.7     12    1057
WARNING: The median and mean for the initial connection time are not within a normal deviation
These results are probably not that reliable.

Percentage of the requests served within a certain time (ms)
50%     12
66%     16
75%     19
80%     21
90%     30
95%     68
98%    914
99%    934
100%   1057 (longest request)
