#!/usr/bin/env bash

# 시도별 실시간 측정정보 조회
#
# 시도명을 검색조건으로 하여
# 시도별 측정소목록에 대한 일반 항목과
# CAI 최종 실시간 측정값과
# 지수 정보 조회 기능을 제공하는
# 시도별 실시간 측정정보 조회
#
# 시도 이름 (서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종)


# validate
if [ "$#" -lt "2" ]; then
  echo "Usage: find_pm_by_state_name.sh {SERVICE_KEY} {STATE_NAME} {PAGE=1} {SIZE=10} {VERSION=1.3}"
  echo "       STATE_NAME: 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종"
  echo "       시도 이름은 utf-8 charset 을 url encoding 한 값으로 입력해야 합니다"
  echo "       (ex, 서울: %EC%84%9C%EC%9A%B8, 경기: %EA%B2%BD%EA%B8%B0, 인천: %EC%9D%B8%EC%B2%9C, 부산: %EB%B6%80%EC%82%B0)"
  exit 1
fi


# resolve parameters
SERVICE_KEY=$1
STATE_NAME=$2
PAGE=1
if [ -n "$3" ]; then
  PAGE=$3
fi
SIZE=10
if [ -n "$4" ]; then
  SIZE=$4
fi
VERSION=1.3
if [ -n "$5" ]; then
  VERSION=$5
fi


# print
echo "SERVICE_KEY: ${SERVICE_KEY}"
echo "STATE_NAME: ${STATE_NAME}"
echo "PAGE: ${PAGE}"
echo "SIZE: ${SIZE}"
echo "VERSION: ${VERSION}"


# config
REQUEST_URL=http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty


# execute
curl -s "${REQUEST_URL}?ServiceKey=${SERVICE_KEY}&sidoName=${STATE_NAME}&numOfRows=${SIZE}&pageNo=${PAGE}&ver=${VERSION}&_returnType=json"
