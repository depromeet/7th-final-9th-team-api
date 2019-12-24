#!/usr/bin/env bash

# 측정소정보 조회 서비스
#
# TM 좌표를 이용하여
# 좌표 주변 측정소 정보와 측정정소와
# 좌표 간의 거리 정보를 제공하는 서비스


# validate
if [ "$#" -lt "3" ]; then
  echo "Usage: find_pm_by_state_name.sh {SERVICE_KEY} {TM_X} {TM_Y} {PAGE=1} {SIZE=10} {VERSION=1.3}"
  exit 1
fi


# resolve parameters
SERVICE_KEY=$1
TM_X=$2
TM_Y=$3
PAGE=1
if [ -n "$4" ]; then
  PAGE=$4
fi
SIZE=10
if [ -n "$5" ]; then
  SIZE=$5
fi
VERSION=1.0
if [ -n "$6" ]; then
  VERSION=$6
fi


# print
echo "SERVICE_KEY: ${SERVICE_KEY}"
echo "TM_X: ${TM_X}"
echo "TM_Y: ${TM_Y}"
echo "PAGE: ${PAGE}"
echo "SIZE: ${SIZE}"
echo "VERSION: ${VERSION}"


# config
REQUEST_URL=http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList


# execute
curl -v -s "${REQUEST_URL}?ServiceKey=${SERVICE_KEY}&tmX=${TM_X}&tmY=${TM_Y}&numOfRows=${SIZE}&pageNo=${PAGE}&_returnType=json"
