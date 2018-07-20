#!/bin/bash
screen -dmS node sudo source/bazel-bin/SnowBlossomNode configs/node.conf
sleep 1s
screen -r node
