#!/bin/bash

docker-compose -f postgresql.yml -f kafka.yml -f apps.yml up --build --remove-orphans