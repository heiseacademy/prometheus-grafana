#!/bin/bash

docker run -it --rm alexeiled/stress-ng --cpu 2 --io 2 --vm 1 --vm-bytes 1G --timeout 300s --metrics-brief
