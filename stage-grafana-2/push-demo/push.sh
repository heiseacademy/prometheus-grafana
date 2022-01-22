#!/bin/bash

echo "push_demo_start_date $(date +%s)" | \
curl --data-binary @- http://localhost:9091/metrics/job/demo/instance/3270

sleep 1
# simulation - zufallszahl als metrik ergebnis
random_result=$(( 1 + $RANDOM % 10000 ))

cat <<EOF | curl --data-binary @- http://localhost:9091/metrics/job/demo/instance/3270
# TYPE push_demo_value gauge
push_demo_value $random_result
EOF

last_success_date=$(date +%s)
echo "push_demo_last_success_date $last_success_date" | \
curl --data-binary @- http://localhost:9091/metrics/job/demo/instance/3270
