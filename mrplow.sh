#!/bin/bash
screen -dmS mrplow sudo source/bazel-bin/MrPlow configs/pool.conf
sleep 1s
screen -r mrplow
