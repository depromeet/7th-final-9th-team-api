#!/usr/bin/env bash

# 초단기실황조회.
# 초단기실황정보를 조회하기 위해 
# 예보일자, 예보시간, 예보지점 X좌표, 예보지점 Y좌표의 조회 조건으로 
# 자료구분코드, 실황 값, 예보일자, 예보시간, 예보지점X좌표, 예보지점Y좌표의 정보를 조회하는 기능


# validate
if [ "$#" -lt "5" ]; then
  echo "Usage: announce_ultrashort.sh {SERVICE_KEY} {BASE_DATE} {BASE_TIME} {NX} {NY} {PAGE=1} {SIZE=10}" 
  exit 1
fi


# resolve parameters
SERVICE_KEY=$1
BASE_DATE=$2
BASE_TIME=$3
NX=$4
NY=$5
PAGE=1
if [ -n "$6" ]; then
  PAGE=$6
fi
SIZE=10
if [ -n "$7" ]; then
  SIZE=$7
fi


# print 
echo "SERVICE_KEY: ${SERVICE_KEY}"
echo "BASE_DATE: ${BASE_DATE}"
echo "BASE_TIME: ${BASE_TIME}"
echo "NX: ${NX}"
echo "NY: ${NY}"
echo "PAGE: ${PAGE}"
echo "SIZE: ${SIZE}"


# config
REQUEST_URL=http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib


# execute
curl -s "${REQUEST_URL}?ServiceKey=${SERVICE_KEY}&base_date=${BASE_DATE}&base_time=${BASE_TIME}&nx=${NX}&ny=${NY}&numOfRows=${SIZE}&pageNo=${PAGE}&_type=json" | jq

