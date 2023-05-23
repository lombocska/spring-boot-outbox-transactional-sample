#!/bin/bash

docker-compose -f compose.yml -f apps.yml up --build --remove-orphans