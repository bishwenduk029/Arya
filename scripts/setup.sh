#!/bin/bash

CWD=$PWD

echo "===========> Clean up"
./gradlew clean
rm rasa/data/*.yaml
rm rasa/models/*

## Build the bot and compile QnA for RASA
echo "===========> Building the Arya bot"
./gradlew build

## Train the RASA model with QnA from bot
cd rasa
echo "===========> Now training RASA model with the skills in $PWD"
rasa train nlu
