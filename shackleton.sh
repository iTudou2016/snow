#!/bin/bash
screen -dmS shackleton sudo source/bazel-bin/ShackletonExplorer configs/explorer.conf
sleep 1s
screen -r shackleton
